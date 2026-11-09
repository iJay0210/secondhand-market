import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbTest {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DbUtil.getConnection()) {
            System.out.println("连接成功，MySQL 版本："
                    + conn.getMetaData().getDatabaseProductVersion());

            String sql = "INSERT INTO product (name, price, stock, status) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "手机");
                ps.setBigDecimal(2, new BigDecimal("2999.00"));
                ps.setInt(3, 3);
                ps.setString(4, "ON_SALE");

                int rows = ps.executeUpdate();
                System.out.println("插入了 " + rows + " 行");
            }

            String sql1 = "SELECT id, name, price, stock, status FROM product";

            try (PreparedStatement ps = conn.prepareStatement(sql1);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | "
                            + rs.getString("name") + " | "
                            + rs.getBigDecimal("price") + " | "
                            + rs.getInt("stock") + " | "
                            + rs.getString("status"));
                }
            }
        }
    }
}
