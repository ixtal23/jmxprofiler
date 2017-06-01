/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.08 01:43.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean.filters;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanPathTrace;

public interface JMXMBeanFilter {
    boolean infiltrate(final JMXMBeanPathTrace pathTrace);
}
