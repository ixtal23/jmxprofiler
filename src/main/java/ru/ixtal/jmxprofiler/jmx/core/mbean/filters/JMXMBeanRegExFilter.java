/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.08 01:43.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean.filters;

import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanPathTrace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class JMXMBeanRegExFilter implements JMXMBeanFilter {
    private final boolean include;
    private final boolean exclude;
    private final List<Pattern> includingPatterns;
    private final List<Pattern> excludingPatterns;


    public JMXMBeanRegExFilter(final String[] includingRegExs, final String[] excludingRegExs) {
        include = includingRegExs != null && includingRegExs.length > 0;
        exclude = excludingRegExs != null && excludingRegExs.length > 0;

        includingPatterns = new ArrayList<>();
        excludingPatterns = new ArrayList<>();

        if (include) {
            for (final String regex : includingRegExs) {
                includingPatterns.add(Pattern.compile(regex));
            }
        }

        if (exclude) {
            for (final String regex : excludingRegExs) {
                excludingPatterns.add(Pattern.compile(regex));
            }
        }
    }

    public boolean infiltrate(final JMXMBeanPathTrace pathTrace) {
        if (!include && !exclude) {
            // No patterns - no filter.
            return true;
        }

        if (include) {
            if (exclude) {
                return pathTrace.matches(includingPatterns) && !pathTrace.matches(excludingPatterns);
            } else {
                return pathTrace.matches(includingPatterns);
            }
        } else {
            return pathTrace.matches(excludingPatterns);
        }
    }
}
