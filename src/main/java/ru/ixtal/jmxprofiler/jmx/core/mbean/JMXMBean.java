/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.02 00:10.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.IOException;
import java.util.Optional;

public class JMXMBean {
    private final JMXMBeanAttributes attributes;
    private JMXMBeanSnapshot snapshot;

    public JMXMBean(final MBeanServerConnection mBeanServer, final ObjectName objectName) throws IOException, JMException {
        this.attributes = new JMXMBeanAttributes(mBeanServer, objectName);
        this.snapshot = null;
    }

    public Optional<JMXMBeanSnapshot> snapshot(final boolean takeEqualSnapshots) throws IOException, JMException {
        final JMXMBeanSnapshot newSnapshot = new JMXMBeanSnapshot(attributes);

        if (!takeEqualSnapshots && snapshot != null && snapshot.equals(newSnapshot)) {
            return Optional.empty();
        }

        snapshot = newSnapshot;

        return Optional.of(snapshot);
    }
}
