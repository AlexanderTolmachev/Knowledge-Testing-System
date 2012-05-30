package ru.spbstu.appmaths.knowledgetesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 28.05.12
 */
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String userType = req.getParameter("usertype");
        String passwordHash = new PasswordEncoder().encodePassword(password);

        String dataBaseUrl = "jdbc:mysql://localhost/users";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dataBaseUrl, "mysql", "");
            String query = "select passwordhash from users where usertype = " + userType + " and username = " + userName;
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }
}
