import java.util.List;

public class ProductService {
    private final ProductRepository repo = new ProductRepository();

    public Product create(String name,double price,int stock){
        Product p = new Product(name,price,stock,"OFF_SALE");
        return repo.save(p);
    }

    public void putOnSale(int id){
        repo.findById(id).setStatus("ON_SALE");
    }

    public void changePrice(int id,double newPrice){
        repo.findById(id).setPrice(newPrice);
    }

    public void delete(int id){
        Product p = repo.findById(id);
        if (p.getStock() > 0) {
            throw new IllegalStateException(
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

    public boolean buy(int id,User u,int qty){
        Product p = repo.findById(id);
        if(!new BuyChecker().canBuy(p,u,qty)){
            return false;
        }
        p.setStock(p.getStock() - qty);
        u.addPurchase(qty);
        u.setBalance(u.getBalance() - p.getPrice()*qty);
        return true;
    }
}
