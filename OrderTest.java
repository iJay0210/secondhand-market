public class OrderTest {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        System.out.println("=== 1. 正常下单：买 2 件手机 ===");
        int orderId = service.placeOrder("张三", 1, 2);
        System.out.println("下单成功，订单号 " + orderId);

        System.out.println();
        System.out.println("=== 2. 库存不足：买 999 件，应该整体失败 ===");
        try {
            service.placeOrder("李四", 1, 999);
        } catch (Exception e) {
            System.out.println("失败了（预期）：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 3. 商品不存在 ===");
        try {
            service.placeOrder("王五", 999, 1);
        } catch (Exception e) {
            System.out.println("失败了（预期）：" + e.getMessage());
        }
    }
}