public class AlertEngine {

    // Safe operating limits for the car health system
    // If a reading goes above these values, a warning is returned
    private static final double HIGH_TEMP_LIMIT = 35.0;
    private static final double HIGH_CURRENT_LIMIT = 1.0;

    // Checks the sensor readings and returns the current system status
    public static String checkStatus(double temperature, double current) {

        // If the temperature is above the safe limit,
        // return a temperature warning
        if (temperature > HIGH_TEMP_LIMIT) {
            return "WARNING_TEMP";
        }

        // If the current is above the safe limit,
        // return a current warning
        if (current > HIGH_CURRENT_LIMIT) {
            return "WARNING_CURRENT";
        }

        // If neither reading exceeds its limit,
        // the system is operating normally
        return "NORMAL";
    }
}