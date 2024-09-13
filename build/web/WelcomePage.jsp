<%-- 
    Document   : WelcomePage.jsp
    Created on : Jan 03, 2020, 11:44:01 AM
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
    });
    $(function () {
        $.getJSON('GetSumTrx', {}, function (data) {
            console.log(data);

            // AREA CHART
            var area = new Morris.Area({
                element: 'revenue-chart',
                resize: true,
                data: data,
                xkey: 'y',
                ykeys: ['item1'],
                labels: ['Total Trx'],
                lineColors: ['#a0d0e0'],
                hideHover: 'auto'
            });
        });
    });
</script>
<body class="skin-yellow-light sidebar-mini">
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
                <div id="time" align="right" style="margin-top: -18px; font-size: 18px"></div>
            </section >
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-info">
                            <div class="box-header with-border">
                                <h3 class="box-title">Hai ${sessionScope.username_ppob}, Welcome in MATAJARI Monitoring</h3>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="box box-info">
                                    <div class="small-box" style="background-color: #5cb85c">
                                        <div class="inner">
                                            <h4>Your Balance :</h4><br><h1>Rp. ${sessionScope.balance}</h1>
                                        </div>
                                        <div class="small-box-footer"><h5>Last Top Up : ${sessionScope.last_topup}</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                        <div class="col-md-3">
                                <div class="box box-info">
                                    <div class="small-box" style="background-color: #a6e1ec">
                                        <div class="inner">
                                            <h4>Your Best Product :</h4><br><h1>${sessionScope.product}</h1>
                                        </div>
                                        <div class="small-box-footer"><h5>${sessionScope.jml} Transaction</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- AREA CHART -->
                        <div class="box box-primary">
                            <div class="box-header">
                                <h3 class="box-title">Transaction</h3>
                            </div>
                            <div class="box-body chart-responsive">
                                <div class="chart" id="revenue-chart" style="height: 300px;"></div>
                            </div><!-- /.box-body -->
                        </div><!-- /.box -->
                    </div>
                </div>
            </div>
        </div>
        <!-- /.content-wrapper -->
        <%@include file='footer.jsp' %>
        <%@include file='sidebar_right.jsp' %>
    </div>
    <%
        session.removeAttribute("januari");
    %>
</body>
