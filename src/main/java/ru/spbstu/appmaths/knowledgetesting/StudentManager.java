package ru.spbstu.appmaths.knowledgetesting;

import com.sun.tools.javac.util.Pair;
import ru.spbstu.appmaths.knowledgetesting.exceptions.TestException;
import ru.spbstu.appmaths.knowledgetesting.test.TestQuestion;

import java.util.*;

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
        int currentTestQuestionsNumber = testManager.getTest().getQuestionsNumber();
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
        int currentTestQuestionsNumber = testManager.getTest().getQuestionsNumber();

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

        return testManager.getTest().getQuestionByIndex(studentCurrentQuestionNumber);
    }

    public synchronized void handleStudentQuestionReply(String studentName, String reply) throws TestException {
        if (!testManager.isTestStarted()) {
            throw new TestException("Test is not started");
        }

        checkStudentInitialization(studentName);
        StudentTestInfo testInfo = studentTestInfoMap.get(studentName);
        TestQuestion currentQuestion = getStudentCurrentQuestion(studentName);
        String rightAnswer = currentQuestion.getRightAnswer();
        if (rightAnswer.equals(reply)) {
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

    public StudentTestInfo getStudentTestInfo(String studentName) {
        return studentTestInfoMap.get(studentName);
    }

    public List<Pair<String, StudentTestInfo>> getStudentsTestInfo() {
        List<Pair<String, StudentTestInfo>> testInfo = new ArrayList<Pair<String, StudentTestInfo>>();
        for (String studentName : studentTestInfoMap.keySet()) {
            StudentTestInfo studentTestInfo = studentTestInfoMap.get(studentName);
            testInfo.add(Pair.of(studentName, studentTestInfo));
        }
        Collections.sort(testInfo, new StudentTestInfoComparator());
        return testInfo;
    }

    private class StudentTestInfoComparator implements Comparator<Pair<String, StudentTestInfo>> {
        @Override
        public int compare(Pair<String, StudentTestInfo> studentTestInfoPair,
                           Pair<String, StudentTestInfo> anotherStudentTestInfoPair) {
            return studentTestInfoPair.fst.compareToIgnoreCase(anotherStudentTestInfoPair.fst);
        }
    }
}
