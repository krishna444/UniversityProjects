package beans;

/**
 * It is the customer order bean suitable for displaying
 * @author krishna
 */
public class CustomerOrderBean {
    private String productName=null;
    private String productDescription=null;
    private int quantity=-1;
    private String date=null;
    private String status=null;

    public CustomerOrderBean() {
    }
    /**
     * Gets product name
     * @return product name
     */
    public String getProductName(){
        return this.productName;
    }
    /**
     * Sets product name
     * @param productName product name to set
     */
    public void setProductName(String productName){
        this.productName=productName;
    }
    /**
     * Gets product description
     * @return product description
     */
    public String getProductDescription(){
        return this.productDescription;
    }
    /**
     * Set the product description
     * @param description product description
     */
    public void setProductDescription(String description){
        this.productDescription=description;
    }
    /**
     * Gets quantity
     * @return quantity
     */
    public int getQuantity(){
        return this.quantity;
    }
    /**
     * Sets quantity
     * @param quantity Quantity to set
     */
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    /**
     * Gets data
     * @return date
     */
    public String getDate(){
        return this.date;
    }
    /**
     * Sets date
     * @param date date to set
     */
    public void setDate(String date){
        this.date=date;
    }
    /**
     * Gets order status like some order may be pending, some are not processed or some processed.
     * @return status of order
     */
    public String getStatus(){
        return this.status;
    }
    /**
     * Sets status of the order.
     * @param status status of order
     */
    public void setStatus(String status){
        this.status=status;
    }



}
