package Main;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class UIHelper {
    
    public static void showMessage(String message, String title, int messageType) {
        AppContext context = AppContext.getInstance();
        JOptionPane.showMessageDialog(context.getFrame(), message, title, messageType);
    }
    
    public static Image loadBackgroundImage() {
        String[] paths = {"background.png", "../background.png", "src/background.png"};
        for (String path : paths) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    return ImageIO.read(file);
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }
    
    public static String generateAlphanumericString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    public static class ImagePanel extends JPanel {
        private final Image image;

        public ImagePanel(Image image) {
            super(new BorderLayout());
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
