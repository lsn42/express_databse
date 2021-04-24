select *
from `customer`
where id =(
    select `customer`.`id`
    from (
        select `transport_event`.`id` as `teid`
        from `truck`
          left join `transport_event` on `truck`.`method_id` = `transport_event`.`method_id`
        where `time` =(
            select max(`time`)
            from `truck`
              left join `transport_event` on `truck`.`method_id` = `transport_event`.`method_id`
            where `truck`.`id` = 1
          )
      ) m
      left join (
        `order`
        join `customer` on `order`.`customer_id` = `customer`.`id`
        join `transport` on `transport`.`order_id` = `order`.`id`
      ) on `m`.`teid` = `transport`.`event_id`
  )