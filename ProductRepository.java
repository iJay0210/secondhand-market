import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {


    public Product save(Product p){
        if (p.isNew()) {
            return insert(p);
        }
        update(p);
        return p;
    }
    private Product insert(Product p) {
        String sql = "INSERT INTO product (name, price, stock, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            throw new IllegalStateException("插入成功了，但数据库没返回生成的 id");
        } catch (SQLException e) {
            throw new RuntimeException("插入商品失败", e);
        }
    }
    private void update(Product p) {
        String sql = "UPDATE product SET name = ?, price = ?, stock = ?, status = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,p.getName());
            ps.setBigDecimal(2,BigDecimal.valueOf(p.getPrice()));
            ps.setInt(3,p.getStock());
            ps.setString(4,p.getStatus());
            ps.setInt(5,p.getId());

            if (ps.executeUpdate() == 0){
                throw new IllegalStateException("商品不存在： id=" + p.getId());
            }
        }catch (SQLException e) {
            throw new RuntimeException("更新商品失败",e);
        }
            }
    public Product findById(int id){
        String sql = "SELECT id, name, price, stock, status FROM product WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toProduct(rs);
                }
            }
            throw new IllegalArgumentException("商品不存在：id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException("查询商品失败", e);
        }
    }

    public List<Product> findAll(){
        String sql = "SELECT id, name, price, stock, status FROM product ORDER BY id";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(toProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询商品列表失败", e);
        }
        return list;
    }

    public void deleteById(int id){
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("要删的商品不存在：id=" + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("删除商品失败", e);
        }
    }

    public int count(){
        String sql = "SELECT COUNT(*) FROM product";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("统计商品数量失败", e);
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
