import java.util.List;

public class OrderTest {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        System.out.println("=== 查王五（id=2）的订单 ===");
        List<Order> list1 = service.findMyOrders(2);
        if (list1.isEmpty()) {
            System.out.println("（没有订单）");
        } else {
            for (Order o : list1) {
                System.out.println(o);
            }
            System.out.println("共 " + list1.size() + " 张订单");
        }

        System.out.println();
        System.out.println("=== 查张三（id=1）的订单 ===");
        List<Order> list2 = service.findMyOrders(1);
        for (Order o : list2) {
            System.out.println(o);
        }
        System.out.println("共 " + list2.size() + " 张订单");

        System.out.println();
        System.out.println("=== 查李四（id=3）的订单：他一张都没买过 ===");
        List<Order> list3 = service.findMyOrders(3);
        System.out.println("返回的列表大小：" + list3.size());
        System.out.println("是空的吗：" + list3.isEmpty());
    }
}