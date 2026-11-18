import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class OrderServiceTest {

    private OrderService service;
    private UserRepository users;
    private ProductRepository products;

    @BeforeEach
    void setUp() throws Exception {
        service = new OrderService();
        users = new UserRepository();
        products = new ProductRepository();

        try (Connection conn = DbUtil.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM order_item");
            st.executeUpdate("DELETE FROM `order`");
            st.executeUpdate("DELETE FROM `user`");
            st.executeUpdate("INSERT INTO product (id, name, price, stock, status) "
                    + "VALUES (1, '手机', 2999.00, 5, 'ON_SALE') "
                    + "ON DUPLICATE KEY UPDATE price = 2999.00, stock = 5, status = 'ON_SALE'");
        }
    }

    @Test
    @DisplayName("正常下单：库存减 2，余额减 5998")
    void placeOrder_ok() {
        User u = users.save(new User("测试用户", 10000.00));

        int orderId = service.placeOrder(u.getId(), 1, 2);

        assertTrue(orderId > 0, "应该拿到订单号");
        assertEquals(3, products.findById(1).getStock(), "库存 5 减 2 应剩 3");
        assertEquals(4002.00, users.findById(u.getId()).getBalance(), 0.001, "余额 10000 减 5998");
    }

    @Test
    @DisplayName("库存不足：抛异常，库存和余额都不变")
    void placeOrder_notEnoughStock() {
        User u = users.save(new User("测试用户", 10000.00));

        assertThrows(IllegalStateException.class,
                () -> service.placeOrder(u.getId(), 1, 999));

        assertEquals(5, products.findById(1).getStock(), "回滚了，库存应该还是 5");
        assertEquals(10000.00, users.findById(u.getId()).getBalance(), 0.001, "回滚了，余额应该不变");
    }

    @Test
    @DisplayName("余额不足：抛异常，库存和余额都不变")
    void placeOrder_notEnoughBalance() {
        User u = users.save(new User("穷用户", 100.00));

        assertThrows(IllegalArgumentException.class,
                () -> service.placeOrder(u.getId(), 1, 1));

        assertEquals(5, products.findById(1).getStock(), "回滚了，库存应该还是 5");
        assertEquals(100.00, users.findById(u.getId()).getBalance(), 0.001, "余额应该还是 100");
    }

    @Test
    @DisplayName("用户不存在：抛异常")
    void placeOrder_userNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> service.placeOrder(99999, 1, 1));
    }

    @Test
    @DisplayName("查我的订单：下两单就返回两张")
    void findMyOrders_twoOrders() {
        User u = users.save(new User("测试用户", 20000.00));
        service.placeOrder(u.getId(), 1, 1);
        service.placeOrder(u.getId(), 1, 1);

        List<Order> list = service.findMyOrders(u.getId());

        assertEquals(2, list.size(), "买了两单应该有两条记录");
        for (Order o : list) {
            assertEquals(1, o.getItems().size(), "每张订单都该有 1 条明细");
        }
    }

    @Test
    @DisplayName("查我的订单：没买过返回空列表，不抛异常")
    void findMyOrders_empty() {
        User u = users.save(new User("新人", 1000.00));

        List<Order> list = service.findMyOrders(u.getId());

        assertNotNull(list);
        assertTrue(list.isEmpty(), "没买过应该是空列表");
    }
}