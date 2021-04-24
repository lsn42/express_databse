select `address`
from (
    select `address`,
      count(`address`) as `count`
    from `customer`
    group by `address`
  ) m
order by `count` desc
limit 1