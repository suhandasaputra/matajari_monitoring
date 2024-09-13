<%-- 
    Document   : change_password
    Created on : Jan 03, 2020, 11:44:01 AM
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file='defaultextend.jsp'%>

<script>
    function Timer() {
        var date = new Date();
        var day = date.getDate();
        var month;
        switch (new Date().getMonth()) {
            case 0:
                month = "January";
                break;
            case 1:
                month = "Februari";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case  6:
                month = "July";
                break;
            case  7:
                month = "August";
                break;
            case  8:
                month = "September";
                break;
            case  9:
                month = "October";
                break;
            case  10:
                month = "November";
                break;
            case  11:
                month = "December";
        }
        var year = date.getFullYear();
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        time = day + " " + month + " " + year + " | " + hours + ":" + minutes + ":" + seconds;
        var lblTime = document.getElementById("time");
        lblTime.innerHTML = time;
    }
    setInterval(Timer, 1000);
</script>
<style>
    .balance{
        font-size: 18px;
        position: absolute;
        right: 20px;
        top: 8px;
    }
</style>

<body class="skin-yellow-light sidebar-mini sidebar-collapse">
    <div class="wrapper">
        <%@include file='header.jsp' %>
        <%@include file='sidebar_left.jsp' %>
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    MATAJARI MONITORING
                </h1>
                <div id="time" align="right" style="margin-top: -25px; font-size: 18px"></div>
            </section >

            <!-- Main content -->
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title">Change Password ${sessionScope.username_ppob} on MATAJARI</h3>
                            </div>
                        </div>
                        <div class="box box-success">
                            <form class="form-horizontal" method="POST" action="UpServlet" name="frmUser" id="frmUser">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Old Password</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="password" name="old_password1" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Confirm Old Password</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="password" name="old_password2" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">New Password</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" type="password" name="new_password" required/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">Change</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>

