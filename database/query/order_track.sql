-- -------------------------------------------------------------------------- --
-- select from view
select *
from package_tracking
where id = 1;
-- -------------------------------------------------------------------------- --
-- original select statement
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
where id = 1;
order by `transport_event`.`time` desc;