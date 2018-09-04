package com.pi.nbcenter.device.bean.dto.nb;

import java.util.List;

/**
 * @description 设备详情
 * @author chenmfa
 */
public class IotDeviceInfoDTO {
  private String deviceId;
  private String gatewayId;
  private String nodeType;
  private String createTime;
  private String lastModifiedTime;
  private DeviceInfo deviceInfo;
  List<DeviceService> services;
  private String connectionInfo;
  private String location;
  private List<String> devGroupIds;
  public String getDeviceId() {
    return deviceId;
  }
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  public String getGatewayId() {
    return gatewayId;
  }
  public void setGatewayId(String gatewayId) {
    this.gatewayId = gatewayId;
  }
  public String getNodeType() {
    return nodeType;
  }
  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }
  public String getCreateTime() {
    return createTime;
  }
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  public String getLastModifiedTime() {
    return lastModifiedTime;
  }
  public void setLastModifiedTime(String lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }
  public DeviceInfo getDeviceInfo() {
    return deviceInfo;
  }
  public void setDeviceInfo(DeviceInfo deviceInfo) {
    this.deviceInfo = deviceInfo;
  }
  public List<DeviceService> getServices() {
    return services;
  }
  public void setServices(List<DeviceService> services) {
    this.services = services;
  }
  public String getConnectionInfo() {
    return connectionInfo;
  }
  public void setConnectionInfo(String connectionInfo) {
    this.connectionInfo = connectionInfo;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public List<String> getDevGroupIds() {
    return devGroupIds;
  }
  public void setDevGroupIds(List<String> devGroupIds) {
    this.devGroupIds = devGroupIds;
  }
}
