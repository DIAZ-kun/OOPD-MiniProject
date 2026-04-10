package model;

import operator.MetroOperator;

public class RelianceQRCode extends UnifiedQRCode {
    public RelianceQRCode(String qrCodeString) {
        super(qrCodeString);
    }

    @Override
    public void scan(MetroOperator op) {
        System.out.println("Scanning Reliance QR Code System...");
        op.validateQR(this.qrCodeString);
    }
}
