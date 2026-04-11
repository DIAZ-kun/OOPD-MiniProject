import exception.InsufficientBalanceException;
import java.util.*;
import model.*;
import operator.*;
import registry.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    private static List<String> mmrdaStations = Arrays.asList(
            "Gundavali", "Mogra", "Jogeshwari(East)", 
            "Goregaon(East)", "Rashtriya Udyan", "Dahisar(East)", "Kashigaon"
    );
    private static List<String> relianceStations = Arrays.asList(
            "Airport Road", "Marol Naka", "Saki Naka", "Ghatkopar", "Gundavali"
    );

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Mumbai Metro Unified QR ===");
            System.out.println("1. Passenger login / register");
            System.out.println("2. MMRDA Operator login");
            System.out.println("3. Reliance Operator login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    passengerMenu();
                    break;
                case "2":
                    operatorMenu("MMRDA", "admin", "mmrda123", new MMRDAOperator());
                    break;
                case "3":
                    operatorMenu("Reliance", "admin", "reliance123", new RelianceOperator());
                    break;
                case "4":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void passengerMenu() {
        System.out.println("\n--- Passenger Menu ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();
        
        if ("1".equals(choice)) {
            System.out.print("Username: ");
            String user = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            
            Passenger p = PassengerRegistry.login(user, pass);
            if (p != null) {
                System.out.println("Login successful! Welcome " + p.getUsername());
                loggedInPassengerMenu(p);
            } else {
                System.out.println("Invalid credentials.");
            }
        } else if ("2".equals(choice)) {
            System.out.print("Choose Username: ");
            String user = scanner.nextLine();
            if (PassengerRegistry.exists(user)) {
                System.out.println("Username already exists. Try logging in.");
                return;
            }
            System.out.print("Choose Password: ");
            String pass = scanner.nextLine();
            
            System.out.print("Initial Recharge Amount (optional, press Enter to skip): ");
            String amtStr = scanner.nextLine();
            Passenger p;
            if (amtStr.isBlank()) {
                p = new Passenger(user, pass);
            } else {
                try {
                    double amt = Double.parseDouble(amtStr);
                    p = new Passenger(user, pass, amt);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount using 0 balance.");
                    p = new Passenger(user, pass);
                }
            }
            PassengerRegistry.register(p);
            System.out.println("Registration successful. You can now login.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void loggedInPassengerMenu(Passenger p) {
        while (true) {
            System.out.println("\n--- Passenger Dashboard ---");
            System.out.println("1. View balance");
            System.out.println("2. Recharge wallet");
            System.out.println("3. Book ticket");
            System.out.println("4. View ticket history");
            System.out.println("5. Logout");
            System.out.print("Select: ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Current Balance: Rs." + p.getWalletBalance());
                    break;
                case "2":
                    System.out.print("Enter recharge amount: ");
                    try {
                        double amt = Double.parseDouble(scanner.nextLine());
                        p.rechargeWallet(amt);
                        System.out.println("Recharge successful. New balance: Rs." + p.getWalletBalance());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case "3":
                    bookTicketFlow(p);
                    break;
                case "4":
                    p.displayHistory();
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void bookTicketFlow(Passenger p) {
        System.out.println("\n--- Book Metro Ticket ---");
        System.out.println("Select Line Operator:");
        System.out.println("1. MMRDA");
        System.out.println("2. Reliance");
        System.out.println("3. Interchange (MMRDA <-> Reliance via Gundavali)");
        System.out.print("Your choice: ");
        String opChoice = scanner.nextLine();
        
        String operatorType = "";
        String source = "";
        String dest = "";
        String intermediate = null;
        int fare = 0;
        
        int srcIdx, destIdx;
        
        try {
            if ("1".equals(opChoice)) {
                operatorType = "MMRDA";
                System.out.println("\nMMRDA Stations: ");
                for(int i=0; i<mmrdaStations.size(); i++) {
                    System.out.println((i+1) + ". " + mmrdaStations.get(i));
                }
                System.out.print("Choose Source Index: ");
                srcIdx = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Choose Destination Index: ");
                destIdx = Integer.parseInt(scanner.nextLine()) - 1;
                
                source = mmrdaStations.get(srcIdx);
                dest = mmrdaStations.get(destIdx);
                fare = Math.abs(srcIdx - destIdx) * 10;
                
            } else if ("2".equals(opChoice)) {
                operatorType = "Reliance";
                System.out.println("\nReliance Stations: ");
                for(int i=0; i<relianceStations.size(); i++) {
                    System.out.println((i+1) + ". " + relianceStations.get(i));
                }
                System.out.print("Choose Source Index: ");
                srcIdx = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Choose Destination Index: ");
                destIdx = Integer.parseInt(scanner.nextLine()) - 1;
                
                source = relianceStations.get(srcIdx);
                dest = relianceStations.get(destIdx);
                fare = Math.abs(srcIdx - destIdx) * 10;
            } else if ("3".equals(opChoice)) {
                intermediate = "Gundavali";
                
                System.out.println("\nAre you starting at:");
                System.out.println("1. MMRDA");
                System.out.println("2. Reliance");
                System.out.print("Choice: ");
                String startLine = scanner.nextLine();
                
                if ("1".equals(startLine)) {
                    operatorType = "Interchange-MMRDA-Reliance";
                    System.out.println("\nSelect Source from MMRDA Stations:");
                    for(int i=0; i<mmrdaStations.size(); i++) {
                        System.out.println((i+1) + ". " + mmrdaStations.get(i));
                    }
                    System.out.print("Choose Source Index: ");
                    srcIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    source = mmrdaStations.get(srcIdx);
                    
                    System.out.println("\nSelect Destination from Reliance Stations:");
                    for(int i=0; i<relianceStations.size(); i++) {
                        if (!relianceStations.get(i).equals("Gundavali")) {
                            System.out.println((i+1) + ". " + relianceStations.get(i));
                        }
                    }
                    System.out.print("Choose Destination Index: ");
                    destIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    dest = relianceStations.get(destIdx);
                    
                    int dist1 = Math.abs(srcIdx - mmrdaStations.indexOf("Gundavali"));
                    int dist2 = Math.abs(destIdx - relianceStations.indexOf("Gundavali"));
                    fare = (dist1 + dist2) * 10;
                    
                } else if ("2".equals(startLine)) {
                    operatorType = "Interchange-Reliance-MMRDA";
                    System.out.println("\nSelect Source from Reliance Stations:");
                    for(int i=0; i<relianceStations.size(); i++) {
                        System.out.println((i+1) + ". " + relianceStations.get(i));
                    }
                    System.out.print("Choose Source Index: ");
                    srcIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    source = relianceStations.get(srcIdx);
                    
                    System.out.println("\nSelect Destination from MMRDA Stations:");
                    for(int i=0; i<mmrdaStations.size(); i++) {
                        if (!mmrdaStations.get(i).equals("Gundavali")) {
                            System.out.println((i+1) + ". " + mmrdaStations.get(i));
                        }
                    }
                    System.out.print("Choose Destination Index: ");
                    destIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    dest = mmrdaStations.get(destIdx);
                    
                    int dist1 = Math.abs(srcIdx - relianceStations.indexOf("Gundavali"));
                    int dist2 = Math.abs(destIdx - mmrdaStations.indexOf("Gundavali"));
                    fare = (dist1 + dist2) * 10;
                } else {
                    System.out.println("Invalid line choice.");
                    return;
                }
            } else {
                System.out.println("Invalid choice.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input for station indices.");
            return;
        }

        if (fare == 0 || source.equals(dest)) {
            System.out.println("Source and Destination cannot be the same.");
            return;
        }

        try {
            p.deductBalance(fare);
            String qrString = generateAlphanumericString(10);
            Ticket t = new Ticket(p.getUsername(), operatorType, source, intermediate, dest, fare, qrString);
            
            p.addTicket(t);
            TicketRegistry.saveTicket(t);
            System.out.println("\nBooking successful! Fare deducted: Rs." + fare);
            
            System.out.println("\nWait, generating QR Code...");
            
            UnifiedQRCode qrObj;
            if ("MMRDA".equals(operatorType)) {
                qrObj = new MMRDAQRCode(qrString);
            } else if ("Reliance".equals(operatorType)) {
                qrObj = new RelianceQRCode(qrString);
            } else {
                // For interchange, arbitrarily generate MMRDA QR referencing it as root for simplicity
                qrObj = new MMRDAQRCode(qrString); 
            }
            
            System.out.println("Generated " + qrObj.getClass().getSimpleName() + " with QR: " + qrObj.getQrCodeString());
            System.out.println("Total QR codes issued system-wide: " + UnifiedQRCode.totalIssued);
            
        } catch (InsufficientBalanceException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during booking: " + e.getMessage());
        }
    }

    private static void operatorMenu(String role, String expUser, String expPass, MetroOperator operatorInstance) {
        System.out.println("\n--- " + role + " Operator Login ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        
        if (expUser.equals(user) && expPass.equals(pass)) {
            System.out.println("Login successful.");
            while (true) {
                System.out.println("\n--- Operator Actions ---");
                System.out.println("1. Scan QR Code String");
                System.out.println("2. Logout");
                System.out.print("Choice: ");
                String ch = scanner.nextLine();
                if ("1".equals(ch)) {
                    System.out.print("Enter QR Code String (10 chars): ");
                    String qrStr = scanner.nextLine();
                    
                    Ticket t = TicketRegistry.getTicket(qrStr);
                    if (t != null) {
                        UnifiedQRCode qrObj;
                        if (operatorInstance.getOperatorName().equals("Reliance")) {
                           qrObj = new RelianceQRCode(qrStr);
                        } else {
                           qrObj = new MMRDAQRCode(qrStr);
                        }
                        
                        // Demonstrate polymorphism using the UnifiedQRCode abstract class
                        // This scan method will call operatorInstance.validateQR internally
                        qrObj.scan(operatorInstance);
                    } else {
                        System.out.println("> Invalid QR Code. Ticket not found in system.");
                    }
                    
                } else if ("2".equals(ch)) {
                    break;
                } else {
                    System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Invalid operator credentials.");
        }
    }
    
    private static String generateAlphanumericString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
