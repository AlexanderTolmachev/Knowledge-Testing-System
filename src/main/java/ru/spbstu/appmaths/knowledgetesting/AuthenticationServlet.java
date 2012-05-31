package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 28.05.12
 */
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String userPassword = req.getParameter("password");
        String userType = req.getParameter("usertype");

        AuthenticationManager authenticationManager = new AuthenticationManager();
        boolean isUserAuthenticated;
        try {
            isUserAuthenticated = authenticationManager.authenticateUser(userName, userPassword, userType);
        } catch (DataBaseDriverNotFoundException e) {
            String errorMessage = "Unable to connect to the database: " + e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            resp.sendRedirect("sysemerror.jsp");
            return;
        } catch (SQLException e) {
            String errorMessage = "SQL error happened: " + e.getMessage();
            req.setAttribute("errorMessage", errorMessage);
            resp.sendRedirect("sysemerror.jsp");
            return;
        }

        if (!isUserAuthenticated) {
            resp.sendRedirect("authorizationerror.jsp");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("username", userName);
        session.setAttribute("usertype", userType);
        resp.sendRedirect("testwaiting.jsp");
    }
}
