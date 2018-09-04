
CREATE TABLE `base_platform_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '最近一次修改时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  `platform_name` varchar(100) NOT NULL COMMENT '平台名称',
  `platform_alias` varchar(50) NOT NULL COMMENT '平台别称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '平台信息表' ROW_FORMAT = Compact;

CREATE TABLE `base_partner_platform`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `version` int(11) NOT NULL,
  `partner_code` bigint(20) NOT NULL COMMENT '合作商/app来源',
  `platform_id` int(11) NOT NULL,
  `platform_version` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '合作商平台映射表' ROW_FORMAT = Compact;


CREATE TABLE `iot_platform_dev_entry`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_date` datetime NOT NULL COMMENT '修改日期',
  `version` int(11) NOT NULL COMMENT '流水版本',
  `platform_dev_id` bigint(20) NOT NULL COMMENT '平台设备id',
  `platform_dev_entry` varchar(2048) NOT NULL COMMENT '操作流水',
  `user_id` bigint(20) NOT NULL COMMENT '平台用户编号',
  `remark` varchar(255)  NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '平台设备日志表' ROW_FORMAT = Compact;

CREATE TABLE `base_platform_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `version` int(11) NOT NULL DEFAULT 200 COMMENT '版本',
  `platform_id` int(11) NOT NULL COMMENT '平台流水号',
  `config_key` varchar(50) NOT NULL COMMENT '平台配置编号',
  `config_value` varchar(255) NOT NULL COMMENT '平台配置值',
  `config_version` int(11) NOT NULL DEFAULT 200 COMMENT '配置版本(一般不可变)',
  `config_state` int(11) NOT NULL DEFAULT 1 COMMENT '配置状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '平台配置信息表' ROW_FORMAT = Compact;

ALTER TABLE `iot_device_info` 
ADD COLUMN `iot_protocol_version` int(0) NOT NULL DEFAULT 200 COMMENT '平台协议版本' AFTER `partner_code`;

ALTER TABLE base_partner_info
ADD COLUMN `partner_alias` varchar(50) NOT NULL COMMENT '合作商别名' AFTER `partner_name`;


truncate TABLE base_partner_info;
INSERT INTO `base_partner_info`(`id`, `partner_code`, `partner_name`, `partner_alias`) VALUES (1, 1, 'NB模块/杭电', 'HDU');
INSERT INTO `base_partner_info`(`id`, `partner_code`, `partner_name`, `partner_alias`) VALUES (2, 2, '蜗牛', 'SNAIL');
INSERT INTO `base_partner_info`(`id`, `partner_code`, `partner_name`, `partner_alias`) VALUES (3, 3, '金华电信', 'JINHUA_TELECOM');



INSERT INTO `base_partner_platform`(`id`, `create_date`, `update_date`, `version`, `partner_code`, `platform_id`, `platform_version`) VALUES (1, '2018-06-23 16:53:55', '2018-06-23 16:53:55', 200, 1, 1, 200);
INSERT INTO `base_partner_platform`(`id`, `create_date`, `update_date`, `version`, `partner_code`, `platform_id`, `platform_version`) VALUES (2, '2018-06-23 16:53:55', '2018-06-23 16:53:55', 200, 1, 1, 201);
INSERT INTO `base_partner_platform`(`id`, `create_date`, `update_date`, `version`, `partner_code`, `platform_id`, `platform_version`) VALUES (3, '2018-06-23 16:53:55', '2018-06-23 16:53:55', 200, 2, 1, 200);
INSERT INTO `base_partner_platform`(`id`, `create_date`, `update_date`, `version`, `partner_code`, `platform_id`, `platform_version`) VALUES (4, '2018-06-23 16:53:55', '2018-06-23 16:53:55', 200, 2, 1, 201);
INSERT INTO `base_partner_platform`(`id`, `create_date`, `update_date`, `version`, `partner_code`, `platform_id`, `platform_version`) VALUES (5, '2018-06-23 16:53:55', '2018-06-23 16:53:55', 200, 3, 1, 203);



INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (1, '2018-06-23 15:03:12', '2018-06-23 15:03:15', 200, 1, 'appId', 'MSFPRiVvRpW9Li7OV0UJehG7AIUa', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (2, '2018-06-23 15:03:58', '2018-06-23 15:04:00', 200, 1, 'appSecret', 'JdMW2WfIfuvz4PqkC1g8tjAqYcga', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (3, '2018-06-23 15:05:52', '2018-06-23 15:05:50', 200, 1, 'manufacturerId', 'Paiai', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (4, '2018-06-23 15:06:02', '2018-06-23 15:06:00', 200, 1, 'manufacturerName', 'Paiai', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (5, '2018-06-23 15:06:24', '2018-06-23 15:06:22', 200, 1, 'deviceType', 'DoorLock', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (6, '2018-06-23 15:06:45', '2018-06-23 15:06:43', 200, 1, 'model', 'NBIoTDLock', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (7, '2018-06-23 15:11:08', '2018-06-23 15:11:05', 200, 1, 'protocolType', 'CoAP', 200, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (8, '2018-06-23 15:03:12', '2018-06-23 15:03:15', 200, 1, 'appId', 'aGZa0RgUs7f0RZea2BzA7TkWLzAa', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (9, '2018-06-23 15:03:58', '2018-06-23 15:04:00', 200, 1, 'appSecret', 'no_bMvHqtkxEndF2JfYuRvIB3z4a', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (10, '2018-06-23 15:05:52', '2018-06-23 15:05:50', 200, 1, 'manufacturerId', 'Paiai', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (11, '2018-06-23 15:06:02', '2018-06-23 15:06:00', 200, 1, 'manufacturerName', 'Paiai', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (12, '2018-06-23 15:06:24', '2018-06-23 15:06:22', 200, 1, 'deviceType', 'DoorLock', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (13, '2018-06-23 15:06:45', '2018-06-23 15:06:43', 200, 1, 'model', 'LA0101', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (14, '2018-06-23 15:11:08', '2018-06-23 15:11:05', 200, 1, 'protocolType', 'CoAP', 201, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (15, '2018-06-23 15:03:12', '2018-06-23 15:03:15', 200, 1, 'appId', 'yEADskbTvrU0WjI1B1xIqQ9oVLAa', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (16, '2018-06-23 15:03:58', '2018-06-23 15:04:00', 200, 1, 'appSecret', '7FbZDkKhbGQt3rWdRaJ7tuKzmHsa', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (17, '2018-06-23 15:05:52', '2018-06-23 15:05:50', 200, 1, 'manufacturerId', 'Paiai', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (18, '2018-06-23 15:06:02', '2018-06-23 15:06:00', 200, 1, 'manufacturerName', 'Paiai', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (19, '2018-06-23 15:06:24', '2018-06-23 15:06:22', 200, 1, 'deviceType', 'DoorLock', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (20, '2018-06-23 15:06:45', '2018-06-23 15:06:43', 200, 1, 'model', 'LA0101', 202, 1);
INSERT INTO `base_platform_config`(`id`, `create_date`, `update_date`, `version`, `platform_id`, `config_key`, `config_value`, `config_version`, `config_state`) VALUES (21, '2018-06-23 15:11:08', '2018-06-23 15:11:05', 200, 1, 'protocolType', 'CoAP', 202, 1);



INSERT INTO `base_platform_info`(`id`, `create_date`, `update_date`, `version`, `platform_name`, `platform_alias`) VALUES (1, '2018-06-23 12:19:39', '2018-06-23 12:19:42', 200, '华为IOT平台', '');
INSERT INTO `base_platform_info`(`id`, `create_date`, `update_date`, `version`, `platform_name`, `platform_alias`) VALUES (2, '2018-06-23 12:20:46', '2018-06-23 12:20:49', 200, '白色家电', '');
INSERT INTO `base_platform_info`(`id`, `create_date`, `update_date`, `version`, `platform_name`, `platform_alias`) VALUES (3, '2018-06-23 12:21:04', '2018-06-23 12:21:06', 200, '天翼云社区', '');
