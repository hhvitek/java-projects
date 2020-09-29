package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyLocalTimeParser {

    private static Pattern patternNumericNumeric = Pattern.compile("(\\d{1,2}):(\\d{1,2})");

    public static int getSecondsFromMmss(String mmss) {
        Matcher m = patternNumericNumeric.matcher(mmss);
        if (m.matches()) {
            int minutes = Integer.parseInt(m.group(1));
            int seconds = Integer.parseInt(m.group(2));
            return seconds + minutes * 60;
        } else {
            throw new IllegalArgumentException("Cannot parse: \"" + mmss + "\"");
        }
    }

    public static int getSecondsFromHhmm(String hhmm) {
        Matcher m = patternNumericNumeric.matcher(hhmm);
        if (m.matches()) {
            int hours = Integer.parseInt(m.group(1));
            int minutes = Integer.parseInt(m.group(2));
            return hours * 60 * 60 + minutes * 60;
        } else {
            throw new IllegalArgumentException("Cannot parse: \"" + hhmm + "\"");
        }
    }
}
