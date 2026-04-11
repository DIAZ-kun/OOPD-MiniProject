//package user;

//import qr.QRCode;

public class User {

    private String name;
    private QRCode qr;

    public User(String name, QRCode qr) {
        this.name = name;
        this.qr = qr;
    }

    public String getName() {
        return name;
    }

    public QRCode getQr() {
        return qr;
    }

    public void displayUser() {
        System.out.println("User: " + name);
        qr.display();
    }
}
