public class BuyChecker {

    // static boolean check(int stock, int qty){
    //     if (stock>=qty) {
    //         return true;
    //     }else{
    //         return false;
    //     }
    // }
    // public static void main(String[] args) {
    //     System.out.println( check(10, 3));  // 应该打印 true
    //     System.out.println( check(2, 5));   // 应该打印 false
    // }

    // static boolean canBuy(int stock, double price, double balance, int qty){
    //     // if (qty > 0 && stock >=qty && balance >=price*qty) {
    //     //     return true;
    //     // }else{
    //     //     return false;
    //     // }
    //     if (qty<=0) {
    //         return false;
    //     }else{
    //         if (stock >=qty && balance >=price*qty) {
    //             return true;
    //         }else{
    //             return false;
    //         }
    //     }
    // }

    static int totalBought(int[] history){
        int sum = 0;
        for (int i = 0; i < history.length; i++) {
            sum+=history[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(totalBought(new int[]{55}));
    }
}