/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:02.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanVisitor;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public interface JMXPrinter extends Flushable, Closeable, JMXMBeanVisitor {
    void open() throws IOException;
}
