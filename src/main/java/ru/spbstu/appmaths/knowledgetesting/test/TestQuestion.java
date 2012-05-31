package ru.spbstu.appmaths.knowledgetesting.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class TestQuestion {
    private String questionText;
    private List<String> options;
    private int answerOptionNumber;

    public TestQuestion(String questionText, List<String> options, int answerOptionNumber) {
        this.questionText = questionText;
        this.options = new ArrayList<String>(options);
        this.answerOptionNumber = answerOptionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getAnswerOptionNumber() {
        return answerOptionNumber;
    }

    public int getOptionsNumber() {
        return options.size();
    }

    public String getOptionByIndex(int index) {
        return options.get(index);
    }
}
