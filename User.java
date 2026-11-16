import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
   private String name;
   private double balance;
   private final List<Integer> history = new ArrayList<>();




    public User(String name,double balance) {
        this.id = 0;
        setName(name);
        setBalance(balance);
    }

    public User(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {return id;}

    public boolean isNew(){return id == 0;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("余额不能为负：" + balance);
        }
        this.balance = balance;
    }

    public void addPurchase(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("购买数量必须为正：" + qty);
        history.add(qty);
    }

    public List<Integer> getHistory() {
        return List.copyOf(history);
    }


    public int totalBought(){
        int sum = 0;
        for (Integer qty : history) {
            sum += qty;
        }
        return sum;
    }

    @Override
    public String toString(){
        return "#" + id + " " + name + ",余额" + balance;
    }

}