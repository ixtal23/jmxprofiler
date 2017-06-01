/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.12 20:59.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.json;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

public final class JMXJSONConsolePrinter extends JMXJSONPrinter {
    public JMXJSONConsolePrinter() {
        super(JMXPrinterType.json_console);
    }

    @Override
    void print(final BytesReference bytes, final XContentType contentType) {
        System.out.println(bytes.utf8ToString());
    }
}
