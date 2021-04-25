from bs4 import BeautifulSoup as bs
from functools import cmp_to_key
import json
import math
import networkx
import pymysql
import random
import requests
import time
import datetime

GAODE = "e8396f296b808b543a7aec70218659d2"
REGEO = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={lnt},{lat}"
GEO = "https://restapi.amap.com/v3/geocode/geo?key={key}&address={addr}"
CHINESE_NAME = "https://www.qmsjmfb.com/"
WAREHOUSES_GRAPH_PATH = "database/data/warehouses_graph"

TIME_FORMAT = "%Y-%m-%d %H:%M:%S"

CAR_PLATE_PROVINCE = ["京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "新",
                      "苏", "浙", "赣", "鄂", "桂", "甘", "晋", "蒙", "陕", "吉", "闽", "赣", "粤", "青", "藏", "川", "宁", "琼"]
CAR_PLATE_CITY = "ABCDEFGHJKLMNPQRSTUVWXYZ"
CAR_PLATE_COMMON = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ"

FLIGHT_PERFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

TRUCK_TYPE = ["面包车", "小货车", "大货车", "半挂车", ]
PLANE_TYPE = ["大飞机", "小飞机", "直升飞机", ]
PACKAGE_TYPE = ["普通包裹", "小盒子", "大盒子", "中盒子", "木箱", "冷冻物品", ]
HAZARDOUS_MATERIAL = ["化学物品", "医疗废物", "放射性物质", ]
INTERNATIONAL_SHIPMENT = ["电子设备", "药品", "书籍", ]

WAREHOUSE_SQL = "insert into `warehouse` values({id}, '{name}', '{address}', {lnt}, {lat}, {vol});"
WORKER_SQL = "insert into `worker` values({id}, '{name}','{sex}','{tel}','{address}',{salary});"

TRANSPORT_METHOD_SQL = "insert into `transport_method` values({method_id}, '{name}');"
TRUCK_SQL = "insert into `truck` values({id}, {method_id}, {worker_id}, '{truck_type}', '{plate}', {vol});"
PLANE_SQL = "insert into `plane` values({id}, {method_id}, {worker_id}, '{plane_type}', '{flight}', {vol});"
PORTER_SQL = "insert into `porter` values({id}, {method_id}, {worker_id});"

CUSTOMER_SQL = "insert into `customer` values({id}, '{name}','{sex}','{tel}','{address}');"
PAY_METHOD_SQL = "insert into `pay_method` values({id}, {customer_id});"
PAY_WITH_CONTRACT_SQL = "insert into `pay_with_contract` values({id}, {method_id}, '{number}', '{bill}', '{description}');"
PAY_WITH_CREDIT_CARD_SQL = "insert into `pay_with_credit_card` values({id}, {method_id}, '{number}');"
CONTRACT_DESCRIPTION_1 = "每月可邮寄{count}件普通物品"

ORDER_SQL = "insert into `order` values({id}, {customer_id}, '{sender}', '{recipient}', '{package_type}', {weight}, '{create_time}', '{timeliness}', {fare});"
TRANSPORT_EVENT_SQL = "insert into `transport_event` values({id}, {method_id}, '{time}', '{transport_type}', {source}, {destination});"
TRANSPORT_SQL = "insert into `transport` values({order_id}, {event_id});"

ORDER_TRANSPORT_QUERY = "select * from `order` join `transport` on `order`.id = `transport`.order_id join `transport_event` on `transport`.event_id = `transport_event`.id;"

ON_TIME_RATE = 0.95

mysql_conn = pymysql.connect(
    host="127.0.0.1", port=3306, user="root", password="20001005", db="express")


def get_china_location():
    NORTH = 53.55
    SOUTH = 3.86
    EAST = 135.05
    WEST = 73.66
    while True:
        lnt = random.uniform(WEST, EAST)
        lat = random.uniform(SOUTH, NORTH)
        r = json.loads(requests.get(REGEO.format(
            key=GAODE, lnt=lnt, lat=lat)).text)
        if r["regeocode"]["addressComponent"]["country"] == "中国" and r["regeocode"]["formatted_address"] != "中华人民共和国" and type(r["regeocode"]["formatted_address"]) == type(""):
            return (r["regeocode"]["formatted_address"], lnt, lat)


def get_coordinate_of(addr):
    r = json.loads(requests.get(GEO.format(key=GAODE, addr=addr)).text)
    lnt, lat = r["geocodes"][0]["location"].split(",")
    return (float(lnt), float(lat))


def get_people_names(c):
    r = requests.post(CHINESE_NAME, data={"num": c})
    r.encoding = r.apparent_encoding
    soup = bs(r.text, "html.parser")
    selector = soup.select("body > div.name_box > ul.name_show")[0]
    names = []
    for i in selector.find_all("li"):
        names.append(i.get_text().strip())
    return names


def generate_warehouse(si, c):
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            r = get_china_location()
            sql = WAREHOUSE_SQL.format(
                id=si+i,
                name=r[0]+"仓库",
                address=r[0],
                lnt=r[1],
                lat=r[2],
                vol=random.randrange(50, 100000))
            cursor.execute(sql)
            mysql_conn.commit()


def generate_worker(si, c):
    names = get_people_names(c)
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            addr = get_china_location()
            salary = int(random.gauss(10000, 5000))
            salary = 3000 if salary < 3000 else salary
            sql = WORKER_SQL.format(
                id=si+i, name=names[i],
                sex="男"if random.randint(0, 1) == 0 else "女",
                tel="1"+"".join(random.choices("0123456789", k=10)),
                address=addr[0],
                salary=salary)
            cursor.execute(sql)
            mysql_conn.commit()


def generate_truck(si, msi, c):
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            w = random.choice(WORKERS)
            tt = random.choice(TRUCK_TYPE)
            plate = random.choice(CAR_PLATE_PROVINCE) + \
                random.choice(CAR_PLATE_CITY)+"-" + \
                random.choice(["", "D"]) + \
                "".join(random.choices(CAR_PLATE_COMMON, k=5))
            sql = TRANSPORT_METHOD_SQL.format(
                method_id=msi+i,
                name=tt + " " + plate)
            cursor.execute(sql)
            sql = TRUCK_SQL.format(
                id=si+i,
                method_id=msi+i,
                worker_id=w[0],
                truck_type=tt,
                plate=plate,
                vol=random.randrange(50, 10000))
            cursor.execute(sql)
            mysql_conn.commit()


def generate_plane(si, msi, c):
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            w = random.choice(WORKERS)
            pt = random.choice(PLANE_TYPE)
            flight = "".join(random.choices(FLIGHT_PERFIX, k=3)) +\
                "".join(random.choices("0123456789", k=4))
            sql = TRANSPORT_METHOD_SQL.format(
                method_id=msi+i,
                name=pt + " " + flight)
            cursor.execute(sql)
            sql = PLANE_SQL.format(
                id=si+i,
                method_id=msi+i,
                worker_id=w[0],
                plane_type=pt,
                flight=flight,
                vol=random.randrange(50, 10000))
            cursor.execute(sql)
            mysql_conn.commit()


def generate_porter(si, msi, c):
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            w = random.choice(WORKERS)
            sql = TRANSPORT_METHOD_SQL.format(
                method_id=msi+i,
                name="快递员："+w[1])
            cursor.execute(sql)
            sql = PORTER_SQL.format(
                id=si+i,
                method_id=msi+i,
                worker_id=w[0])
            cursor.execute(sql)
            mysql_conn.commit()


def generate_customer(si, psi, pcsi, pccsi, c):
    '''
    start_index pay_method_start_index pay_with_contract_start_index
    pay_with_credit_card_start_index count
    '''
    names = get_people_names(c)
    rpc = 0  # recorded pay_with_contract
    rpcc = 0  # recorded pay_with_credit_card
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            addr = get_china_location()
            sql = CUSTOMER_SQL.format(
                id=si+i,
                name=names[i],
                sex="男"if random.randint(0, 1) == 0 else "女",
                tel="1"+"".join(random.choices("0123456789", k=10)),
                address=addr[0])
            cursor.execute(sql)

            pcc = int(random.gauss(0, 1))  # contract
            for j in range(pcc):
                ci = random.randrange(100, 3000)
                sql = PAY_METHOD_SQL.format(
                    id=psi+rpc+rpcc,
                    customer_id=si+i)
                cursor.execute(sql)
                sql = PAY_WITH_CONTRACT_SQL.format(
                    id=pcsi+rpc,
                    method_id=psi+rpc+rpcc,
                    number="".join(random.choices("0123456789", k=16)),
                    bill=int(random.gauss(ci*4, ci)),
                    description=CONTRACT_DESCRIPTION_1.format(count=ci))
                cursor.execute(sql)
                rpc += 1

            pccc = int(random.gauss(2, 1))  # credit card
            for j in range(pccc):
                sql = PAY_METHOD_SQL.format(
                    id=psi+rpc+rpcc,
                    customer_id=si+i)
                cursor.execute(sql)
                sql = PAY_WITH_CREDIT_CARD_SQL.format(
                    id=pccsi+rpcc,
                    method_id=psi+rpc+rpcc,
                    number="".join(random.choices("0123456789", k=16)))
                cursor.execute(sql)
                rpcc += 1
            mysql_conn.commit()


def generate_order(si, c, st=int(time.time())):
    names = get_people_names(c)
    with mysql_conn.cursor() as cursor:
        for i in range(c):
            cu = random.choice(CUSTOMERS)
            s = get_china_location()
            r = get_china_location()
            now = st
            fare = int(random.gauss(8, 4))
            fare = 2 if fare < 2 else fare
            sql = ORDER_SQL.format(
                id=si+i,
                customer_id=cu[0],
                sender=s[0]+" "+cu[1],
                recipient=r[0]+" "+names[i],
                package_type=random.choice(PACKAGE_TYPE),
                weight=random.randrange(10, 10000)/1000,
                create_time=time.strftime(TIME_FORMAT, time.localtime(
                    random.randrange(now-60*60*24, now))),
                timeliness=time.strftime(TIME_FORMAT, time.localtime(
                    random.randrange(now, now+5*60*60*24))),
                fare=fare)
            cursor.execute(sql)
        mysql_conn.commit()


def transport(tesi):
    tec = 0
    with mysql_conn.cursor() as cursor:

        cursor.execute(ORDER_TRANSPORT_QUERY)
        rot = cursor.fetchall()
        cursor.execute("select * from `porter`;")
        rp = cursor.fetchall()

        eo = [i[0] for i in ORDER]
        empty_order = []
        for i in rot:
            if i[0] in eo:
                eo.remove(i[0])
        for i in ORDER:
            if i[0] in eo:
                empty_order.append(i)

        for o in empty_order:
            sw = get_nearest_warehouse(
                get_coordinate_of(o[2]))  # start warehouse id
            sp = random.choice(rp)[1]  # start porter id
            # destination warehouse id
            dw = get_nearest_warehouse(get_coordinate_of(o[3]))
            dp = random.choice(rp)[1]  # destination porter id
            st = o[6]  # start time
            et = o[7]  # end time
            if et < st:  # invalid order
                continue

            l = random.randrange(2, 7)  # middle transport step
            pm = random.sample(
                [i[0] for i in TRANSPORT_METHODS if not i[1].startswith("快递员")], l)  # path methods
            pt = sorted(random.sample(
                range(int(((et-st).seconds)/ON_TIME_RATE)), l*2+1+2))  # path times
            p = networkx.dijkstra_path(
                WAREHOUSES_GRAPH, source=sw, target=dw)  # dijkstra path
            pw = random.sample(p[1:-1], l)  # path warehouse
            pw.append(p[0])
            pw.append(p[-1])
            def cmp(x, y): return p.index(x) < p.index(y)
            for i in range(len(pw)):
                for j in range(i):
                    if cmp(pw[i], pw[j]):
                        t = pw[i]
                        pw[i] = pw[j]
                        pw[j] = t
            # print([(st+datetime.timedelta(seconds=i)).strftime(TIME_FORMAT)
            #        for i in pt], pm, pw)

            # company recieve package from customer
            # warehouse 0 means customer
            sql = TRANSPORT_EVENT_SQL.format(
                id=tesi+tec,
                method_id=sp,
                time=st+datetime.timedelta(seconds=pt[0]),
                transport_type="in",
                source=0,
                destination=sw)
            cursor.execute(sql)
            sql = TRANSPORT_SQL.format(
                order_id=o[0],
                event_id=tesi+tec)
            tec += 1
            cursor.execute(sql)

            # transporting package
            # for each middle transport
            for i in range(l):
                sql = TRANSPORT_EVENT_SQL.format(
                    id=tesi+tec,
                    method_id=pm[i],
                    time=st+datetime.timedelta(seconds=pt[2*i+1]),
                    transport_type="out",
                    source=pw[i],
                    destination=pw[i+1])
                cursor.execute(sql)
                sql = TRANSPORT_SQL.format(
                    order_id=o[0],
                    event_id=tesi+tec)
                tec += 1
                cursor.execute(sql)
                sql = TRANSPORT_EVENT_SQL.format(
                    id=tesi+tec,
                    method_id=pm[i],
                    time=st+datetime.timedelta(seconds=pt[2*i+1+1]),
                    transport_type="in",
                    source=pw[i],
                    destination=pw[i+1])
                cursor.execute(sql)
                sql = TRANSPORT_SQL.format(
                    order_id=o[0],
                    event_id=tesi+tec)
                tec += 1
                cursor.execute(sql)

            # package arrived
            # warehouse 0 means customer
            sql = TRANSPORT_EVENT_SQL.format(
                id=tesi+tec,
                method_id=dp,
                time=st+datetime.timedelta(seconds=pt[-2]),
                transport_type="out",
                source=dw,
                destination=0)
            cursor.execute(sql)
            sql = TRANSPORT_SQL.format(
                order_id=o[0],
                event_id=tesi+tec)
            tec += 1
            cursor.execute(sql)
            sql = TRANSPORT_EVENT_SQL.format(
                id=tesi+tec,
                method_id=dp,
                time=st+datetime.timedelta(seconds=pt[-1]),
                transport_type="in",
                source=dw,
                destination=0)
            cursor.execute(sql)
            sql = TRANSPORT_SQL.format(
                order_id=o[0],
                event_id=tesi+tec)
            tec += 1
            cursor.execute(sql)

            mysql_conn.commit()


def get_data():
    global WORKERS, WAREHOUSES, TRANSPORT_METHODS, ORDER, TRANSPORT_EVENT, WAREHOUSES_GRAPH
    global TRANSPORT_METHODS, PLANES, TRUCKS, PORTERS
    global CUSTOMERS, PAY_METHODS, PAY_METHOD_CREDIT_CARD, PAY_METHOD_CONTRACT
    with mysql_conn.cursor() as cursor:

        sql = "select * from `worker`;"
        cursor.execute(sql)
        WORKERS = cursor.fetchall()

        sql = "select * from `warehouse` where id>0;"
        cursor.execute(sql)
        WAREHOUSES = cursor.fetchall()

        sql = "select * from `transport_method`;"
        cursor.execute(sql)
        TRANSPORT_METHODS = cursor.fetchall()
        sql = "select * from `plane`;"
        cursor.execute(sql)
        PLANES = cursor.fetchall()
        sql = "select * from `truck`;"
        cursor.execute(sql)
        TRUCKS = cursor.fetchall()
        sql = "select * from `porter`;"
        cursor.execute(sql)
        PORTERS = cursor.fetchall()

        sql = "select * from `customer`;"
        cursor.execute(sql)
        CUSTOMERS = cursor.fetchall()
        sql = "select * from `pay_method`;"
        cursor.execute(sql)
        PAY_METHODS = cursor.fetchall()
        sql = "select * from `pay_with_credit_card`;"
        cursor.execute(sql)
        PAY_METHOD_CREDIT_CARD = cursor.fetchall()
        sql = "select * from `pay_with_contract`;"
        cursor.execute(sql)
        PAY_METHOD_CONTRACT = cursor.fetchall()

        sql = "select * from `order`;"
        cursor.execute(sql)
        ORDER = cursor.fetchall()

        sql = "select * from `transport_event`;"
        cursor.execute(sql)
        TRANSPORT_EVENT = cursor.fetchall()

    try:
        WAREHOUSES_GRAPH = networkx.read_gpickle(WAREHOUSES_GRAPH_PATH)
        if len(WAREHOUSES) != len(WAREHOUSES_GRAPH.nodes):
            get_warehouse_graph()
            networkx.write_gpickle(WAREHOUSES_GRAPH, WAREHOUSES_GRAPH_PATH)
    except FileNotFoundError:
        get_warehouse_graph()
        networkx.write_gpickle(WAREHOUSES_GRAPH, WAREHOUSES_GRAPH_PATH)


def get_warehouse_graph():
    global WAREHOUSES_GRAPH
    whl = [[i[0], float(i[3]), float(i[4])] for i in WAREHOUSES]
    wg = networkx.Graph()
    for w in whl:
        wg.add_node(w[0])
    for w1 in whl:
        for w2 in whl:
            if w1 != w2:
                wg.add_edge(
                    w1[0], w2[0], weight=distance(w1[1], w1[2], w2[1], w2[2]))
    WAREHOUSES_GRAPH = networkx.minimum_spanning_tree(wg)


def last(l):
    if len(l) > 0:
        m = l[0][0]
        for i in l:
            m = i[0] if i[0] > m else m
        return m
    else:
        return 0


def distance(lnt1, lat1, lnt2, lat2):
    lnt1, lat1, lnt2, lat2 = map(math.radians, [lnt1, lat1, lnt2, lat2])
    dlnt = lnt2 - lnt1
    dlat = lat2 - lat1
    a = math.sin(dlat/2)**2 + math.cos(lat1) * \
        math.cos(lat2) * math.sin(dlnt/2)**2
    c = 2 * math.asin(math.sqrt(a))
    r = 6371
    return c * r


def get_nearest_warehouse(loc):
    lnt, lat = loc
    min = WAREHOUSES[0]
    d = distance(lnt, lat, float(WAREHOUSES[0][3]), float(WAREHOUSES[0][4]))
    for w in WAREHOUSES:
        dd = distance(lnt, lat, float(w[3]), float(w[4]))
        if dd < d:
            d = dd
            min = w
    return min[0]


WAREHOUSES = []
WORKERS = []

TRANSPORT_METHODS = []
PLANES = []
TRUCKS = []
PORTERS = []

CUSTOMERS = []
PAY_METHODS = []
PAY_METHOD_CREDIT_CARD = []
PAY_METHOD_CONTRACT = []

ORDER = []
TRANSPORT_EVENT = []

WAREHOUSES_GRAPH = None

INIT_INFO = 40*"-" + '''
warehouse count: {whc}
worker count: {wc}
customer count: {cc}
'''+40*"-"

if __name__ == "__main__":
    get_data()
    print(INIT_INFO.format(whc=len(WAREHOUSES),
                           wc=len(WORKERS), cc=len(CUSTOMERS)))

    # transport(last(TRANSPORT_EVENT)+1)

    # import matplotlib.pyplot as plt
    # plt.figure(figsize=(8, 8))
    # networkx.draw_networkx(WAREHOUSES_GRAPH)
    # plt.show()

    # s = random.randint(1, len(whr))
    # d = random.randint(1, len(whr))
    # path = networkx.dijkstra_path(WG1, source=s, target=d)
    # print(path)
    # d = networkx.dijkstra_path_length(WG1, source=s, target=d)
    # print(d)

    # warehouse(last(WAREHOUSES)+1, 500)
    # worker(last(WORKERS)+1, 900)
    # get_data()
    # truck(1, 1, 10)
    # plane(11, 1, 10)
    # porter(21, 1, 10)
    # order(2, 10)

    while True:
        print(">>> ", end="")
        c = input().split(" ")
        if c[0] == "eval":
            eval(c[1])
        elif c[0] == "init":
            # generate customers
            generate_customer(last(CUSTOMERS)+1,
                              last(PAY_METHODS)+1,
                              last(PAY_METHOD_CONTRACT)+1,
                              last(PAY_METHOD_CREDIT_CARD)+1,
                              100)
        elif c[0] == "info":
            get_data()
            print(INIT_INFO.format(whc=len(WAREHOUSES),
                                   wc=len(WORKERS), cc=len(CUSTOMERS)))
        elif c[0] == "add":
            if c[1] == "customer":
                generate_customer(last(CUSTOMERS)+1,
                                  last(PAY_METHODS)+1,
                                  last(PAY_METHOD_CONTRACT)+1,
                                  last(PAY_METHOD_CREDIT_CARD)+1,
                                  int(c[2]))
            if c[1] == "worker":
                generate_worker(last(WORKERS)+1,
                                int(c[2]))
            if c[1] == "warehouse":
                generate_warehouse(last(WAREHOUSES)+1,
                                   int(c[2]))
            if c[1] == "transport_method":
                generate_truck(last(TRUCKS)+1,
                               last(TRANSPORT_METHODS)+1,
                               80)
        elif c[0] == "transport":
            transport(last(TRANSPORT_EVENT))
        elif c[0] == "exit" or c[0] == "q":
            break
