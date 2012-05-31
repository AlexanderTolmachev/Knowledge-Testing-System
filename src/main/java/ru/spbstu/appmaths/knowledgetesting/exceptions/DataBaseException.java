package ru.spbstu.appmaths.knowledgetesting.exceptions;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 31.05.12
 */
public class DataBaseException extends Exception {
    public DataBaseException() {
        super();
    }

    public DataBaseException(String s) {
        super(s);
    }

    public DataBaseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DataBaseException(Throwable throwable) {
        super(throwable);
    }
}
