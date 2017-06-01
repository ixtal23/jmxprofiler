/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.13 23:09.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WorkThread {
    private static final Logger log = LoggerFactory.getLogger(WorkThread.class);

    final Thread thread;
    final ShutdownHook shutdownHook;

    public WorkThread(final String name, final Runnable runnable) {
        thread = new Thread(runnable, name);

        shutdownHook = new ShutdownHook(() -> {
            log.debug("Shutdown '{}' work thread", thread.getName());
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                // Ignore this exception.
            }
        });
    }

    public WorkThread start() {
        log.debug("'{}' work thread started", thread.getName());
        thread.start();
        return this;
    }

    public void waitForCompletion() throws InterruptedException {
        log.debug("Wait for completion of '{}' work thread", thread.getName());
        thread.join();
    }
}
