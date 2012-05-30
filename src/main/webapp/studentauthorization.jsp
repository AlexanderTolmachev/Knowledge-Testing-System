<%--
  @author Alexander Tolmachev starlight@yandex-team.ru
  Date: 28.05.12
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Система автоматического тестирования знаний – Авторизация</title>
</head>
<body>

<div class="link" align="right">
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
                <td>Логин:</td>
                <td><input type="text" size="20" name="username"/>
            </tr>
            <tr>
                <td>Пароль:</td>
                <td><input type="password" size="20" name="password"/>
            </tr>
            <tr>
                <td></td>
                <td><input type="hidden" value="student" name="usertype"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Войти" name="submitbutton"/></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>