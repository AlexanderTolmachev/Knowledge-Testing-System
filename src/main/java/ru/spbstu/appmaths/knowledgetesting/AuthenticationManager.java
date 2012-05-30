package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;

import java.sql.*;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 30.05.12
 */
public class AuthenticationManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost";
    private static final String DATABASE_NAME = "knowledgetestingsystembd";

    private static final String DATABASE_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_USER_PASSWORD = "";

    private static final String DATABASE_USERS_TABLE_NAME = "users";
    private static final String DATABASE_USER_NAME_COLUMN_NAME = "username";
    private static final String DATABASE_USER_TYPE_COLUMN_NAME = "usertype";
    private static final String DATABASE_USER_PASSWORD_HASH_COLUMN_NAME = "passwordhash";

    private String getDatabaseConnectionUrl() {
        StringBuilder connectionUrlBuilder = new StringBuilder();
        connectionUrlBuilder.append(DATABASE_URL);
        connectionUrlBuilder.append("/");
        connectionUrlBuilder.append(DATABASE_NAME);
        return connectionUrlBuilder.toString();
    }

   private String buildGetUserPasswordHashByUserNameAndTypeQuery(String userName, String userType) {
       StringBuilder queryBuilder = new StringBuilder();
       queryBuilder.append("SELECT ");
       queryBuilder.append(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
       queryBuilder.append(" FROM ");
       queryBuilder.append(DATABASE_USERS_TABLE_NAME);
       queryBuilder.append(" WHERE ");
       queryBuilder.append(DATABASE_USER_TYPE_COLUMN_NAME);
       queryBuilder.append(" = ");
       queryBuilder.append(userType);
       queryBuilder.append(" AND ");
       queryBuilder.append(DATABASE_USER_NAME_COLUMN_NAME);
       queryBuilder.append(" = ");
       queryBuilder.append(userName);
       return queryBuilder.toString();
   }

    public boolean authenticateUser(String userName, String userPasswordHash, String userType)
            throws DataBaseDriverNotFoundException, SQLException {
        String passwordHash;

        try {
            Class.forName(DATABASE_DRIVER_NAME);
            Connection connection = DriverManager.getConnection(getDatabaseConnectionUrl(),
                    DATABASE_USER_NAME, DATABASE_USER_PASSWORD);
            String query = buildGetUserPasswordHashByUserNameAndTypeQuery(userName, userType);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                return false;
            }
            passwordHash = resultSet.getString(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverNotFoundException(e);
        }

        return userPasswordHash.equals(passwordHash);
    }


}
