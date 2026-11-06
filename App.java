public class App {
    public static void main(String[] args) {
        ProductService service = new ProductService();

        System.out.println("=== 1. 上架三个商品（C）===");
        Product p1 = service.create("手机", 2999.0, 3);
        Product p2 = service.create("耳机", 299.0, 10);
        Product p3 = service.create("笔记本", 4999.0, 0);
        System.out.println("新建：" + p1);
        System.out.println("新建：" + p2);
        System.out.println("新建：" + p3);

        System.out.println();
        System.out.println("=== 2. 上架状态默认是 OFF_SALE，卖不了 ===");
        User u = new User();
        u.setName("张三");
        u.setBalance(10000.0);
        System.out.println("上架前买手机：" + service.buy(p1.getId(), u, 1));

        System.out.println();
        System.out.println("=== 3. 上架后再买（U）===");
        service.putOnSale(p1.getId());
        service.putOnSale(p2.getId());
        service.putOnSale(p3.getId());
        System.out.println("上架后买手机：" + service.buy(p1.getId(), u, 2));
        System.out.println("手机现在：" + service.findById(p1.getId()));
        System.out.println("张三余额：" + u.getBalance());
        System.out.println("张三累计买了：" + u.totalBought());

        System.out.println();
        System.out.println("=== 4. 改价（U）===");
        service.changePrice(p2.getId(), 199.0);
        System.out.println("耳机现在：" + service.findById(p2.getId()));

        System.out.println();
        System.out.println("=== 5. 删除（D）===");
        service.delete(p3.getId());
        System.out.println("删掉笔记本后，还有 " + service.listAll().size() + " 个商品");

        System.out.println();
        System.out.println("=== 6. 删不掉的情况：库存还有 ===");
        try {
            service.delete(p2.getId());
        } catch (IllegalStateException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 7. 查不到的情况 ===");
        try {
            service.delete(999);
        } catch (IllegalArgumentException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 8. 全部商品（R）===");
        for (Product p : service.listAll()) {
            System.out.println(p);
        }
    }
}