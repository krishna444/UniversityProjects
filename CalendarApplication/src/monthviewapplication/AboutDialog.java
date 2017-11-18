package monthviewapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * About Dialog class
 * @author Group 3
 */
public class AboutDialog extends JDialog {

    /** Unique ID for this window, used for getting and setting size and location */
    private final String windowID = "AboutDialog";
    /** Default window-location used if no previously location has been stored */
    private final Point defaultLocation = new Point(50, 50);
    /** Default window-size used if no previously size has been stored */
    private final Dimension defaultSize = new Dimension(300, 200);


    /*
     * Creates a simple about dialog with some labels.
     */
    public AboutDialog() {
        setTitle("About");

        // Add a windowlistener that saves the window state at exit.
        addWindowListener(new AboutDialogCloseListener());

        /* Set the size and location of this dialog.
         * Tries to get the prevously used values,
         * but will use the default values if no such values exist.
         */
        Point location = WindowInformation.getWindowLocation(windowID, defaultLocation);
        Dimension size = WindowInformation.getWindowSize(windowID, defaultSize);
        Rectangle bounds = new Rectangle(location, size);
        setBounds(bounds);

        setModal(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 0));

        // Labels
        JLabel label1 = new JLabel("Calendar Application", JLabel.CENTER);
        label1.setForeground(Color.RED);
        Label label2 = new Label("Created By:", Label.CENTER);
        label2.setForeground(Color.BLACK);
        Label label3 = new Label("Students, Uppsala University", Label.CENTER);
        label3.setForeground(Color.BLUE);
        Label label4 = new Label("Thank You!", Label.CENTER);
        label4.setForeground(Color.LIGHT_GRAY);

        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        mainPanel.add(panel, BorderLayout.NORTH);
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }


    /*
     * A simple windowlistener class.
     * Used to save window location and size at closing time.
     */
    private class AboutDialogCloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            WindowInformation.setWindowLocation(windowID, getLocation());
            WindowInformation.setWindowSize(windowID, getSize());
        }
    }
}
