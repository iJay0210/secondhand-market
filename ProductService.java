import java.util.List;

public class ProductService {
    private final ProductRepository repo = new ProductRepository();

    public Product create(String name,double price,int stock){
        Product p = new Product(name,price,stock,"OFF_SALE");
        return repo.save(p);
    }

    public void putOnSale(int id){
        Product p = repo.findById(id);
        p.setStatus("ON_SALE");
        repo.save(p);
    }

    public void changePrice(int id,double newPrice){
        Product p = repo.findById(id);
        p.setPrice(newPrice);
        repo.save(p);
    }

    public void delete(int id){
        Product p = repo.findById(id);
        if (p.getStock() > 0) {
            throw new BusinessException(
                    "库存还有" + p.getStock() + "件，不能删：" + p.getName());
        }
            repo.deleteById(id);

    }

    public Product findById(int id){
        return repo.findById(id);
    }

    public List<Product> listAll(){
        return repo.findAll();
    }


}
