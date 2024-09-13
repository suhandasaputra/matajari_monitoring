<%-- 
    Document   : history_transaction
    Created on : Jan 03, 2020, 11:44:01 AM
    Author     : suhanda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file='defaultextend.jsp'%>

<script>
//hidetabel
    $(document).ready(function () {
        var x = document.getElementById("hidetabel");
        x.style.display = "none";
    });

    function go() {
        var y = document.getElementById('startdate').value = '';
        var z = document.getElementById('enddate').value = '';

        var x = document.getElementById("hidetabel");
        x.style.display = "none";
    }
    ;
    //daterange
    $(function () {
        $('#daterange-btn').daterangepicker(
                {
                    ranges: {
                        'Today': [moment(), moment()],
                        'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                        'Last 7 Days': [moment().subtract('days', 6), moment()],
                        'Last 30 Days': [moment().subtract('days', 29), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')],
                        'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                    },
                    startDate: moment().subtract('days', 29),
                    endDate: moment()
                },
                function (start, end) {
                    $('#reportrange span').html(start.format('DD MMMM YYYY') + ' - ' + end.format('DD MMMM YYYY'));
                    var sdate = document.getElementById("startdate");
                    var edate = document.getElementById("enddate");
                    sdate.value = start.format('DD MMMM YYYY');
                    edate.value = end.format('DD MMMM YYYY');

                }
        );
    });


//getdatatable
    function getData() {
        //out
        var sdate = document.getElementById("startdate").value;
        var edate = document.getElementById("enddate").value;

        var sdate1 = new Date(sdate);
        var format_sdate1 = sdate1.getFullYear() + "-" + (sdate1.getMonth() + 1) + "-" + sdate1.getDate();
        var edate1 = new Date(edate);
        var format_edate1 = edate1.getFullYear() + "-" + (edate1.getMonth() + 1) + "-" + edate1.getDate();
        var table = $('#tabletopupagent').DataTable({

            "order": [[0, "desc"]],
            retrieve: true,

            dom: 'ftp',
            "ajax": {
                "url": "/matajari_monitoring/MonitoringtempmsgServlet?sdate=" + format_sdate1 + "&edate=" + format_edate1 + "",
                "dataSrc": ""
            },
            "columns": [
                {"data": "requesttime"},
                {"data": "noref"},
                {"data": "productcode"},
                {"data": "custno"},
                {"data": "amount"},
                {"data": "prev_bal"},
                {"data": "curr_bal"},
                {"data": "rcinternal",
                render: function (data) {
                        if (data === '0000') {
                            return '<span class="label label-success">Success</span>';
                        } else if (data === '0068') {
                            return '<span class="label label-warning">Suspect</span>';
                        } else {
                            return '<span class="label label-danger">Failed</span>';
                        }
                    }
                
                
                }
            ],

            responsive: true,
            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            orderCellsTop: true,
            scrollX: true,
            colReorder: true,
            language: {
                lengthMenu: '<div class="input-group"><span class="input-group-addon"><span class="glyphicon glyphicon-menu-hamburger"></span></span>_MENU_</div>',
                processing: "<img src='image/loading.gif'>"
            },
            processing: true,
            "initComplete": function (settings, json) {
                $('#tabletopupagent').fadeIn();
            },
            "footerCallback": function (tfoot, data, start, end, display) {
                var info = $('#tabletopupagent').DataTable().page.info();
                $(tfoot).find('td').eq(0).html("Total Count: " + info.recordsDisplay);
            }
        });
    }
    ;



//showtable
    function show() {
        var x = document.getElementById("hidetabel");
        x.style.display = "block";
    }
    ;



//show table
    function tampilkan() {
        var input = document.getElementById('startdate').value;
        if (input === '') {
            document.getElementById('errUser').innerHTML = "<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>Date must be filled\n"
                    + "</div>";
        } else {
            getData();
            show();
        }
    }
    ;

    function refresh() {
        $('#hidetabel').load(document.URL + ' #hidetabel');
    }
    ;

    function removeModaluser() {
        $("#errUser").empty();
    }

//waktu
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
                                <h3 class="box-title">History Transaction ${sessionScope.username_ppob} on MATAJARI</h3>
                                <!--<div class="balance">Your Balance Rp. ${sessionScope.balance}</div>-->
                            </div>
                        </div>
                        <div class="box box-primary">
                            <div class="box-header">
                                <h3 class="box-title">Choose Your Transaction Date :</h3>
                                <button class="btn btn-default" id="daterange-btn" onclick="removeModaluser()">
                                    <i class="fa fa-calendar"></i>
                                    <input type="text" id="startdate" placeholder="start date" readonly="" required="">
                                    <input type="text" id="enddate" placeholder="end date" readonly="" required="">
                                    <i class="fa fa-caret-down"></i>
                                </button>
                                <button class="btn btn-success" id="aa" onclick="tampilkan()">
                                    Show
                                </button>
                                <button class="btn btn-danger" id="bb" onclick="refresh();go();">
                                    Clear
                                </button>
                                <div id="errUser" class="errmsg"></div>
                            </div>
                        </div><!-- /.box -->
                        <div class="hidetabel" id="hidetabel">
                            <div class="box box-primary">
                                <div class="box-header">
                                    <div class="col-md-12">
                                        <table id="tabletopupagent" class="table display table-striped table-bordered responsive-utilities jambo_table" cellspacing="0" width="100%">    
                                            <thead>                                            
                                                <tr>
                                                    <th><strong>Request Time</strong></th>
                                                    <th><strong>rrn</strong></th>
                                                    <th><strong>Product Name</strong></th>
                                                    <th><strong>Cust No</strong></th>
                                                    <th><strong>Amount</strong></th>
                                                    <th><strong>Before Balance</strong></th>
                                                    <th><strong>After Balance</strong></th>
                                                    <th><strong>Status</strong></th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <td ></td>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                            </div>
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

