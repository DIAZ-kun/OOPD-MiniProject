//package metro;

//import qr.QRCode;
//import exceptions.*;

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
    public void validate(QRCode qr) {
        if (qr == null) {
            throw new RuntimeException("Invalid QR Code");
        }
        System.out.println("QR Validated Successfully ✔");
    }

    @Override
    public void processPayment(QRCode qr) throws InsufficientBalanceException {

        double fare = 30;

        if (qr.getBalance() < fare) {
            throw new InsufficientBalanceException("Low balance in MMRDA Metro");
        }

        qr.deductBalance(fare);
        System.out.println("Fare Deducted: ₹" + fare);
        System.out.println("Remaining Balance: ₹" + qr.getBalance());
    }
}
