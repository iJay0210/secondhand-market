import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class OrderService {
    private final ProductRepository products = new ProductRepository();
    private final UserRepository users = new UserRepository();
    private final OrderRepository orders = new OrderRepository();

    public int placeOrder(int userId, int productId, int qty) {
        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);
            try {
                User u = users.findById(conn, userId);
                Product p = products.findById(conn, productId);

                if (!"ON_SALE".equals(p.getStatus())) {
                    throw new IllegalStateException("商品未上架：" + p.getName());
                }

                double total = p.getPrice() * qty;

                int orderId = orders.insertOrder(conn, userId, total);        // ← 改成订单仓
                orders.insertItem(conn, orderId, productId, qty, p.getPrice());

                if (!products.deductStock(conn, productId, qty)) {
                    throw new IllegalStateException(
                            "库存不足：" + p.getName() + "，只剩 " + p.getStock() + " 件");
                }

                u.setBalance(u.getBalance() - total);   // 余额不够会在这里抛异常
                users.save(conn, u);

                conn.commit();
                return orderId;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("下单失败", e);
        }
    }

    public List<Order> findMyOrders(int userId){
        return orders.findByUserId(userId);
    }

}
