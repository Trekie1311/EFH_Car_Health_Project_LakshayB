import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class LogWriter {
    private static final Path LOG_FILE = Path.of("logs", "logs1.csv");

    public static void writeLog(double temperature, double current, String status) {
        try {
            Files.createDirectories(LOG_FILE.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(
                    LOG_FILE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                writer.write(LocalDateTime.now() + "," + temperature + "," + current + "," + status);
                writer.newLine();
            }

            System.out.println("Log written successfully.");
        } catch (IOException ex) {
            System.getLogger(LogWriter.class.getName())
                    .log(System.Logger.Level.ERROR, "Could not write log file.", ex);
        }
    }
}
