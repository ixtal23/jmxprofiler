/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.23 22:20.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanFilter;

import javax.management.AttributeList;
import javax.management.JMException;
import java.io.IOException;
import java.time.Instant;

public final class JMXMBeanSnapshot {
    private final Instant timestamp;
    private final String name;
    private final AttributeList attributes;

    public JMXMBeanSnapshot(final JMXMBeanAttributes attributes) throws IOException, JMException {
        this.timestamp = Instant.now();
        this.name = attributes.name();
        this.attributes = attributes.values();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if ((o == null) || (o.getClass() != this.getClass())) {
            return false;
        }

        final JMXMBeanSnapshot that = (JMXMBeanSnapshot) o;

        return new JMXMBeanComparator(this.attributes).compareTo(that.attributes);
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        attributes.forEach((attribute) -> {
            if (s.length() > 0) {
                s.append(", ");
            }

            s.append(attribute);
        });

        return timestamp + " " + name + ": " + s;
    }

    public void traverse(final JMXMBeanVisitor visitor, final JMXMBeanFilter filter) throws Exception {
        new JMXMBeanTraverser(visitor, filter, timestamp, name, attributes).traverse();
    }
}
