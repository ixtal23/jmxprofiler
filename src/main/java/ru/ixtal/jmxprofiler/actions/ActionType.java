/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.30 23:34.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.actions;

import ru.ixtal.jmxprofiler.configuration.Configuration;
import ru.ixtal.jmxprofiler.jmx.actions.JMXProfile;

@SuppressWarnings({"DeserializableClassInSecureContext", "SerializableClassInSecureContext"})
public enum ActionType {
    profile {
        @Override
        public Action implementation(final Configuration configuration) {
            return new JMXProfile(configuration);
        }
    };

    public abstract Action implementation(final Configuration configuration);
}
