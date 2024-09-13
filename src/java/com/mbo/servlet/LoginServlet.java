/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.servlet;

import com.bo.function.SHA256Enc;
import com.bo.function.StringFunction;
import com.mbo.model.Agent_login;
import com.mbo.model.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mbo.util.DatabaseProcess;
import java.text.ParseException;

/**
 *
 * @author syukur
 */
public class LoginServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LoginServlet.class);

    private static final long serialVersionUID = 1L;

    public LoginServlet() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userlogin = request.getParameter("userlogin");
        String userpass = SHA256Enc.encryptProc(request.getParameter("userpass"));
        DatabaseProcess dp = new DatabaseProcess();
        try {
            HttpSession session = request.getSession(true);
            Agent_login resp = new Agent_login();
            resp = dp.validate(userlogin, userpass);
            if (resp.getStatus().equalsIgnoreCase("0000")) {
                Product resp1 = new Product();
                resp = dp.validate(userlogin, userpass);
                resp1 = dp.getAllproduct(userlogin);
                session.setAttribute("username_ppob", resp.getAgent_name());
                
                String bal1 = resp1.getJml();
                double Dou1 = Double.parseDouble(bal1);
                int val1 = (int) Dou1;
                
                session.setAttribute("jml", StringFunction.rupiahFormat(val1));
                session.setAttribute("product", resp1.getProductname());
                
                String bal = resp.getCurr_bal();
                double Dou = Double.parseDouble(bal);
                int val = (int) Dou;
                session.setAttribute("balance", StringFunction.rupiahFormat(val));
                session.setAttribute("last_topup", resp.getLast_topup());
                session.setAttribute("userlogin", userlogin);
                response.sendRedirect(request.getContextPath()+"/wlcm");
                log.info("user dengan id " + resp.getAgent_name() + " telah login");
                System.out.println("user dengan id " + resp.getAgent_name() + " telah login");
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/ho");
                session.setAttribute("responsenya", "Incorrect username or password");
                log.info("Incorrect username or password");
                System.out.println("Incorrect username or password");
                rd.include(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
