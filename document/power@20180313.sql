/*
Navicat MySQL Data Transfer

Source Server         : test.oksheng.com.cn
Source Server Version : 50635
Source Host           : test.oksheng.com.cn:3306
Source Database       : jydp

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-03-13 22:53:59
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of backer_role_power_map_tab
-- ----------------------------
INSERT INTO `backer_role_power_map_tab` VALUES ('100000', '交易管理', '1', '0', '2018-02-23 10:18:38');
INSERT INTO `backer_role_power_map_tab` VALUES ('101000', '挂单记录', '2', '100000', '2018-02-23 10:19:33');
INSERT INTO `backer_role_power_map_tab` VALUES ('101001', '查询', '3', '101000', '2018-02-23 10:21:29');
INSERT INTO `backer_role_power_map_tab` VALUES ('101002', '撤销', '3', '101000', '2018-02-23 10:20:42');
INSERT INTO `backer_role_power_map_tab` VALUES ('102000', '交易记录', '2', '100000', '2018-02-23 10:19:51');
INSERT INTO `backer_role_power_map_tab` VALUES ('102001', '查询', '3', '102000', '2018-02-23 10:20:25');
INSERT INTO `backer_role_power_map_tab` VALUES ('103000', '后台做单', '2', '100000', '2018-02-23 10:20:04');
INSERT INTO `backer_role_power_map_tab` VALUES ('103001', '查询', '3', '103000', '2018-02-23 10:21:55');
INSERT INTO `backer_role_power_map_tab` VALUES ('103002', '新增', '3', '103000', '2018-02-23 10:22:11');
INSERT INTO `backer_role_power_map_tab` VALUES ('103003', '删除', '3', '103000', '2018-02-23 10:22:39');
INSERT INTO `backer_role_power_map_tab` VALUES ('103004', '查看详情', '3', '103000', '2018-02-23 10:22:48');
INSERT INTO `backer_role_power_map_tab` VALUES ('104000', '币种管理', '2', '100000', '2018-03-01 16:57:13');
INSERT INTO `backer_role_power_map_tab` VALUES ('104001', '查询', '3', '104000', '2018-03-01 16:57:31');
INSERT INTO `backer_role_power_map_tab` VALUES ('104002', '新增', '3', '104000', '2018-03-01 16:57:56');
INSERT INTO `backer_role_power_map_tab` VALUES ('104003', '修改', '3', '104000', '2018-03-01 16:58:14');
INSERT INTO `backer_role_power_map_tab` VALUES ('104004', '导出', '3', '104000', '2018-03-01 16:58:28');
INSERT INTO `backer_role_power_map_tab` VALUES ('104005', '上线', '3', '104000', '2018-03-01 16:58:39');
INSERT INTO `backer_role_power_map_tab` VALUES ('104006', '下线', '3', '104000', '2018-03-01 16:58:55');
INSERT INTO `backer_role_power_map_tab` VALUES ('104007', '复牌', '3', '104000', '2018-03-01 16:59:26');
INSERT INTO `backer_role_power_map_tab` VALUES ('104008', '停牌', '3', '104000', '2018-03-01 16:59:37');
INSERT INTO `backer_role_power_map_tab` VALUES ('104009', '上移', '3', '104000', '2018-03-09 03:46:09');
INSERT INTO `backer_role_power_map_tab` VALUES ('104010', '下移', '3', '104000', '2018-03-09 03:46:23');
INSERT INTO `backer_role_power_map_tab` VALUES ('104011', '置顶', '3', '104000', '2018-03-09 03:46:36');
INSERT INTO `backer_role_power_map_tab` VALUES ('110000', '运营中心', '1', '0', '2018-02-12 15:08:58');
INSERT INTO `backer_role_power_map_tab` VALUES ('111000', '首页广告', '2', '110000', '2018-02-12 15:10:39');
INSERT INTO `backer_role_power_map_tab` VALUES ('111001', '查询', '3', '111000', '2018-02-12 15:11:05');
INSERT INTO `backer_role_power_map_tab` VALUES ('111002', '新增首页广告', '3', '111000', '2018-02-12 15:11:57');
INSERT INTO `backer_role_power_map_tab` VALUES ('111003', '上移', '3', '111000', '2018-02-12 15:12:26');
INSERT INTO `backer_role_power_map_tab` VALUES ('111004', '下移', '3', '111000', '2018-02-12 15:12:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('111005', '修改', '3', '111000', '2018-02-12 15:13:10');
INSERT INTO `backer_role_power_map_tab` VALUES ('111006', '删除', '3', '111000', '2018-02-12 15:13:35');
INSERT INTO `backer_role_power_map_tab` VALUES ('111007', '置顶', '3', '111000', '2018-02-12 15:13:35');
INSERT INTO `backer_role_power_map_tab` VALUES ('112000', '合作伙伴', '2', '110000', '2018-02-12 15:17:14');
INSERT INTO `backer_role_power_map_tab` VALUES ('112001', '查询', '3', '112000', '2018-02-12 15:19:45');
INSERT INTO `backer_role_power_map_tab` VALUES ('112002', '新增合作伙伴', '3', '112000', '2018-02-12 15:20:04');
INSERT INTO `backer_role_power_map_tab` VALUES ('112003', '置顶', '3', '112000', '2018-02-12 15:20:25');
INSERT INTO `backer_role_power_map_tab` VALUES ('112004', '修改', '3', '112000', '2018-02-12 15:20:39');
INSERT INTO `backer_role_power_map_tab` VALUES ('112005', '删除', '3', '112000', '2018-02-12 15:20:54');
INSERT INTO `backer_role_power_map_tab` VALUES ('112006', '上移', '3', '112000', '2018-02-12 15:20:54');
INSERT INTO `backer_role_power_map_tab` VALUES ('112007', '下移', '3', '112000', '2018-02-12 15:20:54');
INSERT INTO `backer_role_power_map_tab` VALUES ('113000', '用户公告管理', '2', '110000', '2018-02-12 15:24:54');
INSERT INTO `backer_role_power_map_tab` VALUES ('113001', '查询', '3', '113000', '2018-02-12 15:25:19');
INSERT INTO `backer_role_power_map_tab` VALUES ('113002', '新增公告', '3', '113000', '2018-02-12 15:25:40');
INSERT INTO `backer_role_power_map_tab` VALUES ('113003', '置顶', '3', '113000', '2018-02-12 15:25:59');
INSERT INTO `backer_role_power_map_tab` VALUES ('113004', '修改', '3', '113000', '2018-02-12 15:26:18');
INSERT INTO `backer_role_power_map_tab` VALUES ('113005', '详情', '3', '113000', '2018-02-12 15:26:36');
INSERT INTO `backer_role_power_map_tab` VALUES ('113006', '删除', '3', '113000', '2018-02-12 15:26:48');
INSERT INTO `backer_role_power_map_tab` VALUES ('113007', '上移', '3', '113000', '2018-02-12 15:26:48');
INSERT INTO `backer_role_power_map_tab` VALUES ('113008', '下移', '3', '113000', '2018-02-12 15:26:48');
INSERT INTO `backer_role_power_map_tab` VALUES ('114000', '热门话题管理', '2', '110000', '2018-02-12 15:44:13');
INSERT INTO `backer_role_power_map_tab` VALUES ('114001', '查询', '3', '114000', '2018-02-12 15:44:35');
INSERT INTO `backer_role_power_map_tab` VALUES ('114002', '新增热门话题', '3', '114000', '2018-02-12 15:45:03');
INSERT INTO `backer_role_power_map_tab` VALUES ('114003', '置顶', '3', '114000', '2018-02-12 15:46:03');
INSERT INTO `backer_role_power_map_tab` VALUES ('114004', '修改', '3', '114000', '2018-02-12 15:46:19');
INSERT INTO `backer_role_power_map_tab` VALUES ('114005', '详情', '3', '114000', '2018-02-12 15:46:35');
INSERT INTO `backer_role_power_map_tab` VALUES ('114006', '删除', '3', '114000', '2018-02-12 15:46:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('114007', '上移', '3', '114000', '2018-02-12 15:46:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('114008', '下移', '3', '114000', '2018-02-12 15:46:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('115000', '帮助中心', '2', '110000', '2018-02-12 16:11:51');
INSERT INTO `backer_role_power_map_tab` VALUES ('115001', '查询', '3', '115000', '2018-02-12 16:12:14');
INSERT INTO `backer_role_power_map_tab` VALUES ('115002', '提交', '3', '115000', '2018-02-12 16:12:34');
INSERT INTO `backer_role_power_map_tab` VALUES ('116000', '意见反馈', '2', '110000', '2018-02-12 16:21:33');
INSERT INTO `backer_role_power_map_tab` VALUES ('116001', '查询', '3', '116000', '2018-02-12 16:21:47');
INSERT INTO `backer_role_power_map_tab` VALUES ('116002', '回复', '3', '116000', '2018-02-12 16:22:00');
INSERT INTO `backer_role_power_map_tab` VALUES ('120000', '管理员操作记录', '1', '0', '2018-02-07 17:12:44');
INSERT INTO `backer_role_power_map_tab` VALUES ('121000', '增减用户余额记录', '2', '120000', '2018-02-07 17:14:01');
INSERT INTO `backer_role_power_map_tab` VALUES ('121001', '查询', '3', '121000', '2018-02-07 17:14:27');
INSERT INTO `backer_role_power_map_tab` VALUES ('121100', '增减用户冻结余额记录', '2', '120000', '2018-02-07 17:14:01');
INSERT INTO `backer_role_power_map_tab` VALUES ('121101', '查询', '3', '121100', '2018-02-07 17:14:27');
INSERT INTO `backer_role_power_map_tab` VALUES ('121200', '增减用户可用币记录', '2', '120000', '2018-02-07 17:14:01');
INSERT INTO `backer_role_power_map_tab` VALUES ('121201', '查询', '3', '121200', '2018-02-07 17:14:27');
INSERT INTO `backer_role_power_map_tab` VALUES ('121300', '增减用户冻结币记录', '2', '120000', '2018-02-07 17:14:01');
INSERT INTO `backer_role_power_map_tab` VALUES ('121301', '查询', '3', '121300', '2018-02-07 17:14:27');
INSERT INTO `backer_role_power_map_tab` VALUES ('130000', '后台账号管理', '1', '0', '2018-01-06 01:46:20');
INSERT INTO `backer_role_power_map_tab` VALUES ('131000', '账号角色', '2', '130000', '2018-01-06 01:46:47');
INSERT INTO `backer_role_power_map_tab` VALUES ('131001', '查询', '3', '131000', '2018-01-06 01:47:04');
INSERT INTO `backer_role_power_map_tab` VALUES ('131002', '新增角色', '3', '131000', '2018-01-06 01:47:32');
INSERT INTO `backer_role_power_map_tab` VALUES ('131003', '修改角色', '3', '131000', '2018-01-06 01:47:48');
INSERT INTO `backer_role_power_map_tab` VALUES ('131004', '删除角色', '3', '131000', '2018-01-06 01:48:11');
INSERT INTO `backer_role_power_map_tab` VALUES ('131100', '后台账号', '2', '130000', '2018-01-06 01:48:32');
INSERT INTO `backer_role_power_map_tab` VALUES ('131101', '查询', '3', '131100', '2018-01-06 01:48:47');
INSERT INTO `backer_role_power_map_tab` VALUES ('131102', '新增', '3', '131100', '2018-01-06 01:49:07');
INSERT INTO `backer_role_power_map_tab` VALUES ('131103', '修改', '3', '131100', '2018-01-06 01:49:28');
INSERT INTO `backer_role_power_map_tab` VALUES ('131104', '删除', '3', '131100', '2018-01-06 01:49:46');
INSERT INTO `backer_role_power_map_tab` VALUES ('131105', '启用', '3', '131100', '2018-01-06 01:50:05');
INSERT INTO `backer_role_power_map_tab` VALUES ('131106', '禁用', '3', '131100', '2018-01-06 01:50:19');
INSERT INTO `backer_role_power_map_tab` VALUES ('131107', '重置密码', '3', '131100', '2018-01-06 01:50:42');
INSERT INTO `backer_role_power_map_tab` VALUES ('140000', '账号管理', '1', '0', '2018-02-08 17:03:26');
INSERT INTO `backer_role_power_map_tab` VALUES ('141000', '实名认证', '2', '140000', '2018-02-08 17:07:42');
INSERT INTO `backer_role_power_map_tab` VALUES ('141001', '查询', '3', '141000', '2018-02-08 17:10:23');
INSERT INTO `backer_role_power_map_tab` VALUES ('141002', '详情', '3', '141000', '2018-02-08 17:12:06');
INSERT INTO `backer_role_power_map_tab` VALUES ('141003', '审核通过', '3', '141000', '2018-02-08 17:12:24');
INSERT INTO `backer_role_power_map_tab` VALUES ('141004', '审核拒绝', '3', '141000', '2018-02-08 17:12:38');
INSERT INTO `backer_role_power_map_tab` VALUES ('141100', '用户账号', '2', '140000', '2018-02-08 17:14:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('141101', '查询', '3', '141100', '2018-02-08 17:15:15');
INSERT INTO `backer_role_power_map_tab` VALUES ('141102', '导出', '3', '141100', '2018-02-08 17:15:27');
INSERT INTO `backer_role_power_map_tab` VALUES ('141103', '账户明细', '3', '141100', '2018-02-08 17:15:56');
INSERT INTO `backer_role_power_map_tab` VALUES ('141104', '禁用', '3', '141100', '2018-02-08 17:17:05');
INSERT INTO `backer_role_power_map_tab` VALUES ('141105', '启用', '3', '141100', '2018-02-08 17:17:09');
INSERT INTO `backer_role_power_map_tab` VALUES ('141106', '增加账户余额', '3', '141100', '2018-02-08 17:17:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('141107', '减少账户余额', '3', '141100', '2018-02-08 17:18:02');
INSERT INTO `backer_role_power_map_tab` VALUES ('141108', '增加账户锁定余额', '3', '141100', '2018-02-08 17:17:50');
INSERT INTO `backer_role_power_map_tab` VALUES ('141109', '减少账户锁定余额', '3', '141100', '2018-02-08 17:18:02');
INSERT INTO `backer_role_power_map_tab` VALUES ('141110', '增加账户币种数量', '3', '141100', '2018-03-08 20:25:26');
INSERT INTO `backer_role_power_map_tab` VALUES ('141111', '减少账户币种数量', '3', '141100', '2018-03-08 20:25:39');
INSERT INTO `backer_role_power_map_tab` VALUES ('141112', '增加账户币种冻结数量', '3', '141100', '2018-03-08 20:25:59');
INSERT INTO `backer_role_power_map_tab` VALUES ('141113', '减少账户币种冻结数量', '3', '141100', '2018-03-08 20:26:33');
