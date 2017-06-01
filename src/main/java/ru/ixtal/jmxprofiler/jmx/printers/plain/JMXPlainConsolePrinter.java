/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.plain;

import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

public final class JMXPlainConsolePrinter extends JMXPlainPrinter {
    public JMXPlainConsolePrinter() {
        super(JMXPrinterType.plain_console);
    }

    @Override
    public void print(final String s) {
        System.out.println(s);
    }
}
