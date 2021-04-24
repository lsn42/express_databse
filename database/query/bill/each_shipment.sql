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
where `order`.`create_time` > date_add(now(), interval -1 month)