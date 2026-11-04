public class BuyChecker {

    static final int MAX_PER_USER = 5;

    boolean canBuy(Product p, User u, int qty) {
        if (qty <= 0) {
            System.out.println("卡在数量");
            return false;
        }
        if (!"ON_SALE".equals(p.getStatus())) {
            System.out.println("卡在状态");
            return false;
        }
        if (p.getStock() < qty) {
            System.out.println("卡在库存");
            return false;
        }
        if (u.getBalance() < p.getPrice() * qty) {
            System.out.println("卡在余额");
            return false;
        }
        if (u.totalBought() + qty > MAX_PER_USER) {
            System.out.println("卡在限购");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Product p = new Product("手机", 2999.0, 3, "ON_SALE");
        User u = new User();
        u.setName("张三");
        u.setBalance(10000.0);
        u.addPurchase(1);
        u.addPurchase(2);// 历史共买 3 件

        BuyChecker checker = new BuyChecker();

        System.out.println("qty=2  → " + checker.canBuy(p, u, 2));
        System.out.println("qty=3  → " + checker.canBuy(p, u, 3));
        System.out.println("qty=4  → " + checker.canBuy(p, u, 4));
        System.out.println("qty=0  → " + checker.canBuy(p, u, 0));
        System.out.println("qty=-1 → " + checker.canBuy(p, u, -1));

        System.out.println("历史购买总数：" + u.totalBought());
    }
}
