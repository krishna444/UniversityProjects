package beans;
/**
 * OrderListBean.java lists all the orders that can be viewed from admin section
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import utility.DBConnection;
import utility.GlobalVariables;

/**
 * This stores the complete list of orders
 * @author Suman
 */
public class OrderListBean {
    
    private String url;
    DBConnection connection;
    ResultSet rs;
    Collection ordersList;
    OrderBean ob;

    /**
     * constructor that initializes the url
     * @throws Exception
     */
    public OrderListBean() throws Exception {
        this(GlobalVariables.getDatabaseURL());
    }

    /**
     * constructor that populates the orderlist object
     * @param _url
     * @throws Exception
     */
    public OrderListBean(String _url) throws Exception{

        try{
            this.init(_url);
            String query="SELECT products.name Product, customers.name Customer, `order`.`date` OrderDate, `order`.`status` OrderStatus "
                    + "FROM `order` INNER JOIN customers ON `order`.name=customers.name INNER JOIN products ON `order`.product_id=products.product_id ORDER BY Customer DESC";
            rs=connection.ExecuteQuery(query);
            while(rs.next()){
               ob = new OrderBean();
//               ob.setOrderId(rs.getInt("OrderID"));
               ob.setUsername(rs.getString("Customer"));
               ob.setProduct(rs.getString("Product"));
               ob.setOrderDate(rs.getString("OrderDate"));
               ob.setOrderStatus(rs.getString("OrderStatus"));
               ordersList.add(ob);
            }
            connection.close();
        }
        catch(SQLException sqle){
            throw new Exception(sqle);
        }
    }

    /**
     * initializes the variables
     * @param _url
     */
    private void init(String _url){
        url = _url;
        connection=new DBConnection(url);
        rs = null;
        ordersList = new ArrayList();

    }

    /**
     * returns orderlist collection
     * @return
     */
    public Collection getOrderList(){
        return ordersList;
    }  

    /**
     * XML representation for display in the XSLT
     * Suitable for XSLT
     * @return xml string
     */
    public String getXml(){
        Iterator iterator=ordersList.iterator();
        StringBuilder xmlOut=new StringBuilder();
        xmlOut.append("<Orders>");
        while(iterator.hasNext()){
            OrderBean item=(OrderBean)iterator.next();
            xmlOut.append(item.getXml());
        }
        xmlOut.append("</Orders>");
        return xmlOut.toString();
    }

}
