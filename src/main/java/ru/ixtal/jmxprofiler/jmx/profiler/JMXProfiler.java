/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.07 16:43.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.profiler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanFilter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinter;

import java.io.IOException;

public final class JMXProfiler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(JMXProfiler.class);

    private final JMXSampler sampler;
    private final JMXPrinter printer;
    private final JMXMBeanFilter filter;
    private final long sampleInterval;
    private final long flushInterval;
    private final long duration;

    long startTime;
    long lastFlushTime;
    long lastSampleTime;

    public JMXProfiler(
            final JMXSampler sampler,
            final JMXPrinter printer,
            final JMXMBeanFilter filter,
            final long sampleInterval,
            final long flushInterval,
            final long duration
    ) {
        this.sampler = sampler;
        this.printer = printer;
        this.filter = filter;
        this.sampleInterval = sampleInterval;
        this.flushInterval = flushInterval;
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            log.debug("JMXProfiler started with sample {} and flush {} intervals and {} duration in msec", sampleInterval, flushInterval, duration);

            printer.open();

            startTime = System.currentTimeMillis();
            lastFlushTime = System.currentTimeMillis();

            try {
                while (true) {
                    sample();

                    flush();

                    if (checkSampleInterval()) {
                        break;
                    }

                    if (checkDuration()) {
                        break;
                    }
                }
            } finally {
                try {
                    printer.close();
                } catch (Exception e) {
                    log.error("Failed to close the printer", e);
                }
            }

            log.debug("JMXProfiler finished");
        } catch (Exception e) {
            log.error("JMXProfiler failed", e);
        }
    }

    private void sample() throws Exception {
        sampler.sample().print(printer, filter);
        lastSampleTime = System.currentTimeMillis();
    }

    private void flush() throws IOException {
        final long elapsedFlushInterval = System.currentTimeMillis() - lastFlushTime;
        if (flushInterval == 0 || elapsedFlushInterval >= flushInterval) {
            printer.flush();
            lastFlushTime = System.currentTimeMillis();
        }
    }

    private boolean checkSampleInterval() {
        final long elapsedSampleInterval = System.currentTimeMillis() - lastSampleTime;

        if (sampleInterval > 0 && elapsedSampleInterval < sampleInterval) {
            try {
                Thread.sleep(sampleInterval - elapsedSampleInterval);
            } catch (final InterruptedException e) {
                log.debug("JMXProfiler interrupted");
                return true;
            }
        }

        return false;
    }

    private boolean checkDuration() {
        final long elapsedTime = System.currentTimeMillis() - startTime;

        if (duration > 0 && elapsedTime >= duration) {
            log.debug("JMXProfiler interrupted because of duration");
            return true;
        }

        return false;
    }
}
