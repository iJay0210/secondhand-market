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

        DigitalProduct d = new DigitalProduct("笔记本电脑", 4999.0, 2, "ON_SALE", 24);
        System.out.println(d);
        System.out.println(d.getStock());      // 父类的方法，子类直接能用
        a.printStock(a);
       b.printStock(b);
    }
}
