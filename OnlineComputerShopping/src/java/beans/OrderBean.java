package beans;

/**
 *OrderBean.java contains information about the orders
 * @author Suman
 */
public class OrderBean {

    private String Username;
    private int ProductId;
    private String Product;
    private String OrderDate;
    private String OrderStatus;


    /**
     * gets username
     * @return username
     */
    public String getUsername(){
        return Username;
    }
    /**
     * sets username
     * @param _Username
     */
    public void setUsername(String _Username){
        Username=_Username;
    }

    /**
     * gets productid
     * @return productid
     */
    public int getProductId(){
        return ProductId;
    }

    /**
     * gets product name
     * @return 
     */
    public String getProduct(){
        return Product;
    }

    /**
     * sets product name
     * @param _Product
     */
    public void setProduct(String _Product){
        Product = _Product;
    }

    /**
     * gets product id
     * @param _ProductId
     */
    public void setProductId(int _ProductId){
        ProductId=_ProductId;
    }

    /**
     * gets order date
     * @return
     */
    public String getOrderDate(){
        return OrderDate;
    }

    /**
     * sets order date
     * @param _OrderDate
     */
    public void setOrderDate(String _OrderDate){
        OrderDate=_OrderDate;
    }

    /**
     * gets order status
     * @return
     */
    public String getOrderStatus(){
        return OrderStatus;
    }

    /**
     * sets order status
     * @param _OrderStatus
     */
    public void setOrderStatus(String _OrderStatus){
        OrderStatus=_OrderStatus;
    }

    /**
     * gets order xml that is shown in the xslt file
     * @return
     */
    public String getXml(){

        StringBuilder xmlOut = new StringBuilder();

        xmlOut.append("<Order>");
        xmlOut.append("<Customer>");
        xmlOut.append(Username);
        xmlOut.append("</Customer>");
        xmlOut.append("<Product>");
        xmlOut.append(Product);
        xmlOut.append("</Product>");
        xmlOut.append("<OrderDate>");
        xmlOut.append(OrderDate);
        xmlOut.append("</OrderDate>");
        xmlOut.append("<OrderStatus>");
        xmlOut.append(OrderStatus);
        xmlOut.append("</OrderStatus>");
        xmlOut.append("</Order>");

        return xmlOut.toString();
    }

}
