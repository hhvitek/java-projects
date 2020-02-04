package time;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    @Test
    void isValidTimeString_InvalidInput_ReturnsFalse() {
        String invalidInput = "65:25";
        Assertions.assertFalse(Time.isValidTimeString(invalidInput));
    }

    @Test
    void isValidTimeString_ValidInput_ReturnsTrue() {
        String validInput = "23:25";
        Assertions.assertTrue(Time.isValidTimeString(validInput));
    }

    @Test
    void parse_Invalid_Throws() {
        String invalidInput = "65:25";
        Assertions.assertThrows(TimeParseException.class, () -> Time.parse(invalidInput));
    }

    @Test
    void parse_Valid_NotThrow() {
        String validInput = "23:25";
        Assertions.assertDoesNotThrow(() -> Time.parse(validInput));
    }

    @Test
    void testFroms() {

        int seconds = 9999;
        Duration duration = Duration.ofSeconds(seconds);
        Time timeDuration = Time.from(duration);

        LocalTime localTime = LocalTime.ofSecondOfDay(seconds);
        Time timeLocalTime = Time.from(localTime);

        Assertions.assertEquals(localTime.toSecondOfDay(), timeDuration.toSeconds());
    }

    @Test
    void fromSeconds() {
        toSeconds();
    }

    @Test
    void between() {

    }

    @Test
    void addTime() {

    }

    @Test
    void addDuration() {

    }

    @Test
    void addLocalTime() {

    }

    @Test
    void testAddDuration() {

    }

    @Test
    void addSeconds() {
        int seconds = 9999;
        int additionalSeconds = 21;

        Time time = Time.fromSeconds(seconds);
        Time sumTime = time.addSeconds(additionalSeconds);

        Assertions.assertEquals(seconds + additionalSeconds, sumTime.toSeconds());
    }

    @Test
    void isBefore_TrueAndFalse() {
        Time timeBefore = Time.fromSeconds(4321);
        Time timeAfter = Time.fromSeconds(9876);

        Assertions.assertTrue(timeBefore.isBefore(timeAfter));
        Assertions.assertFalse(timeAfter.isBefore(timeBefore));
    }

    @Test
    void isAfter_FalseAndTrue() {
        Time timeBefore = Time.fromSeconds(4321);
        Time timeAfter = Time.fromSeconds(9876);

        Assertions.assertFalse(timeBefore.isAfter(timeAfter));
        Assertions.assertTrue(timeAfter.isAfter(timeBefore));
    }

    @Test
    void getSecondsPart() {
        int seconds = 9999; //02:46:39
        Time time = Time.fromSeconds(seconds);

        int expected = Duration.ofSeconds(seconds).toSecondsPart();

        Assertions.assertEquals(expected, time.getSecondsPart());
    }

    @Test
    void getMinutesPart() {

        int seconds = 9999; //02:46:39
        Time time = Time.fromSeconds(seconds);

        int expected = Duration.ofSeconds(seconds).toMinutesPart();

        Assertions.assertEquals(expected, time.getMinutesPart());
    }

    @Test
    void getHoursPart() {
        int seconds = 9999;//02:46:39
        Time time = Time.fromSeconds(seconds);

        int expected = Duration.ofSeconds(seconds).toHoursPart();

        Assertions.assertEquals(expected, time.getHoursPart());
    }

    @Test
    void toSeconds() {
        int seconds = 9999;
        Time time = Time.fromSeconds(seconds);
        Assertions.assertEquals(time.toSeconds(), seconds);
    }

    @Test
    void testToString_InvalidFormat_Throws() {
        int seconds = 9999;
        Time time = Time.fromSeconds(seconds);

        Assertions.assertThrows(UnsupportedTemporalTypeException.class, () -> time.toString("HH:MM:ss"));
    }

    @Test
    void testToString_ValidFormatHHmmSS() {
        int seconds = 9999;
        Time time = Time.fromSeconds(seconds);

        String expected = "02:46:39";
        String actual = time.toString("HH:mm:ss");

        Assertions.assertEquals(expected, actual);
    }
}
