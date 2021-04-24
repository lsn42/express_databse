insert into `customer`
values(
    1,
    '小明',
    '男',
    '+8613234567891',
    '广东省广州市天河区华南农业大学泰山区第1栋学生宿舍'
  );
insert into `pay_method`
values(1, 1);
insert into `pay_with_contract`
values(1, 1, '1234567891011121', 100, '邮寄100件普通物品');
insert into `customer`
values(
    2,
    '小强',
    '男',
    '+8613345678910',
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
    '+8613456789101',
    '广东省广州市天河区华南农业大学泰山区第2栋学生宿舍'
  );
insert into `customer`
values(
    4,
    '小刚',
    '男',
    '+8613567891011',
    '广东省广州市天河区华南农业大学华山区第3栋学生宿舍'
  );
insert into `pay_method`
values(3, 4);
insert into `pay_with_contract`
values(2, 3, '2345678910111213', 200, '邮寄200件普通物品');