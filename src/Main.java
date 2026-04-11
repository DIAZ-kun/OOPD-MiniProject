import javax.swing.*;
import java.awt.*;


public class Main {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Mumbai Metro Unified QR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 520);
        frame.setLocationRelativeTo(null);

        AppContext.getInstance().setFrame(frame);

        Image backgroundImage = UIHelper.loadBackgroundImage();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        UIManager uiManager = new UIManager(backgroundImage, cardLayout, cardPanel);

        cardPanel.add(uiManager.createHomePanel(), "HOME");
        cardPanel.add(uiManager.createPassengerAuthPanel(), "PASSENGER_AUTH");
        cardPanel.add(uiManager.createPassengerDashboardPanel(), "PASSENGER_DASHBOARD");
        cardPanel.add(uiManager.createBookingPanel(), "BOOK_TICKET");
        cardPanel.add(uiManager.createOperatorLoginPanel(), "OPERATOR_LOGIN");
        cardPanel.add(uiManager.createOperatorDashboardPanel(), "OPERATOR_DASHBOARD");

        frame.setContentPane(cardPanel);
        showCard("HOME");
        frame.setVisible(true);
    }

    public static void showCard(String name) {
        cardLayout.show(cardPanel, name);
        if ("PASSENGER_DASHBOARD".equals(name)) {
            PassengerService.getInstance().refreshPassengerDashboard();
        }
    }
}
