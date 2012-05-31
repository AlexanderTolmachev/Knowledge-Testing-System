<%--
  @author Alexander Tolmachev starlight@yandex-team.ru
  Date: 31.05.12
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Система автоматического тестирования знаний – Регистранция тестируемого</title>
</head>
<body>

<div class="top-right-header" align="right">
    <a href="teacherregistration.jsp">Регистрация для преподавателей</a>
</div>


<div class="headers" align="center">
    <h1>Система автоматического <br/> тестирования знаний</h1>

    <h2>Регистрация тестируемого</h2>
</div>

<div class="authorization-form" align="center">
    <form action="registration" method="post">
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
                    Повторите пароль: <br/>
                    <input type="password" size="30" name="passwordconformation"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="hidden" value="student" name="usertype"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Зарегистрироваться" name="submitbutton"/>
                </td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>