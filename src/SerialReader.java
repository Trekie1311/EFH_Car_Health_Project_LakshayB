import com.fazecast.jSerialComm.SerialPort;
import java.util.Scanner;

public class SerialReader {

    public static void listAvailableSerialPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            System.out.println("No serial ports available.");
        } else {
            System.out.println("Available serial ports:");

            for (SerialPort port : ports) {
                System.out.println(port.getSystemPortName());
            }
        }
    }

    public static void readFromArduino(String portName, GUI gui) {
        SerialPort port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);

        if (!port.openPort()) {
            System.out.println("Could not open port " + portName);
            return;
        }

        try (Scanner scanner = new Scanner(port.getInputStream())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                System.out.println("Received from Arduino: " + line);
                gui.updateData(line);
            }
        } finally {
            port.closePort();
        }
    }
}
