/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.30 23:34.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.actions;

import ru.ixtal.jmxprofiler.configuration.Configuration;
import ru.ixtal.jmxprofiler.configuration.ConfigurationException;

import java.util.Collection;
import java.util.Collections;

public final class ActionExecutor {
    final Collection<Action> actions;

    public ActionExecutor(final Configuration configuration) throws ConfigurationException {
        actions = Collections.singletonList(configuration.action().implementation(configuration));
    }

    public void execute() {
        actions.forEach(Action::run);
    }
}
