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
                    throw new BusinessException("商品未上架：" + p.getName());
                }

                double total = p.getPrice() * qty;

                if (u.getBalance() < total) {
                    throw new BusinessException(
                            "余额不足：需要 " + total + "，只有 " + u.getBalance());
                }

                int orderId = orders.insertOrder(conn, userId, total);        // ← 改成订单仓
                orders.insertItem(conn, orderId, productId, qty, p.getPrice());

                if (!products.deductStock(conn, productId, qty)) {
                    throw new BusinessException(
                            "库存不足：" + p.getName() + "，只剩 " + p.getStock() + " 件");
                }


                u.setBalance(u.getBalance() - total);
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
