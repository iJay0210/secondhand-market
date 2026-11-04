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

