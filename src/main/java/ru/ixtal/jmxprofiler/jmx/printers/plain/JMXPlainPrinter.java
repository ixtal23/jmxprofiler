/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.plain;

import ru.ixtal.jmxprofiler.jmx.printers.JMXBasePrinter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import javax.management.ObjectName;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

public abstract class JMXPlainPrinter extends JMXBasePrinter {
    private StringBuilder buffer;

    protected JMXPlainPrinter(final JMXPrinterType type) {
        super(type);
    }

    @Override
    public void open() throws IOException {
        buffer = new StringBuilder();
    }

    @Override
    public void close() throws IOException {
        super.close();
        buffer = null;
    }

    abstract void print(final String s);

    @Override
    public void flush() throws IOException {
        if (buffer.length() > 0) {
            print(buffer.toString());

            buffer = new StringBuilder();
        }
    }

    @Override
    public void beginMBean(final String name, final Instant timestamp) throws Exception {
        buffer.append(timestamp).append(" ").append(name).append(":");
    }

    @Override
    public void endMBean() throws Exception {
    }

    @Override
    public void beginCompositeData(final String name) throws Exception {
        buffer.append(" {").append(name).append(":");
    }

    @Override
    public void beginCompositeData(final int index) throws Exception {
        buffer.append(" {").append(index).append(":");
    }

    @Override
    public void endCompositeData() throws Exception {
        buffer.append("}");
    }

    @Override
    public void beginTabularData(final String name) throws Exception {
        buffer.append(" {").append(name).append(":");
    }

    @Override
    public void beginTabularData(final int index) throws Exception {
        buffer.append(" {").append(index).append(":");
    }

    @Override
    public void endTabularData() throws Exception {
        buffer.append("}");
    }

    @Override
    public void beginArrayData(final String name) throws Exception {
        buffer.append(" [").append(name).append(":");
    }

    @Override
    public void beginArrayData(final int index) throws Exception {
        buffer.append(" [").append(index).append(":");
    }

    @Override
    public void endArrayData() throws Exception {
        buffer.append("]");
    }

    @Override
    public void simpleData(final String name, final Void value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Boolean value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Character value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Byte value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Short value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Integer value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Long value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Float value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final Double value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final String value) throws Exception {
        buffer.append(", ").append(name).append("=").append(value);
    }

    @Override
    public void simpleData(final String name, final Date value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final BigInteger value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final BigDecimal value) throws Exception {
        simpleData(name, value.toString());
    }

    @Override
    public void simpleData(final String name, final ObjectName value) throws Exception {
        simpleData(name, value.getCanonicalName());
    }

    @Override
    public void simpleData(final int index, final Void value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Boolean value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Character value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Byte value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Short value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Integer value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Long value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Float value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final Double value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final String value) throws Exception {
        buffer.append(", ").append(index).append(":").append(value);
    }

    @Override
    public void simpleData(final int index, final Date value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final BigInteger value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final BigDecimal value) throws Exception {
        simpleData(index, value.toString());
    }

    @Override
    public void simpleData(final int index, final ObjectName value) throws Exception {
        simpleData(index, value.toString());
    }
}
