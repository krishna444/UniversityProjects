
package monthviewapplication;


import DataStore.CalendarEventMarshaller;
import DataStore.xmlDataRepository;
import TodoManager.CalendarEvent;
import TodoManager.Settings;
import java.io.IOException;
import java.util.Properties;
import javax.swing.SwingUtilities;
import org.jdom.JDOMException;

/**
 *
 * @author Admin
 * @author Krishna
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /**
         * Initialize the repository
         */
        try {
            Settings.setRepository(
                    new xmlDataRepository<CalendarEvent>(
             System.getProperty("user.home")+"/Group5/CalendarXml.xml",
                    new CalendarEventMarshaller()));

        }
        catch (JDOMException ex) {
        }
        catch (IOException ex) {
        }

        /**
         * This method helps to update the GUI while some other thread is
         * running. The GUI is not blocked while another thread is working.
         */
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Properties pref = Settings.getPreference();

                Settings.setLocale(pref.getProperty("Language", "en").toString(),
                        pref.getProperty("Country", "US").toString());

                Settings.setTheme(pref.getProperty("Theme", "MetalTheme2"));

                System.out.println(System.getProperty("user.home"));               
                
                // TODO code application logic here
                MainFrame frame=new MainFrame(pref);
                frame.setVisible(true);
                Settings.setMainGUI(frame);
                
                 
            }
        });



 
    }

   
}
