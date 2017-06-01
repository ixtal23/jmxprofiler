/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.29 00:46.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBean;

import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JMXConnection implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(JMXConnection.class);

    private static final String RMI_URI_FORMAT = "service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi";
    private static final String ANY_URI_FORMAT = "service:jmx:%s://%s:%d";

    private JMXConnector jmxConnector;
    private MBeanServerConnection mBeanServerConnection;

    public JMXConnection(final String host, final int port) throws IOException {
        open(host, port);
    }

    @SuppressWarnings("resource")
    private void open(final String host, final int port) throws IOException {
        log.debug("Open JMX connection to {}:{}", host, port);

        @SuppressWarnings("AccessOfSystemProperties")
        final String protocol = System.getProperty("jmx.service.protocol", "rmi");

        final String uri = "rmi".equals(protocol) ?
                String.format(RMI_URI_FORMAT, host, port) :
                String.format(ANY_URI_FORMAT, protocol, host, port);

        try {
            final JMXServiceURL jmxURL = new JMXServiceURL(uri);
            jmxConnector = JMXConnectorFactory.connect(jmxURL);
            mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        } catch (IOException e) {
            throw new IOException("Failed to create JMX connection: " + e.toString(), e);
        }
    }

    @Override
    public void close() {
        log.debug("Close JMX connection");

        try {
            jmxConnector.close();
        } catch (IOException e) {
            log.error("Failed to close JMX connection", e);
        }
    }

    public List<JMXMBean> queryMBeans(final ObjectName objectName) throws IOException, JMException {
        log.debug("Query JMX managed beans for name {}", objectName.getCanonicalName());

        final List<JMXMBean> result = new ArrayList<>();

        for (final ObjectName o : mBeanServerConnection.queryNames(objectName, null)) {
            log.debug("Use MBean {}", o.getCanonicalName());
            result.add(new JMXMBean(mBeanServerConnection, o));
        }

        return result;
    }
}
