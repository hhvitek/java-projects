package app;

import java.util.Arrays;
import java.util.Objects;

public final class MyCommons {

    private MyCommons() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean areAllStringsNotNullAndNotBlank(String... args) {
        return Arrays.stream(args)
                     .noneMatch(x -> Objects.isNull(x) || x.isBlank());
    }

    public static boolean areAllArgsNonNull(Object... args) {
        return Arrays.stream(args)
                     .noneMatch(Objects::isNull);
    }

}
