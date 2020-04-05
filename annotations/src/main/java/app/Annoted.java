package app;

import org.jetbrains.annotations.NotNull;

public class Annoted {

    public static String concatenate(@NotNull String first, @NotNull String second) {
        //return null;
        return first.concat(second);
    }
}
