package games.negative.framework.discord.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class Validate {

    public void notNull(@Nullable Object object, @Nullable String message) {
        if (object == null) {
            if (message == null)
                throw new NullPointerException("Object cannot be null");
             else
                throw new NullPointerException(message);
        }
    }

    public void notEmpty(@Nullable String string, @Nullable String message) {
        if (string == null || string.isEmpty()) {
            if (message == null)
                throw new IllegalArgumentException("String cannot be empty");
            else
                throw new IllegalArgumentException(message);
        }
    }

    public void isTrue(boolean condition, @Nullable String message) {
        if (!condition) {
            if (message == null)
                throw new IllegalArgumentException("Condition must be true");
            else
                throw new IllegalArgumentException(message);
        }
    }

    public void isFalse(boolean condition, @Nullable String message) {
        if (condition) {
            if (message == null)
                throw new IllegalArgumentException("Condition must be false");
            else
                throw new IllegalArgumentException(message);
        }
    }

}
