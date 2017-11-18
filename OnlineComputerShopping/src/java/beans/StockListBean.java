package beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import utility.DBConnection;
import utility.GlobalVariables;

/**
 *This is the javabean which is used to store the stock lists and product lists
 * @author Suman
 */
public class StockListBean {
    private String url;
    DBConnection connection;
    ResultSet rs;
    Collection stockList;
    Collection productList;

    
    /**
     * constructor
     *
     * @throws Exception
     */
    public StockListBean() throws Exception{
        this(GlobalVariables.getDatabaseURL());
    }

    /**
     * parameterized constructor that takes connection url as parameter
     * @param _url
     * @throws Exception
     */
    public StockListBean(String _url) throws Exception{

            init(_url);
        //ItemListBean ilb = new ItemListBean();
            ProductListBean plb = new ProductListBean();
            stockList = plb.getProductList();
    }
    
    private void init(String _url){
        url=_url;
        connection=new DBConnection(url);
        rs=null;
        stockList=new ArrayList();
        productList=new ArrayList();
    }

    
    /**
     * XML representation for displaying in the admin section
     * Suitable for XSLT
     * @return xml xml format of all the products including respective components
     */
    public String getXml() throws Exception{
        Iterator iterator=this.stockList.iterator();
        StringBuilder xmlOut=new StringBuilder();
        xmlOut.append("<productlist>");

        try {
        while(iterator.hasNext()){

            ProductBean item=(ProductBean)iterator.next();
            xmlOut.append("<product>");
                xmlOut.append("<name>"+item.getName()+"</name><description>"+item.getDescription()+"</description>");

                MappingListBean mlb = new MappingListBean(url);
                
                if(mlb.hasProduct(item.getId())){//show only those products which are in mappings

                    xmlOut.append("<components>");

                    ProductListBean plb = new ProductListBean();
                    productList = plb.getComponents(item.getId());//list all the components of this product
                    Iterator mlbIterator = productList.iterator();
                    while(mlbIterator.hasNext()){

                        ComponentBean compBean = (ComponentBean) mlbIterator.next();
                        //xmlOut.append("<component><name>"+compBean.getName()+"</name><quantity>"+compBean.getQuantity()+"</quantity><rate>"+compBean.getRate()+"</rate><description>"+compBean.getDescription()+"</description></component>");
                        xmlOut.append(compBean.getXml());

                    }

                    xmlOut.append("</components>");

                }
                xmlOut.append("</product>");
        }
        }
        catch(Exception e)
        {
            throw new Exception();
        }
        
        xmlOut.append("</productlist>");

        return xmlOut.toString();
    }

}
