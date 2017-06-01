/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.29 00:58.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.threads;

public final class ShutdownHook extends Thread {
    private final Runnable callback;

    public ShutdownHook(final Runnable callback) {
        this.callback = callback;
        Runtime.getRuntime().addShutdownHook(this);
    }

    @Override
    public void run() {
        try {
            callback.run();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
