package app.photos.downloaders;

import com.google.auth.oauth2.UserCredentials;
import com.google.protobuf.Timestamp;
import com.google.type.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

class GooglePhotosCommonsTest {

    @Test
    void isAnyArgNullOrBlank_bothArgsNotNull() {

        String arg1 = "hello";
        String arg2 = "world";

        Assertions.assertFalse(GooglePhotosCommons.isAnyStringNullOrBlank(arg1, arg2));
    }

    @Test
    void isAnyArgNullOrBlank_oneArgIsNull() {

        String arg1 = null;
        String arg2 = "world";

        Assertions.assertTrue(GooglePhotosCommons.isAnyStringNullOrBlank(arg1, arg2));
    }

    @Test
    void isAnyArgNullOrBlank_oneArgIsBlank() {

        String arg1 = "     ";
        String arg2 = "world";

        Assertions.assertTrue(GooglePhotosCommons.isAnyStringNullOrBlank(arg1, arg2));
    }

    @Test
    void isUserCredentialsNonBlank_allArgsNonNull() {
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId("clientId")
                .setClientSecret("clientSecret")
                .setRefreshToken("refreshToken")
                .build();

        Assertions.assertTrue(GooglePhotosCommons.isUserCredentialsNonBlank(credentials));
    }

    @Test
    void isUserCredentialsNonBlank_oneArgIsBlank() {
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId("clientId")
                .setClientSecret("clientSecret")
                .setRefreshToken("  ")
                .build();

        Assertions.assertFalse(GooglePhotosCommons.isUserCredentialsNonBlank(credentials));
    }

    @Test
    void checkDateStringFormat_correctFormat() {
        String input = "2001-01-01";
        Assertions.assertTrue(GooglePhotosCommons.checkDateStringFormat(input));
    }

    @Test
    void checkDateStringFormat_incorrectFormat() {
        String input = "01.01.2001";
        Assertions.assertFalse(GooglePhotosCommons.checkDateStringFormat(input));
    }

    @Test
    void getDateFromString_correctFormat() {
        String input = "2001-01-01";
        Date expected = Date.newBuilder()
                .setYear(2001)
                .setMonth(1)
                .setDay(1)
                .build();

        Assertions.assertEquals(expected, GooglePhotosCommons.getDateFromString(input));
    }

    @Test
    void getDateFromString_incorrectFormat_throwsException() {
        String input = "01.01.2001";
        Date expected = Date.newBuilder()
                .setYear(2001)
                .setMonth(1)
                .setDay(1)
                .build();

        Assertions.assertThrows(
                DateTimeParseException.class,
                () -> GooglePhotosCommons.getDateFromString(input)
        );
    }

    @Test
    void getStringDateFromGoogleTimestamp() {

        int todaySeconds = 1572903292;
        int todayNanos = 0;
        String expected = "2019-11-04";

        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(todaySeconds)
                .setNanos(todayNanos)
                .build();

        Assertions.assertEquals(
                expected,
                GooglePhotosCommons.getStringDateFromGoogleTimestamp(timestamp)
        );
    }


}
