package pl.kemot.scrum.scrumteczki2.utils;

/**
 * Created by Tomek on 01.01.14.
 */
public class TimeUtils {
    private static final long VB_ZERO_TIME = 2209161600000L;
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_HOUR = 60 * 60;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int SECONDS_IN_DAY = 3600 * 24;

    public static long visualBasicTimeToMilliseconds(long windowsTime) {
        return windowsTime + VB_ZERO_TIME;
    }
    public static long visualBasicTimeToSeconds(long windowsTime) {
        return visualBasicTimeToMilliseconds(windowsTime) / MILLISECONDS_IN_SECOND;
    }
    public static String timeInSecondsToEstimatedTime(long timeInSeconds) {
        if (timeInSeconds < 0) {
            throw new IllegalArgumentException("Estymowany czas musi być większy od 0! Sekundy = " + timeInSeconds);
        }
        String hours = String.valueOf(timeInSeconds / SECONDS_IN_HOUR);
        String minutes = String.valueOf((timeInSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE);
        String seconds = String.valueOf(timeInSeconds % SECONDS_IN_MINUTE);
        return hours + ":" + StringUtils.zerofill(Integer.valueOf(minutes), 2)
                + ":" + StringUtils.zerofill(Integer.valueOf(seconds), 2);
    }
    public static long changeFractionOfDayToSeconds(double numericValue) {
        return Math.round(numericValue * SECONDS_IN_DAY);
    }
}
