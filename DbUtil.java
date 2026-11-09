import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static final String CONFIG_FILE = "db.properties";
    private static final Properties CONFIG = loadConfig();

    private DbUtil() {}

    private static Properties loadConfig() {
        Properties p = new Properties();
        try (InputStream in = new FileInputStream(CONFIG_FILE)) {
            p.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "读不到 " + CONFIG_FILE + "。\n"
                            + "当前工作目录是：" + System.getProperty("user.dir") + "\n"
                            + "解决办法：把 db.properties.example 复制一份改名为 db.properties，填上密码。",
                    e);
        }
        return p;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                CONFIG.getProperty("db.url"),
                CONFIG.getProperty("db.user"),
                CONFIG.getProperty("db.password"));
    }
}
