select `warehouse_s`.`name`,
  `warehouse_d`.`name`,
  `transport_event`.`time`,
  `transport_event`.`type`
from `order`
  join `transport` on `order`.`id` = `transport`.`order_id`
  join `transport_event` on `transport`.`event_id` = `transport_event`.`id`
  join `warehouse` as `warehouse_s` on `transport_event`.`source` = `warehouse_s`.`id`
  join `warehouse` as `warehouse_d` on `transport_event`.`destination` = `warehouse_d`.`id`
where `order`.id = 1 -- modify id here
order by `transport_event`.`time`;