<%-- 
    Document   : product
    Created on : Jun 8, 2011, 3:38:19 PM
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
        <title>OCS::Product</title>
        <link type="text/css" rel="stylesheet" href="../css/styles.css" charset='UTF-8' />
    </head>
    <body>


    <div id="container">
            <%@include file="header.jsp" %>
            <jsp:useBean id="componentList" class="beans.ComponentListBean" scope="request"/>
            <div id="content">
                <div id="left">
                    <%@include file="leftbar.jsp" %>
                </div>
                <div id="right">
                   <table border="0" class="right_content">
                        <tr>
                            <td  class="content_head">Product Entry Form</td>
                        </tr>
                        <tr><td align="center">
                                <form name="frmProduct" action="Admin?action=product_save" method="post">
                                <table border="0">
                                    <tr>
                                        <td class="right_content_detail">Product name</td>
                                        <td><input class="textfield" name="p_name" value="" type="text" size="20"></td>
                                    </tr>
                                    <tr>
                                        <td class="right_content_detail">Required Components</td>
                                        <td><select name="p_components" multiple size="6">
                                                <c:forEach items="${componentList.componentList}" var="item">
                                                    <option value="<c:out value="${item.id}"/>"><c:out value="${item.name}"/></option>
                                                </c:forEach>
                                                
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="right_content_detail">Product description</td>
                                        <td><textarea rows="6" cols="40"name="p_description"></textarea></td>
                                    </tr>
                                    <tr><td>&nbsp;</td><td><input type="submit" name="btnSave" value="Save" /></td></tr>
                                    
                                </table>
                                </form>
                            </td></tr>
                   </table>

                </div>
                

            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
