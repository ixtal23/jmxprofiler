/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.01 00:30.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.configuration.Configuration;

public abstract class Action implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Action.class);

    protected final ActionType type;
    protected final Configuration configuration;

    protected Action(final ActionType type, final Configuration configuration) {
        this.type = type;
        this.configuration = configuration;
    }

    @Override
    public final String toString() {
        return type.toString();
    }

    @Override
    public final void run() {
        log.debug("Execute action '{}'", this);

        try {
            start();
        } catch (final Exception e) {
            log.error("Action '" + this + "' does not start", e);
            return;
        }

        try {
            execute();
        } catch (final Exception e) {
            log.error("Action '" + this + "' failed", e);
        } finally {
            try {
                finish();
            } catch (final Exception e) {
                log.error("Action '" + this + "' finished incorrectly", e);
            }
        }

        log.debug("Action '{}' was executed", this);
    }

    protected abstract void start() throws Exception;
    protected abstract void execute() throws Exception;
    protected abstract void finish() throws Exception;
}
