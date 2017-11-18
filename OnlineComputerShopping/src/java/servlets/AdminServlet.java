package servlets;

import beans.ComponentBean;
import beans.ComponentListBean;
import beans.MappingBean;
import beans.OrderListBean;
import beans.ProductBean;
import beans.ProductListBean;
import beans.StockListBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Suman
 *
 * AdminServlet handles Admin related business logic layer.
 */
public class AdminServlet extends HttpServlet {

    private static String adminLogin = null;
    private static String adminHome = null;
    private static String adminOrders = null;
    private static String adminStocks = null;
    private static String adminLogout = null;
    private static String adminProduct = null;
    private static String adminResult = null;
    private static String adminComponent = null;
    private static String adminComponents = null;
    private static String jdbcURL = null;
    private static ProductListBean productList = null;

    @Override
    public void init(ServletConfig config) {

        adminLogin = config.getInitParameter("ADMIN_LOGIN");
        adminHome = config.getInitParameter("ADMIN_HOME");
        adminOrders = config.getInitParameter("ADMIN_ORDERS");
        adminStocks = config.getInitParameter("ADMIN_STOCKS");
        adminProduct = config.getInitParameter("ADMIN_PRODUCT");
        adminResult = config.getInitParameter("ADMIN_RESULT");
        adminComponent = config.getInitParameter("ADMIN_COMPONENT");
        adminComponents = config.getInitParameter("ADMIN_COMPONENTS");
        jdbcURL = config.getServletContext().getInitParameter("JDBC_URL");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * It handles the requests for showing Orders, Stocks, Components and also the forms for saving component and product according to the request parameters.
     *
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession sess = request.getSession();
        RequestDispatcher rd = null;
        sess.setAttribute("sess_admin", request.getRemoteUser());
        sess.setAttribute(adminLogin, out);


        //redirect the page according to "action"
        if (request.getParameter("action") == null
                || request.getParameter("action").equalsIgnoreCase("login")) {
            rd = request.getRequestDispatcher(adminHome);
            rd.forward(request, response);
        } else if (request.getParameter("action").equalsIgnoreCase("orders")) {

            OrderListBean orderList = null;
            try {
                orderList = new OrderListBean(jdbcURL);
            } catch (Exception ex) {
                response.getWriter().write(ex.toString());
            }

            request.setAttribute("orderList", orderList);
            rd = request.getRequestDispatcher(adminOrders);
            rd.forward(request, response);

        } else if (request.getParameter("action").equalsIgnoreCase("stocks")) {

            StockListBean stockList = null;
            try {
                stockList = new StockListBean(jdbcURL);
            } catch (Exception ex) {
                response.getWriter().write(ex.toString());
            }

            request.setAttribute("stockList", stockList);

            rd = request.getRequestDispatcher(adminStocks);
            rd.forward(request, response);

        } else if (request.getParameter("action").equalsIgnoreCase("product_entry") || request.getParameter("action").equalsIgnoreCase("product_save")) {

            if (request.getParameter("action").equalsIgnoreCase("product_save")) {
                try {

                    String p_name = request.getParameter("p_name");
                    String[] p_components = request.getParameterValues("p_components");
                    String p_description = request.getParameter("p_description");

                    ProductBean productBean = new ProductBean(jdbcURL);

                    productBean.setName(p_name);
                    productBean.setDescription(p_description);

                    int productId = productBean.saveProduct();

                    if (productId > 0) {
                        for (int i = 0; i < p_components.length; i++) {
                            MappingBean mb = new MappingBean(jdbcURL);
                            mb.setComponentId(Integer.parseInt(p_components[i]));
                            mb.setProductId(productId);
                            mb.saveMapping();
                        }
                    }


                } catch (Exception ex) {
                    response.getWriter().write(ex.toString());
                }
                rd = request.getRequestDispatcher(adminResult);
                rd.forward(request, response);

            } else {
                ComponentListBean componentList = null;
                try {
                    componentList = new ComponentListBean(jdbcURL);

                    //componentList = .getComponents(Integer.parseInt(productId));
                } catch (Exception ex) {
                    response.getWriter().write(ex.toString());
                }

                request.setAttribute("complist", componentList);
                rd = request.getRequestDispatcher(adminProduct);
                rd.forward(request, response);
            }

        } else if (request.getParameter("action").equalsIgnoreCase("component_entry")
                || request.getParameter("action").equalsIgnoreCase("component_save")
                || request.getParameter("action").equalsIgnoreCase("component_update")) {

            if (request.getParameter("action").equalsIgnoreCase("component_save")) {

                try {
                    ComponentBean componentBean = new ComponentBean(jdbcURL);

                    String c_name = request.getParameter("c_name");
                    String c_rate = request.getParameter("c_rate");
                    String c_quantity = request.getParameter("c_quantity");
                    String c_description = request.getParameter("c_description");


                    componentBean.setName(c_name);
                    componentBean.setRate(Integer.parseInt(c_rate));
                    componentBean.setQuantity(Integer.parseInt(c_quantity));
                    componentBean.setDescription(c_description);

                    componentBean.insertComponent();
                } catch (Exception ex) {
                    response.getWriter().write(ex.toString());
                }

                rd = request.getRequestDispatcher(adminResult);
                rd.forward(request, response);
            } else if (request.getParameter("action").equalsIgnoreCase("component_update")) {
                ComponentBean component = new ComponentBean(jdbcURL);
                try {
                    //Get the id and quantity of the component to add
                    int id = Integer.parseInt(request.getParameter("id"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    //Set the quantity
                    component.updateComponent(id, quantity);
                    ComponentListBean componentList = null;
                    try {
                        componentList = new ComponentListBean(jdbcURL);
                    } catch (Exception ex) {
                        response.getWriter().write(ex.toString());
                    }
                    //Set component list and forward
                    request.setAttribute("componentList", componentList);
                    rd = request.getRequestDispatcher(adminComponents);
                    rd.forward(request, response);
                } catch (Exception ex) {
                }

            } else {
                rd = request.getRequestDispatcher(adminComponent);
                rd.forward(request, response);
            }

        } else if (request.getParameter("action").equalsIgnoreCase("components")) {

            ComponentListBean componentList = null;
            try {
                componentList = new ComponentListBean(jdbcURL);
            } catch (Exception ex) {
                response.getWriter().write(ex.toString());
            }

            request.setAttribute("componentList", componentList);
            rd = request.getRequestDispatcher(adminComponents);
            rd.forward(request, response);

        } else if (request.getParameter("action").equalsIgnoreCase("logout")) {
            sess.invalidate();
            response.sendRedirect("/OnlineComputerShopping");
        } else if (request.getParameter("action").equals("refresh")) {
            rd = request.getRequestDispatcher(adminHome);
            rd.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
