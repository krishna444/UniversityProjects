<%-- 
    Document   : home
    Created on : Jun 2, 2011, 12:12:35 PM
    Author     : Suman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OCS::Admin Homepage</title>
        <link type="text/css" rel="stylesheet" href="../css/styles.css" charset='UTF-8' />
    </head>
    <div id=container>
            <%@include file="header.jsp" %>
             <c:if test="${sess_admin==null}">
                    <script language="JavaScript" type="text/javascript">
                        window.location='Admin?action=refresh';
                    </script>
                </c:if>
            <div id="content">
                <div id="left">
                    <%@include file="leftbar.jsp" %>
                </div>
                <div id="right">
      			Welcome to Admin Home Page. Navigate through the Menus on the left to view stocks, orders and Add new products/ components. 
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
</html>
