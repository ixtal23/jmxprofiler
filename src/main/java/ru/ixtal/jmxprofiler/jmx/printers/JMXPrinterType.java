/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.16 00:26.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers;

import ru.ixtal.jmxprofiler.configuration.Configuration;
import ru.ixtal.jmxprofiler.configuration.ConfigurationException;
import ru.ixtal.jmxprofiler.jmx.printers.chart.JMXGIFChartPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.chart.JMXJPGChartPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.chart.JMXPNGChartPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.json.JMXElasticsearchPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.json.JMXJSONConsolePrinter;
import ru.ixtal.jmxprofiler.jmx.printers.json.JMXJSONLogPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.plain.JMXPlainConsolePrinter;
import ru.ixtal.jmxprofiler.jmx.printers.plain.JMXPlainLogPrinter;

@SuppressWarnings({"DeserializableClassInSecureContext", "SerializableClassInSecureContext"})
public enum JMXPrinterType {
    plain_console {
        @Override
        public JMXPrinter implementation(final Configuration configuration) {
            return new JMXPlainConsolePrinter();
        }
    },
    plain_log {
        @Override
        public JMXPrinter implementation(final Configuration configuration) {
            return new JMXPlainLogPrinter();
        }
    },
    json_console {
        @Override
        public JMXPrinter implementation(final Configuration configuration) {
            return new JMXJSONConsolePrinter();
        }
    },
    json_log {
        @Override
        public JMXPrinter implementation(final Configuration configuration) {
            return new JMXJSONLogPrinter();
        }
    },
    elasticsearch {
        @Override
        public JMXPrinter implementation(final Configuration configuration) throws ConfigurationException {
            return new JMXElasticsearchPrinter(
                    configuration.elasticsearchHost(),
                    configuration.elasticsearchPort(),
                    configuration.elasticsearchIndexName(),
                    configuration.elasticsearchIndexType()
            );
        }
    },
    png_chart {
        @Override
        public JMXPrinter implementation(final Configuration configuration) throws ConfigurationException {
            return new JMXPNGChartPrinter(
                    configuration.outputFile(),
                    configuration.chartWidth(),
                    configuration.chartHeight(),
                    configuration.chartTitle(),
                    configuration.chartXAxisTitle(),
                    configuration.chartYAxisTitle(),
                    configuration.chartDateFormat(),
                    configuration.chartDecimalFormat()
            );
        }
    },
    gif_chart {
        @Override
        public JMXPrinter implementation(final Configuration configuration) throws ConfigurationException {
            return new JMXGIFChartPrinter(
                    configuration.outputFile(),
                    configuration.chartWidth(),
                    configuration.chartHeight(),
                    configuration.chartTitle(),
                    configuration.chartXAxisTitle(),
                    configuration.chartYAxisTitle(),
                    configuration.chartDateFormat(),
                    configuration.chartDecimalFormat()
            );
        }
    },
    jpg_chart {
        @Override
        public JMXPrinter implementation(final Configuration configuration) throws ConfigurationException {
            return new JMXJPGChartPrinter(
                    configuration.outputFile(),
                    configuration.chartWidth(),
                    configuration.chartHeight(),
                    configuration.chartTitle(),
                    configuration.chartXAxisTitle(),
                    configuration.chartYAxisTitle(),
                    configuration.chartDateFormat(),
                    configuration.chartDecimalFormat()
            );
        }
    };

    public abstract JMXPrinter implementation(final Configuration configuration) throws ConfigurationException;
}
