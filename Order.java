import java.util.ArrayList;

public class Order {

    @Override
    public String toString() {
        return "订单 #" + id + "，金额 " + amount + "，状态 " + status;
    }

    int id;
    int amount;
    String status;

    public Order(int id, int amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }

    public static void main(String[] args) {
        Order a = new Order(1,1,"A");
        Order b = new Order(2,2,"B");
        b.status = "已结算";

        System.out.println(a);
        System.out.println(b);
    }
}
