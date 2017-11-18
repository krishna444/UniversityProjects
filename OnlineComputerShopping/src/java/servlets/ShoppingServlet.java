package servlets;

import beans.ComponentBean;
import beans.ComponentListBean;
import beans.CustomerOrderListBean;
import beans.CustomerProfileBean;
import beans.ProductBean;
import beans.ProductListBean;
import beans.ShoppingBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import utility.EmailSender;

/**
 * It is the main business processing part oriented for customers. This servlet is
 * responsible for processing requesets and forwarding to appropriate pages.
 * This serves as the controller residing between Web pages and data store.
 * @author krishna
 */
public class ShoppingServlet extends HttpServlet {
    //Customer page
    private static String customerPage = null;
    //Page to display products
    private String productPage = null;
    //Page to display produt detail
    private String productDetailPage = null;
    //Page to checkout. It is customer oriented page
    private String checkoutPage = null;
    //Page when some event occurs successfully
    private String successPage = null;
    //When some error occurs
    private String errorPage = null;
    //JDBC URL
    private static String jdbcURL = null;
    //Bean for list of products
    private static ProductListBean productList = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        customerPage = config.getInitParameter("CustomerPage");
        productPage = config.getInitParameter("ProductPage");
        productDetailPage = config.getInitParameter("DetailPage");
        checkoutPage = config.getInitParameter("CheckoutPage");
        successPage = config.getInitParameter("SuccessPage");
        errorPage = config.getInitParameter("ErrorPage");

        jdbcURL = config.getServletContext().getInitParameter("JDBC_URL");
        try {
            productList = new ProductListBean(jdbcURL);
        } catch (Exception ex) {
        }
    }

    /**
     * All processnig is carried out here
     * It checks the request parameter action,and performs the tasks accordingly
     * @param request HttpRequest
     * @param response HttpResponse
     * @throws ServletException
     * @throws IOException
     */
    public void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher requestDispatcher = null;
        ShoppingBean cartItems = getShoppingBean(request);
        session.setAttribute("customer", request.getRemoteUser());
        session.setAttribute("jdbcURL", jdbcURL);
        String action = request.getParameter("action");

        //Viewing Product Details
        if (action == null || action.equals("view_products")) {
            //get product detail
            ProductListBean productList = null;
            try {
                productList = new ProductListBean(jdbcURL);
            } catch (Exception ex) {
                response.getWriter().write(ex.toString());
            }
            request.setAttribute("productlist", productList);
            //Forward
            requestDispatcher = request.getRequestDispatcher(this.productPage);
            requestDispatcher.forward(request, response);

        }

        //Components of a product
        if (action.equals("detail")) {
            String productId = request.getParameter("product_id");
            try {
                productList = new ProductListBean(jdbcURL);
            } catch (Exception ex) {
                response.getWriter().write(ex.toString());
            }
            if (productList != null) {
                ProductBean product = productList.getProduct(Integer.parseInt(productId));
                request.setAttribute("product", product);
                ArrayList componentList = null;
                try {
                    componentList = productList.getComponents(Integer.parseInt(productId));
                } catch (Exception ex) {
                    //
                }
                request.setAttribute("complist", componentList);

                requestDispatcher = request.getRequestDispatcher(productDetailPage);
                requestDispatcher.forward(request, response);
            }
        }

        //Adding new product in the shopping list
        if (action.equals("add")) {
            String paramId = request.getParameter("id");
            String paramQuantity = request.getParameter("quantity");
            ProductBean productBean = productList.getProduct(Integer.parseInt(paramId));
            cartItems.addProduct(productBean, Integer.parseInt(paramQuantity));
            requestDispatcher = request.getRequestDispatcher(this.productPage);
            requestDispatcher.forward(request, response);
        }

        //Removing the product from the shopping list
        if (action.equals("remove")) {
            String paramId = request.getParameter("id");
            cartItems.removeProduct(productList.getProduct(Integer.parseInt(paramId)));
            requestDispatcher = request.getRequestDispatcher(this.productPage);
            requestDispatcher.forward(request, response);
        }
        //Checks out the selected items from the shopping list. It checks the validity
        //of the items from the stock
        if (action.equals("checkout")) {
            //Check the validity of the quantity
            String result = this.checkAvailabilityComponents(request);
            if (result.length() > 0) {
                request.setAttribute("errormessage", result);
                requestDispatcher = request.getRequestDispatcher(this.errorPage);
                requestDispatcher.forward(request, response);
            } else {
                //Redirect
                response.sendRedirect("/OnlineComputerShopping/" + this.checkoutPage);
            }
        }

        //Register New User
        if (action.equals("register")) {
            CustomerProfileBean customerProfile = new CustomerProfileBean(jdbcURL);
            //Check password Validity
            String password1 = request.getParameter("password1");
            String password2 = request.getParameter("password2");
            //If two passwords dont match, or password is empty, its ERROR
            if (!password1.equals(password2) || password1.trim().length() <= 0) {
                String errorMessage = "Password does not match!";
                request.setAttribute("errormessage", errorMessage);
                requestDispatcher = request.getRequestDispatcher(this.errorPage);
                requestDispatcher.forward(request, response);
                return;
            }
            //If the user name already taken, it is not possible to register
            if (customerProfile.userExists(request.getParameter("userid"))) {
                String errorMessage = "User already exists!";
                request.setAttribute("errormessage", errorMessage);
                requestDispatcher = request.getRequestDispatcher(this.errorPage);
                requestDispatcher.forward(request, response);
                return;
            } else {
                //Create Profile
                customerProfile.setName(request.getParameter("userid"));
                customerProfile.setPassword(request.getParameter("password1"));
                customerProfile.setFirstName(request.getParameter("first_name"));
                customerProfile.setMiddleName(request.getParameter("middle_name"));
                customerProfile.setLastName(request.getParameter("last_name"));
                customerProfile.setAddress1(request.getParameter("address1"));
                customerProfile.setAddress2(request.getParameter("address2"));
                customerProfile.setCity(request.getParameter("city"));
                customerProfile.setPostBox(request.getParameter("post_box"));
                customerProfile.setEmail(request.getParameter("email"));
                customerProfile.setCountry(request.getParameter("country"));
                try {
                    customerProfile.insertProfile();
                    requestDispatcher = request.getRequestDispatcher(this.successPage);
                    requestDispatcher.forward(request, response);
                } catch (Exception ex) {
                    String errorMessage = ex.toString();
                    request.setAttribute("errormessage", errorMessage);
                    requestDispatcher = request.getRequestDispatcher(this.errorPage);
                    requestDispatcher.forward(request, response);
                    //
                }
            }
        }

        //Sends an order, updates database, sends a confirmation message to customer
        if (action.equals("order")) {
            cartItems.updateShoppingBean((String) session.getAttribute("customer"));
            //Send Email
            CustomerProfileBean profile = null;
            Object[] shippingInfo = null;
            try {
                profile = new CustomerProfileBean(jdbcURL);
                profile.populateCustomerProfile((String) session.getAttribute("customer"));
                String toAddress = profile.getEmail();
                StringBuilder builder = new StringBuilder();
                builder.append("Dear ").append(profile.getFirstName()).append(",");
                builder.append("\n").append("We have received the below mentioned order from you:\n\n");
                Iterator iterator = cartItems.getCart().iterator();
                int count = 1;
                while (iterator.hasNext()) {
                    shippingInfo = (Object[]) iterator.next();
                    ProductBean product = (ProductBean) shippingInfo[0];
                    int quantity = (Integer) shippingInfo[1];
                    builder.append(count).append(":");
                    builder.append(product.getName()).append(" ").append(product.getDescription()).append("  ");
                    builder.append("QTY:").append(quantity).append("\n\n");
                    count = count + 1;
                }
                builder.append("\n").append("\nThanks.\n");
                builder.append("With Regards,\n").append("Online Shipping Team");
                EmailSender emailSender = new EmailSender();
                emailSender.SendMail(toAddress, "Confirmation of Order", builder.toString());
            } catch (Exception ex) {
            }
            //Forward to success page
            String message = "Thank you, your order has been successfully processed.<br/> An email has been sent to you for confirmation.";
            request.setAttribute("successmessage", message);
            requestDispatcher = request.getRequestDispatcher(this.successPage);
            requestDispatcher.forward(request, response);
        }
        //It is for refreshing the customer page or check out page. These two pages
        //require customer authentication. So, when these pages are accessed first
        //time after login, then refreshing is required to get the customer info.
        if (action.equals("refresh")) {
            if (request.getParameter("page").equals("customer")) {
                requestDispatcher = request.getRequestDispatcher(customerPage);
                requestDispatcher.forward(request, response);
            } else {
                requestDispatcher = request.getRequestDispatcher(checkoutPage);
                requestDispatcher.forward(request, response);
            }

        }


        /****PROFILE RELATED OPERATIONS***/
        if (action.equals("profile")) {
            String category = request.getParameter("category");
            //going to change password
            if (category.equals("change_password")) {
                CustomerProfileBean profile = null;
                try {
                    profile = new CustomerProfileBean(jdbcURL);
                    profile.populateCustomerProfile(request.getRemoteUser());
                    session.setAttribute("profile", profile);
                    request.setAttribute("category", category);
                    requestDispatcher = request.getRequestDispatcher(customerPage);
                    requestDispatcher.forward(request, response);
                } catch (Exception ex) {
                    //
                }
            }
            //going to change profile
            if (category.equals("change_profile")) {
                CustomerProfileBean profile = null;
                try {
                    profile = new CustomerProfileBean(jdbcURL);
                    profile.populateCustomerProfile(request.getRemoteUser());
                    session.setAttribute("profile", profile);
                    request.setAttribute("category", category);
                    requestDispatcher = request.getRequestDispatcher(customerPage);
                    requestDispatcher.forward(request, response);
                } catch (Exception ex) {
                    //
                }
            }
            //Going to display orders
            if (category.equals("view_order")) {
                CustomerOrderListBean customerOrders = new CustomerOrderListBean(jdbcURL);
                customerOrders.loadCustomerOrders((String) session.getAttribute("customer"));
                request.setAttribute("orders", customerOrders);
                request.setAttribute("category", category);
                requestDispatcher = request.getRequestDispatcher(customerPage);
                requestDispatcher.forward(request, response);

            }
        }


        //Updating of profile or password is carried out here
        if (action.equals("update")) {
            String category = request.getParameter("category");
            //Changing password
            if (category.equals("password")) {
                String oldPassword = request.getParameter("old_password");
                String newPassword = request.getParameter("new_password");
                String confirmPassword = request.getParameter("confirm_password");
                CustomerProfileBean profile = (CustomerProfileBean) session.getAttribute("profile");
                //Validation
                if (!oldPassword.equals(profile.getPassword())) {
                    //Forward to error page
                    request.setAttribute("errormessage", "Invalid Password!");
                    requestDispatcher = request.getRequestDispatcher(this.errorPage);
                    requestDispatcher.forward(request, response);
                }
                if (!newPassword.equals(confirmPassword)) {
                    //Forward to error page
                    request.setAttribute("errormessage", "Passwords do not match!");
                    requestDispatcher = request.getRequestDispatcher(this.errorPage);
                    requestDispatcher.forward(request, response);
                }
                //Update password
                profile.setPassword(newPassword);
                try {
                    profile.updateProfile();
                    //Forward to success page
                    request.setAttribute("successmessage", "Password successfully changed");
                    requestDispatcher = request.getRequestDispatcher(this.successPage);
                    requestDispatcher.forward(request, response);
                } catch (Exception ex) {
                    //
                }
            }
            //Update Profile
            if (category.equals("profile")) {
                //Get request parameters
                String firstName = request.getParameter("first_name");
                String middleName = request.getParameter("middle_name");
                String lastName = request.getParameter("last_name");
                String address1 = request.getParameter("address1");
                String address2 = request.getParameter("address2");
                String city = request.getParameter("city");
                String postBox = request.getParameter("post_box");
                String email = request.getParameter("email");
                String country = request.getParameter("country");

                //Set parameters in profile bean
                CustomerProfileBean profile = (CustomerProfileBean) session.getAttribute("profile");
                profile.setFirstName(firstName);
                profile.setMiddleName(middleName);
                profile.setLastName(lastName);
                profile.setAddress1(address1);
                profile.setAddress2(address2);
                profile.setCity(city);
                profile.setPostBox(postBox);
                profile.setEmail(email);
                profile.setCountry(country);
                //Update profile
                try {
                    profile.updateProfile();
                    //Forward to success page
                    request.setAttribute("successmessage", "Profile Updated.");
                    requestDispatcher = request.getRequestDispatcher(this.successPage);
                    requestDispatcher.forward(request, response);
                } catch (Exception ex) {
                    //
                }
            }
        }


        //Logs out
        if (action.equals("logout")) {
            session.invalidate();
            response.sendRedirect("");
        }

    }

    /**
     * Gets the shopping bean
     * @param request request
     * @return shopping bean
     */
    private ShoppingBean getShoppingBean(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ShoppingBean shoppingBean = (ShoppingBean) session.getAttribute("shoppinglist");
        if (shoppingBean == null) {
            shoppingBean = new ShoppingBean(jdbcURL);
            session.setAttribute("shoppinglist", shoppingBean);
        }
        return shoppingBean;
    }

    /**
     * This checks whether the components are available or not for the selected items
     * @return result: empty string implies success...otherwise problem
     */
    private String checkAvailabilityComponents(HttpServletRequest request){
        String result = "";
        try{
        HttpSession session = request.getSession();
        ShoppingBean basket = (ShoppingBean) session.getAttribute("shoppinglist");
        //Hash map to store the quantiy of the components selected
        HashMap componentQTY = new HashMap();
        ArrayList usedComponents = null;
        Iterator iterator = basket.getCart().iterator();
        Object[] cartInfo = null;
        //Check for all objects selected and their components
        while (iterator.hasNext()) {
            cartInfo = (Object[]) iterator.next();
            ProductBean product = (ProductBean) cartInfo[0];
            int quantity = (Integer) cartInfo[1];
            ProductListBean productList = new ProductListBean(jdbcURL);
            usedComponents = productList.getComponents(product.getId());
            for (Iterator i = usedComponents.iterator(); i.hasNext();) {
                ComponentBean component = (ComponentBean) i.next();
                if (componentQTY.containsKey(component.getId())) {
                    int newValue = (Integer) componentQTY.get(component.getId()) + quantity;
                    componentQTY.put(component.getId(), newValue);
                } else {
                    componentQTY.put(component.getId(), quantity);
                }
            }
        }

        //Check the requested components whether they are available in the stock or not
        ComponentListBean componentList = new ComponentListBean(jdbcURL);
        Collection components = componentList.getComponentList();
        for (Iterator i = components.iterator(); i.hasNext();) {
            ComponentBean temp = (ComponentBean) i.next();
            if (componentQTY.containsKey(temp.getId())) {
                int selectedQuantity = (Integer) componentQTY.get(temp.getId());
                int availableQuantity = temp.getQuantity();
                if ((availableQuantity - selectedQuantity) < 0) {
                    result = "The component " + temp.getName() + " is not available right now.";
                    break;
                }
            }
        }
        }catch(Exception ex){
            result=ex.getMessage();
        }
        return result;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.processRequest(request, response);
    }
}
