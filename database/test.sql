-- select * from truck join transport_method on truck.method_id=transport_method.id;
select *
from `order`
  join `transport` on `order`.id = `transport`.order_id
  join `transport_event` on `transport`.event_id = `transport_event`.id
where `order`.id = 2;