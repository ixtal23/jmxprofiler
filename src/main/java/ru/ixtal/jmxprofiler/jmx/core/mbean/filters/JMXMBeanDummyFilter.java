/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.08 01:43.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean.filters;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanPathTrace;

public final class JMXMBeanDummyFilter implements JMXMBeanFilter {
    public JMXMBeanDummyFilter() {
    }

    public boolean infiltrate(final JMXMBeanPathTrace pathTrace) {
        // Pass everything.
        return true;
    }
}
