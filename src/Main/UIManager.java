package Main;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class UIManager {
    private Image backgroundImage;
    private PassengerService passengerService;
    private OperatorService operatorService;
    private AppContext context;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public UIManager(Image backgroundImage, CardLayout cardLayout, JPanel cardPanel) {
        this.backgroundImage = backgroundImage;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.context = AppContext.getInstance();
        this.passengerService = PassengerService.getInstance();
        this.operatorService = OperatorService.getInstance();
    }
    
    public JPanel createHomePanel() {
        JPanel panel = new UIHelper.ImagePanel(backgroundImage);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Mumbai Metro Unified QR", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        title.setOpaque(false);
        panel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setOpaque(false);
        JButton passengerButton = new JButton("Passenger Login / Register");
        JButton mmrdaButton = new JButton("MMRDA Operator Login");
        JButton relianceButton = new JButton("Reliance Operator Login");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(150, 32);
        passengerButton.setPreferredSize(buttonSize);
        mmrdaButton.setPreferredSize(buttonSize);
        relianceButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);
        passengerButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        mmrdaButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        relianceButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        exitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        passengerButton.addActionListener(e -> showCard("PASSENGER_AUTH"));
        mmrdaButton.addActionListener(e -> operatorService.showOperatorLogin("MMRDA"));
        relianceButton.addActionListener(e -> operatorService.showOperatorLogin("Reliance"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(passengerButton);
        buttonPanel.add(mmrdaButton);
        buttonPanel.add(relianceButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    public JPanel createPassengerAuthPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Passenger Login / Register", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel lblUsername = new JLabel("Username:");
        JTextField tfUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField pfPassword = new JPasswordField();

        form.add(lblUsername);
        form.add(tfUsername);
        form.add(lblPassword);
        form.add(pfPassword);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");
        Dimension actionButtonSize = new Dimension(100, 30);
        loginButton.setPreferredSize(actionButtonSize);
        registerButton.setPreferredSize(actionButtonSize);
        backButton.setPreferredSize(actionButtonSize);
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        registerButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        backButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));

        loginButton.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword());
            passengerService.handlePassengerLogin(username, password);
        });

        registerButton.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword());
            passengerService.handlePassengerRegistration(username, password);
        });

        backButton.addActionListener(e -> showCard("HOME"));

        form.add(loginButton);
        form.add(registerButton);
        form.add(new JLabel());
        form.add(backButton);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    public JPanel createPassengerDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lblPassengerName = new JLabel("", SwingConstants.LEFT);
        lblPassengerName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        JLabel lblPassengerBalance = new JLabel("", SwingConstants.LEFT);
        lblPassengerBalance.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        context.setLblPassengerName(lblPassengerName);
        context.setLblPassengerBalance(lblPassengerBalance);
        header.add(lblPassengerName);
        header.add(lblPassengerBalance);
        panel.add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton rechargeButton = new JButton("Recharge Wallet");
        JButton bookTicketButton = new JButton("Book Ticket");
        JButton ticketHistoryButton = new JButton("View Ticket History");
        JButton logoutButton = new JButton("Logout");
        Dimension actionButtonSize = new Dimension(140, 30);
        rechargeButton.setPreferredSize(actionButtonSize);
        bookTicketButton.setPreferredSize(actionButtonSize);
        ticketHistoryButton.setPreferredSize(actionButtonSize);
        logoutButton.setPreferredSize(actionButtonSize);
        rechargeButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        bookTicketButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        ticketHistoryButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        logoutButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));

        rechargeButton.addActionListener(e -> passengerService.handleRecharge());
        bookTicketButton.addActionListener(e -> {
            passengerService.initializeBookingForm();
            showCard("BOOK_TICKET");
        });
        ticketHistoryButton.addActionListener(e -> passengerService.showTicketHistory());
        logoutButton.addActionListener(e -> {
            context.setCurrentPassenger(null);
            showCard("HOME");
        });

        buttonPanel.add(rechargeButton);
        buttonPanel.add(bookTicketButton);
        buttonPanel.add(ticketHistoryButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    public JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Book Metro Ticket", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.add(new JLabel("Ticket Type:"));
        JComboBox<String> cbBookType = new JComboBox<>(new String[]{"MMRDA", "Reliance", "Interchange"});
        context.setCbBookType(cbBookType);

        form.add(cbBookType);
        form.add(new JLabel("Interchange Start Line:"));
        JComboBox<String> cbStartLine = new JComboBox<>(new String[]{"MMRDA", "Reliance"});
        context.setCbStartLine(cbStartLine);
        form.add(cbStartLine);
        form.add(new JLabel("Source Station:"));
        JComboBox<String> cbSource = new JComboBox<>();
        context.setCbSource(cbSource);
        form.add(cbSource);
        form.add(new JLabel("Destination Station:"));
        JComboBox<String> cbDest = new JComboBox<>();
        context.setCbDest(cbDest);
        form.add(cbDest);
        form.add(new JLabel("Estimated Fare:"));
        JLabel lblFare = new JLabel("Rs. 0", SwingConstants.LEFT);
        context.setLblFare(lblFare);
        form.add(lblFare);

        JButton calculateFareButton = new JButton("Calculate Fare");
        JButton bookButton = new JButton("Book Now");
        JButton backButton = new JButton("Back");
        Dimension actionButtonSize = new Dimension(110, 30);
        calculateFareButton.setPreferredSize(actionButtonSize);
        bookButton.setPreferredSize(actionButtonSize);
        backButton.setPreferredSize(actionButtonSize);
        calculateFareButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        bookButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        backButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));

        calculateFareButton.addActionListener(e -> passengerService.updateFarePreview());
        bookButton.addActionListener(e -> passengerService.attemptBooking());
        backButton.addActionListener(e -> showCard("PASSENGER_DASHBOARD"));

        form.add(calculateFareButton);
        form.add(bookButton);
        form.add(new JLabel());
        form.add(backButton);

        cbBookType.addActionListener(e -> passengerService.initializeBookingForm());
        cbStartLine.addActionListener(e -> passengerService.updateBookingSources());

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    public JPanel createOperatorLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Operator Login", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel lblRole = new JLabel("Operator Role:");
        JLabel lblRoleValue = new JLabel("", SwingConstants.LEFT);
        JLabel lblUsername = new JLabel("Username:");
        JTextField tfUsername = new JTextField();
        JLabel lblPassword = new JLabel("Password:");
        JPasswordField pfPassword = new JPasswordField();

        form.add(lblRole);
        form.add(lblRoleValue);
        form.add(lblUsername);
        form.add(tfUsername);
        form.add(lblPassword);
        form.add(pfPassword);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        Dimension actionButtonSize = new Dimension(100, 30);
        loginButton.setPreferredSize(actionButtonSize);
        backButton.setPreferredSize(actionButtonSize);
        loginButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        backButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));

        loginButton.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword());
            operatorService.handleOperatorLogin(lblRoleValue.getText(), username, password);
        });

        backButton.addActionListener(e -> showCard("HOME"));

        form.add(loginButton);
        form.add(backButton);

        panel.add(form, BorderLayout.CENTER);

        panel.putClientProperty("roleLabel", lblRoleValue);
        return panel;
    }

    public JPanel createOperatorDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblOperatorRole = new JLabel("", SwingConstants.LEFT);
        lblOperatorRole.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        context.setLblOperatorRole(lblOperatorRole);
        panel.add(lblOperatorRole, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 10));
        JPanel scanPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        scanPanel.add(new JLabel("QR Code String:"));
        JTextField tfScanCode = new JTextField();
        context.setTfScanCode(tfScanCode);
        scanPanel.add(tfScanCode);
        scanPanel.add(new JLabel());
        JButton scanButton = new JButton("Scan Ticket");
        scanButton.setPreferredSize(new Dimension(110, 30));
        scanButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        scanButton.addActionListener(e -> operatorService.handleScanTicket());
        scanPanel.add(scanButton);

        center.add(scanPanel, BorderLayout.NORTH);

        JTextArea taOperatorLog = new JTextArea(10, 40);
        context.setTaOperatorLog(taOperatorLog);
        taOperatorLog.setEditable(false);
        taOperatorLog.setLineWrap(true);
        taOperatorLog.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(taOperatorLog);
        center.add(scrollPane, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        logoutButton.addActionListener(e -> {
            context.setCurrentOperator(null);
            taOperatorLog.setText("");
            tfScanCode.setText("");
            showCard("HOME");
        });
        panel.add(logoutButton, BorderLayout.SOUTH);
        return panel;
    }

    private void showCard(String name) {
        cardLayout.show(cardPanel, name);
        if ("PASSENGER_DASHBOARD".equals(name)) {
            passengerService.refreshPassengerDashboard();
        }
    }
}
