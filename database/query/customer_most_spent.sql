select *
from(
    select `customer_id` as `id`,
      sum(`fare`) as `fare`
    from `order`
    where `create_time` > date_add(now(), interval -1 month)
    group by `customer_id`
    order by `fare` desc
    limit 1
  ) m natural join `customer`