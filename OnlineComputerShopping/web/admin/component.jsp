<%-- 
    Document   : component
    Created on : Jun 9, 2011, 5:43:11 PM
    Author     : Suman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OCS::Component</title>
        <link type="text/css" rel="stylesheet" href="../css/styles.css" charset='UTF-8' />
    </head>
    <body>


    <div id="container">
            <%@include file="header.jsp" %>
            <div id="content">
                <div id="left">
                    <%@include file="leftbar.jsp" %>
                </div>
                <div id="right">
                   <table border="0" class="right_content">
                        <tr>
                            <td  class="content_head">Component Entry Form</td>
                        </tr>
                        <tr><td align="center">
                                <form name="frmComponent" action="Admin?action=component_save" method="post">
                                <table border="0">
                                    <tr>
                                        <td class="right_content_detail">Component name</td>
                                        <td><input class="textfield" name="c_name" value="" type="text"></td>
                                    </tr>
                                    <tr>
                                        <td class="right_content_detail">Rate/Price</td>
                                        <td><input class="textfield" name="c_rate" value="" type="text"></td>
                                    </tr>
                                    <tr>
                                        <td class="right_content_detail">Quantity</td>
                                        <td><input class="textfield" name="c_quantity" value="" type="text"></td>
                                    </tr>
                                    <tr>
                                        <td class="right_content_detail">Component description</td>
                                        <td><textarea rows="6" cols="40"name="c_description"></textarea></td>
                                    </tr>
                                    <tr><td>&nbsp;</td><td><input type="submit" name="btnSave" value="Save" /></td></tr>
                                    <tr><td></td></tr>
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
