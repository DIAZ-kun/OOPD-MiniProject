package model;

import enums.QRStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String passengerName;
    private String operatorType;
    private String source;
    private String intermediate;
    private String destination;
    private double fare;
    private String qrCode;
    private QRStatus mmrdaStatus;
    private QRStatus relianceStatus;
    private int mmrdaScanCount;
    private int relianceScanCount;
    private String bookingTime;
    private String entryTime;
    private String exitTime;

    public Ticket(String passengerName, String operatorType, String source, String intermediate, String destination, double fare, String qrCode) {
        this.passengerName = passengerName;
        this.operatorType = operatorType;
        this.source = source;
        this.intermediate = intermediate;
        this.destination = destination;
        this.fare = fare;
        this.qrCode = qrCode;
        this.mmrdaStatus = null;
        this.relianceStatus = null;
        this.mmrdaScanCount = 0;
        this.relianceScanCount = 0;
        this.bookingTime = LocalDateTime.now().format(FORMATTER);
        this.entryTime = "N/A";
        this.exitTime = "N/A";

        if (operatorType.equals("MMRDA")) {
            this.mmrdaStatus = QRStatus.CREATED;
        } else if (operatorType.equals("Reliance")) {
            this.relianceStatus = QRStatus.CREATED;
        } else if (operatorType.equals("Interchange-MMRDA-Reliance") || operatorType.equals("Interchange-Reliance-MMRDA")) {
            this.mmrdaStatus = QRStatus.CREATED;
            this.relianceStatus = QRStatus.CREATED;
        }
    }

    public String getQrCode() { return qrCode; }

    public QRStatus getMMRDAStatus() { return mmrdaStatus; }
    public QRStatus getRelianceStatus() { return relianceStatus; }

    public void setMMRDAStatus(QRStatus status) { this.mmrdaStatus = status; }
    public void setRelianceStatus(QRStatus status) { this.relianceStatus = status; }

    public int getMMRDAScanCount() { return mmrdaScanCount; }
    public int getRelianceScanCount() { return relianceScanCount; }

    public void incrementMMRDAScanCount() { this.mmrdaScanCount++; }
    public void incrementRelianceScanCount() { this.relianceScanCount++; }

    public String getPassengerName() { return passengerName; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getFare() { return fare; }
    public String getOperatorType() { return operatorType; }
    public String getBookingTime() { return bookingTime; }
    public String getEntryTime() { return entryTime; }
    public String getExitTime() { return exitTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime.format(FORMATTER); }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime.format(FORMATTER); }

    public boolean supportsOperator(String operatorName) {
        if (operatorName.equals("MMRDA")) {
            return (operatorType.equals("MMRDA") || operatorType.equals("Interchange-MMRDA-Reliance") || operatorType.equals("Interchange-Reliance-MMRDA"));
        }
        if (operatorName.equals("Reliance")) {
            return (operatorType.equals("Reliance") || operatorType.equals("Interchange-MMRDA-Reliance") || operatorType.equals("Interchange-Reliance-MMRDA"));
        }
        return false;
    }

    public String getSegmentSource(String operatorName) {
        if (operatorName.equals("MMRDA")) {
            if (operatorType.equals("MMRDA")) {
                return source;
            }
            if (operatorType.equals("Interchange-MMRDA-Reliance")) {
                return source;
            }
            if (operatorType.equals("Interchange-Reliance-MMRDA")) {
                return intermediate;
            }
        }
        if (operatorName.equals("Reliance")) {
            if (operatorType.equals("Reliance")) {
                return source;
            }
            if (operatorType.equals("Interchange-MMRDA-Reliance")) {
                return intermediate;
            }
            if (operatorType.equals("Interchange-Reliance-MMRDA")) {
                return source;
            }
        }
        return null;
    }

    public String getSegmentDestination(String operatorName) {
        if (operatorName.equals("MMRDA")) {
            if (operatorType.equals("MMRDA")) {
                return destination;
            }
            if (operatorType.equals("Interchange-MMRDA-Reliance")) {
                return intermediate;
            }
            if (operatorType.equals("Interchange-Reliance-MMRDA")) {
                return destination;
            }
        }
        if (operatorName.equals("Reliance")) {
            if (operatorType.equals("Reliance")) {
                return destination;
            }
            if (operatorType.equals("Interchange-MMRDA-Reliance")) {
                return destination;
            }
            if (operatorType.equals("Interchange-Reliance-MMRDA")) {
                return intermediate;
            }
        }
        return null;
    }

    public QRStatus getStatusForOperator(String operatorName) {
        if (operatorName.equals("MMRDA")) {
            return mmrdaStatus;
        }
        if (operatorName.equals("Reliance")) {
            return relianceStatus;
        }
        return null;
    }

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
            "MMRDA     : %s\n" +
            "Reliance  : %s\n" +
            "QR Code   : %s\n" +
            "Booking   : %s\n" +
            "Entry     : %s\n" +
            "Exit      : %s\n" +
            "====================================",
            passengerName,
            operatorType,
            source,
            (intermediate == null ? "N/A" : intermediate),
            destination,
            fare,
            (mmrdaStatus == null ? "N/A" : mmrdaStatus),
            (relianceStatus == null ? "N/A" : relianceStatus),
            qrCode,
            bookingTime,
            entryTime,
            exitTime
        );
    }
}
