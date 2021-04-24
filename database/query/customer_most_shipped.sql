select *
from(
    select `customer_id` as `id`,
      count(*) as `count`
    from `order`
    where `create_time` > date_add(now(), interval -1 month)
    group by `customer_id`
    order by `count` desc
    limit 1
  ) m natural join `customer`