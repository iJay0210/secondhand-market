public class DigitalProduct extends Product {
    private int warrantyMonths;

    public DigitalProduct(String name, double price, int stock, String status, int warrantyMonths) {
        super(name, price, stock, status);   // 先让父类把自己那部分初始化好
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() { return warrantyMonths; }

    @Override
    public String toString() {
        return super.toString() + "，保修 " + warrantyMonths + " 个月";
    }
}