package ru.spbstu.appmaths.knowledgetesting.utils;

/**
 * @author Alexander Tolmachev starlight@yandex-team.ru
 *         Date: 01.06.12
 */
public class TimeConverter {
    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;

    public long convertTimeToMilliseconds(int hours, int minutes, int seconds) {
        return hours * ONE_HOUR + minutes * ONE_MINUTE + seconds * ONE_SECOND;
    }

    public long[] convertMillisecondsToTime(long milliseconds) {
        long hours = milliseconds / ONE_HOUR;
        long restMilliseconds = milliseconds - hours * ONE_HOUR;
        long minutes = restMilliseconds / ONE_MINUTE;
        restMilliseconds -= minutes * ONE_MINUTE;
        long seconds = restMilliseconds / ONE_SECOND;
        return new long[]{hours, minutes, seconds};
    }
}
