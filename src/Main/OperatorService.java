package Main;
import model.Ticket;
import operator.MMRDAOperator;
import operator.RelianceOperator;
import registry.TicketRegistry;
import javax.swing.*;

import java.util.Arrays;

public class OperatorService {
    private static OperatorService instance;
    private AppContext context;
    
    private OperatorService() {
        this.context = AppContext.getInstance();
    }
    
    public static OperatorService getInstance() {
        if (instance == null) {
            instance = new OperatorService();
        }
        return instance;
    }
    
    public void handleOperatorLogin(String role, String username, String password) {
        if (role == null || role.isEmpty()) {
            UIHelper.showMessage("Operator role is not set.", "Login", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!"admin".equals(username) || username.isEmpty() || password.isEmpty()) {
            UIHelper.showMessage("Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean valid = ("MMRDA".equals(role) && "mmrda123".equals(password))
                || ("Reliance".equals(role) && "reliance123".equals(password));
        if (!valid) {
            UIHelper.showMessage("Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        context.setCurrentOperator("Reliance".equals(role) ? new RelianceOperator() : new MMRDAOperator());
        context.getLblOperatorRole().setText(role + " Operator Dashboard");
        context.getTaOperatorLog().setText("Welcome " + role + " operator. Enter a QR code to scan.\n");
        Main.showCard("OPERATOR_DASHBOARD");
    }

    public void handleScanTicket() {
        if (context.getCurrentOperator() == null) {
            return;
        }
        String qrString = context.getTfScanCode().getText().trim();
        if (qrString.isEmpty()) {
            UIHelper.showMessage("Enter a QR code string.", "Scan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Ticket ticket = TicketRegistry.getTicket(qrString);
        if (ticket == null) {
            logOperator("Invalid QR Code. Ticket not found.");
            return;
        }

        boolean valid = context.getCurrentOperator().validateQR(qrString);
        if (valid) {
            enums.QRStatus statusObj = ticket.getStatusForOperator(context.getCurrentOperator().getOperatorName());
            String status = statusObj == null ? "UNKNOWN" : statusObj.toString();
            if (status.equals("ENTERED")) {
                logOperator("Scan successful for " + qrString + ": ENTRY recorded.");
            } else if (status.equals("USED")) {
                logOperator("Scan successful for " + qrString + ": EXIT recorded.");
            } else {
                logOperator("Scan successful for " + qrString + ": " + status + ".");
            }
        } else {
            logOperator("Scan failed for " + qrString + ".");
        }
        context.getTfScanCode().setText("");
    }

    public void logOperator(String message) {
        context.getTaOperatorLog().append(message + "\n");
    }

    public void showOperatorLogin(String role) {
        JPanel panel = (JPanel) Arrays.stream(context.getFrame().getContentPane().getComponents())
                .filter(c -> c instanceof JPanel && ((JPanel) c).getClientProperty("roleLabel") != null)
                .findFirst()
                .orElse(null);
        if (panel != null) {
            JLabel roleLabel = (JLabel) panel.getClientProperty("roleLabel");
            roleLabel.setText(role);
        }
        Main.showCard("OPERATOR_LOGIN");
    }
}
