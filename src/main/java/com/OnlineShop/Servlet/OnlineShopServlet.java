package com.OnlineShop.Servlet;

import com.OnlineShop.DataBase.DBMain;
import com.OnlineShop.DataBase.WebData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by v.babiak on 06.06.2016.
 */
@WebServlet(name = "OnlineShopServlet")
public class OnlineShopServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebData webData = new WebData();
        webData.setName(request.getParameter("product"));
        webData.setCount(Integer.parseInt(request.getParameter("count")));
        webData.setSum(Float.parseFloat(request.getParameter("sum")));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        Integer monthPeriod = Integer.parseInt(request.getParameter("period"));
        ArrayList<WebData> webDatas = DBMain.getWebData(monthPeriod);

        for (WebData webData : webDatas) {
            out.print("product:" + webData.getName() + " ");
            out.print("count:" + webData.getCount() + " ");
            out.print("sum:" + webData.getSum() + "\n");
        }

        out.close();

    }
}
