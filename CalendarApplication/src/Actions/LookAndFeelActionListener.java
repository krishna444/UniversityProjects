
package Actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *This is the action listener for look and feel. This changes the look and feel of all components
 * @author krishna
 */
public class LookAndFeelActionListener implements ActionListener{
    private UIManager.LookAndFeelInfo look;
    public LookAndFeelActionListener(UIManager.LookAndFeelInfo look) {
        this.look = look;
    }
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(look.getClassName());
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}