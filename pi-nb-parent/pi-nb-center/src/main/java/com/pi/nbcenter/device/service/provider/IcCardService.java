package com.pi.nbcenter.device.service.provider;

public interface IcCardService {
  /**
   * @description 注册ic卡
   * @param deviceId 设备编号
   * @param userId 用户编号
   * @param icCard id卡号
   * @return 存入之后的设备id
   */
  public Long sendIcCard(String imei, Long userId, String icCard, Long sourceId)
      throws Exception;
  /**
   * @description 注册ic卡
   * @param deviceId 设备编号
   * @param userId 用户编号
   * @param icCard id平台编号
   */
  public void deleteIcCard(String imei, Long userId, String icCard, Long sourceId)
      throws Exception;
}
