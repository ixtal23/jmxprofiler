/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.07 23:03.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.profiler;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBean;
import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanSnapshot;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanFilter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinter;

import javax.management.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JMXSample {
    final List<JMXMBeanSnapshot> snapshots;

    public JMXSample(final boolean takeEqualSamples, final List<JMXMBean> mBeans) throws IOException, JMException {
        snapshots = new ArrayList<>(mBeans.size());
        for (final JMXMBean mBean : mBeans) {
            mBean.snapshot(takeEqualSamples).ifPresent(snapshots::add);
        }
    }

    public void print(final JMXPrinter printer, final JMXMBeanFilter filter) throws Exception {
        for (final JMXMBeanSnapshot snapshot : snapshots) {
            snapshot.traverse(printer, filter);
        }
    }
}
