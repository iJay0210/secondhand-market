public class CatalogTest {
    public static void main(String[] args) {
        ProductRepository repo = new ProductRepository();

        Product p1 = new Product("手机", 2999.0, 3, "ON_SALE");
        Product p2 = new Product("耳机", 299.0, 10, "ON_SALE");
        Product p3 = new DigitalProduct("笔记本", 4999.0, 2, "ON_SALE", 24);
        repo.save(p1);
        repo.save(p2);
        repo.save(p3);

        System.out.println("=== 全部商品 ===");
        for (Product p : repo.findAll()) {
            System.out.println(p);
        }

        System.out.println();
        System.out.println("=== 按 id 查一个存在的 ===");
        Product found = repo.findById(p1.getId());
        System.out.println("查到的 id：" + found.getId());
        System.out.println("查到的商品：" + found);
        System.out.println("库存：" + found.getStock());

        System.out.println();
        System.out.println("=== 按 id 查一个不存在的 ===");
        try {
            repo.findById(999);
        } catch (IllegalArgumentException e) {
            System.out.println("拦住了：" + e.getMessage());
        }

        System.out.println();
        System.out.println("=== 总数 ===");
        System.out.println("仓库里现在有 " + repo.count() + " 个商品");
    }
}
