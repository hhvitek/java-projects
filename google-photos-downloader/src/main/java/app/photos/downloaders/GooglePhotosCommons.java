package app.photos.downloaders;

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
import java.util.Arrays;
import java.util.Objects;

public class GooglePhotosCommons {

    // This class contains only static methods
    // should never be instantiated
    private GooglePhotosCommons() {
    }

    public static boolean isAnyStringNullOrBlank(String... args) {
        return Arrays.stream(args)
                .anyMatch(x -> Objects.isNull(x) || x.isBlank());
    }

    public static boolean areAllArgsNonNull(Object... args) {
        return Arrays.stream(args)
                .noneMatch(Objects::isNull);
    }

    public static boolean isUserCredentialsNonBlank(UserCredentials credentials) {
        if (credentials != null) {
            return !isAnyStringNullOrBlank(
                    credentials.getRefreshToken(),
                    credentials.getClientId(),
                    credentials.getClientSecret());
        }
        return false;
    }

    /**
     * Expects date-string in the format yyyy-MM-DD
     *
     * @param date
     *
     * @return
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
     * @param date
     *
     * @return
     *
     * @throws IllegalArgumentException
     * @throws DateTimeParseException
     */
    public static Date getDateFromString(String date)
            throws DateTimeParseException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dtf);
        return Date.newBuilder().setDay(localDate.getDayOfMonth())
                .setMonth(localDate.getMonthValue()).setYear(localDate.getYear()).build();
    }

    /**
     * Returns Google's type Date of present day.
     *
     * @return
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
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                timestamp.getSeconds(),
                timestamp.getNanos(),
                ZoneOffset.UTC
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(dtf);
    }

    /**
     * Returns Google's UserCredentials as an printable String.
     *
     * @param credentials
     *
     * @return
     */
    public static String credentialsToString(UserCredentials credentials) {
        if (credentials != null) {
            return String.format("{credentials: [clientId=%s, clientSecret=%s, refreshToken=%s]}",
                    credentials.getClientId(), credentials.getClientSecret(),
                    credentials.getRefreshToken());
        }
        return "{credentials: null}";
    }

    /**
     * Stores UserCredentials into the file specified by the parameter path.
     *
     * @param credentials
     * @param path
     *
     * @throws IOException
     */
    public static void storeCredentials(UserCredentials credentials, Path path)
            throws IOException {
        String accessToken = credentials.getAccessToken().getTokenValue();
        String refreshToken = credentials.getRefreshToken();
        String clientId = credentials.getClientId();
        String clientSecret = credentials.getClientSecret();

        Files.writeString(path,
                String.format(
                        "accessToken = %s%nrefreshToken = %s%nclientId = %s%nclientSecret = %s%n",
                        accessToken, refreshToken, clientId, clientSecret));
    }
}
