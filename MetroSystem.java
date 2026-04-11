//package metro;

//import qr.QRCode;
//import interfaces.*;

public abstract class MetroSystem implements Scannable, Validatable {

    protected String metroName;

    public MetroSystem(String metroName) {
        this.metroName = metroName;
    }

    public abstract void processPayment(QRCode qr) throws Exception;

    public void showMetroName() {
        System.out.println("\n[" + metroName + "]");
    }
}
