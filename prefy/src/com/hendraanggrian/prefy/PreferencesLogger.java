package com.hendraanggrian.prefy;

/**
 * Interface that determines preferences logging behavior.
 */
public interface PreferencesLogger {

    /**
     * Logger that prints to {@link System#out} with type prefix.
     */
    PreferencesLogger SYSTEM = new PreferencesLogger() {
        @Override
        public void info(String message) {
            System.out.println("INFO: " + message);
        }

        @Override
        public void warn(String message) {
            System.out.println("WARN: " + message);
        }
    };

    /**
     * Log message in information channel.
     *
     * @param message text to print.
     */
    void info(String message);

    /**
     * Log message in warning channel.
     *
     * @param message text to print.
     */
    void warn(String message);
}
