package ru.spbstu.appmaths.knowledgetesting.servlets;

import ru.spbstu.appmaths.knowledgetesting.TestManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 06.06.12
 */
public class IsTestFinishedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TestManager testManager = TestManager.getInstance();
        PrintWriter writer = response.getWriter();
        writer.print(testManager.isTestFinished());
    }
}
