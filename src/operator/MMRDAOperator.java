package operator;

import model.Ticket;
import registry.TicketRegistry;
import enums.QRStatus;

public class MMRDAOperator implements MetroOperator {
    @Override
    public boolean validateQR(String code) {
        Ticket t = TicketRegistry.getTicket(code);
        if (t == null) {
            System.out.println("> Invalid QR Code. Ticket not found in system.");
            return false;
        }
        if (!t.supportsOperator(getOperatorName())) {
            System.out.println("> This ticket is not valid for MMRDA operations.");
            return false;
        }

        String segmentSource = t.getSegmentSource(getOperatorName());
        String segmentDestination = t.getSegmentDestination(getOperatorName());

        t.incrementMMRDAScanCount();
        int scanCount = t.getMMRDAScanCount();

        System.out.println("\n--- MMRDA QR Validation ---");
        System.out.println("Passenger       : " + t.getPassengerName());
        System.out.println("Line Segment    : " + segmentSource + " -> " + segmentDestination);
        System.out.println("Scan Count      : " + scanCount);

        if (scanCount == 1) {
            t.setMMRDAStatus(QRStatus.ENTERED);
            System.out.println("> Entry scan accepted. Status set to ENTERED.");
            return true;
        } else if (scanCount == 2) {
            t.setMMRDAStatus(QRStatus.USED);
            System.out.println("> Exit scan accepted. Status set to USED.");
            return true;
        } else {
            System.out.println("> Invalid scan: Ticket has already been used.");
            return false;
        }
    }

    @Override
    public String getOperatorName() {
        return "MMRDA";
    }
}
