import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import startupApp.LoadingModel;
import utils.NativeLoadingHandler;

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
            NativeLoadingHandler.loadNatives();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLookAndFeel();
        registerNativeKeyListener();


        createStartupApp();


    }

    /**
     * creates the startup app
     */
    private static void createStartupApp() {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoadingModel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * registers the native key listener for the application
     */
    private static void registerNativeKeyListener() {
        // Disables the Logger of the NativeHook library
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ignored) {

        }
    }

    /**
     * sets the LookAndFeel of the application
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
