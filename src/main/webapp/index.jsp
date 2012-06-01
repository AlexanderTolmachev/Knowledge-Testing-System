<%@ page import="ru.spbstu.appmaths.knowledgetesting.RedirectManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 @author Alexander Tolmachev starlight@yandex-team.ru
 Date: 28.05.12
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    RedirectManager redirectManager = new RedirectManager();
    String redirectUrl = redirectManager.getRedirectUrl(session);
    if (!redirectUrl.equals("index.jsp")) {
        response.sendRedirect(redirectUrl);
    }
%>

<html>
<head>
    <title>Система автоматического тестирования знаний – Авторизация</title>
</head>
<body>

<div class="top-right-header" align="right">
    <a href="teacherauthorization.jsp">Вход для преподавателей</a>
</div>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Вход в систему</h2>
</div>

<div class="authorization-form" align="center">
    <form action="authentication" method="post">
        <table>
            <tr>
                <td>
                    Логин: <br/>
                    <input type="text" size="30" name="username"/>
                </td>
            </tr>
            <tr>
                <td>
                    Пароль: <br/>
                    <input type="password" size="30" name="password"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" value="student" name="usertype"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Войти" name="submitbutton"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<div class="plain-text" align="center">
    <a href="registration.jsp">Регистрация тестируемого</a>
</div>


</body>
</html>