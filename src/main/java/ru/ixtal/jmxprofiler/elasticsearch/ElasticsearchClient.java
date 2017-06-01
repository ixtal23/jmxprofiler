/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.16 01:31.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class ElasticsearchClient implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchClient.class);

    private final TransportClient client;

    @SuppressWarnings({"resource", "IOResourceOpenedButNotSafelyClosed"})
    public ElasticsearchClient(final String host, final int port) throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(host), port)
                );
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    public void index(
            final String index,
            final String type,
            final BytesReference bytes,
            final XContentType contentType
    ) {
        log.debug("Elasticsearch index with name '{}', type '{}'", index, type);

        final IndexResponse response = client.prepareIndex(index, type)
                .setSource(bytes, contentType)
                .get();

        if (response.status() == RestStatus.CREATED) {
            log.debug("Elasticsearch index response: {}", response);
        } else {
            log.error("Elasticsearch failed to index, response: {}", response);
        }
    }
}
