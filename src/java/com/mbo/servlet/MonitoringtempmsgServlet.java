/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbo.servlet;

import com.bo.function.StringFunction;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mbo.model.Monitoringtempmsg;
import com.mbo.util.DatabaseProcess;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class MonitoringtempmsgServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MonitoringtempmsgServlet.class);

    private static final long serialVersionUID = 1L;

    public MonitoringtempmsgServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<Monitoringtempmsg> ppob = new ArrayList<Monitoringtempmsg>();
            DatabaseProcess dp = new DatabaseProcess();

            HttpSession session = request.getSession(true);
            String userlogin = (String) session.getAttribute("userlogin");

            String sdate = request.getParameter("sdate");
            String edate = request.getParameter("edate");

            String start = "";
            String end = "";

            SimpleDateFormat requesttime1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat requesttime2 = new SimpleDateFormat("yyyy-MM-dd");
            if (sdate.length() < 10) {
                Date start1 = requesttime1.parse(sdate);
                start = requesttime2.format(start1);
            } else if (sdate.length() == 10) {
                start = request.getParameter("sdate");
            }
            
            if (edate.length() < 10) {
                Date end1 = requesttime1.parse(edate);
                end = requesttime2.format(end1);
            } else if (edate.length() == 10) {
                end = request.getParameter("edate");
            }

            ppob = dp.getAllTemp(userlogin, start, end);
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(ppob, new TypeToken<List<Monitoringtempmsg>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            log.info(jsonArray);
            response.setContentType("application/json");
            response.getWriter().print(jsonArray);
        } catch (ParseException ex) {
            Logger.getLogger(MonitoringtempmsgServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
