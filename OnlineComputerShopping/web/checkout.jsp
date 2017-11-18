<%-- 
    Document   : checkout
    Created on : Jun 6, 2011, 10:34:30 PM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset="UTF-8"/>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
        <%@taglib prefix="shop" uri="WEB-INF/tlds/myshop.tld" %>
        <title>OCS:Checkout</title>
    </head>
    <body>        
        <div id="container">
            <%@include file="header.jsp" %>
            <div id="content">
                <c:if test="${customer==null}">
                    <script language="JavaScript" type="text/javascript">
                        window.location='Shop?action=refresh&page=checkout';
                    </script>
                </c:if>
                <div id="left"></div>
                <div id="right">

                <c:set var="checkout_xsl">
                    <c:import url="xslt/checkout_xslt.html"/>
                </c:set>
                <x:transform xslt="${checkout_xsl}">
                    <checkout>
                    <shoppinglist>
                        <shop:productinfo/>
                    </shoppinglist>
                        <shop:profleinfo/>
                    </checkout>
                </x:transform>
                    <br/>
                <form action="Shop" method="get">
                    <input name="action" value="order" type="hidden"/>
                    <input type="submit" value="Order"/>                    
                </form>
            </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
