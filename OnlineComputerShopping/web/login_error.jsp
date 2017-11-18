<%-- 
    Document   : login_error
    Created on : Jun 3, 2011, 3:16:30 PM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Error</title>
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />
    </head>
    <body>
        <div id="container">
            <%@include file="header.jsp" %>
            <div id="content">
                <font color="#FF0000"> LOGIN FAILURE!</font><br/><br/>
                 <a href="javascript:history.back();" title="Go to home page">Back</a>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
