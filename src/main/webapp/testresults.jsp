<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.sun.tools.javac.util.Pair" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.test.Test" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.*" %>
<%@ page import="java.util.Collections" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 01.06.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RedirectManager redirectManager = new RedirectManager();
    String redirectUrl = redirectManager.getRedirectUrl(session);
    if (!redirectUrl.equals("testresults.jsp")) {
        response.sendRedirect(redirectUrl);
    }
%>

<html>
<head>
    <title>Система автоматического тестирования знаний – Результаты тестирования</title>
</head>
<body>

<div class="top-right-header" align="right">
    Пользователь: <c:out value="${username}"/> <br/>
    <a href="logout">Выход</a>
</div>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Результаты тестирования</h2>
</div>

<%
    String userName = (String)session.getAttribute("username");
    StudentTestInfo testInfo = StudentManager.getInstance().getStudentTestInfo(userName);
    Test test = TestManager.getInstance().getTest();
%>

<div class="plain-text" align="center">
    Тест: <%=test.getName()%> <br/>  <br/>
</div>

<div class="test-results-table" align="center">
    <table border="1" cellpadding="5" cellspacing="2">
        <tr>
            <td align="center"> # Вопроса </td>
            <% for (int i = 1; i <= test.getQuestionsNumber(); i++) { %>
            <td align="center">
                <%=i%>
            </td>
            <% } %>
            <td align="center"> Количество правильных ответов</td>
        </tr>
        <tr>
            <td align="center"> Ответ </td>
            <%
                List<AnswerType> answers = testInfo.getAnswers();
                int rightAnswersNumber = Collections.frequency(answers, AnswerType.RIGHT);
                for (AnswerType answer : answers) {
                    if (answer == AnswerType.RIGHT) {
            %>
            <td align="center">&#10004;</td>
            <%
                    } else if (answer == AnswerType.WRONG) {
            %>
            <td align="center">&#10007;</td>
            <%
                    } else {
            %>
            <td align="center">&#9675;</td>
            <%
                    }
                }
            %>
            <td align="center"><%=rightAnswersNumber%></td>
        </tr>
    </table>
</div>

<div class="plain-text" align="center">
    <br/>
    <a href="reset">На главную страницу</a>
</div>

</body>
</html>