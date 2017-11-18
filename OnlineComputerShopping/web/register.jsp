<%-- 
    Document   : register
    Created on : Jun 8, 2011, 2:25:55 AM
    Author     : krishna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />
        <title>OCS::Register</title>
        <!--Script file for validation-->
        <script type="text/javascript" language="JavaScript">
            function validateForm(){
            var userid=document.forms["registerForm"]["userid"].value;
            var firstname=document.forms["registerForm"]["first_name"].value;
            var lastname=document.forms["registerForm"]["last_name"].value;
            var address1=document.forms["registerForm"]["address1"].value;
            var city=document.forms["registerForm"]["city"].value;
            var postbox=document.forms["registerForm"]["post_box"].value;
            var email=document.forms["registerForm"]["email"].value;
            
            if((userid==null ||userid=="")
                      ||(firstname==null || firstname=="")
                      ||(lastname==null || lastname=="")
                      ||(address1==null || address1=="")
                      ||(city==null || city=="")
                      ||(postbox==null || postbox=="")
                      ||(email==null || email=="")){
                      alert("Some required fields are missing! Please, fill those fields");
                      return false;
                      }
            }
        </script>

    </head>
    <body>
        <div id="container">
            <%@include file="header.jsp"%>
            <div id="content">
                <form action="Shop" method="get" name="registerForm" onsubmit="return validateForm()">
                    <div>
                        <ul style="list-style: none">
                            <li>
                                Customer ID:<input name="userid" type="text"/>
                            </li>
                            <li>
                                Password:<input name="password1" type="password"/>
                            </li>
                            <li>
                                Confirm Password:<input name="password2" type="password" />
                            </li>
                        </ul>
                    </div>
                    <div>
                        <h2>Detail Information</h2>
                        <ul style="list-style: none">
                            <li>
                                First Name:<input name="first_name" type="text"/>
                            </li>
                            <li>
                                Middle Name:<input name="middle_name" type="text"/>
                            </li>
                            <li>
                                Last Name:<input name="last_name" type="text"/>
                            </li>
                            <li>
                                Assress1:<input name="address1" type="text"/>
                            </li>
                            <li>
                                Assress2:<input name="address2" type="text"/>
                            </li>
                            <li>
                                City:<input name="city" type="text"/>
                            </li>
                            <li>
                                Post Box:<input name="post_box" type="text"/>
                            </li>
                            <li>
                                Email:<input name="email" type="text"/>
                            </li>
                            <li>
                                Country:<select name="country"><option value="Sweden" selected>Sweden</option><option value="Nepal">Nepal</option> </select>
                            </li>
                            <li>
                                <input type="hidden" name="action" value="register"/>
                                <input type="submit" value="Register"/>
                            </li>

                        </ul>
                    </div>
                </form>
            </div>

            <%@include file="footer.jsp"%>
        </div>

    </body>


</html>
