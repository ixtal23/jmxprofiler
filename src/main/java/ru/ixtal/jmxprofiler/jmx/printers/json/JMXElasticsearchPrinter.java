/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.15 11:06.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.json;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import ru.ixtal.jmxprofiler.elasticsearch.ElasticsearchClient;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import java.io.IOException;

public final class JMXElasticsearchPrinter extends JMXJSONPrinter {
    private final String host;
    private final int port;
    private final String indexName;
    private final String indexType;
    private ElasticsearchClient client;

    public JMXElasticsearchPrinter(
            final String host,
            final int port,
            final String indexName,
            final String indexType
    ) {
        super(JMXPrinterType.elasticsearch);
        this.host = host;
        this.port = port;
        this.indexName = indexName;
        this.indexType = indexType;
    }

    @Override
    public void open() throws IOException {
        super.open();
        client = new ElasticsearchClient(host, port);
    }

    @Override
    public void close() throws IOException {
        super.close();
        client.close();
        client = null;
    }

    @Override
    void print(final BytesReference bytes, final XContentType contentType) {
        client.index(indexName, indexType, bytes, contentType);
    }
}
