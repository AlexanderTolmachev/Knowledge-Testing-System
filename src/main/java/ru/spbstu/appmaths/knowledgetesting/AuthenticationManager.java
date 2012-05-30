package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 30.05.12
 */
public class AuthenticationManager extends DataBaseManager {
    private static final String DATABASE_USERS_TABLE_NAME = "users";
    private static final String DATABASE_USER_NAME_COLUMN_NAME = "name";
    private static final String DATABASE_USER_TYPE_COLUMN_NAME = "usertype";
    private static final String DATABASE_USER_PASSWORD_HASH_COLUMN_NAME = "passwordhash";

    private String buildGetUserPasswordHashByUserNameQuery(String userName) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_USERS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_USER_NAME_COLUMN_NAME);
        queryBuilder.append(" = \"");
        queryBuilder.append(userName);
        queryBuilder.append("\"");
        return queryBuilder.toString();
    }

    private String buildAddUserQuery(String userName, String userPasswordHash, String userType) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ");
        queryBuilder.append(DATABASE_USERS_TABLE_NAME);
        queryBuilder.append(" SET ");
        queryBuilder.append(DATABASE_USER_NAME_COLUMN_NAME);
        queryBuilder.append(" = \"");
        queryBuilder.append(userName);
        queryBuilder.append("\", ");
        queryBuilder.append(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
        queryBuilder.append(" = \"");
        queryBuilder.append(userPasswordHash);
        queryBuilder.append("\", ");
        queryBuilder.append(DATABASE_USER_TYPE_COLUMN_NAME);
        queryBuilder.append(" = \"");
        queryBuilder.append(userType);
        queryBuilder.append("\"");
        return queryBuilder.toString();
    }

    public boolean authenticateUser(String userName, String userPassword, String userType)
            throws DataBaseDriverNotFoundException, SQLException {
        String passwordHash;

        try {
            Connection connection = getDataBaseConnection();
            String query = buildGetUserPasswordHashByUserNameQuery(userName);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                return false;
            }
            passwordHash = resultSet.getString(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverNotFoundException(e);
        }

        String userPasswordHash = new PasswordEncoder().encodePassword(userPassword);
        return userPasswordHash.equals(passwordHash);
    }

    public boolean registerUser(String userName, String userPassword, String userType)
            throws DataBaseDriverNotFoundException, SQLException {

        try {
            Connection connection = getDataBaseConnection();
            String query = buildGetUserPasswordHashByUserNameQuery(userName);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return false;
            }

            String userPasswordHash = new PasswordEncoder().encodePassword(userPassword);
            query = buildAddUserQuery(userName, userPasswordHash, userType);
            statement.executeUpdate(query);
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverNotFoundException(e);
        }

        return true;
    }

//    public static void main(String[] args) {
//        AuthenticationManager authenticationManager = new AuthenticationManager();
//        String password = "test-password";
//
//        try {
//            boolean isAuthenticated = authenticationManager.authenticateUser("testuser", password, "teacher");
//            System.out.println("isAuthenticated = " + isAuthenticated);
//        } catch (DataBaseDriverNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        String userName = "auto_added_user";
//        password = "p@ssw0rd";
//        String userType = "student";
//
//        try {
//            boolean isAdded = authenticationManager.registerUser(userName, password, userType);
//            System.out.println("isAded = " + isAdded);
//            boolean isAuthenticated = authenticationManager.authenticateUser(userName, password, userType);
//            System.out.println("isAuthenticated = " + isAuthenticated);
//            isAdded = authenticationManager.registerUser(userName, password, userType);
//            System.out.println("isAded = " + isAdded);
//        } catch (DataBaseDriverNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//    }
}
