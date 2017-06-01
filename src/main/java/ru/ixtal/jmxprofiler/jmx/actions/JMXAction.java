/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.01 00:24.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.actions;

import ru.ixtal.jmxprofiler.actions.Action;
import ru.ixtal.jmxprofiler.actions.ActionType;
import ru.ixtal.jmxprofiler.configuration.Configuration;
import ru.ixtal.jmxprofiler.jmx.core.connection.JMXConnection;

public abstract class JMXAction extends Action {
    JMXConnection jmxConnection;

    protected JMXAction(final ActionType type, final Configuration configuration) {
        super(type, configuration);
    }

    @Override
    protected void start() throws Exception {
        jmxConnection = new JMXConnection(configuration.jmxHost(), configuration.jmxPort());
    }

    @Override
    protected void finish() throws Exception {
        jmxConnection.close();
    }
}
