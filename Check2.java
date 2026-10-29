/**
 * Check2
 */
public class Check2 {

    static Boolean canBuy(Product p,User u,int qty){
        return p.stock >= qty && u.balance >= p.price*qty;
    }
    public static void main(String[] args) {
        User u = new User();
        u.name = "李四";
        u.balance = 100000;
        u.history = new int[]{1,0,2};

        Product p =new Product();
        p.name = "内存条";
        p.price = 2999;
        p.stock = 3;
        p.status = "ON_SALE";


        System.out.println(canBuy(p, u, 2));

    }
}