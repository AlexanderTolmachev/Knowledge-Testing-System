package ru.spbstu.appmaths.knowledgetesting;

import ru.spbstu.appmaths.knowledgetesting.exceptions.TestException;
import ru.spbstu.appmaths.knowledgetesting.test.TestQuestion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 01.06.12
 */
public class StudentManager {
    private static StudentManager instance;
    private static TestManager testManager = TestManager.getInstance();

    private Map<String, StudentTestInfo> studentTestInfoMap;

    public static synchronized StudentManager getInstance() {
        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    private StudentManager() {
        studentTestInfoMap = new HashMap<String, StudentTestInfo>();
    }

    public void initialize() {
        if (!studentTestInfoMap.isEmpty()) {
            studentTestInfoMap.clear();
        }
    }

    private void initializeStudentTestInfo(String studentName) throws TestException {
        int currentTestQuestionsNumber = testManager.getCurrentTest().getQuestionsNumber();
        StudentTestInfo testInfo = new StudentTestInfo(currentTestQuestionsNumber);
        studentTestInfoMap.put(studentName, testInfo);
    }

    private void checkStudentInitialization(String studentName) throws TestException {
        if (!studentTestInfoMap.containsKey(studentName)) {
            initializeStudentTestInfo(studentName);
        }
    }

    public synchronized boolean hasStudentNextQuestion(String studentName) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        int studentCurrentQuestionNumber = testInfo.getCurrentQuestionNumber();
        int currentTestQuestionsNumber = testManager.getCurrentTest().getQuestionsNumber();

        return (studentCurrentQuestionNumber < currentTestQuestionsNumber);
    }

    public synchronized int getStudentCurrentQuestionNumber(String studentName) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        return testInfo.getCurrentQuestionNumber();
    }

    public synchronized TestQuestion getStudentCurrentQuestion(String studentName) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        int studentCurrentQuestionNumber = testInfo.getCurrentQuestionNumber();

        return testManager.getCurrentTest().getQuestionByIndex(studentCurrentQuestionNumber);
    }

    public synchronized void setStudentCurrentQuestionAnswer(String studentName, String answer) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        TestQuestion currentQuestion = getStudentCurrentQuestion(studentName);
        String rightAnswer = currentQuestion.getRightAnswer();
        if (rightAnswer.equals(answer)) {
            testInfo.setCurrentAnswer(AnswerType.RIGHT);
        } else {
            testInfo.setCurrentAnswer(AnswerType.WRONG);
        }
    }

    public synchronized void skipStudentCurrentQuestion(String studentName) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        testInfo.setCurrentAnswer(AnswerType.SKIPPED);
    }

//    public Map<String, StudentTestInfo> getStudentTestInfoMap() {
//        return studentTestInfoMap;
//    }
//    public static void main(String[] args) {
//        StudentManager studentsManager = StudentManager.getInstance();
//        studentsManager.initialize();
//        Map<String, StudentTestInfo> stringStudentTestInfoMap = studentsManager.getStudentTestInfoMap();
//        StudentTestInfo testInfo = new StudentTestInfo(10);
//        stringStudentTestInfoMap.put("abc", testInfo);
//        StudentTestInfo info = stringStudentTestInfoMap.get("abc");
//        System.out.println("info.getCurrentQuestionNumber() = " + info.getCurrentQuestionNumber());
//        info.setCurrentAnswer(AnswerType.RIGHT);
//        testInfo = stringStudentTestInfoMap.get("abc");
//        System.out.println("info.getCurrentQuestionNumber() = " + info.getCurrentQuestionNumber());
//    }
}
