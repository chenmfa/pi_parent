package nbcenter.test;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import com.pi.base.util.time.DateTools;

public class TimeTest {
  public static void main(String[] args) throws ParseException {
    String dateTime = "20180813T160342Z";
    Date d = DateTools.parserDate(dateTime, "yyyyMMdd'T'HHmmss'Z'", TimeZone.getTimeZone("GMT+8:00"));
    Date d2 = DateTools.parserDate(dateTime, "yyyyMMdd'T'HHmmss'Z'", TimeZone.getTimeZone("GMT+0:00"));
    Date d3 = DateTools.parserDate(dateTime, "yyyyMMdd'T'HHmmss'Z'");
    System.out.println(DateTools.formatDate(d, "yyyy-MM-dd HH:mm:ss"));
    System.out.println(DateTools.formatDate(d2, "yyyy-MM-dd HH:mm:ss"));
    System.out.println(DateTools.formatDate(d3, "yyyy-MM-dd HH:mm:ss"));
  }
}
