import java.math.BigDecimal;
import java.sql.*;

public class OrderService {
    private final ProductRepository products = new ProductRepository();
    private final UserRepository users = new UserRepository();

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

                int orderId = insertOrder(conn, userId, total);
                insertItem(conn, orderId, productId, qty, p.getPrice());

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

    private int insertOrder(Connection conn, int userId, double total) throws SQLException {
        String sql = "INSERT INTO `order` (user_id, total) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setBigDecimal(2, BigDecimal.valueOf(total));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new IllegalStateException("插入订单后没拿到 id");
    }

    private void insertItem(Connection conn, int orderId, int productId, int qty, double unitPrice)
            throws SQLException {
        String sql = "INSERT INTO order_item (order_id, product_id, qty, unit_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            ps.setBigDecimal(4, BigDecimal.valueOf(unitPrice));
            ps.executeUpdate();
        }
    }
}
