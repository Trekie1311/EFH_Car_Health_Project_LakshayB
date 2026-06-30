import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

    // Labels shown in the GUI window
    private JLabel tempLabel;
    private JLabel currentLabel;
    private JLabel statusLabel;

    public GUI() {
        // Set up the main GUI window
        setTitle("Car Health Monitoring System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Arrange the three labels in 3 rows and 1 column
        setLayout(new GridLayout(3, 1));

        // Create labels with default starting text
        tempLabel = new JLabel("Temperature: ");
        currentLabel = new JLabel("Current: ");
        statusLabel = new JLabel("Status: ");

        // Add labels to the window
        add(tempLabel);
        add(currentLabel);
        add(statusLabel);

        // Show the window after all components are added
        setVisible(true);
    }

    public void updateData(String line) {
        // If this method is called from the serial reader thread,
        // send the GUI update to Swing's GUI thread.
        // Without this, the GUI may freeze, flicker, or behave unpredictably.
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> updateData(line));
            return;
        }

        // Split one Arduino message into separate parts
        // Example: TEMP:26.5,CURRENT:0.4,STATUS:NORMAL
        String[] parts = line.split(",");

        // Store parsed values so they can be used for alerts and logging in the for loop
        Double temperature = null;
        Double current = null;
        String status = null;

        for (String part : parts) {
            //removes any leading or trailing whitespace from the part
            part = part.trim();
            //takes the numebr after the TEMP: and CURRENT: and converts it to a double
            if (part.startsWith("TEMP:")) {
                String value = part.substring(5).trim();
                temperature = parseDouble(value);
                tempLabel.setText("Temperature: " + value + " deg C");

            } else if (part.startsWith("CURRENT:")) {
                String value = part.substring(8).trim();
                current = parseDouble(value);
                currentLabel.setText("Current: " + value + " A");
                
            } else if (part.startsWith("STATUS:")) {
                status = part.substring(7).trim();
            }
        }



        // Allow label color changes to be visible
        statusLabel.setOpaque(true);

        // Color-code the status text based on the alert level
        if ("NORMAL".equals(status)) {
            statusLabel.setForeground(Color.GREEN);
        } else if (status != null && status.contains("WARNING")) {
            statusLabel.setForeground(Color.YELLOW);
        } else if ("CRITICAL".equals(status)) {
            statusLabel.setForeground(Color.RED);
        }

        // Save the reading only if all needed values were successfully parsed
        if (temperature != null && current != null && status != null) {
            LogWriter.writeLog(temperature, current, status);
        }
    }

    private Double parseDouble(String value) {
        try {
            // Convert sensor text like "26.5" into a decimal number
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            // If Arduino sends invalid data, do not crash the GUI
            System.err.println("Invalid number from Arduino: " + value);
            return null;
        }
    }
}