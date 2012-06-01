package ru.spbstu.appmaths.knowledgetesting.exceptions;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 01.06.12
 */
public class TestException extends Exception {
    public TestException() {
    }

    public TestException(String s) {
        super(s);
    }

    public TestException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TestException(Throwable throwable) {
        super(throwable);
    }
}
