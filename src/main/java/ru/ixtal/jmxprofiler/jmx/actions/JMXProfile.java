/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.13 23:46.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.actions;

import ru.ixtal.jmxprofiler.actions.ActionType;
import ru.ixtal.jmxprofiler.configuration.Configuration;
import ru.ixtal.jmxprofiler.jmx.profiler.JMXProfiler;
import ru.ixtal.jmxprofiler.jmx.profiler.JMXSampler;
import ru.ixtal.jmxprofiler.threads.WorkThread;

public final class JMXProfile extends JMXAction {
    public JMXProfile(final Configuration configuration) {
        super(ActionType.profile, configuration);
    }

    @Override
    protected void execute() throws Exception {
        new WorkThread(
                "JMXProfiler",
                new JMXProfiler(
                        new JMXSampler(
                                configuration.takeEqualSamples(),
                                jmxConnection.queryMBeans(configuration.jmxMBeanObjectName())
                        ),
                        configuration.printer(),
                        configuration.jmxMBeanFilter(),
                        configuration.sampleInterval(),
                        configuration.flushInterval(),
                        configuration.duration()
                )
        ).start().waitForCompletion();
    }
}
