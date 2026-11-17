import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {

    public List<Order> findByUserId(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            return findByUserId(conn, userId);
        } catch (SQLException e) {
            throw new RuntimeException("查询订单失败", e);
        }
    }

    public List<Order> findByUserId(Connection conn, int userId) throws SQLException {
        String sql = "SELECT o.id AS order_id, o.user_id, o.total, o.created_at, "
                + "oi.product_id, oi.qty, oi.unit_price, p.name AS product_name "
                + "FROM `order` o "
                + "JOIN order_item oi ON oi.order_id = o.id "
                + "JOIN product p ON p.id = oi.product_id "
                + "WHERE o.user_id = ? "
                + "ORDER BY o.id DESC, oi.id";

        Map<Integer,Order> byId = new LinkedHashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");

                    Order o = byId.get(orderId);
                    if (o == null) {
                        o = new Order(
                                orderId,
                                rs.getInt("user_id"),
                                rs.getBigDecimal("total").doubleValue(),
                                rs.getString("created_at"));
                        byId.put(orderId, o);
                    }

                    o.addItem(new OrderItem(rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("qty"),
                            rs.getBigDecimal("unit_price").doubleValue()));
                }
            }
        }
        return new ArrayList<>(byId.values());
    }

    public int insertOrder(Connection conn,int userId,double total)throws SQLException {
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
        throw new IllegalStateException("输入订单后没拿到 id");
    }

    public void insertItem(Connection conn, int orderId, int productId, int qty, double unitPrice) throws SQLException {
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
