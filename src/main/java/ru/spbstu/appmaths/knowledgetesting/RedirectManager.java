package ru.spbstu.appmaths.knowledgetesting;

import javax.servlet.http.HttpSession;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class RedirectManager {
    public String getRedirectUrl(HttpSession httpSession) {
        if (httpSession.getAttribute("username") == null) {
            return "index.jsp";
        }

        String userType = (String)httpSession.getAttribute("usertype");
        if ("teacher".equals(userType)) {
            return "testselection.jsp";
        } else {
            return "testwaiting.jsp";
        }
    }
}
