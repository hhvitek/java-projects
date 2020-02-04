package time;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;

public class Time {

    /**
     * Milliseconds:
     * int: -2,147,483,648 2,147,483,647 -> / 1000 s / 60 min / 60 h / 24 days /
     * max cca 24 days can be stored
     * <p>
     * Seconds:
     * int: -2,147,483,648 2,147,483,647 -> / 60 min / 60 h / 24 days
     * max ccc 24000 days can be stored
     * <p>
     * The second's precision is sufficient for this program.
     **/
    int seconds;

    private Time() {
        throw new UnsupportedOperationException();
    }

    private Time(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Copy constructor
     * @param other
     */
    private Time(Time other) {
        this.seconds = other.seconds;
    }

    /**
     * Returns true if a string is a valid time-string such as: "HH:mm:ss"
     * @param time
     * @return
     */
    public static boolean isValidTimeString(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch(DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * Obtains an instance of Time from a text string such as 10:15.
     * The string must represent a valid time and is parsed using DateTimeFormatter.ISO_LOCAL_TIME.
     * @param time
     * @return the parsed Time instance
     * @throws TimeParseException
     */
    public static Time parse(String time) throws TimeParseException {
        try {
            LocalTime localTime = LocalTime.parse(time);
            int seconds = localTime.toSecondOfDay();
            return new Time(seconds);
        } catch(DateTimeParseException ex) {
            throw new TimeParseException(ex);
        }
    }

    /**
     * Obtains an instance of Time from Java's Duration
     * @param duration
     * @return
     */
    public static Time from(Duration duration) {
        int seconds = (int) duration.toSeconds();
        return fromSeconds(seconds);
    }

    /**
     * Obtains an instance of Time from Java's LocalTime
     * @param localTime
     * @return
     */
    public static Time from(LocalTime localTime) {
        int seconds = localTime.toSecondOfDay();
        return fromSeconds(seconds);
    }

    /**
     * Obtains an instance of Time from number of seconds
     * @param seconds
     * @return
     */
    public static Time fromSeconds(int seconds) {
        return new Time(seconds);
    }

    public static Time between(Time from, Time to) {
        throw new UnsupportedOperationException();
    }

    public Time addTime(Time other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sums two Time instances and returns the result.
     * @param other
     * @return
     */
    public Time addDuration(Time other) {
        return addSeconds(other.seconds);
    }

    public Time addLocalTime(LocalTime localTime) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a Java's Duration to this variable and
     * returns a new Time
     * @param duration
     * @return Sum of this Time and Duration parameter
     */
    public Time addDuration(Duration duration) {
        int seconds = (int) duration.toSeconds();
        return addSeconds(seconds);
    }

    /**
     * Adds seconds to this variable and
     * returns a new Time
     * @param seconds
     * @return
     */
    public Time addSeconds(int seconds) {
        this.seconds += seconds;
        return new Time(this);
    }

    /**
     * Returns true it this Time is before the other time.
     * @param other
     * @return
     */
    public boolean isBefore(Time other) {
        if (this.seconds < other.seconds) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the parameter other is before this Time.
     * @param other
     * @return
     */
    public boolean isAfter(Time other) {
        return !isBefore(other);
    }

    /**
     * Converts this Time to a Java's LocalTime
     * @return
     */
    public LocalTime getLocalTime() {
        return LocalTime.ofSecondOfDay(seconds);
    }

    /**
     * Converts this Time to a Java's Duration
     * @return
     */
    public Duration getDuration() {
        return Duration.ofSeconds(seconds);
    }

    public int getSecondsPart() {
        return (seconds) % 60;
    }

    public int getMinutesPart() {

        return (seconds / 60) % 60;
    }

    public int getHoursPart() {
        return seconds / ( 60 * 60 );
    }

    /**
     * Converts this Time to a seconds.
     * @return
     */
    public int toSeconds() {
        return seconds;
    }

    public int toMinutes() {
        return toSeconds() * 60;
    }

    public int toHours() {
        return toMinutes() * 60;
    }

    /**
     * Converts Time to a time-string in the HH:mm:ss format.
     * @return
     */
    @Override
    public String toString() {
        return toStringHHmmSS();
    }

    public String toString(String format) throws IllegalArgumentException, UnsupportedTemporalTypeException {
        return getLocalTime().format(DateTimeFormatter.ofPattern(format));
    }

    public String toStringHHmm() {
        return toString("HH:mm");
    }

    public String toStringHHmmSS() {
        return toString("HH:mm:ss");
    }


}
