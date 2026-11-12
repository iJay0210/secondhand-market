##  Day 1

今天做了什么： 建立项目仓库，因为自己电脑在修用的舍友电脑所以启动虚拟环境（不在同学电脑上下载软件）codespaces,启动后验证环境是通的，把今天写的代码提交到仓库中，便于后续管理

卡在哪里 国内ip访问GitHub不稳定，git commit中详细步骤理解不清（不知道为什么后面要加-m 后通过AI解析搞清）

明天第一件事 复盘今天做的事 


还没搞懂的  
Hello.class 里到底是什么？ 为什么这个文件能在任何一台装了 JVM 的机器上跑，而不只是你这台？——这是 Java 跨平台的核心，也是你后面学 JVM 的入口
为什么文件名必须和 public 类名一致？ 不一致会报什么错，Java 为什么要这样设计？
git push 的时候，代码是怎么从这台云端虚拟机传到 GitHub 的？ 中间经过了哪些环节？
（必须填一个，填不出来说明今天没思考）


AI 帮了多少
基本都是自己写的，当中一些不清楚的内容去查了ai让其解释




## Day 2

今天做了什么： 脱离ai自己写出一个方法判断这个用户是否能购买，其实是对之前自学的一个复习，这些东西暑假的时候就已经会了

卡在哪里： 方法中引用类中的属性的时候报错说找不到，循环语句写的可读性较差，基本逻辑对，但是后续不知道具体哪里不符合判断条件，可以改进

还没搞懂：一些英语单词的命名吧（需求量为什么要叫qty）

一个方法返回 boolean，怎么才能知道它为什么返回 false？

ai帮助基本为0，帮忙翻译一些报错信息

## Day 3
今天做了什么：把开发环境从浏览器（Codespaces）搬回你自己的电脑，把 D2 欠下的账清零。为复盘日。
卡在哪里： 无
还没搞懂的：第二个，IDEA 那个绿三角背后到底执行了什么命令。（执行javac?）
ai帮了多少：0

## Day 4
练习五： 方法	该不该 static	理由
User.totalBought()	不能	方法体里读 history，那是“我买过什么”，每个用户不同
BuyChecker.canBuy(p,u,qty)	可以，且建议 static	全靠三个参数算，体内没有“我”；BuyChecker 只是规则盒子，没有状态
main(String[] args)	必须 static	JVM 启动时没有任何对象，只能通过类名调用，没有 this 可用
Product.toString()	不能	要用自己的 name、price 拼字符串；而且它重写的是 Object 的实例方法，语法上也强制

今天做了什么：对象和变量到底是什么关系、构造方法是干什么的、一个方法凭什么挂在类上而不是对象上。三个问题都靠做实验，不靠读定义。
卡在哪里：删除掉static后带来的一系列报错。不理解加不加static的区别（这个方法体到底需不需要“我”）
还没搞懂的：除了static和final俩个非访问修饰符，还有哪些，用处是什么
ai帮了多少：解释static，给出例子帮助我更好的理解

## Day 5

今天做了什么：把 Product 和 User 的字段全改成 private，再补 getter 和带校验的 setter。校验写了三条，库存不能为负、单价必须大于 0、状态只能是 ON_SALE 或 OFF_SALE。构造方法原来直接 this.stock = stock，后来改成调 setStock，这样 new 一个非法对象也会被拦下来，实测 new Product("手机", -1, -100, "随便写") 真的抛了异常，报的是「单价必须大于 0」，说明第一个不合法的字段先炸，后面的根本没机会执行。练习五写了 DigitalProduct 继承 Product，super 放第一行，重写 toString 的时候用 super.toString() 把父类那半句接过来，没有从头拼一遍。

卡在哪里：全字段改 private 之后 IDE 一片红，跟之前删 static 那次一样，一开始有点慌。后来发现红色其实是一张地图，把「谁在直接读我的字段」全标出来了，数一下才知道外面有十几处。真正卡住的是检验第二题，问「字段写成 public 你失去了什么」，我脑子里只有「变成公共的了」，答不出失去什么，因为我一直把 public 当成「更方便」，没想过它同时是「少了一层保护」。第三题也答偏了，我答的是「字段 private 了只能调 setter」，但题目问的是构造方法为什么也要走 setter。构造方法在自己类内部，其实不受 private 限制，直接写 this.stock = stock 编译是能过的，所以它不调 setter 就等于绕过了校验。这两题是同一个毛病，我答的是「怎么调用」，不是「为什么」。

还没搞懂的：getHistory 把 history 数组直接返回，外面 h[0] = 999 一改，totalBought 就从 3 变成了 1001，setHistory 里的校验一次都没被调用。这个是 ai 跑实验跑出来的，我自己完全没发现，当时只觉得「反正是我自己在用，不会乱改」。super 为什么必须第一行也只停留在「不写会报错」，说不清「子类的东西全建立在父类之上，所以父类必须先建好」这个理由。

ai帮了多少：封装那两题基本是 ai 讲完我才懂的。getHistory 这个洞是 ai 跑实验发现的，代码改动是自己写的。

## Day 6

今天做了什么：把 User 的 history 从 int[] 换成 List<Integer>，删掉 setHistory 换成 addPurchase，里面加了一条 qty <= 0 就抛异常的校验。getHistory 改成 return List.copyOf(history)，把昨天那个洞堵上，实测外面 clear() 会抛 UnsupportedOperationException，totalBought 还是 3，一点没变。另外新建了 Catalog 用 HashMap 按商品名查商品，又用 Map<String, Integer> 数每个商品卖了几件，累加用的是 getOrDefault(key, 0) + 1，直接 get 再 +1 第一件就空指针。开场还验证了多态，Product 变量装 DigitalProduct，打印出来带「保修 24 个月」那句，说明走的是子类的 toString。

卡在哪里：今天最意外的是 Integer 的 ==。两个 HashMap 里各存一个 1000，用 == 比是 false，用 equals 比才是 true。我原以为都是 1000 应该相等，后来才明白 == 比的是地址，而 -128 到 127 之外的 Integer 每次装箱都新建一个对象，没有缓存池可以复用，所以 127 == 127 是 true，1000 == 1000 是 false。这个坑阴的地方在于它时对时错，数据都是个位数的时候跑得好好的，数据一大就开始出错，以后比 Integer 的值一律用 equals。另外还卡了两次，一次是 Catalog 没有 main，左边没有绿三角，我不知道该怎么「跑一下」，后来才明白要单独写一个带 main 的类去驱动它；一次是装箱拆箱，知道 List<> 里要写 Integer 不能写 int，但说不清装箱拆箱到底是什么，直到 ai 反编译看了字节码，源码里一个 valueOf 都没写，字节码里却有四个，我才真的信了这是编译器在替我做事。

还没搞懂的：HashMap 打印出来是 {手机=2, 耳机=1}，看着像按插入顺序，但说是哈希算出来的，为什么正好对上了还没搞明白。for-each 遍历 List 的时候 remove 会抛 ConcurrentModificationException，这个还没亲手跑过，只知道会崩，不清楚迭代器内部是怎么发现「你绕过我改了结构」的。

ai帮了多少：装箱拆箱和 Integer 的 == 这两条基本靠 ai 讲。CatalogTest 和 SoldTest 两个测试类是 ai 写的，我只跑了一遍。业务代码的改动是自己做的。

## Day 7

今天做了什么：给 Product 加了自增 id，static nextId 配 final id，构造方法里 this.id = nextId++。新建 ProductRepository，用 Map<Integer, Product> 存，五个方法 save、findById、findAll、deleteById、count，findById 找不到就抛 IllegalArgumentException，findAll 返回 new ArrayList<>(store.values()) 而不是原件。新建 ProductService 放业务规则，create 默认 OFF_SALE，delete 要求库存为 0，buy 把 BuyChecker 判断、setStock 扣库存、addPurchase 记账、setBalance 扣钱串在一起。最后写 App 跑通八步完整流程，上架、上架前买失败、上架后买成功、改价、删除、删不掉、查不到、列全部，八步输出全对。再把 CatalogTest 改用 ProductRepository，删掉 Catalog.java。

卡在哪里：真正卡住的是练习五，把 CatalogTest 换成 Repository 的时候「查询怎么改」想不出来。Catalog.find 是按名字查，ProductRepository.findById 是按编号查，这不是换一个方法名，是换了查询的入口。我一开始写成 repo.findById("手机".equals())，报了两个错，一个是 equals 没给参数，一个是 String 转不了 int。后来才明白要先把 save 的返回值接住，用 p1.getId() 拿到编号，再按编号查。第二个卡点是 ProductService 里 changePrice，参数名写的是 price，方法体里写的是 newPrice，报「找不到符号 变量 newPrice」，抄的时候脑子里想着新价格，手上就写成了 newPrice。

还没搞懂的：检验第四题我一开始只会判断「需不需要 static」，说不出去掉之后会怎样，等于只知道该不该，不知道后果。ai 跑了个对比实验才看明白：去掉 static 之后每个商品都有自己的一份 nextId，初始化都是 1，各自加一之后那个 2 跟着对象一起没了，下一个 new 出来又是全新的 1，所以三个商品 id 全是 1。Repository 里三次 put 全落在 key 1 上，前两个被覆盖，仓库里只剩最后一个。而且编译不报错、运行也不报错，是静默的错。这件事让我意识到字段的判据和方法的判据不一样，方法是看「要不要我」，字段是看「是不是大家共用一个」。

ai帮了多少：ProductRepository、ProductService、App 三段的骨架是 ai 给的，我照着写。changePrice 的变量名错和 CatalogTest 的查询是我自己卡住的，ai 讲了原因，CatalogTest 整个是 ai 重写的。检验五题的批改、以及 static 那个对比实验是 ai 做的。

还剩的小事：Product.toString 里写的是 id + name，打印出来是「1手机」，编号和名字粘在一起，应该加个 "#" 和空格。

## W2 周复盘

这周能脱稿说清的：字段是 private 还不够，返回值把内部引用交出去，等于把修改权也交出去了——getHistory 返回 List.copyOf 就是为了堵这个。构造方法自己不受 private 限制，它可以直接 this.stock = stock 编译通过，所以它不调 setter 就等于绕过了校验。Integer 的 == 比的是地址，127 和 1000 结果不同是因为 valueOf 在 -128 到 127 之间复用池子里的对象，超出范围就每次新建。findById 找不到要抛异常，因为调用方确信这个东西存在，搜不到才该返回 null。Repository 和 Service 分开，换数据库只改一个文件，Service 一个字不动。去掉 nextId 的 static，每个商品都有自己的计数器，全从 1 开始数，三次 put 落在同一个 key 上，前两个被覆盖，而且编译运行都不报错。

还是背的：static 的判据。我知道「方法体里要不要用我」这句话，但昨天自测第一题还是只能说个大概，尤其是「参数里传进来的对象不算我」这一层，讲的时候会卡一下，得脑子里过一遍那个 static void printStock(Item self) 的例子才顺得下去。引用复制也是，知道「复制的是地址」，但让我当场把「复制地址」和「改字段」拆成两步讲，还是会说得黏在一起。这两个正好是 Day 4 就答错过的两处，隔了三天还是大概，说明当时是被讲通的，不是自己想通的。W3 讲多线程超卖的时候这两个会再撞上一次，到时候不能再糊过去。

这周改代码最痛的一次：练习五，把 CatalogTest 从 Catalog 换成 ProductRepository，卡在「查询怎么改」完全想不出来。Catalog.find 是按名字查，ProductRepository.findById 是按编号查，我一开始以为就是换个方法名，写成了 repo.findById("手机".equals())，报了两个错才知道这不是改名，是换了查询的入口。要先把 save 的返回值接住拿到 id，再按 id 去查。痛的地方在于改一个名字是十分钟的事，改一个语义要让所有调用点重新想一遍「我到底要什么」，而这周我一共撞了三次：改 private 那次、删 Catalog 这次、还有 getHistory 换返回类型。

下周想提前知道的一件事：数据库到底怎么把对象存进去的。现在是 Map 装对象，明天换成 MySQL，那 Product 这个对象是怎么变成表里一行的？反过来查出来的行又是怎么变回对象的？

## Day 9

今天做了什么：先把 MySQL80 服务启动起来，顺便把启动类型从「手动」改回「自动」，不然每天开机都要手动来一遍。然后把 mysql-connector-j-8.0.33.jar 复制进项目的 lib 目录，在 IDEA 里 Add as Library 接上，.gitignore 加了 lib/*.jar 把 jar 排除出版本控制。建了 secondhand_market 库和 product 表，五个字段 id、name、price、stock、status，id 用 AUTO_INCREMENT，price 用 DECIMAL(10,2)。写了 DbTest，跑通连接、插入一条、再查出来。最后把连接那部分抽成 DbUtil。

卡在哪里：把插入的代码写在了第一个 try 的 } 外面，报「找不到符号 变量 conn」。原因是 conn 是在 try 的括号里声明的，出了那对花括号就失效了，而我把 String sql 和第二个 try 都写在了 8 格缩进的位置，跟 try 平级。缩进其实早就提示了这件事，我当时没看出来。教训是写完按一次 Ctrl+Alt+L 自动格式化，如果排出来的缩进和我以为的不一样，那就是我写错了地方。

还有一件事值得记：代码里是硬编码的「手机 2999.00」，所以我连跑两次，表里就多了两条一模一样的记录，id 分别是 1 和 2。这说明「跑通一次」和「能重复跑」是两件事，真实项目里要么每次先清表，要么用不重复的测试数据，不然数据会被自己污染。

还没搞懂的：ResultSet 到底占不占内存，如果一次查十万行会不会把内存撑爆，是不是应该分批取。还有连接这东西到底能开多少个，一个程序同时开着十个连接会怎样，什么情况下该用连接池。这两个问题今天只是听说，没真的撞过。

ai帮了多少：DbTest 和 DbUtil 的代码骨架是任务单给的，我照着写。作用域的报错是 ai 讲了原因我才明白。检验五题的答案基本靠 ai 讲，其中 double 和 BigDecimal 的差别、SQL 注入那两段是 ai 跑实验给我看的，我自己没动手验证。

还剩的小事：本来 DbTest 里明文写着数据库密码，而仓库是公开的，所以改成从 db.properties 读。DbUtil 加了 loadConfig，把 url、user、password 从文件里读出来，DbTest 改成直接调 DbUtil.getConnection()，不用自己再写一份连接信息。读文件那一步用的是 try-with-resources，跟今天学的关连接是同一个道理——文件流也是外部资源，用完必须关。db.properties 加进了 .gitignore 不会被提交，另外提交一个 db.properties.example 放占位符，别人 clone 下来照着填一份就能跑。

## Day 10

今天做了什么：把 Product 的 static nextId 删掉，id 改成由数据库给。做法是两个构造方法，不带 id 的那个把 id 设成 0 表示还没入库，带 id 的那个专给从数据库读回来用，再配一个 isNew() 判断。ProductRepository 整个重写，Map 换成 JDBC：save 里按 isNew 分成 insert 和 update 两条路，insert 用 RETURN_GENERATED_KEYS 拿回自增 id 再 return new Product(id, ...)，因为 id 是 final 改不了；update 和 deleteById 用 executeUpdate() == 0 判断这一行到底存不存在；findAll 加了 ORDER BY id 保证顺序稳定，不然比对输出时会以为出错；ResultSet 转 Product 抽成 toProduct 复用。最后跑 App 八步，输出和上周内存版一字不差。

卡在哪里：最意外的是 ProductService 得改。我原来写的是 repo.findById(id).setStatus("ON_SALE") 这种，内存版里没问题，因为 findById 返回的就是仓库里那个对象；换成数据库版之后，findById 每次都 SELECT 再 new 一个全新对象，我改的只是这个临时对象，方法一结束它就没了，数据库一点没变。三个方法各补一行 repo.save(p)，一共改了九行。而且这个错误不报任何异常，程序跑得好好的，只有去查表才发现库存和价格都没动。

还没搞懂的：Product.price 是 double，数据库里是 DECIMAL(10,2)，Java 里对应 BigDecimal。所以每轮读写都在 double 和 BigDecimal 之间转两次，精度在第一次转换时就丢了。要彻底解决得把 price 改成 BigDecimal，但那样 ProductService、BuyChecker、App 全要跟着改，先记着。

ai帮了多少：Repository 的代码骨架是任务单给的，我照着写。ProductService 要补 save 这件事是 ai 先看出来的，我自己没想到；final 的 id 怎么保住也是 ai 讲的。

## Day 11

今天做了什么：建了 order 和 order_item 两张表。order 是保留字所以要用反引号包起来，两张都是 ENGINE=InnoDB，不然事务和外键都不生效。order_item 上加了两个外键分别指向 order 和 product，还给了约束名方便看报错。写 OrderService.placeOrder，用一个 Connection 走完查商品、写订单、写明细、扣库存四步，中间 setAutoCommit(false)、commit、rollback 保证四步要么全成要么全不成。扣库存用的是 UPDATE product SET stock = stock - ? WHERE id = ? AND stock >= ?，靠返回的影响行数判断够不够。跑 OrderTest 三个用例，然后做了关掉事务看脏数据的实验。

卡在哪里：三个地方。第一个是 INSERT INTO 'order' 用了单引号，报 SQL 语法错，单引号在 SQL 里是字符串字面量，包表名要用反引号。第二个是做关掉事务的实验时，我只注释了 setAutoCommit(false)，commit() 和 rollback() 还留着，报 Can't call rollback when autocommit=true。原来这三行是绑在一起的，autoCommit 为 true 的时候 commit 和 rollback 都算非法调用。而且 catch 里 rollback 抛出的新异常会把原来那个覆盖掉，导致我看到的 Caused by 是 rollback 的错误，真正的原因反而看不见。三处全注释掉才跑出干净的脏数据。第三个是查完数据库以为库存扣错了，查询显示 stock 是 3，表格里显示 2，我以为对不上。后来才明白那个 2 是 order_item 表的 qty（买了几个），不是 product 表的 stock（剩几个），5 减 2 等于 3，本来就对。

还没搞懂的：两个线程同时买最后一件商品，靠 UPDATE ... WHERE stock >= ? 这种条件更新到底挡不挡得住，还是得靠锁或者事务隔离级别，这个要等讲多线程。还有 rollback 之后自增号不回收，实验里看到 order_item 的 id 从 2 开始、order 的 id 也跳过号，但不太清楚这对业务有什么实际影响。

ai帮了多少：OrderService 的骨架是任务单给的。单引号反引号、rollback 那个报错、还有 qty 和 stock 看混，三个卡点都是 ai 定位的。脏数据的实验是 ai 代跑的，原理我懂，但没有一步步自己操作完。

## Day 11 检验答案

第一题，为什么 unit_price 要存进明细表。因为价格会变。张三今天花 2999 买的手机，我明天降价到 1999，如果订单金额是每次去 product 表 JOIN 出来的，他的历史订单会跟着变成 1999，他会发现账单被改了。所以 unit_price 存的是下单那一刻的价格，是一个快照。同样该拍快照的还有收货地址、用的哪张优惠券，甚至商品名称——商品改名之后历史订单应该显示当时那个名字。一句话：凡是要留作历史证据的，都得在发生当时抄一份，不能依赖将来去别处查。

第二题，外键约束挡了什么。它保证 order_item 里的每一条明细都挂在一张真实存在的订单上、指向一个真实存在的商品。我试过往 order_item 插一条 order_id = 999 的记录，被数据库直接拒绝了，报外键约束失败。如果没有外键，这个检查就得靠 Java 代码记得写，漏一个判断数据库里就会堆一批孤儿明细——查订单查不到，但它们实实在在占着空间。外键的价值是把这条保证交给数据库执行，不依赖我的代码有没有写对。

第三题，ACID 里今天用到的是哪一个。原子性。它的意思是这一组操作是一个不可分割的整体，要么全成功，要么全不成功，不存在中间状态。下单就是典型：查商品、写订单、写明细、扣库存，这四步必须一起成功。如果库存不够，前面已经写进去的订单和明细必须撤销，否则就会留下「有订单但没扣库存」的脏数据，那件商品还能被别人买走。另外三个里，隔离性会在讲多线程时撞上，持久性由 InnoDB 的日志保证，一致性是原子性、隔离性、持久性共同的结果。

第四题，为什么所有 SQL 必须用同一个 Connection。因为事务是绑在连接上的，不是绑在代码块上的。setAutoCommit(false) 只对这一条连接生效，commit 和 rollback 也只影响这一条。如果其中某一句跑到了另一条连接上，那条连接是 autoCommit=true，它那一句执行完立刻生效、脱离事务控制，最后 rollback 的时候它不会跟着撤销。这也正是今天 OrderService 不复用 ProductRepository 的原因——Repository 每个方法第一行都是 DbUtil.getConnection()，自己开自己的连接，调它的话那次更新会跑在另一条连接上，事务管不到。

第五题，不开事务会留下什么脏数据。实测的结果是：order 表里多了一行李四的订单，total 是 2996001.00（999 件乘 2999），order_item 表里多了一条 qty = 999 的明细，但库存一点没减。也就是说系统认为李四买到了，可那件商品还在架上，还能被别人买走。而控制台输出一切正常，只打印了两行「失败了（预期）」，程序没崩、没报警，问题全在数据库里躺着。这就是为什么做完一定要去查表，光看控制台是看不出来的。

## Day 12

今天做了什么：把 ProductRepository 的五个公开方法各拆成两个版本，一个自己开连接的包装版（三行，catch 到 SQLException 就包成 RuntimeException），一个接收 Connection 的实现版（直接 throws SQLException，不 catch，让错误处理只有一份）；再把 deductStock 从 OrderService 搬进 Repository，它本来就是改商品表的动作。OrderService 里自己写的那两句 product 表的 SQL 删掉了，改成调 repo 的重载版本，现在只剩订单表的两句。改完 OrderService 从 85 行降到 62 行，SQL 从 4 句变成 2 句。

卡在哪里：今天最大的事不是写代码，是做到检验第四题时发现——**事务是什么，我根本没搞懂**。代码跑通了、脏数据实验也做了、昨天的检验答案也「看过」了，但让我解释「为什么必须用同一个 Connection」，我只能说「因为事务要同一个连接」，这就是同义反复，等于没答。后来 ai 用命令行把 START TRANSACTION、ROLLBACK、COMMIT 现场跑了一遍（库存 3，事务里改成 2，回滚后回到 3；再跑一次改成 2 然后提交，就留在 2），我才真的明白事务是「给一组 SQL 打开打包开关，开了之后先进草稿不写硬盘，最后由 commit 或 rollback 决定」。最坑的地方是名字：SQL 里叫 START TRANSACTION，JDBC 里叫 setAutoCommit(false)，一个说「开始」一个说「关闭」，字面上完全没关系，我之前一直没把它们当成同一件事。

还没搞懂的：检验还差三题没做，分别是方案 A 和方案 B 各自最大的代价、重构的定义怎么验证、insertOrder 为什么没搬走，约好明天用拆小问的方式一道一道做。另外重构的验证我只是跑了 OrderTest 对输出，没有更严格的办法，不知道真实项目里除了自动测试还有没有别的手段。

ai帮了多少：重构的代码是任务单给的，两处搬移是我自己改的。今天更值得记的是 ai 把检验的出法改了——原来一次给五道开放题，我一道都答不上，只能直接要答案；改成一次问一个能用一两个词答的问题，五步下来我自己就能拼出完整答案。这个方法对我明显有效，以前不是不会，是不知道从哪儿开始答。


