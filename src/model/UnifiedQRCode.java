package model;

import operator.MetroOperator;

public abstract class UnifiedQRCode {
    public static int totalIssued = 0;
    protected String qrCodeString;

    public UnifiedQRCode(String qrCodeString) {
        this.qrCodeString = qrCodeString;
        totalIssued++;
    }

    public String getQrCodeString() {
        return qrCodeString;
    }

    public abstract void scan(MetroOperator op);
}
