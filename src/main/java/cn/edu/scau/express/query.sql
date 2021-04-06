WITH a AS(
  select ex_id,
    customer.express_list.create_time,
    customer.transport_list.start_time,
    customer.transport_list.end_time,
    customer.transport_list.truck_id,
    customer.transport_list.plane_id,
    customer.transport_list.postman_id
  from customer.express_list
    inner join customer.transport_list on customer.express_list.trans_id = customer.transport_list.trans_id
  where customer.transport_list.start_time > customer.express_list.create_time
),
b AS(
  SELECT ex_id,
    customer.express_list.create_time,
    customer.stored_list.intime,
    customer.stored_list.outtime,
    customer.stored_list.stored_id
  FROM customer.express_list
    INNER JOIN customer.stored_list ON customer.express_list.store_id = customer.stored_list.stored_id
  where customer.stored_list.intime > customer.express_list.create_time
)
SELECT *
FROM a
  NATURAL JOIN b
WHERE (b.intime >= a.end_time
  or b.outtime <= a.start_time)
  and a.ex_id = 800005