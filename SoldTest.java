import java.util.HashMap;
import java.util.Map;

public class SoldTest {
    public static void main(String[] args) {

        System.out.println("=== 坑一：key 不存在时直接累加 ===");
        Map<String, Integer> empty = new HashMap<>();
        try {
            empty.put("手机", empty.get("手机") + 1);
        } catch (NullPointerException e) {
            System.out.println("崩了 -> " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 正确写法：getOrDefault ===");
        Map<String, Integer> sold = new HashMap<>();
        sold.put("手机", sold.getOrDefault("手机", 0) + 1);
        sold.put("手机", sold.getOrDefault("手机", 0) + 1);
        sold.put("耳机", sold.getOrDefault("耳机", 0) + 1);

        System.out.println("手机卖了几台：" + sold.get("手机"));
        System.out.println("耳机卖了几台：" + sold.get("耳机"));
        System.out.println("平板卖了几台：" + sold.getOrDefault("平板", 0));
        System.out.println("整个表：" + sold);

        System.out.println();
        System.out.println("=== 坑二：Integer 的 == ===");
        Integer a = 127, b = 127;
        Integer c = 1000, d = 1000;
        System.out.println("127 == 127      ? " + (a == b));
        System.out.println("1000 == 1000    ? " + (c == d));
        System.out.println("1000.equals(1000)? " + c.equals(d));

        System.out.println();
        System.out.println("=== 这个坑在统计里怎么咬人 ===");
        Map<String, Integer> m1 = new HashMap<>();
        m1.put("手机", 1000);
        Map<String, Integer> m2 = new HashMap<>();
        m2.put("手机", 1000);
        System.out.println("两个表里都存着 1000，用 == 比    ：" + (m1.get("手机") == m2.get("手机")));
        System.out.println("用 equals 比                      ：" + m1.get("手机").equals(m2.get("手机")));
    }
}
