import java.math.BigDecimal;
import java.sql.*;

public class UserRepository {

    public User save(User u) {
        try (Connection conn = DbUtil.getConnection()){
            return save(conn,u);
        }catch (SQLException e){
            throw new RuntimeException("保存用户失败",e);
        }
    }
    public User save(Connection conn, User u) throws SQLException{
        if (u.isNew()){
            return insert(conn,u);
        }
        update(conn,u);
        return u;
    }

    public User findById(int id){
        try (Connection conn = DbUtil.getConnection()){
            return findById(conn,id);
        }catch (SQLException e){
            throw new RuntimeException("查询用户失败",e);
        }
    }

    public User findById(Connection conn,int id) throws SQLException{
        String sql = "select id,name,balance from user where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return toUser(rs);
                }
            }
        }
        throw new BusinessException("用户不存在：id=" + id);
    }

    private User insert(Connection conn,User u) throws SQLException{
        String sql = "insert into `user` (name,balance) values (?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,u.getName());
            ps.setBigDecimal(2, BigDecimal.valueOf(u.getBalance()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()){
                    return new User(rs.getInt(1), u.getName(), u.getBalance());
                }
            }
        }
        throw new IllegalStateException("插入成功了，但数据库没返回生成的id");
    }
    private void update(Connection conn,User u) throws SQLException{
        String sql = "update `user` set name = ?, balance = ? where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,u.getName());
            ps.setBigDecimal(2, BigDecimal.valueOf(u.getBalance()));
            ps.setInt(3,u.getId());
            if (ps.executeUpdate()==0){
                throw new BusinessException("用户不存在：id="+u.getId());
            }
        }
    }
    private User toUser(ResultSet rs) throws SQLException{
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("balance").doubleValue()
        );
    }
}
