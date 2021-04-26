drop procedure if exists `create_common_order`;
drop procedure if exists `create_hazardous_order`;
drop procedure if exists `create_international_order`;

delimiter $$
create procedure `create_common_order`(
  in p_id int,
  in p_customer_id int,
  in p_sender varchar(256) character set utf8mb4,
  in p_recipient varchar(256) character set utf8mb4,
  in p_type varchar(64) character set utf8mb4,
  in p_weight decimal(10, 3),
  in p_create_time datetime,
  in p_timeliness datetime,
  in p_fare decimal(10, 2)
) begin
insert into `order`
values(
    p_id,
    p_customer_id,
    p_sender,
    p_recipient,
    p_type,
    p_weight,
    p_create_time,
    p_timeliness,
    p_fare
  );
end$$
create procedure `create_hazardous_order`(
  in p_id int,
  in p_customer_id int,
  in p_sender varchar(256) character set utf8mb4,
  in p_recipient varchar(256) character set utf8mb4,
  in p_type varchar(64) character set utf8mb4,
  in p_weight decimal(10, 3),
  in p_create_time datetime,
  in p_timeliness datetime,
  in p_fare decimal(10, 2),
  in p_info text
) begin
insert into `order`
values(
    p_id,
    p_customer_id,
    p_sender,
    p_recipient,
    p_type,
    p_weight,
    p_create_time,
    p_timeliness,
    p_fare
  );
insert into `hazardous_material`
values(
    p_id,
    p_info
  );
end$$
create procedure `create_international_order`(
  in p_id int,
  in p_customer_id int,
  in p_sender varchar(256) character set utf8mb4,
  in p_recipient varchar(256) character set utf8mb4,
  in p_type varchar(64) character set utf8mb4,
  in p_weight decimal(10, 3),
  in p_create_time datetime,
  in p_timeliness datetime,
  in p_fare decimal(10, 2),
  in p_stating text,
  in p_value decimal(10, 2)
) begin
insert into `order`
values(
    p_id,
    p_customer_id,
    p_sender,
    p_recipient,
    p_type,
    p_weight,
    p_create_time,
    p_timeliness,
    p_fare
  );
insert into `international_shipment`
values(
    p_id,
    p_stating,
    p_value
  );
end$$
delimiter ;