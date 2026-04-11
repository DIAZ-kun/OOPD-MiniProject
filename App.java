//package main;

import java.util.Scanner;

//import qr.QRCode;
//import metro.*;
//import manager.TicketManager;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        QRCode qr = new QRCode("Z123", 200);
        TicketManager manager = new TicketManager();

        while (true) {

            System.out.println("\n===== PolyQR: Unified Metro System =====");
            System.out.println("1. MMRDA Metro");
            System.out.println("2. Reliance Metro");
            System.out.println("3. Recharge QR");
            System.out.println("4. Show QR Details");
            System.out.println("5. Exit");
            System.out.print("Choose Option: ");

            int choice = sc.nextInt();

            MetroSystem metro = null;

            switch (choice) {

                case 1:
                    metro = new MMRDAMetro();
                    break;

                case 2:
                    metro = new RelianceMetro();
                    break;

                case 3:
                    System.out.print("Enter recharge amount: ");
                    double amt = sc.nextDouble();
                    qr.recharge(amt);
                    continue;

                case 4:
                    qr.display();
                    continue;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
                    continue;
            }

            manager.processJourney(metro, qr);
        }
    }
}
