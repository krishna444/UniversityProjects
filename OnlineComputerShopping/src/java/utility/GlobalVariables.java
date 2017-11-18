package utility;

/**
 * This class stores the global variables like connection strings
 * @author krishna
 */
public class GlobalVariables {

    private static String databaseURL=null;
    public GlobalVariables() {
    }

    /**
     * gets the database connection string
     * @return connection string
     */
    public static String getDatabaseURL(){
       return databaseURL;
    }

    /**
     * Sets the jdbc url
     * @param url JDBC url to set
     */
    public static void setDatabaseURL(String url){
        databaseURL=url;
    }
}
