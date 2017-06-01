/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.12 21:00.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.json;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

public final class JMXJSONLogPrinter extends JMXJSONPrinter {
    private static final Logger log = LoggerFactory.getLogger(JMXJSONLogPrinter.class);

    public JMXJSONLogPrinter() {
        super(JMXPrinterType.json_log);
    }

    @Override
    void print(final BytesReference bytes, final XContentType contentType) {
        log.info(bytes.utf8ToString());
    }
}
