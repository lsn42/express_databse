insert into `customer`
values(
    1,
    '小明',
    '男',
    '13234567891',
    '广东省广州市天河区华南农业大学泰山区第1栋学生宿舍'
  );
insert into `pay_method`
values(1, 1);
insert into `pay_with_contract`
values(1, 1, '1234567891011121', 100, '每月可邮寄100件普通物品');
insert into `customer`
values(
    2,
    '小强',
    '男',
    '13345678910',
    '广东省广州市天河区华南农业大学泰山区第1栋学生宿舍'
  );
insert into `pay_method`
values(2, 2);
insert into `pay_with_credit_card`
values(1, 2, '1234567891011121');
insert into `customer`
values(
    3,
    '小红',
    '女',
    '13456789101',
    '广东省广州市天河区华南农业大学泰山区第2栋学生宿舍'
  );
insert into `customer`
values(
    4,
    '小刚',
    '男',
    '13567891011',
    '广东省广州市天河区华南农业大学华山区第3栋学生宿舍'
  );
insert into `pay_method`
values(3, 4);
insert into `pay_with_contract`
values(2, 3, '2345678910111213', 200, '每月可邮寄200件普通物品');
-- -------------------------------------------------------------------------- --
insert into `worker`
values(
    1,
    '小明',
    '男',
    '13234567891',
    '广东省广州市天河区华南农业大学泰山区第1栋学生宿舍',
    10000
  );
insert into `worker`
values(
    2,
    '小红',
    '女',
    '13456789101',
    '广东省广州市天河区华南农业大学泰山区第2栋学生宿舍',
    10000
  );
-- -------------------------------------------------------------------------- --
insert into `warehouse`
values(
    1,
    '华南农业大学第四教学楼仓库',
    '广东省广州市天河区华南农业大学第四教学楼',
    113.365941,
    23.152107,
    50000
  );
insert into `warehouse`
values(
    2,
    '华南农业大学图书馆仓库',
    '广东省广州市天河区华南农业大学图书馆',
    113.353424,
    23.157677,
    100000
  );
insert into `warehouse`
values(
    3,
    '广州市仓库',
    '广东省广州市越秀区府前路1号',
    113.264379,
    23.129097,
    80000
  );
insert into `warehouse`
values(
    4,
    '深圳市仓库',
    '广东省深圳市南山区招商街道碧海路3号',
    113.894260,
    22.464894,
    100000
  );
insert into `warehouse`
values(
    5,
    '潮州市仓库',
    '广东省潮州市湘桥区金山街道中山路98号',
    116.644290,
    23.670453,
    50000
  );
-- -------------------------------------------------------------------------- --
insert into `transport_method`
values(1, '大货车 粤A-12345');
insert into `truck`
values(1721, 1, 1, '大货车', '粤A-12345', 10000);
insert into `transport_method`
values(2, '小货车 粤B-12345');
insert into `truck`
values(1, 2, 1, '小货车', '粤B-12345', 1000);
insert into `transport_method`
values(3, '小货车 粤C-12345');
insert into `truck`
values(2, 3, 2, '小货车', '粤C-12345', 2000);
insert into `transport_method`
values(4, '快递员：小明');
insert into `porter`
values(1, 4, 1);
insert into `transport_method`
values(5, '快递员：小红');
insert into `porter`
values(2, 5, 2);
-- -------------------------------------------------------------------------- --
insert into `order`
values(
    1,
    1,
    '广东省潮州市湘桥区环城西路1号 小明',
    '广东省广州市天河区华南农业大学泰山区第6栋学生宿舍 小明',
    '普通包裹',
    1,
    '2021-04-26 08:00:00',
    '2021-04-28 08:00:00',
    6
  );
insert into `order`
values(
    2,
    1,
    '广东省潮州市湘桥区环城西路1号 小明',
    '广东省广州市天河区华南农业大学泰山区第6栋学生宿舍 小明',
    '大盒子',
    5,
    '2021-04-26 08:00:00',
    '2021-04-30 08:00:00',
    20
  );
insert into `hazardous_material`
values(2, '化学物品');
insert into `order`
values(
    3,
    2,
    '香港特别行政区油尖旺区文蔚楼中座 小红',
    '广东省广州市天河区华南农业大学泰山区第2栋学生宿舍 小红',
    '大盒子',
    0.5,
    '2021-04-15 08:00:00',
    '2021-04-30 12:00:00',
    24
  );
insert into `international_shipment`
values(3, '电子设备', 10000);
insert into `order`
values(
    4,
    1,
    '上海市浦东新区祝桥镇S32申嘉湖高速上海浦东国际机场 小明',
    '广东省广州市天河区华南农业大学泰山区第6栋学生宿舍 小明',
    '普通包裹',
    1.5,
    '2021-04-26 08:00:00',
    '2021-05-04 08:00:00',
    10
  );
-- -------------------------------------------------------------------------- --
insert into `transport_event`
values(1, 4, '2021-04-26 09:02:30', 'in', 0, 5);
insert into `transport`
values(1, 1);
insert into `transport_event`
values(2, 4, '2021-04-26 09:38:40', 'in', 0, 5);
insert into `transport`
values(2, 2);
insert into `transport_event`
values(3, 1, '2021-04-26 10:01:05', 'out', 5, 4);
insert into `transport`
values(1, 3);
insert into `transport`
values(2, 3);
insert into `transport_event`
values(4, 1, '2021-04-26 14:54:32', 'in', 5, 4);
insert into `transport`
values(1, 4);
insert into `transport`
values(2, 4);
insert into `transport_event`
values(5, 1, '2021-04-26 15:24:15', 'out', 4, 3);
insert into `transport`
values(2, 5);
insert into `transport_event`
values(6, 3, '2021-04-26 16:37:44', 'out', 4, 3);
insert into `transport`
values(1, 6);
insert into `transport_event`
values(7, 3, '2021-04-26 20:47:55', 'in', 4, 3);
insert into `transport`
values(1, 7);
insert into `transport_event`
values(8, 3, '2021-04-26 20:59:31', 'out', 3, 1);
insert into `transport`
values(1, 8);
insert into `transport_event`
values(9, 3, '2021-04-26 21:43:07', 'in', 3, 1);
insert into `transport`
values(1, 9);
insert into `transport_event`
values(10, 5, '2021-04-26 21:44:58', 'out', 1, 0);
insert into `transport`
values(1, 10);
insert into `transport_event`
values(11, 5, '2021-04-26 22:00:16', 'in', 1, 0);
insert into `transport`
values(1, 11);