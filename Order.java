import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private final int userId;
    private final double total;
    private final String createdAt;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(int id, int userId, double total, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
    }

    public int getId() {return id;}
    public int getUserId() {return userId;}
    public double getTotal() {return total;}
    public String getCreatedAt() {return createdAt;}

    public List<OrderItem> getItems() {return List.copyOf(items);}

    public void addItem(OrderItem item) {items.add(item);}

    public int getTotalQty(){
        int sum = 0;
        for (OrderItem item : items) {
            sum += item.getQty();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("订单 #").append(id).
                append("   ").append(createdAt)
                .append(" 合计 ").append(total);
        for (OrderItem item : items) {
            sb.append("\n").append(item);
        }
        return sb.toString();
    }

}
