<%@page import="org.apache.catalina.core.ApplicationContext"%><%@page import="utility.GlobalVariables"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""DTD/xhtml1-strict.dtd"><html>    <head>        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>        <title>OCS::Homepage</title>        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />         <% GlobalVariables.setDatabaseURL(getServletContext().getInitParameter("JDBC_URL"));%>    </head>    <body class="html">        <div id=container>            <%@include file="header.jsp" %>                       <div id="content">                               <div id="left"><a href="admin/home.jsp" class="leftmenulink"> Admin Login &raquo;</a></div>                <div id="right">                    <h4>What is Online Computer Shopping?</h4>      			This is online computer shopping system. This is the portal where a customer can view items, order items,                        gets email notification for order confirmation.                        <h4></h4>                </div>            </div>            <%@include file="footer.jsp" %>        </div>    </body></html>