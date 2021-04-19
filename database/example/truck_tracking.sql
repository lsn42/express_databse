-- -------------------------------------------------------------------------- --
-- select from view
select *
from truck_tracking
where id = 1;
-- -------------------------------------------------------------------------- --
-- original select statement
select `truck`.`id`,
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
where id = 1;
order by `transport_event`.`time` desc;