<%-- 
    Document   : register_success
    Created on : Jun 8, 2011, 5:23:16 PM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />
        <title>OCS::Registration Success!</title>
    </head>
    <body>
        <div id="container">
            <%@include file="header.jsp"%>
            <div id="content">
                ${requestScope["successmessage"]}<br/><br/>
                <a href="customer.jsp" title="Go to customer page">Customer Page</a>
            </div>
            <%@include file="footer.jsp"%>
        </div>
    </body>
</html>
