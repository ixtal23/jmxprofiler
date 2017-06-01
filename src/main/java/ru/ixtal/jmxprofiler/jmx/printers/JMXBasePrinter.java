/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class JMXBasePrinter implements JMXPrinter {
    private static final Logger log = LoggerFactory.getLogger(JMXBasePrinter.class);

    protected JMXBasePrinter(final JMXPrinterType type) {
        log.debug("Printer: {}", type.toString());
    }

    @Override
    public void close() throws IOException {
        flush();
    }
}
