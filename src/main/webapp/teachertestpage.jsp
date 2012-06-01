<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.TestManager" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.RedirectManager" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 01.06.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    RedirectManager redirectManager = new RedirectManager();
    String redirectUrl = redirectManager.getRedirectUrl(session);
    if (!redirectUrl.equals("teachertestpage.jsp")) {
        response.sendRedirect(redirectUrl);
    }

    String testName = TestManager.getInstance().getTest().getName();
%>

<html>
<head>
    <title>Система автоматического тестирования знаний – Проведение теста</title>
</head>
<body>

<div class="top-right-header" align="right">
    Пользователь: <c:out value="${username}"/> <br/>
    <a href="logout">Выход</a>
</div>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Проведение тестирования</h2>
</div>

<div class="plain-text" align="center">
    <b>
        <p>
            Тест: <%=testName%> <br/>
            Время до окончания: <br/>
        </p>
    </b>
</div>
<div class="test-stop-form" align="center">
    <form action="stoptest" method="post">
        <table>
            <tr>
                <td>
                    <input type="submit" value="Завершить тестирование" name="submitbutton"/>
                </td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>