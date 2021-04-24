select `transport_event`.`id`
from `truck`
  left join `transport_event` on `truck`.`method_id` = `transport_event`.`method_id`
where `time` =(
    select `time`
    from `truck`
      left join `transport_event` on `truck`.`method_id` = `transport_event`.`method_id`
    where `truck`.`id` = 1
    order by `time` desc
    limit 1 offset 1
  )