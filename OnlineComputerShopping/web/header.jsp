<%-- 
    Document   : header
    Created on : Jun 2, 2011, 1:55:31 AM
    Author     : krishna
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id=top>
    <div id="logo"><img src="images/logo2.jpeg" class="logo" /></div>
    <div id="head">
        <div id="site_heading">ONLINE COMPUTER SHOP</div>
        <div id="login_links">
            <c:choose><c:when test="${customer==null}">
                    <a href="/OnlineComputerShopping/customer.jsp">Customer Login</a>
                </c:when>
                <c:otherwise><a href="Shop?action=logout">Logout</a></c:otherwise>
        </c:choose>   </div>
</div>
<div id="top_ver_bar"></div>
<div id="top_hor_bar"></div>
<div id="top_menu_bar">
    <ul>
        <li><a href="/OnlineComputerShopping">Home</a></li>
        <li><a href="Shop?action=view_products">In Stock</a></li>
    </ul>
</div>
</div> 
