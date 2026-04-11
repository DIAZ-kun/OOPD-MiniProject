import model.*;
import registry.*;
import exception.InsufficientBalanceException;
import javax.swing.*;
import java.awt.*;

public class PassengerService {
    private static PassengerService instance;
    private AppContext context;
    
    private PassengerService() {
        this.context = AppContext.getInstance();
    }
    
    public static PassengerService getInstance() {
        if (instance == null) {
            instance = new PassengerService();
        }
        return instance;
    }
    
    public void handlePassengerLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            UIHelper.showMessage("Please fill username and password.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Passenger p = PassengerRegistry.login(username, password);
        if (p == null) {
            UIHelper.showMessage("Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        context.setCurrentPassenger(p);
        refreshPassengerDashboard();
        showCard("PASSENGER_DASHBOARD");
    }

    public void handlePassengerRegistration(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            UIHelper.showMessage("Please provide both username and password.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (PassengerRegistry.exists(username)) {
            UIHelper.showMessage("Username already exists. Choose another.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Passenger p = new Passenger(username, password);
        PassengerRegistry.register(p);
        UIHelper.showMessage("Registration successful. Please login.", "Registered", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handleRecharge() {
        String amountString = JOptionPane.showInputDialog(context.getFrame(), "Enter recharge amount:", "Recharge", JOptionPane.PLAIN_MESSAGE);
        if (amountString == null || amountString.trim().isEmpty()) {
            return;
        }
        try {
            double amount = Double.parseDouble(amountString.trim());
            if (amount <= 0) {
                UIHelper.showMessage("Please enter a positive amount.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
            context.getCurrentPassenger().rechargeWallet(amount);
            refreshPassengerDashboard();
            UIHelper.showMessage("Recharge successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            UIHelper.showMessage("Enter a valid numeric amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showTicketHistory() {
        if (context.getCurrentPassenger() == null) {
            return;
        }
        StringBuilder history = new StringBuilder();
        for (Ticket ticket : context.getCurrentPassenger().getTicketHistory()) {
            history.append(ticket).append("\n\n");
        }
        if (history.length() == 0) {
            UIHelper.showMessage("No booking history found.", "Ticket History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JTextArea area = new JTextArea(history.toString());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(600, 360));
        JOptionPane.showMessageDialog(context.getFrame(), scrollPane, "Ticket History", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initializeBookingForm() {
        String selection = (String) context.getCbBookType().getSelectedItem();
        context.getCbStartLine().setEnabled("Interchange".equals(selection));
        updateBookingSources();
        context.getLblFare().setText("Rs. 0");
    }

    public void updateBookingSources() {
        context.getCbSource().removeAllItems();
        context.getCbDest().removeAllItems();

        String selection = (String) context.getCbBookType().getSelectedItem();
        if ("MMRDA".equals(selection)) {
            context.getMmrdaStations().forEach(context.getCbSource()::addItem);
            context.getMmrdaStations().forEach(context.getCbDest()::addItem);
        } else if ("Reliance".equals(selection)) {
            context.getRelianceStations().forEach(context.getCbSource()::addItem);
            context.getRelianceStations().forEach(context.getCbDest()::addItem);
        } else {
            String startLine = (String) context.getCbStartLine().getSelectedItem();
            if ("MMRDA".equals(startLine)) {
                context.getMmrdaStations().forEach(context.getCbSource()::addItem);
                context.getRelianceStations().stream().filter(s -> !s.equals("Gundavali")).forEach(context.getCbDest()::addItem);
            } else {
                context.getRelianceStations().forEach(context.getCbSource()::addItem);
                context.getMmrdaStations().stream().filter(s -> !s.equals("Gundavali")).forEach(context.getCbDest()::addItem);
            }
        }
    }

    public void updateFarePreview() {
        String selection = (String) context.getCbBookType().getSelectedItem();
        if (selection == null) {
            return;
        }
        int fare = calculateFare(selection);
        if (fare < 0) {
            context.getLblFare().setText("Invalid route");
        } else {
            context.getLblFare().setText("Rs. " + fare);
        }
    }

    public int calculateFare(String selection) {
        String source = (String) context.getCbSource().getSelectedItem();
        String dest = (String) context.getCbDest().getSelectedItem();
        if (source == null || dest == null) {
            return -1;
        }
        if (source.equals(dest)) {
            return -1;
        }
        int srcIdx;
        int destIdx;
        switch (selection) {
            case "MMRDA":
                srcIdx = context.getMmrdaStations().indexOf(source);
                destIdx = context.getMmrdaStations().indexOf(dest);
                return Math.abs(srcIdx - destIdx) * 10;
            case "Reliance":
                srcIdx = context.getRelianceStations().indexOf(source);
                destIdx = context.getRelianceStations().indexOf(dest);
                return Math.abs(srcIdx - destIdx) * 10;
            default:
                String startLine = (String) context.getCbStartLine().getSelectedItem();
                if ("MMRDA".equals(startLine)) {
                    srcIdx = context.getMmrdaStations().indexOf(source);
                    destIdx = context.getRelianceStations().indexOf(dest);
                    int dist1 = Math.abs(srcIdx - context.getMmrdaStations().indexOf("Gundavali"));
                    int dist2 = Math.abs(destIdx - context.getRelianceStations().indexOf("Gundavali"));
                    return (dist1 + dist2) * 10;
                } else {
                    srcIdx = context.getRelianceStations().indexOf(source);
                    destIdx = context.getMmrdaStations().indexOf(dest);
                    int dist1 = Math.abs(srcIdx - context.getRelianceStations().indexOf("Gundavali"));
                    int dist2 = Math.abs(destIdx - context.getMmrdaStations().indexOf("Gundavali"));
                    return (dist1 + dist2) * 10;
                }
        }
    }

    public void attemptBooking() {
        if (context.getCurrentPassenger() == null) {
            return;
        }
        String selection = (String) context.getCbBookType().getSelectedItem();
        if (selection == null) {
            UIHelper.showMessage("Choose a ticket type.", "Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String source = (String) context.getCbSource().getSelectedItem();
        String dest = (String) context.getCbDest().getSelectedItem();
        if (source == null || dest == null) {
            UIHelper.showMessage("Select source and destination.", "Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (source.equals(dest)) {
            UIHelper.showMessage("Source and destination cannot be the same.", "Booking", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fare = calculateFare(selection);
        if (fare < 0) {
            UIHelper.showMessage("Unable to calculate fare for this route.", "Booking", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            context.getCurrentPassenger().deductBalance(fare);
        } catch (InsufficientBalanceException e) {
            UIHelper.showMessage("Insufficient balance: " + e.getMessage(), "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String operatorType;
        String intermediate = null;
        if ("MMRDA".equals(selection)) {
            operatorType = "MMRDA";
        } else if ("Reliance".equals(selection)) {
            operatorType = "Reliance";
        } else {
            String startLine = (String) context.getCbStartLine().getSelectedItem();
            if ("MMRDA".equals(startLine)) {
                operatorType = "Interchange-MMRDA-Reliance";
                intermediate = "Gundavali";
            } else {
                operatorType = "Interchange-Reliance-MMRDA";
                intermediate = "Gundavali";
            }
        }

        String qrString = UIHelper.generateAlphanumericString(10);
        Ticket ticket = new Ticket(context.getCurrentPassenger().getUsername(), operatorType, source, intermediate, dest, fare, qrString);
        context.getCurrentPassenger().addTicket(ticket);
        TicketRegistry.saveTicket(ticket);

        UnifiedQRCode qrObj;
        if ("Reliance".equals(operatorType)) {
            qrObj = new RelianceQRCode(qrString);
        } else {
            qrObj = new MMRDAQRCode(qrString);
        }

        refreshPassengerDashboard();

        JTextField qrField = new JTextField(qrObj.getQrCodeString());
        qrField.setEditable(false);
        qrField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        qrField.setBackground(java.awt.Color.WHITE);

        JButton copyButton = new JButton("Copy QR Code");
        copyButton.addActionListener(e -> {
            qrField.selectAll();
            qrField.copy();
            UIHelper.showMessage("QR code copied to clipboard.", "Copied", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.add(new JLabel("Booking successful! Copy the QR code below:"), BorderLayout.NORTH);
        resultPanel.add(qrField, BorderLayout.CENTER);
        resultPanel.add(copyButton, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(context.getFrame(), resultPanel, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
        showCard("PASSENGER_DASHBOARD");
    }

    public void refreshPassengerDashboard() {
        if (context.getCurrentPassenger() == null) {
            return;
        }
        context.getLblPassengerName().setText("Passenger: " + context.getCurrentPassenger().getUsername());
        context.getLblPassengerBalance().setText(String.format("Wallet Balance: Rs. %.2f", context.getCurrentPassenger().getWalletBalance()));
    }

    private void showCard(String name) {
        // This will be delegated to main
        Main.showCard(name);
    }
}
