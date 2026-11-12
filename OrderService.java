import java.math.BigDecimal;
import java.sql.*;

public class OrderService {

    private final ProductRepository repo = new ProductRepository();

    public int placeOrder(String username,int productId,int qty){
        try (Connection conn = DbUtil.getConnection()){
            conn.setAutoCommit(false);
            try {
                Product p = repo.findById(conn,productId);

                int orderId = insertOrder(conn,username,p.getPrice()*qty);
                insertItem(conn,orderId,productId,qty,p.getPrice());

                if (!repo.deductStock(conn, productId, qty)){
                    throw new IllegalStateException(
                            "库存不足："+p.getName()+",只剩"+p.getStock()+"件，你要买"+qty);
                }

                conn.commit();
                return orderId;
            }catch (Exception e){
                conn.rollback();
                throw e;
            }
        }catch (SQLException e){
            throw new RuntimeException("下单失败",e);
        }
    }

    private int insertOrder(Connection conn,String username,double total) throws SQLException{
        String sql = "INSERT INTO `order` (user_name,total) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, username);
            ps.setBigDecimal(2, BigDecimal.valueOf(total));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()){
                if (keys.next()){
                    return keys.getInt(1);
                }
            }
        }
        throw new IllegalStateException("插入订单后没拿到id");
    }

    private void insertItem(Connection conn,int orderId,int productId,int qty,double unitPrice) throws SQLException{
        String sql = "INSERT INTO order_item (order_id,product_id,qty,unit_price) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, qty);
            ps.setBigDecimal(4, BigDecimal.valueOf(unitPrice));
            ps.executeUpdate();
        }
    }


}
