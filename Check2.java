/**
 * Check2
 */
public class Check2 {

    static Boolean canBuy(Product p,User u,int qty){
        return p.getStock() >= qty && u.getBalance() >= p.getPrice()*qty;
    }
    public static void main(String[] args) {
        User u = new User();
        u.setName("李四");
        u.setBalance(100000);
        u.addPurchase(1);
        u.addPurchase(2);

        Product p = new Product("手机", 2999.0, 3, "ON_SALE");


        System.out.println(canBuy(p, u, 2));

    }
}