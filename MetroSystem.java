// package metro;

// import qr.QRCode;

public abstract class MetroSystem {

    protected String metroName;

    // Constructor
    public MetroSystem(String metroName) {
        this.metroName = metroName;
    }

    // Abstract methods (no body)
    public abstract void scan(QRCode qr);
    public abstract void processPayment(QRCode qr);

    // Common method (same for all)
    public void showMetroName() {
        System.out.println("\n[" + metroName + "]");
    }
}
