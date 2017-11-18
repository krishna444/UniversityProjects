package monthviewapplication;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility for handling window-placement and size.
 * The file handling is taken cared of automatically.
 *
 * @author Ronny
 */
public class WindowInformation {

    /**
     * Contains all properties during execution of the application
     */
    static final private Properties windowProperties;

    /*
     * The url to the file used for persistent storage.
     * Important that the directory exist and is writable.
     */
    static final private String propertiesPath = "data/Window.properties";

    /*
     * Abstract representation of the file pointed to by propertiesPath.
     * Used for simplifying access and creation of the storage file.
     */
    static final private File file;

    /**
     * Static block that initializes and load the window-properties.
     * Will only be executed once during an applications execution-time.
     */
    static {
        windowProperties = new Properties();
        file = new File(propertiesPath);

        // Load properties from file.
        loadWindowInformation();
    }


    /**
     * Load all previously stored information about windows.
     * Uses a predefined storage file.
     * If this file does not exist it will be created.
     */
    private static void loadWindowInformation() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileInputStream tempFileInStream = new FileInputStream(file);
            windowProperties.load(tempFileInStream);
            tempFileInStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Store all collected information about windows to a persistent storage.
     * Uses a predefined storage file.
     * If this file does not exist it will be created.
     *
     * Should preferably be used at main-application exit.
     */
    public static void storeWindowInformation() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream tempFileOutStream = new FileOutputStream(propertiesPath);
            windowProperties.store(tempFileOutStream,
                    "Property file for keeping information about size and location of different windows\n");
            tempFileOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches for both the width and height for the specified windowID.
     * If both values are found, the stored size is returned.
     * If anyone of these values is missing, the default value is returned.
     *
     * @param windowID - the unique ID for the window.
     * @param defaultSize - the default value
     * @return the size value in the property list with the specified window ID.
     */
    public static Dimension getWindowSize(String windowID, Dimension defaultSize) {
        Integer width;
        Integer height;

        /* Get the stored properties for the width and height.
         * The Key used is the window id concatenated with ".width" or ".height".
         */
        String widthString = windowProperties.getProperty(windowID + ".width");
        String heightString = windowProperties.getProperty(windowID + ".height");

        /* Convert the property strings to an integer.
         * If this conversion fails we return the default size.
         */
        try {
            width = new Integer(widthString);
            height = new Integer(heightString);
        } catch(NumberFormatException e) {
            return defaultSize;
        }

        // The conversion was successfull. Return the previously stored value.
        return new Dimension(width, height);
    }

    /**
     * Set a new size-value for the window with specified ID.
     * The value is stored in the windowProperties variable.
     * 
     * The values set with this method is only stored during the life-time of
     * the current application execution.
     * 
     * @param windowID - the unique ID for the window.
     * @param size - the new window-size value.
     */
    public static void setWindowSize(String windowID, Dimension size) {
        Integer width = size.width;
        Integer height = size.height;

        /* Set the properties for the width and height.
         * The Key used is the window id concatenated with ".width" or ".height".
         */
        windowProperties.setProperty(windowID + ".width", width.toString());
        windowProperties.setProperty(windowID + ".height", height.toString());
    }


    /**
     * Searches for both the x and y coordinate for the specified windowID.
     * If both values are found, the stored location is returned.
     * If anyone of these values is missing, the default value is returned.
     *
     * @param windowID - the unique ID for the window.
     * @param defaultLocation - the default value
     * @return the location value in the property list with the specified window ID.
     */
    public static Point getWindowLocation(String windowID, Point defaultLocation) {
        Integer x;
        Integer y;

        /* Get the stored properties for the x and y cordinates.
         * The Key used is the window id concatenated with ".x" or ".y".
         */
        String xString = windowProperties.getProperty(windowID + ".x");
        String yString = windowProperties.getProperty(windowID + ".y");

        /* Convert the property strings to an integer.
         * If this conversion fails we return the default location.
         */
        try {
            x = new Integer(xString);
            y = new Integer(yString);
        } catch(NumberFormatException e) {
            return defaultLocation;
        }

        // The conversion was successfull. Return the previously stored value.
        return new Point(x, y);
    }



   /**
     * Set a new location-value for the window with specified ID.
     * The value is stored in the windowProperties variable.
     *
     * The values set with this method is only stored during the life-time of
     * the current application execution.
     *
     * @param windowID - the unique ID for the window.
     * @param location - the new window-location value.
     */
    public static void setWindowLocation(String windowID, Point location) {
        Integer x = location.x;
        Integer y = location.y;

        /* Set the properties for the x and y cordinates.
         * The Key used is the window id concatenated with ".x" or ".y".
         */
        windowProperties.setProperty(windowID + ".x", x.toString());
        windowProperties.setProperty(windowID + ".y", y.toString());
    }

}
