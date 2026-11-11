# secondhand-market

校园二手交易平台 学习项目

## 进度

- [x] Day 1: 环境打通，Hello World 跑通
- [x] Day 2: Java 语法一，canBuy 五条件
- [x] Day 3: 环境迁回本地，代码重构
- [x] Day 4: 类与对象，static 与引用
- [x] Day 5: 封装与继承
- [x] Day 6: 集合，ArrayList 与 HashMap
- [x] Day 7: 内存版 CRUD，Repository 与 Service
- [x] Day 8: 复盘与 W2 收口
- [x] Day 9: JDBC 连接 MySQL
- [x] Day 10: ProductRepository 换成 MySQL
- [x] Day 11: 多表、外键与事务
- [ ] Day 12: 把 Connection 传进 Repository

## 环境准备

需要 MySQL 8.0。先建库建表：

```sql
CREATE DATABASE IF NOT EXISTS secondhand_market
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE secondhand_market;

CREATE TABLE product (
    id      INT           NOT NULL AUTO_INCREMENT,
    name    VARCHAR(100)  NOT NULL,
    price   DECIMAL(10,2) NOT NULL,
    stock   INT           NOT NULL DEFAULT 0,
    status  VARCHAR(20)   NOT NULL DEFAULT 'OFF_SALE',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order` (
    id         INT           NOT NULL AUTO_INCREMENT,
    user_name  VARCHAR(50)   NOT NULL,
    total      DECIMAL(10,2) NOT NULL,
    created_at DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_item (
    id         INT           NOT NULL AUTO_INCREMENT,
    order_id   INT           NOT NULL,
    product_id INT           NOT NULL,
    qty        INT           NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_order   FOREIGN KEY (order_id)   REFERENCES `order` (id),
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

建表顺序不能反，`order_item` 要引用前两张表。`order` 是 MySQL 的保留字，必须用反引号。`ENGINE=InnoDB` 不能省——另一常见的 MyISAM 不支持事务和外键，换过去会让 `commit` 和 `rollback` 静默失效。

## 依赖

MySQL 驱动不打进仓库。把 `mysql-connector-j-8.0.33.jar` 放进 `lib/` 目录，然后在 IDEA 里右键这个 jar，选 `Add as Library...`。

驱动版本要跟 MySQL 的大版本对上：MySQL 8.0 配 `mysql-connector-j-8.x`，别用 9.x。

## 配置

数据库账号密码不写在代码里。把 `db.properties.example` 复制一份改名为 `db.properties`，填上自己的密码：

```
db.url=jdbc:mysql://localhost:3306/secondhand_market?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true
db.user=root
db.password=你的密码
```

`db.properties` 已在 `.gitignore` 里，不会被提交。

连接串里四个参数缺一不可，少一个就报错：`useSSL=false` 关 SSL，`serverTimezone=Asia/Shanghai` 定时区，`characterEncoding=utf8` 防中文乱码，`allowPublicKeyRetrieval=true` 是 MySQL 8 特有的坑。

## 本地运行
```bash
javac -encoding UTF-8 *.java
java App
```

`App` 是入口，跑一遍完整的 CRUD 流程：上架、购买、改价、删除、异常拦截。

`DbTest` 是 JDBC 的连接与读写验证，单独跑它可以看到连接、插入、查询三步的输出。



## 学习笔记

 
- W1
-  git add 前可以先git status看一下已经有什么了
- Docker 容器与虚拟机的区别：虚拟机虚拟硬件、各装一套 OS；
  容器共享宿主内核、只隔离进程，所以启动是秒级。
- 第一件，字节码和 JVM 的关系，我原来理解反了。我原以为 Java 跨平台是因为 JVM 跨平台，其实正好相反：
- 跨平台的是那份 .class 字节码，JVM 恰恰是每个平台各写一套的那部分。正因为三套 JVM 各不相同，同一份 .class 才能在三个平台上跑。
- 想通这点之后，.gitignore 里为什么要写 *.class 也就顺理成章了——它是可再生的编译产物，还跟编译它的 JDK 版本绑定。

- 第二件，commit 是在本机拍下快照，push 才是真正把代码传出去。第一次 push 时终端打印 289 bytes，我以为自己看错了，整个项目怎么可能只有 289 字节。
- 后来才明白 Git 传的是对象，并不传文件：blob 存内容、tree 存目录结构、commit 存指向关系和说明，内容没变的部分一个字节都不重发，最后只是把远端分支指针挪到新的 commit 上。

- 第三件，静默失败比崩溃危险得多。写数组求和时我把 sum += i 写成了加下标，跑 {} 返回 0、跑 {1,0,2} 返回 3，两个都是正确答案，只有 {5} 暴露了问题。
- 这件事让我第一次分清两种错误：崩溃会立刻被发现，因为它会触发告警；算错会被一路放过。所以测试数据的作用在于看“我如果把代码改错，这条用例会不会失败”，仅“跑一下看看”是不够的；不会失败的用例等于没测。

 字段 public 之后，你失去的不是某个字段的“隐私”，而是对「谁能碰它、怎么碰它、碰了会怎样」这件事的控制权。数据变成了一间没有门锁的屋子，任何一行代码都能进去乱翻。