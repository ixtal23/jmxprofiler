/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.10 00:17.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class JMXMBeanPathTrace {
    private final List<String> path;

    private static final String PATH_SEPARATOR = "/";

    public JMXMBeanPathTrace() {
        this.path = new ArrayList<>();
    }

    public void push(final String name) {
        path.add(name);
    }

    public void push(final int index) {
        path.add(String.valueOf(index));
    }

    public void pop() {
        path.remove(path.size() - 1);
    }

    @Override
    public String toString() {
        return String.join(PATH_SEPARATOR, path);
    }

    public boolean matches(final Pattern pattern) {
        return pattern.matcher(this.toString()).matches();
    }

    public boolean matches(final List<Pattern> patterns) {
        for (final Pattern pattern : patterns) {
            if (matches(pattern)) {
                return true;
            }
        }

        return false;
    }
}
