import java.util.Scanner;

//import qr.QRCode;
//import metro.*;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create a QR user
        QRCode qr = new QRCode("Z123", 000);

        MetroSystem metro = null;

        while (true) {

            System.out.println("\n===== PolyQR: Unified Metro System =====");
            System.out.println("1. MMRDA Metro");
            System.out.println("2. Reliance Metro");
            System.out.println("3. Exit");
            System.out.print("Choose Metro: ");

            int choice = sc.nextInt();

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            // 🔥 Polymorphism happens here
            switch (choice) {
                case 1:
                    metro = new MMRDAMetro();
                    break;

                case 2:
                    metro = new RelianceMetro();
                    break;

                default:
                    System.out.println("Invalid choice!");
                    continue;
            }

            System.out.println("\n1. Scan & Pay");
            System.out.println("2. Show QR Details");
            System.out.print("Choose Action: ");
            int action = sc.nextInt();

            switch (action) {

                case 1:
                    try {
                        metro.scan(qr);
                        metro.processPayment(qr);
                    } catch (Exception e) {
                        System.out.println("❌ Error: " + e.getMessage());
                    }

                    break;

                case 2:
                    qr.display();
                    break;

                default:
                    System.out.println("Invalid action!");
            }
        }

        sc.close();
    }
}
