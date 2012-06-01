package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.TestException;

import javax.servlet.http.HttpSession;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class RedirectManager {
    public String getRedirectUrl(HttpSession httpSession) {
        String userName = (String) httpSession.getAttribute("username");
        if (userName == null) {
            return "index.jsp";
        }

        String userType = (String) httpSession.getAttribute("usertype");
        boolean isTestStarted = TestManager.getInstance().isTestStarted();
        boolean isTestFinished = TestManager.getInstance().isTestFinished();

        if ("teacher".equals(userType)) {
            if (isTestStarted) {
                if (isTestFinished) {
                    return "teachertestresults.jsp";
                }
                return "teachertestpage.jsp";
            }
            return "testselection.jsp";
        } else {
            if (isTestStarted) {
                boolean hasStudentNextQuestion;
                try {
                    hasStudentNextQuestion = StudentManager.getInstance().hasStudentNextQuestion(userName);
                } catch (TestException e) {
                    return "systemerror.jsp";
                }
                if (isTestFinished || !hasStudentNextQuestion) {
                    return "testresults.jsp";
                }
                return "test.jsp";
            }
            return "testwaiting.jsp";
        }
    }
}
