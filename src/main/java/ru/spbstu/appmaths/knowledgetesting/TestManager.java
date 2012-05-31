package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseException;
import ru.spbstu.appmaths.knowledgetesting.test.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class TestManager extends DataBaseManager {
    private static final String DATABASE_TESTS_TABLE_NAME = "tests";
    private static final String DATABASE_TEST_NAME_COLUMN_NAME = "name";


    private static TestManager instance;

    private boolean isTestStarted;
    private Test currentTest;

    public static synchronized TestManager getInstance() {
        if (instance == null) {
            instance = new TestManager();
        }
        return instance;
    }

    private TestManager() {
    }

    private String buildGetTestByNameQuery(String testName) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ");
        queryBuilder.append(DATABASE_TESTS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_TEST_NAME_COLUMN_NAME);
        queryBuilder.append(" = \"");
        queryBuilder.append(testName);
        queryBuilder.append("\"");
        return queryBuilder.toString();
    }

    private String buildGetAllTestNamesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(DATABASE_TEST_NAME_COLUMN_NAME);
        queryBuilder.append("  FROM ");
        queryBuilder.append(DATABASE_TESTS_TABLE_NAME);
        return queryBuilder.toString();
    }

    private void loadTest(String testName) throws SQLException, DataBaseDriverNotFoundException, DataBaseException {
        try {
            Connection connection = getDataBaseConnection();
            String query = buildGetTestByNameQuery(testName);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                String errorMessage = "Test with name '" + testName + "' is not found in database";
                throw new DataBaseException(errorMessage);
            }


        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverNotFoundException(e);
        }

    }

    public synchronized List<String> getAvailableTestNames() throws SQLException, DataBaseDriverNotFoundException {
        List<String> testNames = new ArrayList<String>();

        try {
            Connection connection = getDataBaseConnection();
            String query = buildGetAllTestNamesQuery();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String testName = resultSet.getString(DATABASE_TEST_NAME_COLUMN_NAME);
                testNames.add(testName);
            }
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverNotFoundException(e);
        }

        return testNames;
    }

    public synchronized boolean startTest(String testName) {
        if (isTestStarted) {
            return false;
        }

        //loadTest(testName);
        isTestStarted = true;

        return true;
    }

    public synchronized boolean stopCurrentTest() {
        if (!isTestStarted) {
            return false;
        }
        isTestStarted = false;
        return true;
    }
}
