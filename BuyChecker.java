public class BuyChecker {

    // static boolean check(int stock, int qty){
    //     if (stock>=qty) {
    //         return true;
    //     }else{
    //         return false;
    //     }
    // }
    // public static void main(String[] args) {
    //     System.out.println( check(10, 3));  // 应该打印 true
    //     System.out.println( check(2, 5));   // 应该打印 false
    // }

    

    static int totalBought(int[] history){
        int sum = 0;
        for (int i = 0; i < history.length; i++) {
            sum+=history[i];
        }
        return sum;
    }

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
        u.history = new int[]{1, 0, 2}; 

        // System.out.println(totalBought(new int[]{55}));
        System.out.println(canBuy(p, u, 2));
        System.out.println(canBuy(p, u, 3));
        System.out.println(canBuy(p, u, 4));
        System.out.println(canBuy(p, u, 0));
        System.out.println(canBuy(p, u, -1));
    }
}