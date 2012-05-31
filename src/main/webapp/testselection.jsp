<%@ page import="ru.spbstu.appmaths.knowledgetesting.TestManager" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 31.05.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Система автоматического тестирования знаний – Ожидание тестирования</title>
</head>
<body>

<div class="top-right-header" align="right">
    Пользователь: <c:out value="${username}"/> <br/>
    <a href="logout">Выход</a>
</div>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Выбор теста для авторизованных участников</h2>
</div>

<div class="test-selection-form" align="center">
    <form action="" method="post">
        <table>
            <%
                List<String> testNames = TestManager.getInstance().getAvailableTestNames();
                for (String testName : testNames) {
            %>
            <tr>
                <td>
                    <input type="radio" name="testnames" value="<%=testName%>"/> <%=testName%> <br/>
                </td>
            </tr>
            <%
                }
            %>

            <tr>
                <td>
                    <input type="submit" value="Начать тестирование" name="submitbutton"/>
                </td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>