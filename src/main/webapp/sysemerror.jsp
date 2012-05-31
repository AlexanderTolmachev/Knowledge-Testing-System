<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 31.05.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Система автоматического тестирования знаний – Ошибка системы</title>
</head>
<body>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Ошибка системы</h2>
</div>

<div class="plain-text" align="center">
    <p>
        <c:out value="${errorMessage}"/> <br/>
        <a href="/">На главную страницу</a>
    </p>
</div>


</body>
</html>