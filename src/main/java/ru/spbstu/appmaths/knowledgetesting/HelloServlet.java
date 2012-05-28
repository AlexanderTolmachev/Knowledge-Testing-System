package ru.spbstu.appmaths.knowledgetesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 21.05.12
 */


public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println("<h1>Hello Servlet</h1>");
//        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}
