
-- 创建order-service数据库
CREATE DATABASE `cloud-demo-order`；

CREATE TABLE IF NOT EXISTS `cloud-demo-order`.`t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_code` varchar(45) DEFAULT NULL COMMENT '客户编码',
  `good_code` varchar(45) DEFAULT NULL COMMENT '产品编码',
  `good_quantity` int(11) DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- AT模式需要的undo log表
CREATE TABLE IF NOT EXISTS `cloud-demo-order`.`undo_log`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'increment id',
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME     NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME     NOT NULL COMMENT 'modify datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';

-- 创建storage-service数据库
CREATE DATABASE `cloud-demo-storage`;

CREATE TABLE IF NOT EXISTS `cloud-demo-storage`.`t_inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_code` varchar(45) DEFAULT NULL COMMENT '产品编码',
  `good_quantity` int(11) DEFAULT NULL COMMENT '库存总量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- AT模式需要的undo log表
CREATE TABLE IF NOT EXISTS `cloud-demo-storage`.`undo_log`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'increment id',
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME     NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME     NOT NULL COMMENT 'modify datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';