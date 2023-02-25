package games.negative.framework.discord.runnable.provider;

import games.negative.framework.discord.runnable.RepeatingRunnable;
import games.negative.framework.discord.runnable.Scheduler;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class SchedulerProvider implements Scheduler {
    @NotNull
    @Override
    public RepeatingRunnable runAsync(RepeatingRunnable runnable, long delay, long period) {
        new TaskThread(runnable, delay, period).start();
        return runnable;
    }

    @NotNull
    @Override
    public RepeatingRunnable run(RepeatingRunnable runnable, long delay, long period) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Task(runnable), delay, period);
        return runnable;
    }

    @RequiredArgsConstructor
    private class TaskThread extends Thread {

        private final RepeatingRunnable runnable;
        private final long delay;
        private final long period;

        @Override
        public void run() {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Task(runnable), delay, period);
        }
    }

    @RequiredArgsConstructor
    private class Task extends TimerTask {

        private final RepeatingRunnable runnable;

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            runnable.execute();
        }
    }
}
