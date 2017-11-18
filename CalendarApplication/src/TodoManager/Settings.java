package TodoManager;

import Actions.CalendarEventAddEditAction;
import Actions.CalendarEventAddEditActionNew;
import DataStore.Repository;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is used for storing the application settings that we are
 * currently employing. This class is a concrete class. This class configures
 * our application.
 * @author Rupesh
 * @author volkan cambazoglu
 */
public class Settings {

    private static Locale locale = Locale.getDefault();
    private static ResourceBundle languageResourceBundle;
    private static Repository repository;
    private static Properties preference;
    private static ResourceBundle themeResourceBundle;
    private static String theme;
    private static JFrame mainGUI;
    
    private static int transparency;

    /**
     * Getter method for theme of the application
     * @return theme theme of the application
     */
    public static String getTheme() {
		return theme;
	}

    /**
     * Setter method for the theme of the application
     * @param theme theme of the application
     */
	public static void setTheme(String theme) {
		Settings.theme = theme;
	}

	/**
     * Returns the instance of resource bundle
     * @return This resource bundle is used for theme of the application
     */
    public static ResourceBundle getThemeResourceBundle() {
        if (themeResourceBundle == null) {
        	themeResourceBundle = ResourceBundle.getBundle(
        			"Group9.TodoManager.Theme", locale);
        }
        return themeResourceBundle;
    }
    
    /**
     * This returns the preference class instance for the application
     * @return Preference instance to store/retrieve the preference.
     */
    public static Properties getPreference() {
        if(preference == null){
            try {
                preference = new Properties(); 
                preference.load(new FileInputStream(getPreferencePath()));
            } catch (IOException ex) {
                System.err.println("IOException while getting preferences!");
            }
        }
        return preference;
    }

    /**
     * This method is used to get path of the preferences of the application
     * @return prefPath path of the preference
     */
    public static String getPreferencePath() {
        return System.getProperty("user.home") + "/TODO-Group9";
    }

    /**
     * This method is used for storing preferences
     */
    public static void flushProperties(){
        try {
        	preference.store(new FileOutputStream(getPreferencePath()), "Window States");
        } catch (IOException ex) {
        	System.err.println("Could not store the preference!");
        }
    }

    /**
     * This method returns the reference to the repository instance
     * @return The reference to the repository
     */
    public static Repository getRepository() {
        return repository;
    }

    /**
     * This method sets the Repository for the application. This reference of 
     * repository is used throughout the application.
     * @param repository The repository handler for the application
     */
    public static void setRepository(Repository repo) {
        repository = repo;
    }
   

    /**
     * This returns the view for creating a new task
     * @return taskUI The instance of add new task view instance
     */
    public static TaskUI getCreateTaskUI() {
        TaskUI taskUI = new TaskUI(null);
        taskUI.setSubmitAction(new CalendarEventAddEditAction<CalendarEvent>(
        		Settings.getCurrentResourceBundle().getString("Submit"), null, 
        		Settings.getCurrentResourceBundle().getString("CreateAction"),
                                 taskUI, getRepository()));
        return taskUI;


    }

    public static TaskUI getEditTaskUI(Object task)
    {
        TaskUI taskUI = new TaskUI((CalendarEvent)task);
        taskUI.setSubmitAction(new CalendarEventAddEditAction<CalendarEvent>(
        		Settings.getCurrentResourceBundle().getString("Submit"), null,
        		Settings.getCurrentResourceBundle().getString("EditAction"),
                                 taskUI, getRepository()));
        return taskUI;
    }
    /**
     * Overloaded Method to meet new changes
     * @return TaskView
     */
    public static TaskView getCreateTaskView(){
        TaskView taskView=new TaskView(null);
        taskView.setSubmitAction(new CalendarEventAddEditActionNew<CalendarEvent>(
        		Settings.getCurrentResourceBundle().getString("Submit"), null,
        		Settings.getCurrentResourceBundle().getString("CreateAction"),
                                 taskView, getRepository()));
        return taskView;

    }
    /**
     * Overloaded method to meet the new requirement
     * created By Krishna
     * @param task
     * @return TaskView
     */
    public static TaskView getEditTaskView(Object task){
        TaskView taskView=new TaskView((CalendarEvent)task);
        taskView.setSubmitAction(new CalendarEventAddEditActionNew<CalendarEvent>(
        		Settings.getCurrentResourceBundle().getString("Submit"), null,
        		Settings.getCurrentResourceBundle().getString("CreateAction"),
                                 taskView, getRepository()));
        return taskView;

    }


    /**
     * This method sets the locale for the application
     * @param language The language of the locale
     * @param country The country of the locale
     */
    public static void setLocale(String language, String country) {
        locale = new Locale(language, country);
        languageResourceBundle = null;
    }

    /**
     * Returns the default locale which is the locale of the OS
     * @return defaultLocale Returns the default locale instance
     */
    public static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    /**
     * Returns the instance of resource bundle
     * @return languageResourceBundle This resource bundle is used for 
     * internationalization
     */
    public static ResourceBundle getCurrentResourceBundle() {
        if(languageResourceBundle == null) {
            languageResourceBundle = ResourceBundle.getBundle(
            			"Language/Language", locale);
        }
        return languageResourceBundle;
    }


    /**
     * Returns the language resource bundle for default locale
     * @return resourceBundle It returns the default language resource bundle
     */
    public static ResourceBundle getDefaultResourceBundle() {
        return ResourceBundle.getBundle("Language/Language", getDefaultLocale());
    }

    /**
     * It returns the resource bundle for the specified locale
     * @param loc The locale of which we need the resource bundle
     * @return resourceBundle returns the resource bundle for the parameterized locale
     */
    public static ResourceBundle getResourceBundle(Locale loc) {
        return ResourceBundle.getBundle("Language/Language", loc);
    }

    /**
     * This method is called to show the default not implemented message.
     */
    public static void showNotImplementedMessage() {
        displayMessage(mainGUI,
        Settings.getCurrentResourceBundle().getString("Message.NotImplemented"));
    }

    /**
     * This method shows the specified message in dialog with the main gui set
     * as parent so the dialog is shown as modal.
     * @param message The message to display
     */
    public static void displayMessage(String message) {
        displayMessage(mainGUI, message);
    }

    /**
     * This shows the message in dialog box with parent window set as provided
     * in parameter
     * @param parent The window above which the message should be displayed
     * @param message The message to show
     */
    public static void displayMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    /**
     * This method returns the point where a component with dimension d is
     * to be shown so that it is in the center of the screen. This method is
     * used to know about the position to display a form so that it's shown at
     * the center of the screen.
     * @param d The dimension of the component
     * @return returns the starting point for the component to set so that
     * the component is displayed at the center of the screen.
     */
    public static Point getCenterPosition(Dimension d) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int x = (screenSize.width) / 2;
        int y = (screenSize.height) / 2;

        return new Point(x - (int) d.getWidth() / 2, y - (int) d.getHeight() / 2);
    }

    /**
     * This method shows the specified panel in a dialog box at the center of
     * the screen with the main GUI set as it's parent.
     * @param frame
     */
    public static void showDialog(JPanel frame) {
        JDialog dialog = new JDialog(mainGUI);

        try {
        	((TaskView) frame).setParentWindow(dialog);
        }
        catch(Exception e ) {
        	
        }

        dialog.setLocation(getCenterPosition(frame.getSize()));
        dialog.setContentPane(frame);
        dialog.setModal(true);
        dialog.setSize(frame.getWidth() + 6, frame.getHeight() + 28);
        dialog.setResizable(false);
        dialog.setVisible(true);

        dialog.dispose();
    }

    public static void setMainGUI(JFrame frame) {
        mainGUI  = frame;
    }

    public static Component getMainGUI() {
        return mainGUI;
    }
    /**
     * gets transparency
     * */
    public static int getTransparencyValue(){
        return transparency;        
        
    }
    /**
     * Sets transparency
     * @param trans transparency value
     * */
    public static void setTransparencyValue(int trans){
        transparency=trans;
    }

}