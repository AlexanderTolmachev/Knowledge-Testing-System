package ru.spbstu.appmaths.knowledgetesting.servlets;

import ru.spbstu.appmaths.knowledgetesting.AuthenticationManager;
import ru.spbstu.appmaths.knowledgetesting.RedirectManager;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 28.05.12
 */
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RedirectManager redirectManager = new RedirectManager();
        String redirectUrl = redirectManager.getRedirectUrl(session);
        response.sendRedirect(redirectUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String userPassword = request.getParameter("password");
        String userType = request.getParameter("usertype");

        AuthenticationManager authenticationManager = new AuthenticationManager();
        boolean isUserAuthenticated;
        try {
            isUserAuthenticated = authenticationManager.authenticateUser(userName, userPassword, userType);
        } catch (DataBaseDriverNotFoundException e) {
            String errorMessage = "Unable to connect to the database: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            response.sendRedirect("sysemerror.jsp");
            return;
        } catch (SQLException e) {
            String errorMessage = "SQL error happened: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            response.sendRedirect("sysemerror.jsp");
            return;
        }

        if (!isUserAuthenticated) {
            response.sendRedirect("authorizationerror.jsp");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("username", userName);
        session.setAttribute("usertype", userType);
        RedirectManager redirectManager = new RedirectManager();
        String redirectUrl = redirectManager.getRedirectUrl(session);
        response.sendRedirect(redirectUrl);
    }
}
