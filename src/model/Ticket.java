package model;

import enums.QRStatus;

public class Ticket {
    private String passengerName;
    private String operatorType;
    private String source;
    private String intermediate;
    private String destination;
    private double fare;
    private QRStatus status;
    private String qrCode;

    public Ticket(String passengerName, String operatorType, String source, String intermediate, String destination, double fare, String qrCode) {
        this.passengerName = passengerName;
        this.operatorType = operatorType;
        this.source = source;
        this.intermediate = intermediate;
        this.destination = destination;
        this.fare = fare;
        this.qrCode = qrCode;
        this.status = QRStatus.ACTIVE;
    }

    public String getQrCode() { return qrCode; }
    public QRStatus getStatus() { return status; }
    public void setStatus(QRStatus status) { this.status = status; }
    public String getPassengerName() { return passengerName; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getFare() { return fare; }
    public String getOperatorType() { return operatorType; }

    @Override
    public String toString() {
        return String.format(
            "====================================\n" +
            "            METRO TICKET            \n" +
            "====================================\n" +
            "Passenger : %s\n" +
            "Operator  : %s\n" +
            "Source    : %s\n" +
            "Transit   : %s\n" +
            "Dest      : %s\n" +
            "Fare      : %.2f INR\n" +
            "QR Status : %s\n" +
            "QR Code   : %s\n" +
            "====================================",
            passengerName, operatorType, source, 
            (intermediate == null ? "N/A" : intermediate), 
            destination, fare, status, qrCode
        );
    }
}
