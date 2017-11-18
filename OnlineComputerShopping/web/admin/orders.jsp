<%-- 
    Document   : orders
    Created on : Jun 5, 2011, 12:47:56 PM
    Author     : Suman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OCS::Orders</title>
        <link type="text/css" rel="stylesheet" href="../css/styles.css" charset='UTF-8' />
    </head>
    <body>
        

    <div id=container>
            <%@include file="header.jsp" %>
            <div id="content">
                <div id="left">
                    <%@include file="leftbar.jsp" %>
                </div>
                <div id="right">
      			<jsp:useBean id="orderList" class="beans.OrderListBean" scope="request"/>
                        <c:set var="orderlist_xslt">
                            <c:import url="./orderlist_xslt.xsl"/>
                        </c:set>

                        <x:transform xslt="${orderlist_xslt}">
                            <jsp:getProperty name="orderList" property="xml"/>
                        </x:transform>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>