// package metro;

// import qr.QRCode;

// import exceptions.*;

public class MMRDAMetro extends MetroSystem {

    public MMRDAMetro() {
        super("MMRDA Metro");
    }

    @Override
    public void scan(QRCode qr) {
        showMetroName();
        System.out.println("Scanning QR for MMRDA...");
    }

    @Override
    public void processPayment(QRCode qr) {

    double fare = 30;

    if (qr == null) {
        throw new RuntimeException("QR Code is null");
    }

    if (qr.getBalance() < fare) {
        throw new RuntimeException("Insufficient Balance in MMRDA");
    }

    qr.deductBalance(fare);
    System.out.println("Fare Deducted: ₹" + fare);
    System.out.println("Remaining Balance: ₹" + qr.getBalance());
    }
}
