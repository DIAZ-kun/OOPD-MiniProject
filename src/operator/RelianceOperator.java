package operator;

import model.Ticket;
import registry.TicketRegistry;
import enums.QRStatus;

public class RelianceOperator implements MetroOperator {
    @Override
    public boolean validateQR(String code) {
        Ticket t = TicketRegistry.getTicket(code);
        if (t != null) {
            System.out.println("\n--- QR Validation Details ---");
            System.out.println("Passenger   : " + t.getPassengerName());
            System.out.println("Source      : " + t.getSource());
            System.out.println("Destination : " + t.getDestination());
            System.out.println("Fare Paid   : " + t.getFare());
            System.out.println("QR Status   : " + t.getStatus());
            
            if (t.getStatus() == QRStatus.ACTIVE) {
                t.setStatus(QRStatus.USED);
                System.out.println("> Valid Ticket. Marking as USED.");
                return true;
            } else {
                System.out.println("> Invalid: Ticket is already " + t.getStatus());
                return false;
            }
        }
        System.out.println("> Invalid QR Code. Ticket not found in system.");
        return false;
    }

    @Override
    public String getOperatorName() {
        return "Reliance";
    }
}
