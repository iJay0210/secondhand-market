public class User {


    String name;
    double balance;
    int[] history;

     int totalBought(){
        int sum = 0;
        for (int i = 0; i < history.length; i++) {
            sum+=history[i];
        }
        return sum;
    }

}