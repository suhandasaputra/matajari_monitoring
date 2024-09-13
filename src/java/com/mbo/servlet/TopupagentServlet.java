/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mbo.model.Topupagent;
import com.mbo.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author suhanda
 */
public class TopupagentServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TopupagentServlet.class);

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;

    public TopupagentServlet() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);
        String userlogin = (String) session.getAttribute("userlogin");
        if (action.equalsIgnoreCase("Listtopupagent")) {
            ArrayList<Topupagent> alltopupagent = new ArrayList<Topupagent>();
            try {
                alltopupagent = dp.getAllTopupagent(userlogin);
            } catch (ParseException ex) {
                Logger.getLogger(TopupagentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            //indikasi berubah sort
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(alltopupagent, new TypeToken<List<Topupagent>>() {
            }.getType());

            JsonArray jsonArray = element.getAsJsonArray();
            log.info(jsonArray);

            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String userlogin = (String) session.getAttribute("userlogin");
        Topupagent topagent = new Topupagent();
        topagent.setAgent_id(userlogin);
        topagent.setAmount(request.getParameter("amount"));
        topagent.setBank_name(request.getParameter("bank_name"));
        topagent.setAcct_no(request.getParameter("acct_no"));
        topagent.setTransfer_date(request.getParameter("transfer_date"));
        String status = dp.addTopupagent(topagent, userlogin);
        if (status.equals("0000")) {
            response.sendRedirect(request.getContextPath()+"/htp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>Success , Waiting Approve"
                    + "</div>");
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/rtp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>Failed Request Topup"
                    + "</div>");
        }
    }

}
