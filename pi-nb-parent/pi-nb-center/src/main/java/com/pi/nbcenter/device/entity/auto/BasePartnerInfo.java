package com.pi.nbcenter.device.entity.auto;

public class BasePartnerInfo {
    private Integer id;

    private Long partnerCode;

    private String partnerName;

    private String partnerAlias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Long partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName == null ? null : partnerName.trim();
    }

    public String getPartnerAlias() {
        return partnerAlias;
    }

    public void setPartnerAlias(String partnerAlias) {
        this.partnerAlias = partnerAlias == null ? null : partnerAlias.trim();
    }
}