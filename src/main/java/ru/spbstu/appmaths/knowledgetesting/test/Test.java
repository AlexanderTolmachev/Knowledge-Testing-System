package ru.spbstu.appmaths.knowledgetesting.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class Test {
    private String name;
    private List<TestQuestion> questions;


    public Test(String name, List<TestQuestion> questions) {
        this.name = name;
        this.questions = new ArrayList<TestQuestion>(questions);
    }

    public String getName() {
        return name;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

    public int getQuestionsNumber() {
        return questions.size();
    }

    public TestQuestion getQuestionByIndex(int index) {
        return questions.get(index);
    }
}
