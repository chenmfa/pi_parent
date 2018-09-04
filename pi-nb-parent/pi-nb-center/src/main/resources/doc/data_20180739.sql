ALTER TABLE `iot_device_session` 
ADD COLUMN `create_date` datetime NOT NULL COMMENT '记录创建时间' AFTER `iot_dev_seckey`,
ADD COLUMN `update_date` datetime NOT NULL COMMENT '最近更新时间' AFTER `create_date`,
ADD COLUMN `iot_dev_battery_percent` int(11) NOT NULL DEFAULT 100 COMMENT '电池电量百分比';

ALTER TABLE `iot_device_info` 
ADD COLUMN `iot_dev_imsi` varchar(50) NOT NULL DEFAULT '' COMMENT '设备的imsi号' AFTER `iot_protocol_version`,
ADD COLUMN `update_date` datetime NOT NULL COMMENT '最近的更新时间' AFTER `create_date`,
ADD COLUMN `version` int(0) NOT NULL DEFAULT 200 COMMENT '修改次数' AFTER `update_date`;

DROP TABLE if EXISTS iot_device_notification_log;
CREATE TABLE iot_device_notification_log  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '修改日期',
  `version` int(11) NOT NULL COMMENT '流水版本',
  `platform_dev_id` bigint(20) NOT NULL COMMENT '平台设备id',
  `platform_dev_entry` varchar(2048) NOT NULL COMMENT '操作流水',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_PLATFORM_DEV_ID`(`platform_dev_id`) USING BTREE COMMENT '设备id索引'
) ENGINE = InnoDB COMMENT = '平台设备通知原始数据表' PARTITION BY KEY (`id`)
PARTITIONS 10;

DROP TABLE if EXISTS base_partner_subscription; 
CREATE TABLE base_partner_subscription(
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `version` int(11) NOT NULL DEFAULT 200,
  `partner_code` bigint(20) NOT NULL COMMENT '来源id',
  `notify_type` int(11) NOT NULL COMMENT '订阅通知类型 101. HTTP-POST（Application/json） 102. TCP 103.UDP',
  `notify_url` varchar(255) NOT NULL COMMENT '通知地址',
  `notify_remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `state` int(11) NOT NULL COMMENT '状态 10 正常 20. 已取消',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '合作商消息订阅表';