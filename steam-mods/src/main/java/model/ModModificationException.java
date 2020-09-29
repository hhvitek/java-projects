package model;

import org.jetbrains.annotations.NotNull;

public class ModModificationException extends RuntimeException {

    public ModModificationException(@NotNull String message) {
        super(message);
    }

    public ModModificationException(@NotNull Throwable throwable) {
        super(throwable);
    }

    public ModModificationException(@NotNull String message, @NotNull Throwable throwable) {
        super(message, throwable);
    }
}
