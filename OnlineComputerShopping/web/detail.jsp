<%-- 
    Document   : detail
    Created on : Jun 5, 2011, 3:27:47 PM
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
        <title>Product Detail</title>
    </head>
    <body class="html">
        <div id=container>
            <%@include file="header.jsp" %>
            <div id="content">
                   <div id="left"></div>
                <div id="right">
                    <jsp:useBean id="product" class="beans.ProductBean" scope="request"/>
                    <h1><jsp:getProperty name="product" property="name"/></h1>
                    <h2>Details</h2>
                    <jsp:getProperty name="product" property="description"/>
                    <table border="1">
                        <tr>
                            <td><strong>Name</strong></td>
                            <td><strong>rate</strong></td>
                            <td><strong>description</strong></td>
                        </tr>
                        <c:forEach var="component" items="${complist}">
                            <tr>
                                <td>
                                    ${component.name}
                                </td>
                                <td>
                                    ${component.rate}
                                </td>
                                <td>
                                    ${component.description}
                                </td>

                            </tr>

                        </c:forEach>
                    </table>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
