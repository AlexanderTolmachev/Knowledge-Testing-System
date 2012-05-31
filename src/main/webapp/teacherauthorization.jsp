<%--
  @author Alexander Tolmachev starlight@yandex-team.ru
  Date: 28.05.12
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Система автоматического тестирования знаний – Авторизация преподавателя</title>
</head>
<body>

<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Вход в систему <br/> для преподавателей</h2>
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
                    <input type="hidden" value="teacher" name="usertype"/>
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
    <a href="teacherregistration.jsp">Регистрация преподавателя</a>
</div>

</body>
</html>