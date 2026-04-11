// package qr;

public class QRCode {

    private String userId;
    private double balance;

    public QRCode(String userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void recharge(double amount) {
        balance += amount;
        System.out.println("✅ Recharged ₹" + amount);
    }

    public void display() {
        System.out.println("User ID: " + userId);
        System.out.println("Balance: ₹" + balance);
    }
}
