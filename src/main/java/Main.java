import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import startupApp.LoadingModel;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * main method sets the swing look and feel to the systems standard and launches the loading window
     *
     * @param args unused
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Disables the Logger of the NativeHook library
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }


        SwingUtilities.invokeLater(() -> {
            try {
                new LoadingModel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
}
