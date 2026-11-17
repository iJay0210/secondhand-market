public class OrderItem {
    private final int productId;
    private final String productName;
    private final int qty;
    private final double unitPrice;

    public OrderItem(int productId, String productName, int qty, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {return productId;}
    public String getProductName() {return productName;}
    public int getQty() {return qty;}
    public double getUnitPrice() {return unitPrice;}

    public double getSubtotal() {return unitPrice * qty;}

    @Override
    public String toString() {
        return productName + " x" + qty + "单价" + unitPrice + " 小计" + getSubtotal();
    }
}
