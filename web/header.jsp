<%-- 
    Document   : header
    Created on : Jan 03, 2020, 4:38:43 PM
    Author     : suhanda
--%>
<%
    if (session.getAttribute("userlogin") == null) {
        response.sendRedirect(request.getContextPath()+"/ho");
    }
%>
<!--Untuk edit css header-->
<script>
    window.history.forward("index.jsp");
    function cPass() {
        location.href = "cp";
}
</script>
<!-- Main Header -->
<header class="main-header">
    <!-- Logo -->
    <a href="WelcomePage.jsp" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>MMS</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>MATAJARI</b></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <!--<span class="sr-only">Toggle navigation</span>-->
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown user user-menu">
                    <!-- Menu Toggle Button -->
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <span class="hidden-xs">Logout</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- The user image in the menu -->
                        <li class="user-header">
                            <button class="btn btn-group-justified" style="font-size: 20px">${sessionScope.username_ppob}</button>
                        </li>
                        <li class="user-footer">
                            <form action="LogoutServlet" method="post" class="pull-right">
                                <input class="btn btn-default btn-flat" type="submit" value="Logout">
                            </form>
                            <input class="btn btn-default btn-flat" value="Change Password" onclick="cPass()">
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
</header>