<%-- 
    Document   : req_topup
    Created on : Jan 09, 2020, 11:45:00 AM
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file='defaultextend.jsp'%>
<script>
    $(document).ready(function () {
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
        //Date picker
        $('#datepicker').datepicker({
            format: 'dd-mm-yyyy',
            autoclose: true
        });
        $('#amount').maskMoney();
        $('#frmAdd').submit(function () {
            var amn = $('#amount').maskMoney('unmasked')[0];
            $('#amount').val(amn);
        });
    });
</script>
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
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title">${sessionScope.username_ppob} Request Topup on MATAJARI</h3>
                            </div>
                        </div>
                        <div class="box box-success">
                            <form class="form-horizontal" method="POST" action="TopupagentServlet" name="frmAdd" id="frmAdd">
                                <div class="box-body">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">From Bank</label>
                                            <div class="col-sm-10">
                                                <select name="bank_name" class="form-control select2">
                                                    <option value="">Select Your Bank</option>
                                                    <option value="MANDIRI">MANDIRI</option>
                                                    <option value="BRI">BRI</option>
                                                    <option value="BNI">BNI</option>
                                                    <option value="BTN">BTN</option>
                                                    <option value="BCA">BCA</option>
                                                    <option value="CIMB NIAGA">CIMB NIAGA</option>
                                                    <option value="BTPN">BTPN</option>
                                                    <option value="DANAMON">DANAMON</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Account Number</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="acct_no" placeholder="example : 123456789012" required/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Amount</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="amount" id="amount" placeholder="example : 1000000">
                                            </div>
                                        </div>                                        
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Transfer Date</label>
                                            <div class="col-sm-10">
                                                <input class="form-control" name="transfer_date" type="text" id="datepicker" placeholder="example : DD-MM-YYYY">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" value="Submit" class="btn btn-success pull-right">add</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <!-- /.content-wrapper -->
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
</body>


