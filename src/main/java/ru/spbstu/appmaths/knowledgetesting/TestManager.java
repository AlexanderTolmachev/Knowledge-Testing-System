package ru.spbstu.appmaths.knowledgetesting;

import com.sun.tools.javac.util.Pair;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseDriverNotFoundException;
import ru.spbstu.appmaths.knowledgetesting.exceptions.DataBaseException;
import ru.spbstu.appmaths.knowledgetesting.exceptions.TestException;
import ru.spbstu.appmaths.knowledgetesting.test.Test;
import ru.spbstu.appmaths.knowledgetesting.test.TestQuestion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class TestManager extends DataBaseManager {
    private static final String DATABASE_TESTS_TABLE_NAME = "tests";
    private static final String DATABASE_TEST_NAME_COLUMN_NAME = "name";
    private static final String DATABASE_TEST_ID_COLUMN_NAME = "id";
    private static final String DATABASE_QUESTIONS_TABLE_NAME = "questions";
    private static final String DATABASE_QUESTION_ID_COLUMN_NAME = "id";
    private static final String DATABASE_QUESTION_TEXT_COLUMN_NAME = "questiontext";
    private static final String DATABASE_ANSWER_ID_COLUMN_NAME = "answerid";
    private static final String DATABASE_TEST_QUESTIONS_TABLE_NAME = "testquestions";
    private static final String DATABASE_FOREIGN_TEST_ID_COLUMN_NAME = "testid";
    private static final String DATABASE_FOREIGN_QUESTION_ID_COLUMN_NAME = "questionid";
    private static final String DATABASE_OPTIONS_TABLE_NAME = "options";
    private static final String DATABASE_OPTION_ID_COLUMN_NAME = "id";
    private static final String DATABASE_OPTION_TEXT_COLUMN_NAME = "optiontext";
    private static final String DATABASE_QUESTION_OPTIONS_TABLE_NAME = "questionoptions";
    private static final String DATABASE_FOREIGN_OPTION_ID_COLUMN_NAME = "optionid";

    private static TestManager instance;

    private boolean isTestStarted;
    private boolean isTestFinished;
    private boolean hasPreviousTest;
    private Test currentTest;
    private Timer testCountDownTimer;
    private Date testTime;
    private Date startTime;

    public static synchronized TestManager getInstance() {
        if (instance == null) {
            instance = new TestManager();
        }
        return instance;
    }

    private void initialize() {
        isTestStarted = false;
        isTestFinished = false;
        currentTest = null;
        testCountDownTimer = null;
    }

    private TestManager() {
        initialize();
    }

    public void reset() {
        initialize();
    }

    public synchronized Test getTest() throws TestException {
        return currentTest;
    }

    public synchronized boolean isTestStarted() {
        return isTestStarted;
    }

    public synchronized boolean isTestFinished() {
        return isTestFinished;
    }

    public synchronized long getRemainingTestTime() {
        long passedTime = new Date().getTime() - startTime.getTime();
        return testTime.getTime() - passedTime;
    }

    public synchronized List<String> getAvailableTestNames() throws SQLException, DataBaseDriverNotFoundException {
        Connection connection = getDataBaseConnection();
        String query = buildGetAllTestNamesQuery();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<String> testNames = new ArrayList<String>();
        while (resultSet.next()) {
            String testName = resultSet.getString(DATABASE_TEST_NAME_COLUMN_NAME);
            testNames.add(testName);
        }

        return testNames;
    }

    public synchronized void startTest(String testName, Date time) throws SQLException,
            DataBaseDriverNotFoundException, DataBaseException, TestException {
        if (isTestStarted) {
            throw new TestException("Test is started");
        }
        loadTest(testName);
        StudentManager.getInstance().initialize();
        isTestStarted = true;
        testTime = time;
        startTime = new Date();
        testCountDownTimer = new Timer(true);
        testCountDownTimer.schedule(new TestStopTask(), time.getTime());
    }

    public synchronized void stopTest() throws TestException {
        if (!isTestStarted) {
            throw new TestException("Test is not started");
        }
        isTestFinished = true;
        hasPreviousTest = true;
    }

    private void loadTest(String testName) throws SQLException, DataBaseDriverNotFoundException, DataBaseException {
        List<TestQuestion> testQuestions = getTestQuestionsByTestName(testName);
        currentTest = new Test(testName, testQuestions);
    }

    private String buildGetAllTestNamesQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(DATABASE_TEST_NAME_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_TESTS_TABLE_NAME);
        return queryBuilder.toString();
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

    private String buildGetQuestionsByTestIdQuery(int testId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ");
        queryBuilder.append(DATABASE_QUESTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_QUESTION_ID_COLUMN_NAME);
        queryBuilder.append(" IN (SELECT ");
        queryBuilder.append(DATABASE_FOREIGN_QUESTION_ID_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_TEST_QUESTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_FOREIGN_TEST_ID_COLUMN_NAME);
        queryBuilder.append(" = ");
        queryBuilder.append(testId);
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String buildGetQuestionOptionsByQuestionIdQuery(int questionId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM ");
        queryBuilder.append(DATABASE_OPTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_OPTION_ID_COLUMN_NAME);
        queryBuilder.append(" IN (SELECT ");
        queryBuilder.append(DATABASE_FOREIGN_OPTION_ID_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_QUESTION_OPTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_FOREIGN_QUESTION_ID_COLUMN_NAME);
        queryBuilder.append(" = ");
        queryBuilder.append(questionId);
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private String buildGetQuestionAnswerByQuestionIdQuery(int questionId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append(DATABASE_OPTION_TEXT_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_OPTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_OPTION_ID_COLUMN_NAME);
        queryBuilder.append(" IN (SELECT ");
        queryBuilder.append(DATABASE_ANSWER_ID_COLUMN_NAME);
        queryBuilder.append(" FROM ");
        queryBuilder.append(DATABASE_QUESTIONS_TABLE_NAME);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(DATABASE_QUESTION_ID_COLUMN_NAME);
        queryBuilder.append(" = ");
        queryBuilder.append(questionId);
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private List<Pair<Integer, String>> getQuestionsEntitiesByTestName(String testName)
            throws SQLException, DataBaseDriverNotFoundException, DataBaseException {
        Connection connection = getDataBaseConnection();
        String query = buildGetTestByNameQuery(testName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) {
            String errorMessage = "Test with name '" + testName + "' is not found in database";
            throw new DataBaseException(errorMessage);
        }
        int testId = resultSet.getInt(DATABASE_TEST_ID_COLUMN_NAME);

        List<Pair<Integer, String>> questions = new ArrayList<Pair<Integer, String>>();
        query = buildGetQuestionsByTestIdQuery(testId);
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int questionId = resultSet.getInt(DATABASE_QUESTION_ID_COLUMN_NAME);
            String questionText = resultSet.getString(DATABASE_QUESTION_TEXT_COLUMN_NAME);
            Pair<Integer, String> questionData = new Pair<Integer, String>(questionId, questionText);
            questions.add(questionData);
        }
        return questions;
    }

    private List<String> getQuestionOptionsByQuestionId(int questionId)
            throws DataBaseDriverNotFoundException, SQLException {
        Connection connection = getDataBaseConnection();
        String query = buildGetQuestionOptionsByQuestionIdQuery(questionId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<String> questionOptions = new ArrayList<String>();
        while (resultSet.next()) {
            String option = resultSet.getString(DATABASE_OPTION_TEXT_COLUMN_NAME);
            questionOptions.add(option);
        }
        return questionOptions;
    }

    private String getQuestionAnswerByQuestionId(int questionId)
            throws DataBaseDriverNotFoundException, SQLException, DataBaseException {
        Connection connection = getDataBaseConnection();
        String query = buildGetQuestionAnswerByQuestionIdQuery(questionId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) {
            String errorMessage = "Question with id " + questionId + " has no answer stored in database";
            throw new DataBaseException(errorMessage);
        }
        return resultSet.getString(DATABASE_OPTION_TEXT_COLUMN_NAME);
    }

    private List<TestQuestion> getTestQuestionsByTestName(String testName)
            throws SQLException, DataBaseDriverNotFoundException, DataBaseException {
        List<TestQuestion> testQuestions = new ArrayList<TestQuestion>();
        List<Pair<Integer, String>> questions = getQuestionsEntitiesByTestName(testName);
        for (Pair<Integer, String> question : questions) {
            int questionId = question.fst;
            String questionText = question.snd;
            List<String> questionOptions = getQuestionOptionsByQuestionId(questionId);
            String questionAnswer = getQuestionAnswerByQuestionId(questionId);
            int answerOptionNumber = questionOptions.indexOf(questionAnswer);
            if (answerOptionNumber == -1) {
                String errorMessage = "Question with id " + questionId + " has no answer stored in database";
                throw new DataBaseException(errorMessage);
            }
            TestQuestion testQuestion = new TestQuestion(questionText, questionOptions, answerOptionNumber);
            testQuestions.add(testQuestion);
        }
        return testQuestions;
    }

    private class TestStopTask extends TimerTask {
        @Override
        public void run() {
            try {
                TestManager.getInstance().stopTest();
            } catch (TestException e) {
                // ignore
            }
        }
    }
}
