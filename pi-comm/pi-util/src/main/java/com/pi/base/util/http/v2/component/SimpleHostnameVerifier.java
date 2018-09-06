package com.pi.base.util.http.v2.component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

import org.apache.http.conn.util.DomainType;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.conn.util.PublicSuffixMatcher;

@SuppressWarnings("unused")
public class SimpleHostnameVerifier implements HostnameVerifier{
  enum TYPE { IPv4, IPv6, DNS };
  final static int DNS_NAME_TYPE        = 2;
  final static int IP_ADDRESS_TYPE      = 7;
  
  private final PublicSuffixMatcher publicSuffixMatcher;

  public SimpleHostnameVerifier(final PublicSuffixMatcher publicSuffixMatcher) {
      this.publicSuffixMatcher = publicSuffixMatcher;
  }

  public SimpleHostnameVerifier() {
      this(null);
  }
  
  @Override
  public boolean verify(String hostname, SSLSession session) {
    try {
      final Certificate[] certs = session.getPeerCertificates();
      final X509Certificate x509 = (X509Certificate) certs[0];
      verify(hostname, x509);
      return true;
    } catch (final SSLException ex) {
      return false;
    }
  }
  private void verify(String hostname, X509Certificate cert) throws SSLException {
    TYPE hostFormat = TYPE.DNS;
    if (InetAddressUtils.isIPv4Address(hostname)) {
        hostFormat = TYPE.IPv4;
    } else {
        String s = hostname;
        if (s.startsWith("[") && s.endsWith("]")) {
            s = hostname.substring(1, hostname.length() - 1);
        }
        if (InetAddressUtils.isIPv6Address(s)) {
            hostFormat = TYPE.IPv6;
        }
    }
    final int subjectType = hostFormat == TYPE.IPv4 || hostFormat == TYPE.IPv6 ? IP_ADDRESS_TYPE : DNS_NAME_TYPE;
    final List<String> subjectAlts = extractSubjectAlts(cert, subjectType);
    if (subjectAlts != null && !subjectAlts.isEmpty()) {
        switch (hostFormat) {
            case IPv4:
                matchIPAddress(hostname, subjectAlts);
                break;
            case IPv6:
                matchIPv6Address(hostname, subjectAlts);
                break;
            default:
                matchDNSName(hostname, subjectAlts, this.publicSuffixMatcher);
        }
    } else {
        // CN matching has been deprecated by rfc2818 and can be used
        // as fallback only when no subjectAlts are available
        final X500Principal subjectPrincipal = cert.getSubjectX500Principal();
        final String cn = extractCN(subjectPrincipal.getName(X500Principal.RFC2253));
        if (cn == null) {
            throw new SSLException("Certificate subject for <" + hostname + "> doesn't contain " +
                    "a common name and does not have alternative names");
        }
        matchCN(hostname, cn, this.publicSuffixMatcher);
    }
  }
  private static void matchIPAddress(final String host, final List<String> subjectAlts) throws SSLException {
    for (int i = 0; i < subjectAlts.size(); i++) {
        final String subjectAlt = subjectAlts.get(i);
        if (host.equals(subjectAlt)) {
            return;
        }
    }
    throw new SSLException("Certificate for <" + host + "> doesn't match any " +
            "of the subject alternative names: " + subjectAlts);
  }
  private static void matchIPv6Address(final String host, final List<String> subjectAlts) throws SSLException {
    final String normalisedHost = normaliseAddress(host);
    for (int i = 0; i < subjectAlts.size(); i++) {
        final String subjectAlt = subjectAlts.get(i);
        final String normalizedSubjectAlt = normaliseAddress(subjectAlt);
        if (normalisedHost.equals(normalizedSubjectAlt)) {
            return;
        }
    }
    throw new SSLException("Certificate for <" + host + "> doesn't match any " +
            "of the subject alternative names: " + subjectAlts);
  }
  /**
   * @description 将ipv6和dns地址转换成标准地址
   * @param hostname
   * @return
   */
  private static String normaliseAddress(final String hostname) {
    if (hostname == null) {
        return hostname;
    }
    try {
        final InetAddress inetAddress = InetAddress.getByName(hostname);
        return inetAddress.getHostAddress();
    } catch (final UnknownHostException unexpected) {
      //当校验ipv6地址的时候, 默认返回ipv6地址
        return hostname;
    }
  }
  
  private static void matchDNSName(final String host, final List<String> subjectAlts,
      final PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
    final String normalizedHost = host.toLowerCase(Locale.ROOT);
    for (int i = 0; i < subjectAlts.size(); i++) {
      final String subjectAlt = subjectAlts.get(i);
      final String normalizedSubjectAlt = subjectAlt.toLowerCase(Locale.ROOT);
      if (matchIdentityStrict(normalizedHost, normalizedSubjectAlt, publicSuffixMatcher)){
        return;
      }
    }
    throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
  }
  private static void matchCN(final String host, final String cn,
      final PublicSuffixMatcher publicSuffixMatcher) throws SSLException {
    if (!matchIdentityStrict(host, cn, publicSuffixMatcher)) {
      throw new SSLException("Certificate for <" + host + "> doesn't match " +
         "common name of the certificate subject: " + cn);
    }
  }  
  private static boolean matchDomainRoot(final String host, final String domainRoot) {
    if (domainRoot == null) {
        return false;
    }
    return host.endsWith(domainRoot) && (host.length() == domainRoot.length()
            || host.charAt(host.length() - domainRoot.length() - 1) == '.');
  }
  
  private static boolean matchIdentity(final String host, final String identity,
      final PublicSuffixMatcher publicSuffixMatcher,
      final boolean strict) {
    if (publicSuffixMatcher != null && host.contains(".")) {
      if (!matchDomainRoot(host, publicSuffixMatcher.getDomainRoot(identity, DomainType.ICANN))) {
        return false;
      }
    }

    // RFC 2818, 3.1. Server Identity
    // "...Names may contain the wildcard
    // character * which is considered to match any single domain name
    // component or component fragment..."
    // Based on this statement presuming only singular wildcard is legal
    final int asteriskIdx = identity.indexOf('*');
    if (asteriskIdx != -1) {
      final String prefix = identity.substring(0, asteriskIdx);
      final String suffix = identity.substring(asteriskIdx + 1);
      if (!prefix.isEmpty() && !host.startsWith(prefix)) {
        return false;
      }
      if (!suffix.isEmpty() && !host.endsWith(suffix)) {
        return false;
      }
      // Additional sanity checks on content selected by wildcard can be done here
      if (strict) {
        final String remainder = host.substring(
            prefix.length(), host.length() - suffix.length());
        if (remainder.contains(".")) {
          return false;
        }
      }
      return true;
    }
    return host.equalsIgnoreCase(identity) || host.contains(identity);
  }

  private static boolean matchIdentity(
      final String host, final String identity, final PublicSuffixMatcher publicSuffixMatcher) {
   return matchIdentity(host, identity, publicSuffixMatcher, false);
  }

  private static boolean matchIdentity(final String host, final String identity) {
    return matchIdentity(host, identity, null, false);
  }

  private static boolean matchIdentityStrict(final String host, final String identity,
      final PublicSuffixMatcher publicSuffixMatcher) {
    return matchIdentity(host, identity, publicSuffixMatcher, true);
  }

  private static boolean matchIdentityStrict(final String host, final String identity) {
    return matchIdentity(host, identity, null, true);
  }

  private static String extractCN(final String subjectPrincipal) throws SSLException {
    if (subjectPrincipal == null) {
      return null;
    }
    try {
      final LdapName subjectDN = new LdapName(subjectPrincipal);
      final List<Rdn> rdns = subjectDN.getRdns();
      for (int i = rdns.size() - 1; i >= 0; i--) {
        final Rdn rds = rdns.get(i);
        final Attributes attributes = rds.toAttributes();
        final Attribute cn = attributes.get("cn");
        if (cn != null) {
          try {
            final Object value = cn.get();
            if (value != null) {
              return value.toString();
            }
          } catch (final NoSuchElementException ignore) {
          } catch (final NamingException ignore) {
          }
        }
      }
      return null;
    } catch (final InvalidNameException e) {
      throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
    }
  }

  private static List<String> extractSubjectAlts(final X509Certificate cert, final int subjectType) {
    Collection<List<?>> c = null;
    try {
      c = cert.getSubjectAlternativeNames();
    } catch(final CertificateParsingException ignore) {
    }
    List<String> subjectAltList = null;
    if (c != null) {
      for (final List<?> aC : c) {
        final List<?> list = aC;
        final int type = ((Integer) list.get(0)).intValue();
        if (type == subjectType) {
          final String s = (String) list.get(1);
          if (subjectAltList == null) {
          subjectAltList = new ArrayList<String>();
          }
          subjectAltList.add(s);
        }
      }
    }
    return subjectAltList;
  }  

//  @Override
//  public boolean verify(String hostname, SSLSession session) {
//    Certificate[] peerCertificates = null;
//    try {
//      peerCertificates = session.getPeerCertificates();
//    } catch (SSLPeerUnverifiedException e) {
//      e.printStackTrace();
//    }
//    if(null != peerCertificates && peerCertificates.length > 0){
//      for(Certificate certificate :peerCertificates){
//        if(certificate instanceof X509Certificate){
//          X509Certificate cert = (X509Certificate)certificate;
//          String subjectDN = cert.getSubjectDN().getName();
//          if(null != subjectDN){
//            String[] detailDN = subjectDN.split("[,]");
//            for(String dn :detailDN){
//              String[] keyValuePair = dn.split("=");
//              if(null != keyValuePair 
//                  && keyValuePair.length>1 
//                  && "CN".equalsIgnoreCase(keyValuePair[0])
//                  && hostname.contains(keyValuePair[1])){
//                return true;
//              }
//            }
//          }
//        }
//      }
//    }
//    return false;
//  }

}
