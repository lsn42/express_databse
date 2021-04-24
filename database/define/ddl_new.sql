SET FOREIGN_KEY_CHECKS = 0;
-- -------------------------------------------------------------------------- --
drop table if exists `warehouse`;
create table `warehouse`(
  `id` int primary key not null,
  `name` varchar(64),
  `address` varchar(256),
  `longitude` decimal(10, 6),
  `latitude` decimal(10, 6),
  `volume` decimal(32, 3) comment 'cubemeter'
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop table if exists `worker`;
create table `worker`(
  `id` int primary key not null,
  `name` varchar(64),
  `sex` varchar(8),
  `tel` varchar(16),
  `address` varchar(256),
  `salary` decimal(10, 2)
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop table if exists `transport_method`;
create table `transport_method`(
  `id` int primary key not null,
  `name` varchar(64)
) engine = innodb character set = utf8mb4;
drop table if exists `porter`;
create table `porter`(
  `id` int primary key not null,
  `method_id` int,
  `worker_id` int,
  constraint `work_as_porter` foreign key (`worker_id`) references `worker`(`id`) on delete cascade on update cascade,
  constraint `porter_as_method` foreign key (`method_id`) references `transport_method`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
drop table if exists `truck`;
create table `truck`(
  `id` int primary key not null,
  `method_id` int,
  `worker_id` int,
  `type` varchar(16),
  `plate` varchar(16),
  `volume` decimal(32, 3) comment 'cubemeter',
  constraint `work_with_truck` foreign key (`worker_id`) references `worker`(`id`) on delete cascade on update cascade,
  constraint `truck_as_method` foreign key (`method_id`) references `transport_method`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
drop table if exists `plane`;
create table `plane`(
  `id` int primary key not null,
  `method_id` int,
  `worker_id` int,
  `type` varchar(16),
  `flight_number` varchar(16),
  `volume` decimal(32, 3) comment 'cubemeter',
  constraint `work_with_plane` foreign key (`worker_id`) references `worker`(`id`) on delete cascade on update cascade,
  constraint `plane_as_method` foreign key (`method_id`) references `transport_method`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop table if exists `customer`;
create table `customer`(
  `id` int primary key not null,
  `name` varchar(64),
  `sex` varchar(8),
  `tel` varchar(16),
  `address` varchar(256)
) engine = innodb character set = utf8mb4;
-- empty customer
insert into `customer`
values(0, null, null, null, null);
drop table if exists `pay_method`;
create table `pay_method`(
  `id` int primary key not null,
  `customer_id` int,
  constraint `pay` foreign key (`customer_id`) references `customer`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
-- prepaid order
insert into `pay_method`
values(0, 0);
drop table if exists `pay_with_contract`;
create table `pay_with_contract`(
  `id` int primary key not null,
  `pay_method_id` int,
  `account_number` varchar(64),
  `monthly_bill` decimal(10, 2),
  `description` text,
  constraint `pay_with_contract` foreign key (`pay_method_id`) references `pay_method`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
drop table if exists `pay_with_credit_card`;
create table `pay_with_credit_card`(
  `id` int primary key not null,
  `pay_method_id` int,
  `account_number` varchar(64),
  constraint `pay_with_credit_card` foreign key (`pay_method_id`) references `pay_method`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop table if exists `order`;
create table `order` (
  `id` int primary key not null,
  `customer_id` int,
  `sender` varchar(256),
  `recipient` varchar(256),
  `type` varchar(64),
  `weight` decimal(10, 3) comment 'kilogram',
  `create_time` datetime,
  `timeliness` datetime,
  `fare` decimal(10, 2),
  constraint `customer` foreign key (`customer_id`) references `customer`(`id`) on delete no action on update no action
) engine = innodb character set = utf8mb4;
drop table if exists `hazardous_material`;
create table `hazardous_material`(
  `id` int primary key not null,
  `info` text,
  constraint `hazardous_material` foreign key (`id`) references `order`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
drop table if exists `international_shipment`;
create table `international_shipment`(
  `id` int primary key not null,
  `stating` text,
  `value` decimal(10, 2),
  constraint `international_shipment` foreign key (`id`) references `order`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop table if exists `transport_event`;
create table `transport_event`(
  `id` int primary key not null,
  `method_id` int not null,
  `time` datetime,
  `type` varchar(8),
  `source` int,
  `destination` int,
  constraint `transport_method` foreign key (`method_id`) references `transport_method`(`id`) on delete no action on update no action,
  constraint `transport_from` foreign key (`source`) references `warehouse`(`id`) on delete no action on update no action,
  constraint `transport_to` foreign key (`destination`) references `warehouse`(`id`) on delete no action on update no action
) engine = innodb character set = utf8mb4;
drop table if exists `transport`;
create table `transport`(
  `order_id` int,
  `event_id` int,
  constraint `transport_event` foreign key (`event_id`) references `transport_event`(`id`) on delete no action on update no action,
  constraint `transport_order` foreign key (`order_id`) references `order`(`id`) on delete cascade on update cascade
) engine = innodb character set = utf8mb4;
-- -------------------------------------------------------------------------- --
drop view if exists `package_tracking`;
create view `package_tracking` as
select
  `order`.`id`,
  `warehouse_s`.`name` as `source_name`,
  `warehouse_s`.`longitude` as `source_longitude`,
  `warehouse_s`.`latitude` as `source_latitude`,
  `warehouse_d`.`name` as `destination_name`,
  `warehouse_d`.`longitude` as `destination_longitude`,
  `warehouse_d`.`latitude` as `destination_latitude`,
  `transport_event`.`time`,
  `transport_event`.`type`,
  `transport_method`.`name` as `method`
from `order`
  join `transport` on `order`.`id` = `transport`.`order_id`
  join `transport_event` on `transport`.`event_id` = `transport_event`.`id`
  join `warehouse` as `warehouse_s` on `transport_event`.`source` = `warehouse_s`.`id`
  join `warehouse` as `warehouse_d` on `transport_event`.`destination` = `warehouse_d`.`id`
  join `transport_method` on `transport_event`.`method_id`=`transport_method`.`id`
order by `transport_event`.`time` desc;
drop view if exists `truck_tracking`;
create view `truck_tracking` as
select 
  `truck`.`id`,
  `transport_event`.`time`,
  `transport_event`.`type`as `transport_type`,
  `truck`.`type`,
  `truck`.`plate`,
  `warehouse_s`.`name` as `source_name`,
  `warehouse_d`.`name` as `destination_name`,
  `worker`.`name` as `worker_name`,
  `warehouse_s`.`longitude` as `source_longitude`,
  `warehouse_s`.`latitude` as `source_latitude`,
  `warehouse_d`.`longitude` as `destination_longitude`,
  `warehouse_d`.`latitude` as `destination_latitude`
from `transport_event`
  join `transport_method` on `transport_event`.`method_id` = `transport_method`.`id`
  join `truck` on `truck`.`method_id`= `transport_method`.`id`
  join `worker` on `truck`.`worker_id`= `worker`.`id`
  join `warehouse` as `warehouse_s` on `transport_event`.`source` = `warehouse_s`.`id`
  join `warehouse` as `warehouse_d` on `transport_event`.`destination` = `warehouse_d`.`id`
order by `transport_event`.`time` desc;
SET FOREIGN_KEY_CHECKS = 1;