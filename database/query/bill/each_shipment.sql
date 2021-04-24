select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  `order`.`fare` as `fare`
from `order`
  join `customer` on `order`.`customer_id` = `customer`.`id`;