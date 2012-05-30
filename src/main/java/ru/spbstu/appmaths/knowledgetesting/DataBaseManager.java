package ru.spbstu.appmaths.knowledgetesting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class DataBaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost";
    private static final String DATABASE_NAME = "knowledgetestingsystembd";
    private static final String DATABASE_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_USER_PASSWORD = "";

    private String getDatabaseConnectionUrl() {
        StringBuilder connectionUrlBuilder = new StringBuilder();
        connectionUrlBuilder.append(DATABASE_URL);
        connectionUrlBuilder.append("/");
        connectionUrlBuilder.append(DATABASE_NAME);
        return connectionUrlBuilder.toString();
    }

    protected Connection getDataBaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DATABASE_DRIVER_NAME);
        return DriverManager.getConnection(getDatabaseConnectionUrl(), DATABASE_USER_NAME, DATABASE_USER_PASSWORD);
    }

}
