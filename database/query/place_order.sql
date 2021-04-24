insert into `order`
values(
    1 + (
      select *
      from(
          select count(*)
          from `order`
        ) intermediate_table
    ),
    -- order id, which is auto generated
    1,
    -- modify customer id here
    '广东省潮州市湘桥区环城西路1号 小李',
    -- modify sender here
    '广东省广州市天河区五山路483号 小李',
    -- modify receiver here
    '普通包裹',
    -- modify the package type here
    '1.000',
    -- modify the package weight here
    now(),
    -- take current time as create time
    date_add(now(), interval 86400 second),
    -- modify the timeliness here (86400 second = 1 day)
    10
    -- modify the express fare here
  );