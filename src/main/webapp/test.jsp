<%@ page import="ru.spbstu.appmaths.knowledgetesting.TestManager" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.test.TestQuestion" %>
<%@ page import="ru.spbstu.appmaths.knowledgetesting.StudentManager" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 01.06.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String userName = (String)session.getAttribute("username");
    TestManager testManager = TestManager.getInstance();
    StudentManager studentManager = StudentManager.getInstance();

    if (!studentManager.hasStudentNextQuestion(userName)) {
        response.sendRedirect("testresults.jsp");
    }

    String testName = testManager.getCurrentTest().getName();
    int testQuestionsNumber = testManager.getCurrentTest().getQuestionsNumber();
    int questionNumber = studentManager.getStudentCurrentQuestionNumber(userName);
    TestQuestion question = studentManager.getStudentCurrentQuestion(userName);
%>

<html>
<head>
    <title>Система автоматического тестирования знаний – Прохождение теста</title>
</head>
<body>

<div class="top-right-header" align="right">
    Пользователь: <c:out value="${username}"/> <br/>
    <a href="logout">Выход</a>
</div>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>
</div>

<div class="test-question-form" align="center">
    <form action="" method="post">
        <table>
            <tr>
                <td>
                    Тест: <%=testName%>
                </td>
            </tr>
            <tr>
                <td>
                    Время до окончания:
                </td>
            </tr>
            <tr>
                <td>
                    <br/>
                </td>
            </tr>
            <tr>
                <td>
                    <b>Вопрос <%=questionNumber%> из <%=testQuestionsNumber%>:</b>
                </td>
            </tr>
            <tr>
                <td>
                    <%=question.getQuestionText()%>
                </td>
            </tr>
            <tr>
                <td>
                    <br/>
                </td>
            </tr>

            <%
                List<String> options = question.getOptions();
                boolean isChecked = true;
                for (String option : options) {
            %>
            <tr>
                <td>
                    <%
                        if (isChecked) {
                    %>
                    <input type="radio" name="options" value="<%=option%>" checked=/> <%=option%>
                    <%
                        } else {
                    %>
                    <input type="radio" name="options" value="<%=option%>"/> <%=option%>
                    <%
                        }
                    %>
                </td>
            </tr>
            <%
                    isChecked = false;
                }
            %>

            <tr>
                <td>
                    <br/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Ответить" name="submitbutton"/>
                </td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>