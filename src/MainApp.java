import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("=== Car Health Monitoring System ===");

        SerialReader.listAvailableSerialPorts();

        String arduinoPort = "COM3";

        System.out.println("Selected Arduino port: " + arduinoPort);

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            Thread serialThread = new Thread(
                    () -> SerialReader.readFromArduino(arduinoPort, gui),
                    "Arduino Serial Reader");
            serialThread.start();
        });
    }
}
