/*
Navicat MySQL Data Transfer

Source Server         : test.oksheng.com.cn
Source Server Version : 50635
Source Host           : test.oksheng.com.cn:3306
Source Database       : jydp

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-03-28 21:13:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for backer_handle_user_balance_freeze_money_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_handle_user_balance_freeze_money_tab`;
CREATE TABLE `backer_handle_user_balance_freeze_money_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `typeHandle` tinyint(1) NOT NULL COMMENT '操作类型:1：增加，2：减少',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `userBalance` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '单位(个)',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `addTime` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8 COMMENT='后台管理员增减用户冻结币记录';

-- ----------------------------
-- Table structure for backer_handle_user_balance_freeze_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_handle_user_balance_freeze_tab`;
CREATE TABLE `backer_handle_user_balance_freeze_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `typeHandle` tinyint(1) NOT NULL COMMENT '操作类型:1：增加，2：减少',
  `userBalance` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '单位(美刀$)',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `addTime` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8 COMMENT='后台管理员增减用户冻结余额记录';

-- ----------------------------
-- Table structure for backer_handle_user_balance_money_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_handle_user_balance_money_tab`;
CREATE TABLE `backer_handle_user_balance_money_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `typeHandle` tinyint(1) NOT NULL COMMENT '操作类型:1：增加，2：减少',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `userBalance` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '单位(个)',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `addTime` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=447 DEFAULT CHARSET=utf8 COMMENT='后台管理员增减用户可用币记录';

-- ----------------------------
-- Table structure for backer_handle_user_balance_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_handle_user_balance_tab`;
CREATE TABLE `backer_handle_user_balance_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `typeHandle` tinyint(1) NOT NULL COMMENT '操作类型:1：增加，2：减少',
  `userBalance` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '单位(美刀$)',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `addTime` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8 COMMENT='后台管理员增减用户余额记录';

-- ----------------------------
-- Table structure for backer_role_power_map_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_role_power_map_tab`;
CREATE TABLE `backer_role_power_map_tab` (
  `powerId` int(11) NOT NULL COMMENT '权限Id',
  `powerName` varchar(30) NOT NULL COMMENT '权限名称',
  `powerLevel` tinyint(1) NOT NULL COMMENT '权限等级，从1级开始',
  `uperPowerId` int(11) NOT NULL COMMENT '上级权限Id，没有上级填0',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`powerId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员角色权限映射';

-- ----------------------------
-- Table structure for backer_role_power_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_role_power_tab`;
CREATE TABLE `backer_role_power_tab` (
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `powerJson` longtext NOT NULL COMMENT '权限信息，Json格式字符串',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员账户角色权限';

-- ----------------------------
-- Table structure for backer_role_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_role_tab`;
CREATE TABLE `backer_role_tab` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `roleName` varchar(20) NOT NULL COMMENT '角色名称',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='后台管理员账户角色';

-- ----------------------------
-- Table structure for backer_session_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_session_tab`;
CREATE TABLE `backer_session_tab` (
  `sessionId` varchar(20) NOT NULL COMMENT 'sessionId，业务类型（2）+日期（6）+随机位（12）',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员账号',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `loginTime` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`sessionId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员登录记录';

-- ----------------------------
-- Table structure for backer_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_tab`;
CREATE TABLE `backer_tab` (
  `backerId` int(11) NOT NULL AUTO_INCREMENT COMMENT '后台管理员Id',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话号码',
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `accountStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '帐号状态，1：启用，2：禁用，-1：删除',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`backerId`) USING BTREE,
  UNIQUE KEY `backerAccount` (`backerAccount`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='后台管理员';

-- ----------------------------
-- Table structure for jydp_coin_config_tab
-- ----------------------------
DROP TABLE IF EXISTS `jydp_coin_config_tab`;
CREATE TABLE `jydp_coin_config_tab` (
  `recordNo` varchar(15) NOT NULL COMMENT '币种转出管理记录号',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(10) NOT NULL COMMENT '币种名称',
  `freeCurrencyNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '免审数量',
  `minCurrencyNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低数量',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `backerId` int(11) NOT NULL COMMENT '后台管理员Id\r',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `configStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1:正常, 2:已删除',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`recordNo`),
  UNIQUE KEY `recordNo` (`recordNo`) USING BTREE,
  KEY `currencyId` (`currencyId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JYDP币种转出管理';

-- ----------------------------
-- Table structure for jydp_user_coin_out_record_tab
-- ----------------------------
DROP TABLE IF EXISTS `jydp_user_coin_out_record_tab`;
CREATE TABLE `jydp_user_coin_out_record_tab` (
  `coinRecordNo` varchar(15) NOT NULL COMMENT '币种转出记录号',
  `sylRecordNo` varchar(20) DEFAULT NULL COMMENT '电子钱包操作记录号',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户帐号',
  `walletAccount` varchar(16) NOT NULL COMMENT '电子钱包帐号',
  `currencyName` varchar(10) NOT NULL COMMENT '币种名称',
  `currencyNumber` decimal(18,8) NOT NULL COMMENT '币种数量',
  `handleStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '审核状态 1：待审核，2：审核通过，3：审核拒绝 ，4：已撤回',
  `handleTime` datetime DEFAULT NULL COMMENT '审核时间',
  `outStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '推送状态，1：未推送，2：返回成功，3：返回失败',
  `finishTime` datetime DEFAULT NULL COMMENT '完成时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`coinRecordNo`),
  UNIQUE KEY `coinRecordNo` (`coinRecordNo`) USING BTREE,
  KEY `currencyId` (`currencyId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='JYDP用户币种转出记录';

-- ----------------------------
-- Table structure for k_line_graph_fifteen_minutes_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_fifteen_minutes_tab`;
CREATE TABLE `k_line_graph_fifteen_minutes_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(十五分钟节点)';

-- ----------------------------
-- Table structure for k_line_graph_five_minutes_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_five_minutes_tab`;
CREATE TABLE `k_line_graph_five_minutes_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(五分钟节点)';

-- ----------------------------
-- Table structure for k_line_graph_four_hours_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_four_hours_tab`;
CREATE TABLE `k_line_graph_four_hours_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(四小时节点)';

-- ----------------------------
-- Table structure for k_line_graph_halfhour_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_halfhour_tab`;
CREATE TABLE `k_line_graph_halfhour_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(三十分钟节点)';

-- ----------------------------
-- Table structure for k_line_graph_one_day_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_one_day_tab`;
CREATE TABLE `k_line_graph_one_day_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(一天节点)';

-- ----------------------------
-- Table structure for k_line_graph_one_hours_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_one_hours_tab`;
CREATE TABLE `k_line_graph_one_hours_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(一小时节点)';

-- ----------------------------
-- Table structure for k_line_graph_one_week_tab
-- ----------------------------
DROP TABLE IF EXISTS `k_line_graph_one_week_tab`;
CREATE TABLE `k_line_graph_one_week_tab` (
  `currencyId` int(11) NOT NULL COMMENT '币种id',
  `nodeTime` datetime NOT NULL COMMENT '时间节点',
  `openPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '开盘价',
  `closPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '收盘价',
  `maxPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最高价',
  `minPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '最低价',
  `transactionTotal` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交量',
  UNIQUE KEY `currencyId` (`currencyId`,`nodeTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线图统计数据(一周节点)';

-- ----------------------------
-- Table structure for syl_to_jydp_chain_tab
-- ----------------------------
DROP TABLE IF EXISTS `syl_to_jydp_chain_tab`;
CREATE TABLE `syl_to_jydp_chain_tab` (
  `sylRecordNo` varchar(20) NOT NULL COMMENT '盛源链记录号',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `shengyuanCoin` decimal(18,8) NOT NULL COMMENT '盛源币',
  `coinType` varchar(15) NOT NULL COMMENT '币种类型',
  `handleMark` varchar(200) DEFAULT NULL COMMENT '操作说明',
  `addTime` datetime NOT NULL COMMENT '添加时间,系统生成时间',
  PRIMARY KEY (`sylRecordNo`),
  UNIQUE KEY `sylRecordNo` (`sylRecordNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYL转账盛源链记录(SYL-->JYDP)';

-- ----------------------------
-- Table structure for syl_user_bound_tab
-- ----------------------------
DROP TABLE IF EXISTS `syl_user_bound_tab`;
CREATE TABLE `syl_user_bound_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户帐号',
  `userSylAccount` varchar(16) NOT NULL COMMENT '盛源链APP用户帐号',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '绑定状态,1,绑定2，解绑（预留字段）',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `userId` (`userId`) USING BTREE,
  UNIQUE KEY `userAccount` (`userAccount`) USING BTREE,
  UNIQUE KEY `userSylAccount` (`userSylAccount`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='盛源链账号绑定表';

-- ----------------------------
-- Table structure for system_account_amount_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_account_amount_tab`;
CREATE TABLE `system_account_amount_tab` (
  `accountCode` int(6) NOT NULL COMMENT '账号编码，详见《系统账户编码表》',
  `accountName` varchar(50) NOT NULL COMMENT '账户名称',
  `accountAmount` decimal(18,8) NOT NULL COMMENT '账户金额，单位：美元',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`accountCode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统账户金额';

-- ----------------------------
-- Table structure for system_ads_homepages_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_ads_homepages_tab`;
CREATE TABLE `system_ads_homepages_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `adsTitle` varchar(16) NOT NULL COMMENT '广告标题',
  `adsImageUrl` varchar(100) NOT NULL COMMENT '广告图片地址',
  `webLinkUrl` varchar(500) DEFAULT NULL COMMENT 'web端链接地址',
  `wapLinkUrl` varchar(500) DEFAULT NULL COMMENT 'wap端链接地址',
  `rankNumber` int(11) NOT NULL COMMENT '排名位置',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COMMENT='首页广告';

-- ----------------------------
-- Table structure for system_businesses_partner_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_businesses_partner_tab`;
CREATE TABLE `system_businesses_partner_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `businessesName` varchar(16) NOT NULL COMMENT '商家名称',
  `businessesImageUrl` varchar(500) NOT NULL COMMENT '商家图片地址',
  `webLinkUrl` varchar(500) DEFAULT NULL COMMENT 'web端链接地址',
  `wapLinkUrl` varchar(500) DEFAULT NULL COMMENT 'wap端链接地址',
  `rankNumber` int(11) NOT NULL COMMENT '排名位置',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8 COMMENT='合作商家';

-- ----------------------------
-- Table structure for system_help_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_help_tab`;
CREATE TABLE `system_help_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `helpType` varchar(8) NOT NULL COMMENT '帮助类型',
  `helpTitle` varchar(64) NOT NULL COMMENT '帮助标题',
  `content` text NOT NULL COMMENT '帮助内容',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101017 DEFAULT CHARSET=utf8 COMMENT='帮助中心';

-- ----------------------------
-- Table structure for system_hot_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_hot_tab`;
CREATE TABLE `system_hot_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `noticeTitle` varchar(64) NOT NULL COMMENT '话题标题',
  `noticeType` varchar(16) NOT NULL COMMENT '话题类型',
  `noticeUrl` varchar(500) NOT NULL COMMENT '话题封面图地址',
  `content` text NOT NULL COMMENT '话题内容',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  `rankNumber` int(11) NOT NULL COMMENT '排名位置',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='热门话题';

-- ----------------------------
-- Table structure for system_notice_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_notice_tab`;
CREATE TABLE `system_notice_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `noticeType` varchar(16) NOT NULL COMMENT '公告类型',
  `noticeTitle` varchar(64) NOT NULL COMMENT '公告标题',
  `noticeUrl` varchar(500) NOT NULL COMMENT '公告封面图地址',
  `content` text NOT NULL COMMENT '公告内容',
  `rankNumber` int(11) NOT NULL COMMENT '排名位置',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8 COMMENT='用户公告管理';

-- ----------------------------
-- Table structure for system_switch_record_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_switch_record_tab`;
CREATE TABLE `system_switch_record_tab` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `switchCode` int(6) NOT NULL COMMENT '开关编码，详见《系统开关编码表》',
  `switchName` varchar(30) NOT NULL COMMENT '开关名称',
  `switchStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '开关状态，1：开启，2：关闭',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址，系统自动操作填127.0.0.1',
  `backerId` int(11) DEFAULT NULL COMMENT '后台管理员Id，系统自动操作填0',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号，系统自动操作填system',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `switchCode` (`switchCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='系统开关记录';

-- ----------------------------
-- Table structure for system_validate_phone_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_validate_phone_tab`;
CREATE TABLE `system_validate_phone_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `phoneNo` varchar(20) NOT NULL COMMENT '手机号',
  `validateCode` varchar(6) NOT NULL COMMENT '验证码',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  `validateStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '验证状态，1：待验证，2：已验证',
  `validateTime` datetime DEFAULT NULL COMMENT '验证时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `phoneNo` (`phoneNo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42668 DEFAULT CHARSET=utf8 COMMENT='系统手机验证';

-- ----------------------------
-- Table structure for transaction_currency_coefficient_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_currency_coefficient_tab`;
CREATE TABLE `transaction_currency_coefficient_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号 ,业务类型（2）+日期（6）+随机位（10）',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(10) NOT NULL COMMENT '货币名称',
  `currencyCoefficient` decimal(18,8) NOT NULL COMMENT '币种系数',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`),
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `currencyId` (`currencyId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='币种系数记录表';

-- ----------------------------
-- Table structure for transaction_currency_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_currency_tab`;
CREATE TABLE `transaction_currency_tab` (
  `currencyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '币种Id',
  `currencyShortName` varchar(10) NOT NULL COMMENT '货币简称',
  `currencyName` varchar(10) NOT NULL COMMENT '货币名称',
  `currencyImg` varchar(200) NOT NULL COMMENT '币种徽标',
  `buyFee` decimal(18,8) NOT NULL COMMENT '买入手续费',
  `sellFee` decimal(18,8) NOT NULL COMMENT '卖出手续费',
  `guidancePrice` decimal(18,8) NOT NULL COMMENT '上市指导价',
  `paymentType` tinyint(1) NOT NULL COMMENT '交易状态,1:正常，2:停牌',
  `upStatus` tinyint(1) NOT NULL COMMENT '上线状态,1:待上线,2:上线中,3:停牌,4:已下线',
  `rankNumber` int(5) NOT NULL COMMENT '排名位置',
  `backerAccount` varchar(16) NOT NULL COMMENT '管理员账号',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `upTime` datetime NOT NULL COMMENT '上线时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`currencyId`) USING BTREE,
  UNIQUE KEY `currencyId` (`currencyId`) USING BTREE,
  UNIQUE KEY `currencyName` (`currencyName`) USING BTREE,
  UNIQUE KEY `currencyShortName` (`currencyShortName`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `upStatus` (`upStatus`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 COMMENT='交易币种';

-- ----------------------------
-- Table structure for transaction_deal_redis_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_deal_redis_tab`;
CREATE TABLE `transaction_deal_redis_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `orderNo` varchar(18) NOT NULL COMMENT '记录号',
  `paymentType` tinyint(1) NOT NULL COMMENT '收支类型',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `transactionPrice` decimal(18,8) NOT NULL COMMENT '成交单价',
  `currencyNumber` decimal(18,8) NOT NULL COMMENT '成交数量',
  `currencyTotalPrice` decimal(18,8) NOT NULL COMMENT '成交总价',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `addTime` (`addTime`) USING BTREE,
  KEY `currencyId` (`currencyId`)
) ENGINE=InnoDB AUTO_INCREMENT=1305987 DEFAULT CHARSET=utf8 COMMENT='redis成交记录';

-- ----------------------------
-- Table structure for transaction_make_order_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_make_order_tab`;
CREATE TABLE `transaction_make_order_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号,业务类型（2）+日期（6）+随机位（10）',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `currencyPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交单价',
  `currencyNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交数量',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `executeStatus` tinyint(1) NOT NULL COMMENT '执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `executeTime` datetime NOT NULL COMMENT '执行时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`) USING BTREE,
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `executeStatus` (`executeStatus`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='做单记录';

-- ----------------------------
-- Table structure for transaction_pend_order_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_pend_order_tab`;
CREATE TABLE `transaction_pend_order_tab` (
  `pendingOrderNo` varchar(18) NOT NULL COMMENT '记录号，业务类型（2）+日期（6）+随机位（10）',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `paymentType` tinyint(1) NOT NULL COMMENT '收支类型，1：买入，2：卖出',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `pendingPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '挂单单价',
  `pendingNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '挂单数量',
  `dealNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交数量',
  `buyFee` decimal(18,8) NOT NULL COMMENT '买入手续费',
  `restBalanceLock` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '剩余冻结美金',
  `pendingStatus` tinyint(1) NOT NULL COMMENT '挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `endTime` datetime DEFAULT NULL COMMENT '完成时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  `feeRemark` varchar(200) DEFAULT NULL COMMENT '费率备注，手续费',
  PRIMARY KEY (`pendingOrderNo`) USING BTREE,
  UNIQUE KEY `pendingOrderNo` (`pendingOrderNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `userAccount` (`userAccount`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂单记录';

-- ----------------------------
-- Table structure for transaction_statistics_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_statistics_tab`;
CREATE TABLE `transaction_statistics_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号，业务类型（2）+日期（6）+随机位（10）',
  `statisticsDate` datetime NOT NULL COMMENT '统计日期',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `transactionTotalNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交总数量',
  `transactionTotalPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交总价格',
  `currencyCoefficient` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '币种系数',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`),
  UNIQUE KEY `statisticsDate, currencyId` (`statisticsDate`,`currencyId`) USING BTREE,
  KEY `currencyId` (`currencyId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易统计记录表';

-- ----------------------------
-- Table structure for transaction_user_deal_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_user_deal_tab`;
CREATE TABLE `transaction_user_deal_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号,业务类型（2）+日期（6）+随机位（10）',
  `pendingOrderNo` varchar(18) NOT NULL COMMENT '挂单记录号',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `paymentType` tinyint(1) NOT NULL COMMENT '收支类型,1：买入，2：卖出，3：撤销',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `transactionPrice` decimal(18,8) NOT NULL COMMENT '成交单价',
  `currencyNumber` decimal(18,8) NOT NULL COMMENT '成交数量',
  `feeNumber` decimal(18,8) NOT NULL COMMENT '成交费率',
  `currencyTotalPrice` decimal(18,8) NOT NULL COMMENT '成交总价',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pendTime` datetime NOT NULL COMMENT '挂单时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`) USING BTREE,
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `pendingOrderNo` (`pendingOrderNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `userAccount` (`userAccount`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成交记录';

-- ----------------------------
-- Table structure for user_balance_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_balance_tab`;
CREATE TABLE `user_balance_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号：业务类型（2）+日期（6）+随机位（10）',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `fromType` varchar(20) NOT NULL COMMENT '账户来源',
  `currencyId` int(11) NOT NULL COMMENT '币种id，美元id=999',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `balanceNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '交易数量',
  `frozenNumber` decimal(18,8) NOT NULL COMMENT '冻结数量',
  `remark` varchar(200) NOT NULL COMMENT '备注：手续费',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `fromType` (`fromType`) USING BTREE,
  KEY `currencyId` (`currencyId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户记录';

-- ----------------------------
-- Table structure for user_currency_num_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_currency_num_tab`;
CREATE TABLE `user_currency_num_tab` (
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '货币数量',
  `currencyNumberLock` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '冻结数量',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  UNIQUE KEY `userId, currencyId` (`userId`,`currencyId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户币数量';

-- ----------------------------
-- Table structure for user_feedback_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_feedback_tab`;
CREATE TABLE `user_feedback_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `feedbackTitle` varchar(32) NOT NULL COMMENT '反馈标题',
  `feedbackContent` varchar(400) NOT NULL COMMENT '反馈内容',
  `addTime` datetime NOT NULL COMMENT '反馈时间',
  `handleStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '处理状态，1：待处理，2：处理中，3：已处理',
  `handleContent` varchar(200) DEFAULT NULL COMMENT '处理说明',
  `backerAccount` varchar(16) DEFAULT NULL COMMENT '处理的后台管理员帐号',
  `handleTime` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='意见反馈';

-- ----------------------------
-- Table structure for user_identification_image_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_identification_image_tab`;
CREATE TABLE `user_identification_image_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `identificationId` bigint(18) NOT NULL COMMENT '用户认证Id',
  `imageUrl` varchar(200) NOT NULL COMMENT '用户认证详情图地址',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `identificationId` (`identificationId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=427 DEFAULT CHARSET=utf8 COMMENT='用户认证详情图';

-- ----------------------------
-- Table structure for user_identification_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_identification_tab`;
CREATE TABLE `user_identification_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `userName` varchar(16) NOT NULL COMMENT '用户姓名',
  `userPhone` varchar(20) NOT NULL COMMENT '手机号',
  `userCertNo` varchar(18) NOT NULL COMMENT '证件号码',
  `identificationStatus` tinyint(1) NOT NULL COMMENT '实名认证状态，1：待审核，2：审核通过，3：审核拒绝',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `identiTime` datetime DEFAULT NULL COMMENT '审核时间',
  `addTime` datetime NOT NULL COMMENT '提交时间',
  `userCertType` tinyint(1) NOT NULL COMMENT '证件类型，1:身份证，2：护照',
  `phoneAreaCode` varchar(6) NOT NULL COMMENT '手机号区号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `identificationStatus` (`identificationStatus`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8 COMMENT='用户认证';

-- ----------------------------
-- Table structure for user_session_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_session_tab`;
CREATE TABLE `user_session_tab` (
  `sessionId` varchar(20) NOT NULL COMMENT 'sessionId，业务类型（2）+日期（6）+随机位（12）',
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `loginForm` tinyint(1) NOT NULL COMMENT '登录来源，1：web端，2：wap端，3：APP端',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `loginTime` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`sessionId`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录记录';

-- ----------------------------
-- Table structure for user_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_tab`;
CREATE TABLE `user_tab` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `userAccount` varchar(16) NOT NULL COMMENT '用户账号',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `payPassword` varchar(32) NOT NULL COMMENT '支付密码',
  `phoneAreaCode` varchar(6) NOT NULL COMMENT '手机号区号',
  `phoneNumber` varchar(11) NOT NULL COMMENT '绑定手机号',
  `userBalance` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '可用资产单位(美刀$)',
  `userBalanceLock` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '锁定资产单位(美刀$)',
  `accountStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '账号状态：1：启用，2：禁用，-1：删除',
  `addTime` datetime NOT NULL COMMENT '注册时间',
  `authenticationStatus` tinyint(1) NOT NULL COMMENT '实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交',
  `payPasswordStatus` tinyint(1) NOT NULL DEFAULT '1' COMMENT '支付密码状态，1：每笔交易都输入交易密码，2：每次登录只输入一次交易密码',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE KEY `userAccount` (`userAccount`) USING BTREE,
  UNIQUE KEY `phoneAreaCode, phoneNumber` (`phoneAreaCode`,`phoneNumber`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=290 DEFAULT CHARSET=utf8 COMMENT='用户账号';
