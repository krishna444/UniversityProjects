package beans;
/**
 * ComponentListBean.java lists out all the components that can be view from admin section
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import utility.DBConnection;
import utility.GlobalVariables;

/**
 * It lists the components for specific product id
 * 
 * @author Suman
 */
public class ComponentListBean {

    private String url=null;
    private DBConnection connection;
    ResultSet rs;
    Collection componentList;
    ComponentBean componentBean;


   /**
    * Constructor
    * @throws Exception
    */
    public ComponentListBean() throws Exception {
        this(GlobalVariables.getDatabaseURL());
        
    }


    /**
     * Constructor
     * @param _url
     * @throws Exception
     */
    public ComponentListBean(String _url) throws Exception {
         try{
            init(_url);
            String query="SELECT comp_id, name, rate, quantity, description FROM components ORDER BY name ASC";
            rs=connection.ExecuteQuery(query);
            while(rs.next()){
               componentBean = new ComponentBean();
               componentBean.setId(rs.getInt("comp_id"));
               componentBean.setName(rs.getString("name"));
               componentBean.setRate(rs.getDouble("rate"));
               componentBean.setQuantity(rs.getInt("quantity"));
               componentBean.setDescription(rs.getString("description"));

               componentList.add(componentBean);
            }
            connection.close();
        }
        catch(SQLException sqle){
            throw new Exception(sqle);
        }
    }

    /**
     * Initialises the parameters
     * @param _url JDBC URL
     */
    private void init(String _url){
        url = _url;
        connection=new DBConnection(url);
        rs = null;
        componentList = new ArrayList();
    }

    /**
     * Gets all the componets
     * @return component list
     */
    public Collection getComponentList(){
        return componentList;
    }

    /**
     * XML representation for dislay
     * Suitable for XSLT
     * @return XML string
     */
    public String getXml(){
        Iterator iterator=componentList.iterator();
        StringBuilder xmlOut=new StringBuilder();
        xmlOut.append("<Components>");
        while(iterator.hasNext()){
            ComponentBean item=(ComponentBean)iterator.next();
            xmlOut.append(item.getXml());
        }
        xmlOut.append("</Components>");
        return xmlOut.toString();
    }

    /**
     * For testing purpose only
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{
        ComponentListBean c = new ComponentListBean(GlobalVariables.getDatabaseURL());
        ArrayList al = (ArrayList) c.getComponentList();
        System.out.println("size of the list"+al.size());
    }
}
