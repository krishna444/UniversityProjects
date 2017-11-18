<%-- 
    Document   : stocks
    Created on : Jun 6, 2011, 3:24:43 PM
    Author     : Suman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OCS::Stocks</title>
        <link type="text/css" rel="stylesheet" href="../css/styles.css" charset='UTF-8' />
    </head>
    <body>


    <div id="container">
            <%@include file="header.jsp" %>
            <jsp:useBean id="stockList" class="beans.StockListBean" scope="request"/>
            <div id="content">
                <div id="left">
                    <%@include file="leftbar.jsp" %>
                </div>
                <div id="right">
                <c:set var="stocklist_xslt">
                    <c:import url="./stocklist_xslt.xsl"/>
                </c:set>
                <x:transform xslt="${stocklist_xslt}">
                    <jsp:getProperty name="stockList" property="xml"/>
                </x:transform>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>