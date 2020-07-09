use sharding_db;
CREATE TABLE if not exists `t_yg_sharding_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ORG_CODE` varchar(20) DEFAULT NULL COMMENT '分公司代码',
  `last_cal_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='分库测试表';
