//package metro;

//import qr.QRCode;
//import exceptions.*;

public class RelianceMetro extends MetroSystem {

    public RelianceMetro() {
        super("Reliance Metro");
    }

    @Override
    public void scan(QRCode qr) {
        showMetroName();
        System.out.println("Scanning QR for Reliance...");
    }

    @Override
    public void validate(QRCode qr) {
        if (qr == null) {
            throw new RuntimeException("Invalid QR Code");
        }
        System.out.println("QR Validated Successfully ✔");
    }

    @Override
    public void processPayment(QRCode qr) throws InsufficientBalanceException {

        double fare = 40; // peak pricing

        if (qr.getBalance() < fare) {
            throw new InsufficientBalanceException("Low balance in Reliance Metro");
        }

        qr.deductBalance(fare);
        System.out.println("⚡ Peak Pricing Applied");
        System.out.println("Fare Deducted: ₹" + fare);
        System.out.println("Remaining Balance: ₹" + qr.getBalance());
    }
}
