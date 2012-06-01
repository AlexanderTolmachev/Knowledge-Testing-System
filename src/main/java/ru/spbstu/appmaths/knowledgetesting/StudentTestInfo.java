package ru.spbstu.appmaths.knowledgetesting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 01.06.12
 */
public class StudentTestInfo {
    private List<AnswerType> answers;
    private int currentQuestionNumber;

    public StudentTestInfo(int questionsNumber) {
        answers = new ArrayList<AnswerType>();
        for (int i = 0; i < questionsNumber; i++) {
            answers.add(AnswerType.SKIPPED);
        }
        currentQuestionNumber = 0;
    }

    public List<AnswerType> getAnswers() {
        return answers;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentAnswer(AnswerType answer) {
        answers.set(currentQuestionNumber, answer);
        currentQuestionNumber++;
    }
}
