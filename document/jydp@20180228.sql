/*
Navicat MySQL Data Transfer

Source Server         : test.oksheng.com.cn
Source Server Version : 50635
Source Host           : test.oksheng.com.cn:3306
Source Database       : jydp

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-02-28 18:53:22
*/

SET FOREIGN_KEY_CHECKS=0;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='后台管理员增减用户余额记录';

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
  PRIMARY KEY (`powerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员角色权限映射';

-- ----------------------------
-- Table structure for backer_role_power_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_role_power_tab`;
CREATE TABLE `backer_role_power_tab` (
  `roleId` int(11) NOT NULL COMMENT '角色Id',
  `powerJson` longtext NOT NULL COMMENT '权限信息，Json格式字符串',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理员账户角色权限';

-- ----------------------------
-- Table structure for backer_role_tab
-- ----------------------------
DROP TABLE IF EXISTS `backer_role_tab`;
CREATE TABLE `backer_role_tab` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `roleName` varchar(20) NOT NULL COMMENT '角色名称',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='后台管理员账户角色';

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
  PRIMARY KEY (`sessionId`)
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
  PRIMARY KEY (`backerId`),
  UNIQUE KEY `backerAccount` (`backerAccount`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='后台管理员';

-- ----------------------------
-- Table structure for system_account_amount_tab
-- ----------------------------
DROP TABLE IF EXISTS `system_account_amount_tab`;
CREATE TABLE `system_account_amount_tab` (
  `accountCode` int(6) NOT NULL COMMENT '账号编码，详见《系统账户编码表》',
  `accountName` varchar(50) NOT NULL COMMENT '账户名称',
  `accountAmount` decimal(18,8) NOT NULL COMMENT '账户金额，单位：美元',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`accountCode`)
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='首页广告';

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
  `addTime` datetime NOT NULL COMMENT '添加时间',
  `topTime` datetime DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='合作商家';

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101018 DEFAULT CHARSET=utf8 COMMENT='帮助中心';

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
  `topTime` datetime DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='热门话题';

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
  `addTime` datetime NOT NULL COMMENT '添加时间',
  `topTime` datetime DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='用户公告管理';

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
  PRIMARY KEY (`id`),
  KEY `phoneNo` (`phoneNo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42133 DEFAULT CHARSET=utf8 COMMENT='系统手机验证';

-- ----------------------------
-- Table structure for transaction_currency_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_currency_tab`;
CREATE TABLE `transaction_currency_tab` (
  `currencyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '币种Id',
  `currencyShortName` varchar(10) NOT NULL COMMENT '货币简称',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`currencyId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='交易币种';

-- ----------------------------
-- Table structure for transaction_deal_redis_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_deal_redis_tab`;
CREATE TABLE `transaction_deal_redis_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号',
  `paymentType` tinyint(1) NOT NULL COMMENT '收支类型',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `transactionPrice` decimal(18,8) NOT NULL COMMENT '成交单价',
  `currencyNumber` decimal(18,8) NOT NULL COMMENT '成交数量',
  `currencyTotalPrice` decimal(18,8) NOT NULL COMMENT '成交总价',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`),
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `addTime` (`addTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='redis成交记录';

-- ----------------------------
-- Table structure for transaction_make_order_tab
-- ----------------------------
DROP TABLE IF EXISTS `transaction_make_order_tab`;
CREATE TABLE `transaction_make_order_tab` (
  `orderNo` varchar(18) NOT NULL COMMENT '记录号,业务类型（2）+日期（6）+随机位（10）',
  `paymentType` tinyint(1) NOT NULL COMMENT '收支类型,1：买入，2：卖出',
  `currencyId` int(11) NOT NULL COMMENT '币种Id',
  `currencyName` varchar(32) NOT NULL COMMENT '货币名称',
  `currencyNumber` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交数量',
  `currencyTotalPrice` decimal(18,8) NOT NULL DEFAULT '0.00000000' COMMENT '成交总价',
  `backerAccount` varchar(16) NOT NULL COMMENT '后台管理员帐号',
  `ipAddress` varchar(30) NOT NULL COMMENT '操作时的ip地址',
  `executeStatus` tinyint(1) NOT NULL COMMENT '执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `executeTime` datetime NOT NULL COMMENT '执行时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`),
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `executeStatus` (`executeStatus`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='做单记录';

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
  `pendingStatus` tinyint(1) NOT NULL COMMENT '挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `endTime` datetime DEFAULT NULL COMMENT '完成时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`pendingOrderNo`),
  UNIQUE KEY `pendingOrderNo` (`pendingOrderNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `userAccount` (`userAccount`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='挂单记录';

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
  `transactionPrice` decimal(18,8) NOT NULL,
  `currencyNumber` decimal(18,8) NOT NULL COMMENT '成交数量',
  `currencyTotalPrice` decimal(18,8) NOT NULL COMMENT '成交总价',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pendTime` datetime NOT NULL COMMENT '挂单时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`orderNo`),
  UNIQUE KEY `orderNo` (`orderNo`) USING BTREE,
  KEY `pendingOrderNo` (`pendingOrderNo`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE,
  KEY `paymentType` (`paymentType`) USING BTREE,
  KEY `currencyName` (`currencyName`) USING BTREE,
  KEY `userAccount` (`userAccount`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='成交记录';

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
  PRIMARY KEY (`orderNo`),
  KEY `userId` (`userId`) USING BTREE,
  KEY `fromType` (`fromType`) USING BTREE,
  KEY `currencyId` (`currencyId`)
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
  UNIQUE KEY `userId_2` (`userId`,`currencyId`),
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='意见反馈';

-- ----------------------------
-- Table structure for user_identification_image_tab
-- ----------------------------
DROP TABLE IF EXISTS `user_identification_image_tab`;
CREATE TABLE `user_identification_image_tab` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `identificationId` bigint(18) NOT NULL COMMENT '用户认证Id',
  `imageUrl` varchar(200) NOT NULL COMMENT '用户认证详情图地址',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `identificationId` (`identificationId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='用户认证详情图';

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
  `userCertNo` varchar(18) NOT NULL COMMENT '身份证号码',
  `identificationStatus` tinyint(1) NOT NULL COMMENT '实名认证状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `identiTime` datetime DEFAULT NULL COMMENT '审核时间',
  `addTime` datetime NOT NULL COMMENT '提交时间',
  `userCertType` tinyint(1) NOT NULL COMMENT '证件类型，1:身份证，2：护照',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`) USING BTREE,
  KEY `identificationStatus` (`identificationStatus`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户认证';

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
  PRIMARY KEY (`sessionId`),
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
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userAccount` (`userAccount`) USING BTREE,
  UNIQUE KEY `phoneAreaCode, phoneNumber` (`phoneAreaCode`,`phoneNumber`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='用户账号';
