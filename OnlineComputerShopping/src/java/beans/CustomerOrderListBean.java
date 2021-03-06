package beans;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import utility.DBConnection;
import utility.GlobalVariables;

/**
 * It creates all the orders generated by a customer
 * @author krishna
 */
public class CustomerOrderListBean {
    private String jdbcURL=null;
    private DBConnection connection=null;
    private Collection<CustomerOrderBean> customerOrders=null;
    public CustomerOrderListBean() {
        this(GlobalVariables.getDatabaseURL());
    }
    /**
     * Constructor
     * @param jdbcURL jdbc URL
     */
    public CustomerOrderListBean(String jdbcURL){
        this.jdbcURL=jdbcURL;
        this.connection=new DBConnection(this.jdbcURL);
        this.customerOrders=new ArrayList<CustomerOrderBean>();
    }
    /**
     * Loads all the orders of the customers. It does not return anything,but
     * sets the bean property with the loaded order vlaues
     * @param customerId Customer ID
     */
    public void loadCustomerOrders(String customerId){
        CustomerOrderBean order=null;
        try{
        String query="SELECT products.name,products.description,order.quantity,order.date,order.status FROM dbComputerShopping.order LEFT JOIN products"
                + " ON products.product_id=order.product_id WHERE order.name='"+customerId+"'";
        ResultSet resultSet=this.connection.ExecuteQuery(query);
        while(resultSet.next()){
            order=new CustomerOrderBean();
            order.setProductName(resultSet.getString("name"));
            order.setProductDescription(resultSet.getString("description"));
            order.setQuantity(resultSet.getInt("quantity"));
            order.setDate(resultSet.getString("date"));
            order.setStatus(resultSet.getString("status"));
            this.customerOrders.add(order);
        }
        }catch(Exception ex){
            //
        }
    }

    /**
     * Returns the collection of customer orders
     * @return customer orders
     */
    public Collection getCustomerOrders(){
        return this.customerOrders;
    }



}
