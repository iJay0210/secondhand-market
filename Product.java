public class Product {
    String name;        // 商品名
    double price;       // 单价
    int stock;          // 库存
    String status;      // 状态："ON_SALE" 在售 / "OFF_SALE" 下架

    public Product(String name, double price, int stock, String status) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }
}


