package ru.spbstu.appmaths.knowledgetesting.exceptions;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class DataBaseDriverNotFoundException extends Exception {
    public DataBaseDriverNotFoundException() {
        super();
    }

    public DataBaseDriverNotFoundException(String s) {
        super(s);
    }

    public DataBaseDriverNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataBaseDriverNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
