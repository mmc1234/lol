/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmc1234.lol.base;

import com.google.common.base.Preconditions;

public interface Timer {
    void tryRun();
    boolean shouldRun();

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
        public boolean shouldRun() {
            return (System.currentTimeMillis() - time) >= timeInterval;
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
