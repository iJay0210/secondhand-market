import java.util.HashMap;
import java.util.Map;

public class Catalog {
    private final Map<String, Product> products = new HashMap<>();

    public void add(Product p) {
        products.put(p.getName(), p);
    }
    public Product find(String name) {
        return products.get(name);
    }

    public void printAll(){
        for(Map.Entry<String,Product> e:products.entrySet()){
            System.out.println(e.getKey() + "->" + e.getValue());
        }
    }
}
