/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.23 19:14.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import javax.management.Descriptor;
import javax.management.JMX;
import javax.management.MBeanAttributeInfo;
import java.util.Optional;

public final class JMXMBeanAttribute {
    private final String name;
    private final Object openType;
    private final Object originalType;

    public JMXMBeanAttribute(final MBeanAttributeInfo attribute) {
        name = attribute.getName();

        final Descriptor descriptor = attribute.getDescriptor();
        openType = descriptor.getFieldValue(JMX.OPEN_TYPE_FIELD);
        originalType = descriptor.getFieldValue(JMX.ORIGINAL_TYPE_FIELD);
    }

    public String name() {
        return name;
    }

    public Optional<Object> openType() {
        return Optional.of(openType);
    }

    public Optional<Object> originalType() {
        return Optional.of(originalType);
    }

    @Override
    public String toString() {
        return name + ":" + openType().orElse("Unknown") + ":" + originalType().orElse("Unknown");
    }
}
