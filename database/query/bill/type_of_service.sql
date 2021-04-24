select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  '信用卡' as `pay_method`,
  `m`.`amount` as `amount`
from(
    select `customer_id` as `id`,
      sum(`fare`) as `amount`
    from `order`
    where `create_time` > date_add(now(), interval -1 month)
    group by `customer_id`
  ) m
  natural join `customer`
union all
select `customer`.`id` as `id`,
  `customer`.`name` as `name`,
  `customer`.`address` as `address`,
  '合同' as `pay_method`,
  sum(`pay_with_contract`.`monthly_bill`) as `amount`
from `pay_with_contract`
  join `pay_method` on `pay_with_contract`.`pay_method_id` = `pay_method`.`id`
  join `customer` on `pay_method`.`customer_id` = `customer`.`id`
group by `customer`.`id`;