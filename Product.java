public class Product {
  private String name;        // 商品名
  private double price;       // 单价
   private int stock;          // 库存
   private String status;      // 状态："ON_SALE" 在售 / "OFF_SALE" 下架

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getStatus() { return status; }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("库存不能为负：" + stock);
        }
        this.stock = stock;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("单价必须大于 0：" + price);
        }
        this.price = price;
    }

    public void setStatus(String status) {
        if (!"ON_SALE".equals(status) && !"OFF_SALE".equals(status)) {
            throw new IllegalArgumentException("状态只能是 ON_SALE 或 OFF_SALE：" + status);
        }
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product(String name, double price, int stock, String status) {
        setName(name);
        setPrice(price);
        setStock(stock);
        setStatus(status);
    }
}


