class Item {
    String name;
    int stock;

    static void printStock(Item self) {
        System.out.println(self.name + " 还剩 " + self.stock);
//        java: 无法从静态上下文中引用非静态 变量 name
    }
}

public class Probe {
    public static void main(String[] args) {
        Item a = new Item();
        a.name = "手机";
        a.stock = 1;

        Item b = new Item();
        b.name = "耳机";
        b.stock = 5;

        a.printStock(a);
       b.printStock(b);
    }
}
