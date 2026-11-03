public class User {


   private String name;
   private double balance;
   private int[] history;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("余额不能为负：" + balance);
        }
        this.balance = balance;
    }

    public int[] getHistory() {
        return history;
    }

    public void setHistory(int[] history) {
        this.history = history;
    }

    public int totalBought(){
        int sum = 0;
        for (int i = 0; i < history.length; i++) {
            sum+=history[i];
        }
        return sum;
    }

}