select *
from `transport`
  join `transport_event` on `transport`.`event_id` = `transport_event`.`id`
where `type` = 'in'
  and `destination` = 1
  and `order_id` not in (
    select `order_id` as `id`
    from `transport`
      join `transport_event` on `transport`.`event_id` = `transport_event`.`id`
    where `type` = 'out'
      and `source` = 1
  );