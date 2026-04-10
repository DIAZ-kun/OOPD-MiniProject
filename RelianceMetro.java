// package metro;

// import qr.QRCode;

public class RelianceMetro extends MetroSystem {

    public RelianceMetro() {
        super("Reliance Metro");
    }

    @Override
    public void scan(QRCode qr) {
        showMetroName();
        System.out.println("Scanning QR for Reliance...");
    }

    // import exceptions.*;

    @Override
    public void processPayment(QRCode qr) {

    double fare = 40;

    if (qr == null) {
        throw new RuntimeException("QR Code is null");
    }

    if (qr.getBalance() < fare) {
        throw new RuntimeException("Insufficient Balance in Reliance");
    }

    qr.deductBalance(fare);
    System.out.println("⚡ Peak Pricing Applied");
    System.out.println("Fare Deducted: ₹" + fare);
    System.out.println("Remaining Balance: ₹" + qr.getBalance());
    }

}
