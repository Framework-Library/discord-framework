package games.negative.framework.discord.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@UtilityClass
public class Validate {

    public void notNull(@Nullable Object object, @Nullable String message) {
        if (object == null) {
            throw new NullPointerException(Objects.requireNonNullElse(message, "Object cannot be null"));
        }
    }

    public void notEmpty(@Nullable String string, @Nullable String message) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException(Objects.requireNonNullElse(message, "String cannot be empty"));
        }
    }

    public void isTrue(boolean condition, @Nullable String message) {
        if (!condition) {
            throw new IllegalArgumentException(Objects.requireNonNullElse(message, "Condition must be true"));
        }
    }

    public void isFalse(boolean condition, @Nullable String message) {
        if (condition) {
            throw new IllegalArgumentException(Objects.requireNonNullElse(message, "Condition must be false"));
        }
    }

}
