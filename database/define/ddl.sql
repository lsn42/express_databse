SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL,
  `customer_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customer_sex` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customer_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `customer_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for express_list
-- ----------------------------
DROP TABLE IF EXISTS `express_list`;
CREATE TABLE `express_list` (
  `ex_id` int NOT NULL,
  `worker_id` int NULL DEFAULT NULL,
  `customer_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `ex_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ex_price` decimal(32, 2) NOT NULL,
  `pay_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `require_time` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ex_end_time` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trans_id` int NULL DEFAULT NULL,
  `store_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`ex_id`) USING BTREE,
  INDEX `worker_id`(`worker_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  INDEX `receiver_id`(`receiver_id`) USING BTREE,
  INDEX `express_list_ibfk_5`(`trans_id`) USING BTREE,
  INDEX `express_list_ibfk_6`(`store_id`) USING BTREE,
  CONSTRAINT `express_list_ibfk_1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `express_list_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `express_list_ibfk_3` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `express_list_ibfk_4` FOREIGN KEY (`receiver_id`) REFERENCES `receiver` (`receiver_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `express_list_ibfk_5` FOREIGN KEY (`trans_id`) REFERENCES `transport_list` (`trans_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `express_list_ibfk_6` FOREIGN KEY (`store_id`) REFERENCES `stored_list` (`stored_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` int NOT NULL,
  `goods_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `goods_weight` decimal(32, 2) NOT NULL,
  `goods_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plane
-- ----------------------------
DROP TABLE IF EXISTS `plane`;
CREATE TABLE `plane` (
  `plane_id` int NOT NULL,
  `plane_volume` decimal(32, 2) NOT NULL,
  `plane_company` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`plane_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for receiver
-- ----------------------------
DROP TABLE IF EXISTS `receiver`;
CREATE TABLE `receiver` (
  `receiver_id` int NOT NULL,
  `receiver_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receiver_sex` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receiver_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `receiver_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`receiver_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for stored_list
-- ----------------------------
DROP TABLE IF EXISTS `stored_list`;
CREATE TABLE `stored_list` (
  `stored_id` int NOT NULL,
  `ware_id` int NOT NULL,
  `goods_id` int NOT NULL,
  `worker_id` int NULL DEFAULT NULL,
  `intime` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `outtime` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stored_id`) USING BTREE,
  INDEX `ware_id`(`ware_id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  INDEX `worker_id`(`worker_id`) USING BTREE,
  CONSTRAINT `stored_list_ibfk_1` FOREIGN KEY (`ware_id`) REFERENCES `warehouse` (`ware_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stored_list_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `express_list` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stored_list_ibfk_3` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for transport_list
-- ----------------------------
DROP TABLE IF EXISTS `transport_list`;
CREATE TABLE `transport_list` (
  `trans_id` int NOT NULL,
  `worker_id` int NULL DEFAULT NULL,
  `goods_id` int NOT NULL,
  `truck_id` int NULL DEFAULT NULL,
  `plane_id` int NULL DEFAULT NULL,
  `start_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `end_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` char(20) NOT NULL,
  `end_time` char(20) NULL DEFAULT NULL,
  `postman_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`trans_id`) USING BTREE,
  INDEX `worker_id`(`worker_id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  INDEX `truck_id`(`truck_id`) USING BTREE,
  INDEX `plane_id`(`plane_id`) USING BTREE,
  INDEX `transport_list_ibfk_5`(`postman_id`) USING BTREE,
  CONSTRAINT `transport_list_ibfk_1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transport_list_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `express_list` (`goods_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transport_list_ibfk_3` FOREIGN KEY (`truck_id`) REFERENCES `truck` (`truck_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transport_list_ibfk_4` FOREIGN KEY (`plane_id`) REFERENCES `plane` (`plane_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transport_list_ibfk_5` FOREIGN KEY (`postman_id`) REFERENCES `worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for truck
-- ----------------------------
DROP TABLE IF EXISTS `truck`;
CREATE TABLE `truck` (
  `truck_id` int NOT NULL,
  `truck_volume` decimal(32, 2) NOT NULL,
  `truck_company` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`truck_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `ware_id` int NOT NULL,
  `ware_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ware_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ware_volume` decimal(32, 2) NOT NULL,
  PRIMARY KEY (`ware_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for worker
-- ----------------------------
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker` (
  `worker_id` int NOT NULL,
  `worker_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_sex` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_tel` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_job` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_salary` decimal(10, 0) NOT NULL,
  PRIMARY KEY (`worker_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for worker_attendance
-- ----------------------------
DROP TABLE IF EXISTS `worker_attendance`;
CREATE TABLE `worker_attendance` (
  `worker_id` int NOT NULL,
  `absent_date` date NULL DEFAULT NULL,
  PRIMARY KEY (`worker_id`) USING BTREE,
  CONSTRAINT `worker_attendance_ibfk_1` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- View structure for attendance_view
-- ----------------------------
DROP VIEW IF EXISTS `attendance_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `attendance_view` AS
select `worker_attendance`.`worker_id` AS `员工id`,
  `worker_attendance`.`absent_date` AS `缺勤日期`
from `worker_attendance`;

-- ----------------------------
-- View structure for express_list_view
-- ----------------------------
DROP VIEW IF EXISTS `express_list_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `express_list_view` AS
select `express_list`.`ex_id` AS `快递单号`,
  `express_list`.`worker_id` AS `负责人id`,
  `express_list`.`customer_id` AS `顾客id`,
  `express_list`.`goods_id` AS `货物id`,
  `express_list`.`receiver_id` AS `收件人id`,
  `express_list`.`ex_type` AS `物品类型`,
  `express_list`.`ex_price` AS `订单价格`,
  `express_list`.`pay_type` AS `支付类型`,
  `express_list`.`create_time` AS `创建时间`,
  `express_list`.`require_time` AS `客户要求时间`,
  `express_list`.`ex_end_time` AS `结束时间`
from `express_list`;

-- ----------------------------
-- View structure for stored_list_view
-- ----------------------------
DROP VIEW IF EXISTS `stored_list_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `stored_list_view` AS
select `stored_list`.`stored_id` AS `库存单号`,
  `stored_list`.`ware_id` AS `仓库id`,
  `stored_list`.`goods_id` AS `货物id`,
  `stored_list`.`worker_id` AS `负责人id`,
  `stored_list`.`intime` AS `进仓时间`,
  `stored_list`.`outtime` AS `出仓时间`
from `stored_list`;

-- ----------------------------
-- View structure for transport_list_view
-- ----------------------------
DROP VIEW IF EXISTS `transport_list_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `transport_list_view` AS
select `transport_list`.`trans_id` AS `运输单号`,
  `transport_list`.`worker_id` AS `负责人id`,
  `transport_list`.`goods_id` AS `货物id`,
  `transport_list`.`truck_id` AS `卡车id`,
  `transport_list`.`plane_id` AS `飞机id`,
  `transport_list`.`start_address` AS `起始地点`,
  `transport_list`.`end_address` AS `结束地点`,
  `transport_list`.`start_time` AS `起始时间`,
  `transport_list`.`end_time` AS `结束时间`
from `transport_list`;

-- ----------------------------
-- Triggers structure for table worker
-- ----------------------------
DROP TRIGGER IF EXISTS `delete_worker`;
delimiter;
;
CREATE TRIGGER `delete_worker` BEFORE DELETE ON `worker` FOR EACH ROW begin
update express_list
set worker_id = null
where worker_id = old.worker_id;
update stored_list
set worker_id = null
where worker_id = old.worker_id;
update transport_list
set worker_id = null
where worker_id = old.worker_id;
update worker_attendance
set worker_id = null
where worker_id = old.worker_id;
end;
;
delimiter;

-- ----------------------------
-- Triggers structure for table worker
-- ----------------------------
DROP TRIGGER IF EXISTS `update_worker`;
delimiter;
;
CREATE TRIGGER `update_worker`
AFTER
UPDATE ON `worker` FOR EACH ROW begin
update express_list
set worker_id = new.worker_id
where worker_id = old.worker_id;
update stored_list
set worker_id = new.worker_id
where worker_id = old.worker_id;
update transport_list
set worker_id = new.worker_id
where worker_id = old.worker_id;
update worker_attendance
set worker_id = new.worker_id
where worker_id = old.worker_id;
end;
;
delimiter;

-- ----------------------------
-- Triggers structure for table worker
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_worker`;
delimiter;
;
CREATE TRIGGER `insert_worker`
AFTER
INSERT ON `worker` FOR EACH ROW begin
insert into worker_attendance(worker_id, absent_date)
values(new.worker_id, null);
end;
;
delimiter;
SET FOREIGN_KEY_CHECKS = 1;