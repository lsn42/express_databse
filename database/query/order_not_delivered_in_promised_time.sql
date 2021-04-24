select `order`.`id` as `id`,
`order`.`sender` as `sender`,
`order`.`recipient` as `recipient`,
`order`.`type` as `type`,
`order`.`weight` as `weight`,
`order`.`create_time` as `create_time`,
`order`.`timeliness` as `timeliness`,
`m`.`time` as `actual_arrived_time`
from `order`
  join(
    select `order`.`id` as `id`,
      `package_tracking`.`time` as `time`
    from `order`
      join `package_tracking` on `order`.`id` = `package_tracking`.`id`
    where `package_tracking`.`destination_name` is null
      and `package_tracking`.`type` = 'in'
  ) m
where `order`.`id` = `m`.`id`
  and `order`.`timeliness` < `m`.`time`