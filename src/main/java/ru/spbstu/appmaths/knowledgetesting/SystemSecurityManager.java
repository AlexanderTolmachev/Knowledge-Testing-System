package ru.spbstu.appmaths.knowledgetesting;

import org.apache.commons.lang.StringEscapeUtils;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;
import ru.spbstu.appmaths.knowledgetesting.utils.PasswordEncoder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 30.05.12
 */
public class SystemSecurityManager extends DataBaseManager {
    private static final String DATABASE_USERS_TABLE_NAME = "users";
    private static final String DATABASE_USER_NAME_COLUMN_NAME = "name";
    private static final String DATABASE_USER_TYPE_COLUMN_NAME = "usertype";
    private static final String DATABASE_USER_PASSWORD_HASH_COLUMN_NAME = "passwordhash";
    private static final String TEACHER_REGISTRATION_SECURITY_PASSWORD = "RegSec777";

    private String buildGetUserByUserNameQuery(String userName) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ");
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
        Connection connection = getDataBaseConnection();
        String escapedUserName = StringEscapeUtils.escapeSql(StringEscapeUtils.escapeHtml(userName));
        String query = buildGetUserByUserNameQuery(escapedUserName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) {
            return false;
        }
        String actualPasswordHash = resultSet.getString(DATABASE_USER_PASSWORD_HASH_COLUMN_NAME);
        String actualUserType = resultSet.getString(DATABASE_USER_TYPE_COLUMN_NAME);
        String userPasswordHash = new PasswordEncoder().encodePassword(userPassword);

        return userType.equals(actualUserType) && userPasswordHash.equals(actualPasswordHash);
    }

    public boolean registerUser(String userName, String userPassword, String userType)
            throws DataBaseDriverNotFoundException, SQLException {
        Connection connection = getDataBaseConnection();
        String escapedUserName = StringEscapeUtils.escapeSql(StringEscapeUtils.escapeHtml(userName));
        String query = buildGetUserByUserNameQuery(escapedUserName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return false;
        }
        String userPasswordHash = new PasswordEncoder().encodePassword(userPassword);
        query = buildAddUserQuery(escapedUserName, userPasswordHash, userType);
        statement.executeUpdate(query);

        return true;
    }

    public boolean validateTeacherRegistrationSecurityPassword(String password) {
        return password.equals(TEACHER_REGISTRATION_SECURITY_PASSWORD);
    }

}
