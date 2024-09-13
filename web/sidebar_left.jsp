<%-- 
    Document   : sidebar
    Created on : Jan 08, 2020, 4:48:17 PM
    Author     : suhanda
--%>

<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu">
            <li class="treeview">
                <a href="#"><i class="fa fa-toggle-up"></i> <span>Topup</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/rtp">Request Topup</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-product-hunt"></i> <span>Product</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/lpd">List Product</a></li>
                </ul>
            </li>
            <li class="treeview">
                <a href="#"><i class="fa fa-history"></i> <span>History</span> <i class="fa fa-angle-left pull-right"></i></a>
                <ul class="treeview-menu">
                    <li><a href="<%=request.getContextPath()%>/htp">History Topup</a></li>
                    <li><a href="<%=request.getContextPath()%>/htc">History Transaction</a></li>
                </ul>
            </li>
        </ul>
    </section>
</aside>
