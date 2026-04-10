package model;

import operator.MetroOperator;

public class MMRDAQRCode extends UnifiedQRCode {
    public MMRDAQRCode(String qrCodeString) {
        super(qrCodeString);
    }

    @Override
    public void scan(MetroOperator op) {
        System.out.println("Scanning MMRDA QR Code System...");
        op.validateQR(this.qrCodeString);
    }
}
