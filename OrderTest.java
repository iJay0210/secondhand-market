public class OrderTest {
    public static void main(String[] args) {
        UserRepository users = new UserRepository();
        OrderService service = new OrderService();

        System.out.println("=== 0. 建一个用户 ===");
        User wang = users.save(new User("王五", 10000.00));
        System.out.println("建好了：" + wang);

        System.out.println();
        System.out.println("=== 1. 正常下单：买 2 件手机 ===");
        int orderId = service.placeOrder(wang.getId(), 1, 2);
        System.out.println("下单成功，订单号 " + orderId);
        System.out.println("下单后：" + users.findById(wang.getId()));

        System.out.println();
        System.out.println("=== 2. 库存不足：买 999 件 ===");
        try {
            service.placeOrder(wang.getId(), 1, 999);
        } catch (Exception e) {
            System.out.println("失败了（预期）：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 3. 余额不足：李四只有 100 块 ===");
        User li = users.save(new User("李四", 100.00));
        try {
            service.placeOrder(li.getId(), 1, 1);
        } catch (Exception e) {
            System.out.println("失败了（预期）：" + e.getMessage());
        }
        System.out.println("李四的钱应该没少：" + users.findById(li.getId()));

        System.out.println();
        System.out.println("=== 4. 用户不存在 ===");
        try {
            service.placeOrder(999, 1, 1);
        } catch (Exception e) {
            System.out.println("失败了（预期）：" + e.getMessage());
        }
    }
}