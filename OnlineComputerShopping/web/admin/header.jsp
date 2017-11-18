<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id=top>
    <div id="logo"><img src="../images/logo2.jpeg" class="logo" /></div>
    <div id="head">
        <div id="site_heading">ONLINE COMPUTER SHOP ADMINISTRATOR</div>
        <div id="login_links">
            <c:choose><c:when test="${sess_admin!=null}">
                    <a href="Admin?action=logout">Logout</a>
                </c:when>
                <c:otherwise><a href="Admin?action=login">Login</a></c:otherwise>
            </c:choose>
           
        </div>
    </div>
    <div id="top_ver_bar"></div>
    <div id="top_hor_bar"></div>
    <!--    <div id="top_menu_bar">
            <ul>
                <li><a href="changepass.jsp" class="auth_link">Change Password</a></li>
                <li><a href="Admin?action=orders" class="auth_link">View Orders</a></li>
                <li><a href="addproduct.jsp" class="auth_link">Add New Products</a></li>
            </ul>
        </div>-->
</div>
