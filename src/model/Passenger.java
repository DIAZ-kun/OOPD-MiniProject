package model;

import exception.InsufficientBalanceException;
import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String username;
    private String password;
    private double walletBalance;
    private List<Ticket> ticketHistory;

    public Passenger(String username, String password) {
        this.username = username;
        this.password = password;
        this.walletBalance = 0.0;
        this.ticketHistory = new ArrayList<>();
    }

    public Passenger(String username, String password, double initialBalance) {
        this.username = username;
        this.password = password;
        this.walletBalance = initialBalance;
        this.ticketHistory = new ArrayList<>();
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getUsername() {
        return username;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void rechargeWallet(double amount) {
        if (amount > 0) {
            this.walletBalance += amount;
        }
    }

    public void deductBalance(double amount) throws InsufficientBalanceException {
        if (this.walletBalance < amount) {
            throw new InsufficientBalanceException("Insufficient wallet balance. Current Balance: Rs." + this.walletBalance);
        }
        this.walletBalance -= amount;
    }

    public void addTicket(Ticket ticket) {
        this.ticketHistory.add(ticket);
    }

    public List<Ticket> getTicketHistory() {
        return new ArrayList<>(ticketHistory);
    }

    public void displayHistory() {
        if (ticketHistory.isEmpty()) {
            System.out.println("No booking history found.");
            return;
        }
        for (int i = 0; i < ticketHistory.size(); i++) {
            System.out.println("\n--- Ticket " + (i + 1) + " ---");
            System.out.println(ticketHistory.get(i));
        }
    }
}
