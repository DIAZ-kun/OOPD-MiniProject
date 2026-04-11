import model.Passenger;
import operator.MetroOperator;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class AppContext {
    private static AppContext instance;
    
    private Passenger currentPassenger;
    private MetroOperator currentOperator;
    private JFrame frame;
    
    private final List<String> mmrdaStations = Arrays.asList(
            "Gundavali", "Mogra", "Jogeshwari(East)",
            "Goregaon(East)", "Rashtriya Udyan", "Dahisar(East)", "Kashigaon"
    );
    private final List<String> relianceStations = Arrays.asList(
            "Airport Road", "Marol Naka", "Saki Naka", "Ghatkopar", "Gundavali"
    );
    
    // UI components
    private JLabel lblPassengerName;
    private JLabel lblPassengerBalance;
    private JLabel lblOperatorRole;
    private JComboBox<String> cbBookType;
    private JComboBox<String> cbStartLine;
    private JComboBox<String> cbSource;
    private JComboBox<String> cbDest;
    private JLabel lblFare;
    private JTextArea taOperatorLog;
    private JTextField tfScanCode;
    
    private AppContext() {}
    
    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }
    
    // Getters and setters
    public Passenger getCurrentPassenger() { return currentPassenger; }
    public void setCurrentPassenger(Passenger p) { currentPassenger = p; }
    
    public MetroOperator getCurrentOperator() { return currentOperator; }
    public void setCurrentOperator(MetroOperator op) { currentOperator = op; }
    
    public JFrame getFrame() { return frame; }
    public void setFrame(JFrame f) { frame = f; }
    
    public List<String> getMmrdaStations() { return mmrdaStations; }
    public List<String> getRelianceStations() { return relianceStations; }
    
    public JLabel getLblPassengerName() { return lblPassengerName; }
    public void setLblPassengerName(JLabel lbl) { lblPassengerName = lbl; }
    
    public JLabel getLblPassengerBalance() { return lblPassengerBalance; }
    public void setLblPassengerBalance(JLabel lbl) { lblPassengerBalance = lbl; }
    
    public JLabel getLblOperatorRole() { return lblOperatorRole; }
    public void setLblOperatorRole(JLabel lbl) { lblOperatorRole = lbl; }
    
    public JComboBox<String> getCbBookType() { return cbBookType; }
    public void setCbBookType(JComboBox<String> cb) { cbBookType = cb; }
    
    public JComboBox<String> getCbStartLine() { return cbStartLine; }
    public void setCbStartLine(JComboBox<String> cb) { cbStartLine = cb; }
    
    public JComboBox<String> getCbSource() { return cbSource; }
    public void setCbSource(JComboBox<String> cb) { cbSource = cb; }
    
    public JComboBox<String> getCbDest() { return cbDest; }
    public void setCbDest(JComboBox<String> cb) { cbDest = cb; }
    
    public JLabel getLblFare() { return lblFare; }
    public void setLblFare(JLabel lbl) { lblFare = lbl; }
    
    public JTextArea getTaOperatorLog() { return taOperatorLog; }
    public void setTaOperatorLog(JTextArea ta) { taOperatorLog = ta; }
    
    public JTextField getTfScanCode() { return tfScanCode; }
    public void setTfScanCode(JTextField tf) { tfScanCode = tf; }
}
