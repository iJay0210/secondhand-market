import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private final Map<Integer,Product> store = new HashMap<>();

    public Product save(Product p){
        store.put(p.getId(), p);
        return p;
    }

    public Product findById(int id){
        Product p = store.get(id);
        if(p == null){
            throw new IllegalArgumentException("商品不存在：id=" + id);
        }
        return p;
    }

    public List<Product> findAll(){
        return new ArrayList<>(store.values());
    }

    public void deleteById(int id){
        if (store.remove(id) == null){
            throw new IllegalArgumentException("要删的商品不存在：id=" + id);
        }
    }

    public int count(){
        return store.size();
    }
}
