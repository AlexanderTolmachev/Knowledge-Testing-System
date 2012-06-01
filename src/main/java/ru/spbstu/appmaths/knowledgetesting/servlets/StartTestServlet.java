package ru.spbstu.appmaths.knowledgetesting.servlets;

import ru.spbstu.appmaths.knowledgetesting.RedirectManager;
import ru.spbstu.appmaths.knowledgetesting.TestManager;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseException;
import ru.spbstu.appmaths.knowledgetesting.exceptions.TestException;
import ru.spbstu.appmaths.knowledgetesting.util.TimeConverter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 01.06.12
 */
public class StartTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RedirectManager redirectManager = new RedirectManager();
        String redirectUrl = redirectManager.getRedirectUrl(session);
        response.sendRedirect(redirectUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testName = request.getParameter("testname");
        int testTimeHours = Integer.parseInt(request.getParameter("timehours"));
        int testTimeMinutes = Integer.parseInt(request.getParameter("timeminutes"));
        int testTimeSeconds = Integer.parseInt(request.getParameter("timeseconds"));

        long testTimeInMilliseconds = new TimeConverter().convertTimeToMilliseconds(testTimeHours,
                testTimeMinutes, testTimeSeconds);
        Date testTime = new Date(testTimeInMilliseconds);
        try {
            TestManager.getInstance().startTest(testName, testTime);
        } catch (SQLException e) {
            String errorMessage = "SQL error happened: " + e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("systemerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("systemerror.jsp");
            requestDispatcher.forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        RedirectManager redirectManager = new RedirectManager();
        String redirectUrl = redirectManager.getRedirectUrl(session);
        response.sendRedirect(redirectUrl);
    }
}
