package games.negative.framework.discord.runnable;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the management class for {@link RepeatingRunnable}
 */
public interface Scheduler {

    /**
     * Register a {@link RepeatingRunnable}.
     * @param runnable Runnable to be registered.
     * @param delay Delay before the runnable starts.
     * @param period Period pattern of which the runnable repeats.
     * @return {@link RepeatingRunnable} instance.
     * @deprecated Use {@code #run(runnable, delay, period)} instead.
     */
    @NotNull
    @Deprecated
    default RepeatingRunnable runRepeatingRunnable(@NotNull RepeatingRunnable runnable, long delay, long period) {
        return run(runnable, delay, period);
    }

    @NotNull
    RepeatingRunnable runAsync(RepeatingRunnable runnable, long delay, long period);

    @NotNull
    RepeatingRunnable run(RepeatingRunnable runnable, long delay, long period);
}
