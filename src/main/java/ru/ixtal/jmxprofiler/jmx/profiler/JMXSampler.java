/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.07 23:14.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.profiler;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBean;

import javax.management.JMException;
import java.io.IOException;
import java.util.List;

public final class JMXSampler {
    private final boolean takeEqualSamples;
    private final List<JMXMBean> mBeans;

    public JMXSampler(final boolean takeEqualSamples, final List<JMXMBean> mBeans) {
        this.takeEqualSamples = takeEqualSamples;
        this.mBeans = mBeans;
    }

    public JMXSample sample() throws IOException, JMException {
        return new JMXSample(takeEqualSamples, mBeans);
    }
}
