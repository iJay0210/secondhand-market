public class User {


    String name;
    double balance;
    int[] history;


    static boolean canBuy(Product p, User u, int qty) {
    // 五个条件，按顺序判断，任一不满足就 return false
    // ① qty > 0
    // ② 商品在售：p.status.equals("ON_SALE")   ← 注意用 equals
    // ③ 库存够：p.stock >= qty
    // ④ 余额够：u.balance >= p.price * qty
    // ⑤ 限购：totalBought(u.history) + qty <= 5

    if (qty > 0 && p.status.equals("ON_SALE") && p.stock >= qty && u.balance >= p.price * qty && BuyChecker.totalBought(u.history) + qty <= 5) {
        return true;
    }else{
        return false;
    }
}

public static void main(String[] args) {
    Product p = new Product();
    p.name = "手机";
    p.price = 2999.0;
    p.stock = 3;
    p.status = "ON_SALE";

    User u = new User();
    u.name = "张三";
    u.balance = 10000.0;
    u.history = new int[]{1, 0, 2};   // 历史共买 3 件

    System.out.println(canBuy(p, u, 3));   // 预期：true
}
}