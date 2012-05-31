package ru.spbstu.appmaths.knowledgetesting.servlets;

import ru.spbstu.appmaths.knowledgetesting.RedirectManager;
import ru.spbstu.appmaths.knowledgetesting.SystemSecurityManager;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class RegistrationServlet extends HttpServlet {
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
        String userPasswordConformation = request.getParameter("passwordconformation");
        String userType = request.getParameter("usertype");

        if (userName.isEmpty() || userPassword.isEmpty()) {
            String errorMessage = "Поля 'логин' и 'пароль' не должны быть пустыми";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrationerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        if (!userPassword.equals(userPasswordConformation)) {
            String errorMessage = "Указанные пароли не совпадают";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrationerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        SystemSecurityManager systemSecurityManager = new SystemSecurityManager();
        if ("teacher".equals(userType)) {
            String teacherRegistrationPassword = request.getParameter("registrationpassword");
            if (!systemSecurityManager.validateTeacherRegistrationSecurityPassword(teacherRegistrationPassword)) {
                String errorMessage = "Неверный пароль для регистрации преподавателя";
                request.setAttribute("errorMessage", errorMessage);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrationerror.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
        }

        boolean isUserRegistered;
        try {
            isUserRegistered = systemSecurityManager.registerUser(userName, userPassword, userType);
        } catch (DataBaseDriverNotFoundException e) {
            String errorMessage = "Unable to connect to the database: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("systemerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        } catch (SQLException e) {
            String errorMessage = "SQL error happened: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("systemerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        if (!isUserRegistered) {
            String errorMessage = "Пользователь с указанным логином уже существует";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrationerror.jsp");
            requestDispatcher.forward(request, response);
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
