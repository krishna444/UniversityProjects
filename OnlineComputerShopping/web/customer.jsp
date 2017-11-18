<%-- 
    Document   : customer
    Created on : Jun 2, 2011, 12:21:03 PM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>OCS::Customer Page</title>
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />
    </head>
    <body>
        <div id="container">
            <%@include file="header.jsp" %>
            <div id="content">
                <c:if test="${customer==null}">
              <script language="JavaScript" type="text/javascript">
                window.location='Shop?action=refresh&page=customer';
              </script>
                 </c:if>
                <div id="left"></div>
                <div id="right">
                <table>
                    <tr>
                        <td>
                            <form action="Shop" method="get">
                                <input type="hidden" value="profile" name="action"/>
                                <input type="hidden" value="view_order" name="category">
                                <input type="submit" value="View Order" class="customer_button"/>
                            </form>
                        </td>

                        <td>
                            <form action="Shop" method="get">
                                <input type="hidden" value="profile" name="action"/>
                                <input type="hidden" value="change_password" name="category">
                                <input type="submit" value="Change Password" class="customer_button"/>
                            </form>
                        </td>

                        <td>
                            <form action="Shop" method="get">
                                <input type="hidden" value="profile" name="action"/>
                                <input type="hidden" value="change_profile" name="category">
                                <input type="submit" value="Change Profile" class="customer_button"/>
                            </form>
                        </td>
                    </tr>
                </table>

                <div>
                    <c:choose>
                        <c:when test="${category=='view_order'}">
                            <table border="1" class="right_content">
                                <tr><td colspan="5" class="content_head">Orders</td></tr>
                                <tr>
                                    <td><strong>Product Name</strong></td>
                                    <td><strong>Description</strong></td>
                                    <td><strong>Quantity</strong></td>
                                    <td><strong>Order Date</strong></td>
                                    <td><strong>Status</strong></td>
                                </tr>
                                <c:forEach var="item" items="${orders.customerOrders}">
                                    <tr>
                                        <td>${item.productName}</td>
                                        <td>${item.productDescription}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.date}</td>
                                        <td>${item.status}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                        <c:when test="${category=='change_password'}">
                            
                                
                                    <form name="changePasswordForm" action="Shop">
                                        <table class="right_content">
                                            <tr><td colspan="2" class="content_head">Change Password</td></tr>
                                            <tr><td colspan="2" align="center"><table border="0">
                                            <tr>
                                                <td class="right_content_detail">Old Password:</td>
                                                <td><input name="old_password" type="password" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">New Password:</td>
                                                <td><input name="new_password" type="password" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Confirm Password:</td>
                                                <td><input name="confirm_password" type="password" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td><input type="submit" value="Change"/></td>
                                            </tr>
                                                </table></td></tr>
                                        </table>
                                        <input type="hidden" name="action" value="update"/>
                                        <input type="hidden" name="category" value="password"/>
                                    </form>
                                
                                    

                        </c:when>
                        <c:when test="${category=='change_profile'}">
                            
                                
                                    <form name="changeProfileForm" action="Shop" method="get">
                                        <table class="right_content" border="0" width="200">
                                            <tr><td colspan="2" class="content_head">Change Profile</td></tr>
                                            <tr><td colspan="2" align="center"><table border="0">
                                            <tr>
                                                <td class="right_content_detail">First Name:</td>
                                                <td><input name="first_name"type="text"value="${profile.firstName}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Middle Name:</td>
                                                <td><input name="middle_name"type="text"value="${profile.middleName}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Last Name:</td>
                                                <td><input name="last_name"type="text"value="${profile.lastName}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Address1:</td>
                                                <td><input name="address1" type="text"value="${profile.address1}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Address2:</td>
                                                <td><input name="address2"type="text"value="${profile.address2}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">City:</td>
                                                <td><input name="city"type="text"value="${profile.city}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Post Box:</td>
                                                <td><input name="post_box"type="text"value="${profile.postBox}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Email:</td>
                                                <td><input name="email"type="text"value="${profile.email}" class="textfield"/></td>
                                            </tr>
                                            <tr>
                                                <td class="right_content_detail">Country:</td>
                                                <td><select name="country" class="textfield"><option value="Sweden">Sweden</option><option value="Nepal">Nepal</option></select></td>
                                            </tr>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td><input type="submit" value="Update"/></td>
                                            </tr>
                                                    </table></td></tr>
                                            
                                        </table>
                                        
                                        <input type="hidden" name="action" value="update"/>
                                        <input type="hidden" name="category" value="profile"/>
                                        
                                    </form>

                                
                           

                        </c:when>
                        <c:otherwise>
                            <p>
                            <h4>General Information</h4>
                            Hello ${profile.firstName}, this is your profile page. You can modify your profile information.
                            You can even change the password. Similarly, you can view all the orders from you.
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
                </div>
            </div>

            <%@include file="footer.jsp"%>
        </div>
    </body>
</html>
