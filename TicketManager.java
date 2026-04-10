package manager;

import metro.MetroSystem;
import qr.QRCode;

public class TicketManager {

    public void processJourney(MetroSystem metro, QRCode qr) {

        try {
            metro.scan(qr);
            metro.validate(qr);
            metro.processPayment(qr);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
