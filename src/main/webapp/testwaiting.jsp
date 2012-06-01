<%@ page import="ru.spbstu.appmaths.knowledgetesting.RedirectManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 31.05.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    RedirectManager redirectManager = new RedirectManager();
    String redirectUrl = redirectManager.getRedirectUrl(session);
    if (!redirectUrl.equals("testwaiting.jsp")) {
        response.sendRedirect(redirectUrl);
    }
%>

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

    <h2>Ожидание тестирования</h2>
</div>

<div class="plain-text" align="center">
    <p>
        Здравствуйте, <c:out value="${username}"/>! <br/> <br/>
        Пожалуйста, подождите, когда Ваш организатор тестирования выберет тест
    </p>
</div>

</body>
</html>