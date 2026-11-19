import java.util.List;

public class App {
    public static void main(String[] args) {
        ProductService products = new ProductService();
        OrderService orders = new OrderService();
        UserRepository users = new UserRepository();

        System.out.println("=== 1. 上架三个商品（C）===");
        Product p1 = products.create("手机", 2999.0, 5);
        Product p2 = products.create("耳机", 199.0, 10);
        Product p3 = products.create("笔记本", 4999.0, 0);
        System.out.println("新建：" + p1);
        System.out.println("新建：" + p2);
        System.out.println("新建：" + p3);

        System.out.println();
        System.out.println("=== 2. 建用户（入库，有 id）===");
        User u = users.save(new User("张三", 10000.0));
        System.out.println("建好了：" + u);

        System.out.println();
        System.out.println("=== 3. 没上架时下单，应该被拦住 ===");
        try {
            orders.placeOrder(u.getId(), p1.getId(), 1);
        } catch (BusinessException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 4. 上架后再下单（U）===");
        products.putOnSale(p1.getId());
        products.putOnSale(p2.getId());
        products.putOnSale(p3.getId());

        int orderId = orders.placeOrder(u.getId(), p1.getId(), 2);
        System.out.println("下单成功，订单号 " + orderId);
        System.out.println("手机现在：" + products.findById(p1.getId()));
        System.out.println("张三现在：" + users.findById(u.getId()));

        System.out.println();
        System.out.println("=== 5. 改价（U）===");
        products.changePrice(p2.getId(), 159.0);
        System.out.println("耳机现在：" + products.findById(p2.getId()));

        System.out.println();
        System.out.println("=== 6. 删除（D）===");
        products.delete(p3.getId());
        System.out.println("删掉笔记本后，还有 " + products.listAll().size() + " 个商品");

        System.out.println();
        System.out.println("=== 7. 删不掉的情况：库存还有 ===");
        try {
            products.delete(p1.getId());
        } catch (BusinessException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 8. 查不到的情况 ===");
        try {
            products.delete(999);
        } catch (BusinessException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 9. 余额不足的情况 ===");
        User poor = users.save(new User("李四", 100.0));
        try {
            orders.placeOrder(poor.getId(), p1.getId(), 1);
        } catch (BusinessException e) {
            System.out.println("拦住了：" + e.getMessage());
        }
        System.out.println("李四的钱没少：" + users.findById(poor.getId()));

        System.out.println();
        System.out.println("=== 10. 查我的订单 ===");
        List<Order> mine = orders.findMyOrders(u.getId());
        for (Order o : mine) {
            System.out.println(o);
        }
        System.out.println("共 " + mine.size() + " 张订单");

        System.out.println();
        System.out.println("=== 11. 全部商品（R）===");
        for (Product p : products.listAll()) {
            System.out.println(p);
        }
    }
}