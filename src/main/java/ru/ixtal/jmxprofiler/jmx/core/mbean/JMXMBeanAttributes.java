/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.23 19:14.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import javax.management.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class JMXMBeanAttributes {
    private final MBeanServerConnection mBeanServer;
    private final ObjectName objectName;
    private final Map<String, JMXMBeanAttribute> attributes;
    private final String[] attributeNames;

    public JMXMBeanAttributes(final MBeanServerConnection mBeanServer, final ObjectName objectName) throws IOException, JMException {
        this.mBeanServer = mBeanServer;
        this.objectName = objectName;

        attributes = new LinkedHashMap<>();

        final MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
        for (final MBeanAttributeInfo attribute : mBeanInfo.getAttributes()) {
            if (attribute.isReadable()) {
                attributes.put(attribute.getName(), new JMXMBeanAttribute(attribute));
            } else {
                // Ignore not readable attributes.
            }
        }

        // Cache the names of attributes in order to optimize values() method.
        attributeNames = attributes.keySet().toArray(new String[0]);
    }

    public Optional<Object> valueOf(final String attributeName) throws IOException, JMException {
        return attributes.containsKey(attributeName) ?
                Optional.of(mBeanServer.getAttribute(objectName, attributeName)) :
                Optional.empty();
    }

    public String name() throws IOException, JMException {
        return valueOf("Name").map(o -> (String) o).orElseGet(objectName::toString);
    }

    public AttributeList values() throws IOException, JMException {
        return mBeanServer.getAttributes(objectName, attributeNames);
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        attributes.forEach((name, attribute) -> {
            if (s.length() > 0) {
                s.append(", ");
            }

            s.append(attribute);
        });
        return s.toString();
    }
}
