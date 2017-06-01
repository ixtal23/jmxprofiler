/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.plain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

public final class JMXPlainLogPrinter extends JMXPlainPrinter {
    private static final Logger log = LoggerFactory.getLogger(JMXPlainLogPrinter.class);

    public JMXPlainLogPrinter() {
        super(JMXPrinterType.plain_log);
    }

    @Override
    public void print(final String s) {
        log.info(s);
    }
}
