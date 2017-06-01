/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.03 22:52.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanFilter;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;
import javax.management.openmbean.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public final class JMXMBeanTraverser {
    private static final Logger log = LoggerFactory.getLogger(JMXMBeanTraverser.class);

    private final JMXMBeanVisitor visitor;
    private final JMXMBeanFilter filter;
    private final Instant timestamp;
    private final String name;
    private final AttributeList attributes;
    private final JMXMBeanPathTrace pathTrace;

    private static final String TABULAR_ROW_KEY_SEPARATOR = ":";

    public JMXMBeanTraverser(final JMXMBeanVisitor visitor,
                             final JMXMBeanFilter filter,
                             final Instant timestamp,
                             final String name,
                             final AttributeList attributes) {
        this.visitor = visitor;
        this.filter = filter;
        this.timestamp = timestamp;
        this.name = name;
        this.attributes = attributes;
        this.pathTrace = new JMXMBeanPathTrace();
    }

    public void traverse() throws Exception {
        visitor.beginMBean(name, timestamp);

        for (final Attribute attribute : attributes.asList()) {
            traverseAttribute(attribute.getName(), attribute.getValue());
        }

        visitor.endMBean();
    }

    private boolean isSimpleData(final Object value) {
        return SimpleType.VOID.isValue(value) ||
                SimpleType.BOOLEAN.isValue(value) ||
                SimpleType.CHARACTER.isValue(value) ||
                SimpleType.BYTE.isValue(value) ||
                SimpleType.SHORT.isValue(value) ||
                SimpleType.INTEGER.isValue(value) ||
                SimpleType.LONG.isValue(value) ||
                SimpleType.FLOAT.isValue(value) ||
                SimpleType.DOUBLE.isValue(value) ||
                SimpleType.STRING.isValue(value) ||
                SimpleType.BIGDECIMAL.isValue(value) ||
                SimpleType.BIGINTEGER.isValue(value) ||
                SimpleType.DATE.isValue(value) ||
                SimpleType.OBJECTNAME.isValue(value);
    }

    private boolean isCompositeData(final Object value) {
        return value instanceof CompositeData;
    }

    private boolean isTabularData(final Object value) {
        return value instanceof TabularData;
    }

    private boolean isArrayData(final Object value) {
        return value.getClass().isArray();
    }

    private void traverseAttribute(final String name, final Object value) throws Exception {
        if (value == null) {
            // Ignore null attributes.
            return;
        }

        pathTrace.push(name);
        try {
            if (filter.infiltrate(pathTrace)) {
                if (isSimpleData(value)) {
                    traverseSimpleData(name, value);
                } else if (isCompositeData(value)) {
                    traverseCompositeData(name, (CompositeData) value);
                } else if (isTabularData(value)) {
                    traverseTabularData(name, (TabularData) value);
                } else if (isArrayData(value)) {
                    traverseArrayData(name, value);
                } else {
                    log.error("Unsupported data type {}", value.getClass().getCanonicalName());
                }
            } else {
                // Skip this attribute.
            }
        } finally {
            pathTrace.pop();
        }
    }

    private void traverseAttribute(final int index, final Object value) throws Exception {
        if (value == null) {
            // Ignore null attributes.
            return;
        }

        pathTrace.push(index);
        try {
            if (filter.infiltrate(pathTrace)) {
                if (isSimpleData(value)) {
                    traverseSimpleData(index, value);
                } else if (isCompositeData(value)) {
                    traverseCompositeData(index, (CompositeData) value);
                } else if (isTabularData(value)) {
                    traverseTabularData(index, (TabularData) value);
                } else if (isArrayData(value)) {
                    traverseArrayData(index, value);
                } else {
                    log.error("Unsupported data type {}", value.getClass().getCanonicalName());
                }
            } else {
                // Skip this attribute.
            }
        } finally {
            pathTrace.pop();
        }
    }

    private void traverseCompositeData(final String name, final CompositeData data) throws Exception {
        visitor.beginCompositeData(name);

        traverseCompositeData(data);

        visitor.endCompositeData();
    }

    private void traverseCompositeData(final int index, final CompositeData data) throws Exception {
        visitor.beginCompositeData(index);

        traverseCompositeData(data);

        visitor.endCompositeData();
    }

    private void traverseCompositeData(final CompositeData data) throws Exception {
        final CompositeType compositeType = data.getCompositeType();
        for (final String key : compositeType.keySet()) {
            final Object value = data.get(key);
            traverseAttribute(key, value);
        }
    }

    private void traverseTabularData(final String name, final TabularData data) throws Exception {
        visitor.beginTabularData(name);

        traverseTabularData(data);

        visitor.endTabularData();
    }

    private void traverseTabularData(final int index, final TabularData data) throws Exception {
        visitor.beginTabularData(index);

        traverseTabularData(data);

        visitor.endTabularData();
    }

    @SuppressWarnings({"unchecked"})
    private void traverseTabularData(final TabularData data) throws Exception {
        final TabularType type = data.getTabularType();
        final List<String> indexNames = type.getIndexNames();
        for (final CompositeData row : (Collection<CompositeData>) data.values()) {
            final String rowKey = composeRowKey(row, indexNames);
            traverseAttribute(rowKey, row);
        }
    }

    private String composeRowKey(final CompositeData row, final List<String> indexNames) {
        final StringBuilder rowKey = new StringBuilder();

        indexNames.forEach((indexName) -> {
            if (rowKey.length() > 0) {
                rowKey.append(TABULAR_ROW_KEY_SEPARATOR);
            }

            rowKey.append(row.get(indexName));
        });

        return rowKey.toString();
    }

    private void traverseArrayData(final String name, final Object data) throws Exception {
        visitor.beginArrayData(name);

        traverseArrayData(data);

        visitor.endArrayData();
    }

    private void traverseArrayData(final int index, final Object data) throws Exception {
        visitor.beginArrayData(index);

        traverseArrayData(data);

        visitor.endArrayData();
    }

    private void traverseArrayData(final Object data) throws Exception {
        for (int index = 0, n = Array.getLength(data); index < n; ++index) {
            traverseAttribute(index, Array.get(data, index));
        }
    }

    private void traverseSimpleData(final String name, final Object value) throws Exception {
        final SimpleDataFunction f = SIMPLE_DATA_FUNCTIONS.get(value.getClass());
        if (f != null) {
            f.call(visitor, name, value);
        } else {
            log.error("Unsupported simple data type {}", value.getClass().getCanonicalName());
        }
    }

    private void traverseSimpleData(final int index, final Object value) throws Exception {
        final SimpleArrayDataFunction f = SIMPLE_ARRAY_DATA_FUNCTIONS.get(value.getClass());
        if (f != null) {
            f.call(visitor, index, value);
        } else {
            log.error("Unsupported simple array data type {}", value.getClass().getCanonicalName());
        }
    }

    @FunctionalInterface
    private interface SimpleDataFunction {
        void call(final JMXMBeanVisitor visitor, final String name, final Object value) throws Exception;
    }

    @FunctionalInterface
    private interface SimpleArrayDataFunction {
        void call(final JMXMBeanVisitor visitor, final int index, final Object value) throws Exception;
    }

    private static final Map<Class<?>, SimpleDataFunction> SIMPLE_DATA_FUNCTIONS;
    private static final Map<Class<?>, SimpleArrayDataFunction> SIMPLE_ARRAY_DATA_FUNCTIONS;

    static {
        {
            final Map<Class<?>, SimpleDataFunction> m = new HashMap<>();
            m.put(Void.class,       (visitor, name, value) -> visitor.simpleData(name, (Void) value));
            m.put(Boolean.class,    (visitor, name, value) -> visitor.simpleData(name, (Boolean) value));
            m.put(Character.class,  (visitor, name, value) -> visitor.simpleData(name, (Character) value));
            m.put(Byte.class,       (visitor, name, value) -> visitor.simpleData(name, (Byte) value));
            m.put(Short.class,      (visitor, name, value) -> visitor.simpleData(name, (Short) value));
            m.put(Integer.class,    (visitor, name, value) -> visitor.simpleData(name, (Integer) value));
            m.put(Long.class,       (visitor, name, value) -> visitor.simpleData(name, (Long) value));
            m.put(Float.class,      (visitor, name, value) -> visitor.simpleData(name, (Float) value));
            m.put(Double.class,     (visitor, name, value) -> visitor.simpleData(name, (Double) value));
            m.put(String.class,     (visitor, name, value) -> visitor.simpleData(name, (String) value));
            m.put(Date.class,       (visitor, name, value) -> visitor.simpleData(name, (Date) value));
            m.put(BigInteger.class, (visitor, name, value) -> visitor.simpleData(name, (BigInteger) value));
            m.put(BigDecimal.class, (visitor, name, value) -> visitor.simpleData(name, (BigDecimal) value));
            m.put(ObjectName.class, (visitor, name, value) -> visitor.simpleData(name, (ObjectName) value));
            SIMPLE_DATA_FUNCTIONS = Collections.unmodifiableMap(m);
        }
        {
            final Map<Class<?>, SimpleArrayDataFunction> m = new HashMap<>();
            m.put(Void.class,       (visitor, index, value) -> visitor.simpleData(index, (Void) value));
            m.put(Boolean.class,    (visitor, index, value) -> visitor.simpleData(index, (Boolean) value));
            m.put(Character.class,  (visitor, index, value) -> visitor.simpleData(index, (Character) value));
            m.put(Byte.class,       (visitor, index, value) -> visitor.simpleData(index, (Byte) value));
            m.put(Short.class,      (visitor, index, value) -> visitor.simpleData(index, (Short) value));
            m.put(Integer.class,    (visitor, index, value) -> visitor.simpleData(index, (Integer) value));
            m.put(Long.class,       (visitor, index, value) -> visitor.simpleData(index, (Long) value));
            m.put(Float.class,      (visitor, index, value) -> visitor.simpleData(index, (Float) value));
            m.put(Double.class,     (visitor, index, value) -> visitor.simpleData(index, (Double) value));
            m.put(String.class,     (visitor, index, value) -> visitor.simpleData(index, (String) value));
            m.put(Date.class,       (visitor, index, value) -> visitor.simpleData(index, (Date) value));
            m.put(BigInteger.class, (visitor, index, value) -> visitor.simpleData(index, (BigInteger) value));
            m.put(BigDecimal.class, (visitor, index, value) -> visitor.simpleData(index, (BigDecimal) value));
            m.put(ObjectName.class, (visitor, index, value) -> visitor.simpleData(index, (ObjectName) value));
            SIMPLE_ARRAY_DATA_FUNCTIONS = Collections.unmodifiableMap(m);
        }
    }
}
