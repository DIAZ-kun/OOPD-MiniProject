package operator;

import model.Ticket;
import registry.TicketRegistry;
import enums.QRStatus;

public class RelianceOperator implements MetroOperator {
    @Override
    public boolean validateQR(String code) {
        Ticket t = TicketRegistry.getTicket(code);
        if (t == null) {
            System.out.println("> Invalid QR Code. Ticket not found in system.");
            return false;
        }
        if (!t.supportsOperator(getOperatorName())) {
            System.out.println("> This ticket is not valid for Reliance operations.");
            return false;
        }

        String segmentSource = t.getSegmentSource(getOperatorName());
        String segmentDestination = t.getSegmentDestination(getOperatorName());

        t.incrementRelianceScanCount();
        int scanCount = t.getRelianceScanCount();

        System.out.println("\n--- Reliance QR Validation ---");
        System.out.println("Passenger       : " + t.getPassengerName());
        System.out.println("Line Segment    : " + segmentSource + " -> " + segmentDestination);
        System.out.println("Scan Count      : " + scanCount);

        if (scanCount == 1) {
            t.setRelianceStatus(QRStatus.ENTERED);
            System.out.println("> Entry scan accepted. Status set to ENTERED.");
            return true;
        } else if (scanCount == 2) {
            t.setRelianceStatus(QRStatus.USED);
            System.out.println("> Exit scan accepted. Status set to USED.");
            return true;
        } else {
            System.out.println("> Invalid scan: Ticket has already been used.");
            return false;
        }
    }

    @Override
    public String getOperatorName() {
        return "Reliance";
    }
}
