/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.servlet;

import com.bo.function.SHA256Enc;
import com.mbo.model.Userlogin;
import com.mbo.util.DatabaseProcess;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class UpServlet extends HttpServlet {

    DatabaseProcess dp = new DatabaseProcess();

    private static final long serialVersionUID = 1L;
    private static String INSERT = "add_user.jsp";
    private static String UPDATE = "update_user.jsp";

    public UpServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String userlogin = session.getAttribute("userlogin").toString();

        String pass1 = SHA256Enc.encryptProc(request.getParameter("old_password1"));
        String pass2 = SHA256Enc.encryptProc(request.getParameter("old_password2"));
        String new_pass = SHA256Enc.encryptProc(request.getParameter("new_password"));

        String status = dp.updateUserlogin(userlogin, pass1, pass2, new_pass);
        if (status.equals("0000")) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/wlcm");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-success status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-check\"></i>Change Password, Success"
                    + "</div>");
            session.invalidate();
            rd.include(request, response);
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/cp");
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger status-custom\">\n"
                    + "     <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-hidden=\"true\">&times;</button>\n"
                    + "     <i class=\"icon fa fa-warning\"></i>Change Password, Failed"
                    + "</div>");
            rd.include(request, response);
        }
    }

}
