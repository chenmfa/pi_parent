package nbcenter.test.service;

import com.alibaba.fastjson.JSON;
import com.pi.nbcenter.device.bean.dto.nb.iotservice.SignalInfo;

public class Signaltest {
  public static void main(String[] args) {
    String data = "{\"signalStrength\":-864,\"linkQuality\":-109,\"signalEcl\":0,\"cellId\":79833427,\"signalPci\":191,\"signalSnr\":109,\"txPower\":10,\"IMSI\":\"460111173644156\u0000\u0000\u0000\",\"mode\":1}";
    SignalInfo info = JSON.parseObject(data, SignalInfo.class);
    System.out.println(JSON.toJSONString(info));
  }
}
