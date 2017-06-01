/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.07 20:28.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import javax.management.ObjectName;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

public interface JMXMBeanVisitor {
    void beginMBean(final String name, final Instant timestamp) throws Exception;
    void endMBean() throws Exception;

    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/ArrayType.html
    void beginArrayData(final String name) throws Exception;
    void beginArrayData(final int index) throws Exception;
    void endArrayData() throws Exception;

    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/CompositeType.html
    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/CompositeData.html
    void beginCompositeData(final String name) throws Exception;
    void beginCompositeData(final int index) throws Exception;
    void endCompositeData() throws Exception;

    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/TabularType.html
    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/TabularData.html
    void beginTabularData(final String name) throws Exception;
    void beginTabularData(final int index) throws Exception;
    void endTabularData() throws Exception;

    // https://docs.oracle.com/javase/8/docs/api/javax/management/openmbean/SimpleType.html
    void simpleData(final String name, final Void value) throws Exception;
    void simpleData(final String name, final Boolean value) throws Exception;
    void simpleData(final String name, final Character value) throws Exception;
    void simpleData(final String name, final Byte value) throws Exception;
    void simpleData(final String name, final Short value) throws Exception;
    void simpleData(final String name, final Integer value) throws Exception;
    void simpleData(final String name, final Long value) throws Exception;
    void simpleData(final String name, final Float value) throws Exception;
    void simpleData(final String name, final Double value) throws Exception;
    void simpleData(final String name, final String value) throws Exception;
    void simpleData(final String name, final Date value) throws Exception;
    void simpleData(final String name, final BigInteger value) throws Exception;
    void simpleData(final String name, final BigDecimal value) throws Exception;
    void simpleData(final String name, final ObjectName value) throws Exception;
    void simpleData(final int index, final Void value) throws Exception;
    void simpleData(final int index, final Boolean value) throws Exception;
    void simpleData(final int index, final Character value) throws Exception;
    void simpleData(final int index, final Byte value) throws Exception;
    void simpleData(final int index, final Short value) throws Exception;
    void simpleData(final int index, final Integer value) throws Exception;
    void simpleData(final int index, final Long value) throws Exception;
    void simpleData(final int index, final Float value) throws Exception;
    void simpleData(final int index, final Double value) throws Exception;
    void simpleData(final int index, final String value) throws Exception;
    void simpleData(final int index, final Date value) throws Exception;
    void simpleData(final int index, final BigInteger value) throws Exception;
    void simpleData(final int index, final BigDecimal value) throws Exception;
    void simpleData(final int index, final ObjectName value) throws Exception;
}
