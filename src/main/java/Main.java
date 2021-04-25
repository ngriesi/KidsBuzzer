import startupApp.LoadingModel;

import javax.swing.*;

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

        SwingUtilities.invokeLater(() -> {
            try {
                new LoadingModel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
}
