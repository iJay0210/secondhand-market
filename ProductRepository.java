import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public Product save(Product p) {
        try (Connection conn = DbUtil.getConnection()) {
            return save(conn, p);
        } catch (SQLException e) {
            throw new RuntimeException("保存商品失败", e);
        }
    }

    public Product findById(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            return findById(conn, id);
        } catch (SQLException e) {
            throw new RuntimeException("查询商品失败", e);
        }
    }

    public List<Product> findAll() {
        try (Connection conn = DbUtil.getConnection()) {
            return findAll(conn);
        } catch (SQLException e) {
            throw new RuntimeException("查询商品列表失败", e);
        }
    }

    public void deleteById(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            deleteById(conn, id);
        } catch (SQLException e) {
            throw new RuntimeException("删除商品失败", e);
        }
    }

    public int count() {
        try (Connection conn = DbUtil.getConnection()) {
            return count(conn);
        } catch (SQLException e) {
            throw new RuntimeException("统计商品数量失败", e);
        }
    }

    public Product save(Connection conn, Product p) throws SQLException {
        if (p.isNew()) {
            return insert(conn, p);
        }
        update(conn, p);
        return p;
    }

    public Product findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT id, name, price, stock, status FROM product WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toProduct(rs);
                }
            }
        }
        throw new IllegalArgumentException("商品不存在：id=" + id);
    }

    public List<Product> findAll(Connection conn) throws SQLException {
        String sql = "SELECT id, name, price, stock, status FROM product ORDER BY id";
        List<Product> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(toProduct(rs));
            }
        }
        return list;
    }

    public void deleteById(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("要删的商品不存在：id=" + id);
            }
        }
    }

    public int count(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM product";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /** 扣库存。返回 false 表示库存不够，一行都没扣到 */
    public boolean deductStock(Connection conn, int productId, int qty) throws SQLException {
        String sql = "UPDATE product SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            return ps.executeUpdate() > 0;
        }
    }

    private Product insert(Connection conn, Product p) throws SQLException {
        String sql = "INSERT INTO product (name, price, stock, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, BigDecimal.valueOf(p.getPrice()));
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new Product(id, p.getName(), p.getPrice(), p.getStock(), p.getStatus());
                }
            }
        }
        throw new IllegalStateException("插入成功了，但数据库没返回生成的 id");
    }

    private void update(Connection conn, Product p) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, stock = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, BigDecimal.valueOf(p.getPrice()));
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getId());
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("商品不存在：id=" + p.getId());
            }
        }
    }

    private Product toProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price").doubleValue(),
                rs.getInt("stock"),
                rs.getString("status"));
    }
}