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



