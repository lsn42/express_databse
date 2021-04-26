drop view if exists `package_tracking`;
create view `package_tracking` as
select `order`.`id`,
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
  join `transport_method` on `transport_event`.`method_id` = `transport_method`.`id`
order by `transport_event`.`time` desc;
drop view if exists `truck_tracking`;
create view `truck_tracking` as
select `truck`.`id`,
  `transport_event`.`id` as `event_id`,
  `transport_event`.`time`,
  `transport_event`.`type` as `transport_type`,
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
  join `truck` on `truck`.`method_id` = `transport_method`.`id`
  join `worker` on `truck`.`worker_id` = `worker`.`id`
  join `warehouse` as `warehouse_s` on `transport_event`.`source` = `warehouse_s`.`id`
  join `warehouse` as `warehouse_d` on `transport_event`.`destination` = `warehouse_d`.`id`
order by `transport_event`.`time` desc;
drop view if exists `simple_bill`;
create view `simple_bill` as
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  `m`.`amount` as `amount`
from(
    select `customer_id` as `id`,
      sum(`fare`) as `amount`
    from `order`
    where `create_time` > date_add(now(), interval -1 month)
    group by `customer_id`
  ) m
  natural join `customer`;
drop view if exists `type_of_service_bill`;
create view `type_of_service_bill` as
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  '信用卡' as `pay_method`,
  `m`.`amount` as `amount`
from(
    select `customer_id` as `id`,
      sum(`fare`) as `amount`
    from `order`
    where `create_time` > date_add(now(), interval -1 month)
    group by `customer_id`
  ) m
  natural join `customer`
union all
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  '合同' as `pay_method`,
  sum(`pay_with_contract`.`monthly_bill`) as `amount`
from `pay_with_contract`
  join `pay_method` on `pay_with_contract`.`pay_method_id` = `pay_method`.`id`
  join `customer` on `pay_method`.`customer_id` = `customer`.`id`
group by `customer`.`id`;
drop view if exists `each_shipment_bill`;
create view `each_shipment_bill` as
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  `order`.`sender` as `sender`,
  `order`.`recipient` as `recipient`,
  `order`.`type` as `type`,
  `order`.`weight` as `weight`,
  `order`.`create_time` as `create_time`,
  `order`.`timeliness` as `timeliness`,
  `order`.`fare` as `fare`
from `order`
  join `customer` on `order`.`customer_id` = `customer`.`id`
where `order`.`create_time` > date_add(now(), interval -1 month);
drop view if exists `each_shipment_bill`;
create view `each_shipment_bill` as
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  `order`.`sender` as `sender`,
  `order`.`recipient` as `recipient`,
  `order`.`type` as `type`,
  `order`.`weight` as `weight`,
  `order`.`create_time` as `create_time`,
  `order`.`timeliness` as `timeliness`,
  `order`.`fare` as `fare`
from `order`
  join `customer` on `order`.`customer_id` = `customer`.`id`
where `order`.`create_time` > date_add(now(), interval -1 month);