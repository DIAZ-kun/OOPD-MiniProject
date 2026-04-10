package registry;

import model.Passenger;
import java.util.HashMap;
import java.util.Map;

public class PassengerRegistry {
    private static Map<String, Passenger> passengers = new HashMap<>();

    public static void register(Passenger p) {
        passengers.put(p.getUsername(), p);
    }

    public static Passenger login(String username, String password) {
        Passenger p = passengers.get(username);
        if (p != null && p.validatePassword(password)) {
            return p;
        }
        return null;
    }

    public static boolean exists(String username) {
        return passengers.containsKey(username);
    }
}
