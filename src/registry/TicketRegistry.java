package registry;

import model.Ticket;
import java.util.HashMap;
import java.util.Map;

public class TicketRegistry {
    private static Map<String, Ticket> allTickets = new HashMap<>();

    public static void saveTicket(Ticket t) {
        allTickets.put(t.getQrCode(), t);
    }

    public static Ticket getTicket(String qrCode) {
        return allTickets.get(qrCode);
    }
}
