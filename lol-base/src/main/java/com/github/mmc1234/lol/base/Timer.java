package com.github.mmc1234.lol.base;

import com.google.common.base.Preconditions;

public interface Timer {
    void tryRun();

    static Timer newSystem(Runnable task, long time, boolean resetOnRun) {
        return new SystemTimer(task, time, resetOnRun);
    }

    static Timer newSystem(Runnable task, long time) {
        return new SystemTimer(task, time, false);
    }

    class SystemTimer implements Timer {
        private long timeInterval;
        private long time;
        private boolean resetOnRun;
        private Runnable task;

        public SystemTimer(Runnable task, long time, boolean resetOnRun) {
            this.time = System.currentTimeMillis();
            this.timeInterval = time;
            this.task = task;
            this.resetOnRun = resetOnRun;
            Preconditions.checkNotNull(task);
        }

        @Override
        public void tryRun() {
            var delta = System.currentTimeMillis() - time;
            if (delta >= timeInterval) {
                if (resetOnRun) {
                    time += delta;
                } else {
                    time += timeInterval;
                }
                task.run();
            }
        }
    }
}
