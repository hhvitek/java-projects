package timers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyDateTimeParser {

    private static Pattern patternNumericNumeric = Pattern.compile("(\\d{2}):(\\d{2})");

    public static int getSecondsFromDelay(String delay) {
        Matcher m = patternNumericNumeric.matcher(delay);
        if (m.matches()) {
            int hours = Integer.parseInt(m.group(1));
            int minutes = Integer.parseInt(m.group(2));
            return hours * 60 * 60 + minutes * 60;
        }
        return -1;
    }

    public static int getSecondsBetweenNowToExactTime(String exactTime) {
        int exactTimeToSeconds = getSecondsFromDelay(exactTime);
        if (exactTimeToSeconds >= 0) {
            Date now = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String nowString = dateFormat.format(now);
            int nowToSeconds = getSecondsFromDelay(nowString);
            if (nowToSeconds >= 0) {
                if (exactTimeToSeconds >= nowToSeconds) {
                    return exactTimeToSeconds - nowToSeconds;
                } else {
                    return exactTimeToSeconds + (24 * 60 * 60 - nowToSeconds);
                }
            }
        }
        return -1;
    }

    public static String getStringHHmmFromDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String exactTime = dateFormat.format(date);
        return exactTime;
    }

    public static String getHHmmFromSeconds(int seconds) {
        int hours = seconds / (60 * 60);
        int remainingSeconds = seconds % (60 * 60);
        int minutes = remainingSeconds / 60;

        return String.format("%02d:%02d", hours, minutes);
    }

    public static String getHHmmSSFromSeconds(int seconds) {
        int hours = seconds / (60 * 60);
        int remainingSeconds = seconds % (60 * 60);
        int minutes = remainingSeconds / 60;
        remainingSeconds = remainingSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public static String getSumDateAndSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return getStringHHmmFromDate(calendar.getTime());
    }
}
