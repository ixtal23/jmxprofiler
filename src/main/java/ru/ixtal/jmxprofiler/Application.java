/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.29 00:56.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.actions.ActionExecutor;
import ru.ixtal.jmxprofiler.configuration.CommandLineHelpException;
import ru.ixtal.jmxprofiler.configuration.Configuration;

public final class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        log.debug("{}", new Version("JMXProfiler"));
        log.debug("Application started");

        try {
            new ActionExecutor(new Configuration(args)).execute();
        } catch (final CommandLineHelpException e) {
            e.printHelp();
        } catch (final Exception e) {
            log.error("Application failed", e);
        }

        log.debug("Application finished");
    }
}
