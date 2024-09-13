<%-- 
    Document   : index
    Created on : Jan 03, 2020, 11:44:01 AM
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="css/login.css">
<script>
    function createCaptcha() {
        for (i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                captcha[i] = String.fromCharCode(Math.floor((Math.random() * 26) + 65));
            } else {
                captcha[i] = Math.floor((Math.random() * 10) + 0);
            }
        }
        var thecaptcha = captcha.join("");
        var elm = document.getElementById('captcha');
        elm.innerHTML = "<span class='captcha'> " + thecaptcha + " </span>" + "&nbsp; <a onclick='createCaptcha()' class='badge badge-warning' href='#'>recaptcha</a>";
    }
    var captcha = new Array();
    function validateRecaptcha() {
        var recaptcha = document.getElementById("recaptcha").value;
        var user = document.getElementById("user").value;
        var pass = document.getElementById("pass").value;
        ;
        var validRecaptcha = 0;
        for (var j = 0; j < 6; j++) {
            if (recaptcha.charAt(j) != captcha[j]) {
                validRecaptcha++;
            }
        }
        if (user == "") {
            document.getElementById('errUser').innerHTML = "<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>User Login must be filled\n"
                    + "</div>"
        } else if (user != "") {
            if (pass == "") {
                document.getElementById('errPass').innerHTML = "<div class=\"alert alert-danger status-custom\">\n"
                        + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                        + "     <i class=\"icon fa fa-warning\"></i>Password must be filled\n"
                        + "</div>"
            } else if (pass != "") {
                if (recaptcha == "") {
                    document.getElementById('errCaptcha').innerHTML = "<div class=\"alert alert-danger status-custom\">\n"
                            + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                            + "     <i class=\"icon fa fa-warning\"></i>Re-Captcha must be filled\n"
                            + "</div>"
                } else if (validRecaptcha > 0 || recaptcha.length > 6) {
                    document.getElementById('errCaptcha').innerHTML = "<div class=\"alert alert-danger status-custom\">\n"
                            + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                            + "     <i class=\"icon fa fa-warning\"></i>Sorry, Wrong Re-Captcha\n"
                            + "</div>"
                } else {
                    document.getElementById("myForm").submit();
                }
            }
        }
    }

    function removeModaluser() {
        $("#errUser").empty();
        $("#errPass").empty();
        $("#errCaptcha").empty();
    }
    function removeModalpass() {
        $("#errPass").empty();
        $("#errCaptcha").empty();
    }
    function removeModalcaptcha() {
        $("#errCaptcha").empty();
    }
</script>

<style>
    .captcha
    { font: italic bold 16px "Comic Sans MS", cursive, sans-serif;
      color:#a0a0a0;background-color:#c0c0c0;
      width:100px;border-radius: 5px;
      text-align:center;
      text-decoration:line-through;
    }
    .errmsg
    {color:#ff0000;}
</style>
<body>
    <div class="container">
        <div class="row vertical-offset-100">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default login">
                    <div class="panel-heading">                            
                        <div class="row-fluid user-row">
                            <!--<i class="fa fa-building fa-3x"></i>--> 
                        </div>
                        <div class="logo_matajari">
                            <img src="/matajari_monitoring/image/signmatajari.png" alt="Matajari"/>
                        </div>
                        <!--<h3 class="panel-title user-row"></h3>-->
                        <div class="label_matajari">
                            Web Monitoring
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="keterangan_login">
                            Please fill out the following fields to login:
                        </div>
                        <form action="LoginServlet" method="post" id="myForm">
                            <fieldset>
                                <div class="form-group">
                                    <div id="errUser" class="errmsg"></div>
                                    <input onClick="removeModaluser()" class="form-control" id="user" placeholder="User Login" name="userlogin" type="text" required/>
                                </div>
                                <div class="form-group">
                                    <div class="password_matajari">
                                        <div id="errPass" class="errmsg"></div>
                                        <input onClick="removeModalpass()" id="pass" class="form-control" placeholder="User Password" name="userpass" type="password" required>
                                        <div class="response_login">
                                            ${sessionScope.responsenya}
                                        </div>
                                    </div>
                                    <%
                                        session.removeAttribute("responsenya");
                                    %>
                                </div>
                                <div class="form-group">
                                    <div id="captcha" colspan="2"></div>
                                    <script> createCaptcha();</script><br>
                                    <input onClick="removeModalcaptcha()" type="text" name="recaptcha" id="recaptcha" class="form-control" required/>
                                    <div id="errCaptcha" class="errmsg"></div>
                                </div>
                                <input class="btn btn-lg btn-success btn-block" onClick="validateRecaptcha()" value="Login" readonly type="button" id="login"/>
                                <script>
                                    var input = document.getElementById("recaptcha");
                                    input.addEventListener("keyup", function (event) {
                                        if (event.keyCode === 13) {
                                            event.preventDefault();
                                            document.getElementById("login").click();
                                        }
                                    });
                                </script>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jQuery-2.2.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>