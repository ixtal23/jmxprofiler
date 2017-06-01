/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.json;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import ru.ixtal.jmxprofiler.jmx.printers.JMXBasePrinter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import javax.management.ObjectName;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

@SuppressWarnings("resource")
public abstract class JMXJSONPrinter extends JMXBasePrinter {
    protected XContentBuilder contentBuilder;

    private static final String JSON_FIELD_TIMESTAMP = "timestamp";

    protected JMXJSONPrinter(final JMXPrinterType type) {
        super(type);
    }

    @Override
    public void open() throws IOException {
        contentBuilder = XContentFactory.jsonBuilder();
    }

    @Override
    public void close() throws IOException {
        super.close();
        contentBuilder = null;
    }

    abstract void print(final BytesReference bytes, final XContentType contentType);

    @Override
    public void flush() throws IOException {
        final BytesReference bytes = contentBuilder.bytes();
        if (bytes.length() > 0) {
            print(bytes, contentBuilder.contentType());
        }

        contentBuilder.close();
        contentBuilder = XContentFactory.jsonBuilder();
    }

    @Override
    public void beginMBean(final String name, final Instant timestamp) throws Exception {
        contentBuilder.startObject();
        contentBuilder.field(name);
        contentBuilder.startObject();
        contentBuilder.field(JSON_FIELD_TIMESTAMP, timestamp.toEpochMilli());
    }

    @Override
    public void endMBean() throws Exception {
        contentBuilder.endObject();
        contentBuilder.endObject();
    }

    @Override
    public void beginCompositeData(final String name) throws Exception {
        contentBuilder.field(name);
        contentBuilder.startObject();
    }

    @Override
    public void beginCompositeData(final int index) throws Exception {
        contentBuilder.startObject();
    }

    @Override
    public void endCompositeData() throws Exception {
        contentBuilder.endObject();
    }

    @Override
    public void beginTabularData(final String name) throws Exception {
        contentBuilder.field(name);
        contentBuilder.startObject();
    }

    @Override
    public void beginTabularData(final int index) throws Exception {
        contentBuilder.startObject();
    }

    @Override
    public void endTabularData() throws Exception {
        contentBuilder.endObject();
    }

    @Override
    public void beginArrayData(final String name) throws Exception {
        contentBuilder.field(name);
        contentBuilder.startArray();
    }

    @Override
    public void beginArrayData(final int index) throws Exception {
        contentBuilder.startArray();
    }

    @Override
    public void endArrayData() throws Exception {
        contentBuilder.endArray();
    }

    @Override
    public void simpleData(final String name, final Void value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Boolean value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Character value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Byte value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Short value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Integer value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Long value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Float value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Double value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final String value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final Date value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final BigInteger value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final BigDecimal value) throws Exception {
        contentBuilder.field(name, value);
    }

    @Override
    public void simpleData(final String name, final ObjectName value) throws Exception {
        contentBuilder.field(name, value.getCanonicalName());
    }

    @Override
    public void simpleData(final int index, final Void value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Boolean value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Character value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Byte value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Short value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Integer value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Long value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Float value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Double value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final String value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final Date value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final BigInteger value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final BigDecimal value) throws Exception {
        contentBuilder.value(value);
    }

    @Override
    public void simpleData(final int index, final ObjectName value) throws Exception {
        contentBuilder.value(value.getCanonicalName());
    }
}
