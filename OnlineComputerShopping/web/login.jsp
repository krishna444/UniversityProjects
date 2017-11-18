<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "DTD/xhtml1-strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>OCS::Customer Login</title>
        <link type="text/css" rel="stylesheet" href="css/styles.css" charset='UTF-8' />
        <link type="text/css" rel="stylesheet" href="css/customer.css" charset="UTF-8"/>

    </head>
    <body class="html">

        <div id=container>
            <%@include file="header.jsp" %>
            <div id="content">
                <div id="left"></div>
                <div id="right">
                    <div id="auth_box">
                        <div id="auth_head">Authentication</div>
                        <div id="auth">
                            <form name="frmAuth" action="j_security_check" method="post">
                                <table class="authtable">
                                    <tr>
                                        <td>Username</td>
                                        <td><input name="j_username" type="text" class="textfield"/></td>
                                    </tr>
                                    <tr>
                                        <td>Password</td>
                                        <td><input name="j_password" type="password" class="textfield"/></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td><a href="register.jsp" class="auth_link">New Customer?</a> | <a href="forgotpassword.jsp" class="auth_link">Forgot Password?</a></td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td><input type="submit" value="Login" name="btnSubmit"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">&nbsp;</td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>