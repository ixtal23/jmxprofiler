/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.14 16:54.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.configuration;

@SuppressWarnings({"DeserializableClassInSecureContext", "SerializableClassInSecureContext"})
public final class ConfigurationException extends Exception {
    public ConfigurationException(final String message) {
        super(message);
    }

    public ConfigurationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
