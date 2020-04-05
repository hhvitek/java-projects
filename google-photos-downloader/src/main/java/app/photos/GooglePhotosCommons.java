package app.photos;

import app.MyCommons;
import com.google.auth.oauth2.UserCredentials;
import com.google.protobuf.Timestamp;
import com.google.type.Date;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class GooglePhotosCommons {

    // This class contains only static methods
    // should never be instantiated
    private GooglePhotosCommons() {
        throw new UnsupportedOperationException("Not supported - this is an utility class.");
    }

    public static boolean isUserCredentialsNonBlank(UserCredentials credentials) {
        if (credentials != null) {
            return MyCommons.areAllStringsNotNullAndNotBlank(credentials.getRefreshToken(),
                                                             credentials.getClientId(),
                                                             credentials.getClientSecret()
            );
        }
        return false;
    }

    /**
     * Expects date-string in the format yyyy-MM-DD
     *
     * @param date String representation of date in the "yyyy-MM-DD" format
     *
     * @return returns true if the input String date comforts to "yyyy-MM-dd" format
     */
    public static boolean checkDateStringFormat(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            //LocalDateTime.parse(date, dtf); doesn't work
            LocalDate.parse(date, dtf);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Expects date-string in the format yyyy-MM-DD
     *
     * @param date String format of the date in the expected format "yyyy-MM-dd"
     *
     * @return Google's Date type, if cannot parse the input String throws an exception.
     *
     * @throws DateTimeParseException if the input String cannot be parsed
     */
    public static Date getDateFromString(String date)
            throws DateTimeParseException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dtf);
        return Date.newBuilder()
                   .setDay(localDate.getDayOfMonth())
                   .setMonth(localDate.getMonthValue())
                   .setYear(localDate.getYear())
                   .build();
    }

    /**
     * Returns Google's type Date of present day.
     *
     * @return Today's Google's type Date
     */
    public static Date getDateNow() {
        LocalDate localDate = LocalDate.now();
        return Date.newBuilder()
                   .setDay(localDate.getDayOfMonth())
                   .setMonth(localDate.getMonthValue())
                   .setYear(localDate.getYear())
                   .build();
    }

    public static String getStringDateFromGoogleTimestamp(Timestamp timestamp) {
        LocalDateTime dateTime =
                LocalDateTime.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(),
                                            ZoneOffset.UTC
                );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(dtf);
    }

    /**
     * Returns Google's UserCredentials as a printable String.
     *
     * @param credentials UserCredentials
     *
     * @return UserCredentials as a printable String.
     */
    public static String credentialsToString(UserCredentials credentials) {
        if (credentials != null) {
            return String.format("{credentials: [clientId=%s, clientSecret=%s, refreshToken=%s]}",
                                 credentials.getClientId(), credentials.getClientSecret(),
                                 credentials.getRefreshToken()
            );
        }
        return "{credentials: null}";
    }

    /**
     * Stores UserCredentials into the file specified by the parameter path.
     *
     * @param credentials Google's UserCredentials
     * @param path        The path to the file where the UserCredentials should be stored
     *
     * @throws IOException working with the file system...
     */
    public static void storeCredentials(UserCredentials credentials, Path path)
            throws IOException {
        String accessToken = credentials.getAccessToken()
                                        .getTokenValue();
        String refreshToken = credentials.getRefreshToken();
        String clientId = credentials.getClientId();
        String clientSecret = credentials.getClientSecret();

        Files.writeString(path, String.format(
                "accessToken = %s%nrefreshToken = %s%nclientId = %s%nclientSecret = %s%n",
                accessToken, refreshToken, clientId, clientSecret
        ));
    }

}
