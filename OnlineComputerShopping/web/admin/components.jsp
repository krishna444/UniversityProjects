<%-- 
    Document   : components
    Created on : Jun 9, 2011, 8:28:36 PM
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
        <title>OCS::Components</title>
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
      			<jsp:useBean id="componentList" class="beans.ComponentListBean" scope="request"/>
                        <c:set var="componentlist_xslt">
                            <c:import url="./componentlist_xslt.xsl"/>
                        </c:set>

                        <x:transform xslt="${componentlist_xslt}">
                            <jsp:getProperty name="componentList" property="xml"/>
                        </x:transform>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
