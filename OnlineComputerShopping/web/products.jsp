<%-- 
    Document   : products
    Created on : Jun 3, 2011, 10:55:12 PM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset="UTF-8"/>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
       
       

    </head>
    <body>
        <div id="container">
            <%@include file="header.jsp" %>
            <jsp:useBean id="itemlist" class="beans.ProductListBean" scope="request"/>
            <jsp:useBean id="shoppinglist" class="beans.ShoppingBean" scope="session"/>
            <div id="content">
                <div id="left"></div>
                <div id="right">
                <c:set var="itemlist_xslt">
                    <c:import url="/xslt/itemlist_xslt.html"/>
                </c:set>
                <x:transform xslt="${itemlist_xslt}">
                    <jsp:getProperty name="itemlist" property="xml"/>
                </x:transform>
                <c:set var="shoppinglist_xsl">
                    <c:import url="/xslt/shoppinglist_xslt.html"/>
                </c:set>
                    <br/>
                <x:transform xslt="${shoppinglist_xsl}">
                    <jsp:getProperty name="shoppinglist" property="xml"/>
                </x:transform>
                    <br/>
                <form action="Shop?action=checkout" method="get">
                    <input value="Checkout" type="submit"/>
                    <input type="hidden" name="action" value="checkout"/>
                </form>
                </div>
            </div>


            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
