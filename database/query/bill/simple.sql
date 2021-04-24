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