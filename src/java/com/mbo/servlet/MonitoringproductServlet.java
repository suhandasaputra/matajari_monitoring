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
import com.mbo.model.Monitoringproductmonth;
import com.mbo.util.DatabaseProcess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syukur
 */
public class MonitoringproductServlet extends HttpServlet {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MonitoringproductServlet.class);

    private static final long serialVersionUID = 1L;

    public MonitoringproductServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Monitoringproductmonth> alltoday = new ArrayList<Monitoringproductmonth>();
        DatabaseProcess dp = new DatabaseProcess();
        HttpSession session = request.getSession(true);
        String userlogin = (String) session.getAttribute("userlogin");
        alltoday = dp.getProduct(userlogin);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(alltoday, new TypeToken<List<Monitoringproductmonth>>() {
        }.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        log.info(jsonArray);
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
