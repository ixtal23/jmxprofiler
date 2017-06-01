/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.03.29 01:03.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.configuration;

import org.apache.commons.cli.*;
import ru.ixtal.jmxprofiler.actions.ActionType;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanDummyFilter;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanFilter;
import ru.ixtal.jmxprofiler.jmx.core.mbean.filters.JMXMBeanRegExFilter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

public final class Configuration {
    private static final String DEFAULT_ACTION = ActionType.profile.toString();
    private static final String DEFAULT_JMX_HOST = "localhost";
    private static final String DEFAULT_DURATION = "0";
    private static final String DEFAULT_SAMPLE_INTERVAL = "1000";
    private static final String DEFAULT_FLUSH_INTERVAL = "0";
    private static final String DEFAULT_PRINTER = JMXPrinterType.json_console.toString();
    private static final String DEFAULT_CHART_WIDTH = "2000";
    private static final String DEFAULT_CHART_HEIGHT = "1000";
    private static final String DEFAULT_CHART_X_AXIS_TITLE = "Local Time";
    private static final String DEFAULT_CHART_Y_AXIS_TITLE = "Value";
    private static final String DEFAULT_CHART_DATE_FORMAT = "HH:mm:ss.SSS";
    private static final String DEFAULT_CHART_DECIMAL_FORMAT = "#";

    private final Option actionsOption = Option
            .builder("a").longOpt("action")
            .hasArg()
            .argName("action name")
            .type(String.class)
            .desc("an action to execute" +
                    ", the default value: " + DEFAULT_ACTION +
                    ", the available values: " + Arrays.toString(ActionType.values()))
            .build();

    private final Option jmxHostOption = Option
            .builder("jh").longOpt("jmx-host")
            .hasArg()
            .argName("host name")
            .type(String.class)
            .desc("the name of the host of an application to connect via JMX" +
                    ", the default value: " + DEFAULT_JMX_HOST)
            .build();

    private final Option jmxPortOption = Option
            .builder("jp").longOpt("jmx-port")
            .required()
            .hasArg()
            .argName("port")
            .type(Integer.class)
            .desc("the port of an application to connect via JMX")
            .build();

    private final Option jmxMBeanObjectNameOption = Option
            .builder("jo").longOpt("jmx-mbean-object-name")
            .required()
            .hasArg()
            .argName("object name")
            .type(Long.class)
            .desc("the object name of an MBean or a pattern that can match the names of several MBeans to profile, " +
                    "the example 'java.lang:type=GarbageCollector=,*'")
            .build();

    private final Option jmxMBeanRegExIncludingFilterOption = Option
            .builder("jif").longOpt("jmx-mbean-regex-including-filter")
            .hasArgs()
            .argName("regex list")
            .type(String.class)
            .desc("the list of regular expressions of the including filter of the attributes and values of MBeans, " +
                    "the default value: null")
            .build();

    private final Option jmxMBeanRegExExcludingFilterOption = Option
            .builder("jef").longOpt("jmx-mbean-regex-excluding-filter")
            .hasArgs()
            .argName("regex list")
            .type(String.class)
            .desc("the list of regular expressions of the excluding filter of the attributes and values of MBeans" +
                    ", the default value: null")
            .build();

    private final Option takeEqualSamples = Option
            .builder("tes").longOpt("take-equal-samples")
            .desc("the profiler will take equal samples if this option is presented" +
                    ", the default value: false")
            .build();

    private final Option sampleIntervalOption = Option
            .builder("si").longOpt("sample-interval")
            .hasArg()
            .argName("msec")
            .type(Long.class)
            .desc("an interval in milliseconds between profile samples" +
                    ", the default value: " + DEFAULT_SAMPLE_INTERVAL)
            .build();

    private final Option flushIntervalOption = Option
            .builder("fi").longOpt("flush-interval")
            .hasArg()
            .argName("msec")
            .type(Long.class)
            .desc("an interval in milliseconds of flushing samples into an output file, 0 means the immediate flush" +
                    ", the default value: " + DEFAULT_FLUSH_INTERVAL)
            .build();

    private final Option durationOption = Option
            .builder("d").longOpt("duration")
            .hasArg()
            .argName("msec")
            .type(Long.class)
            .desc("the duration of profiling in milliseconds, 0 means the infinite duration" +
                    ", the default value: " + DEFAULT_DURATION)
            .build();

    private final Option printerOption = Option
            .builder("p").longOpt("printer")
            .hasArg()
            .argName("printer name")
            .type(String.class)
            .desc("the type of a printer to output samples" +
                    ", the default value: " + DEFAULT_PRINTER +
                    ", the available values: " + Arrays.toString(JMXPrinterType.values()))
            .build();

    private final Option outputFileOption = Option
            .builder("o").longOpt("output-file")
            .hasArg()
            .argName("file path")
            .type(String.class)
            .desc("the name of an output file for some printers like the chart printers")
            .build();

    private final Option chartWidthOption = Option
            .builder("cw").longOpt("chart-width")
            .hasArg()
            .argName("pixels")
            .type(Integer.class)
            .desc("the width of a chart in pixels" +
                    ", the default value: " + DEFAULT_CHART_WIDTH)
            .build();

    private final Option chartHeightOption = Option
            .builder("ch").longOpt("chart-height")
            .hasArg()
            .argName("pixels")
            .type(Integer.class)
            .desc("the height of a chart in pixels" +
                    ", the default value: " + DEFAULT_CHART_WIDTH)
            .build();

    private final Option chartTitleOption = Option
            .builder("ct").longOpt("chart-title")
            .hasArg()
            .argName("chart title")
            .type(String.class)
            .desc("the title of a chart" +
                    ", the default value: a current date time")
            .build();

    private final Option chartXAxisTitleOption = Option
            .builder("cxt").longOpt("chart-x-axis-title")
            .hasArg()
            .argName("X axis title")
            .type(String.class)
            .desc("the title of X axis of a chart" +
                    ", the default value: " + DEFAULT_CHART_X_AXIS_TITLE)
            .build();

    private final Option chartYAxisTitleOption = Option
            .builder("cyt").longOpt("chart-y-axis-title")
            .hasArg()
            .argName("Y axis title")
            .type(String.class)
            .desc("the title of Y axis of a chart" +
                    ", the default value: " + DEFAULT_CHART_Y_AXIS_TITLE)
            .build();

    private final Option chartDateFormatOption = Option
            .builder("ctf").longOpt("chart-date-format")
            .hasArg()
            .argName("date format")
            .type(String.class)
            .desc("the format of dates in X axis of a chart" +
                    ", the default value: " + DEFAULT_CHART_DATE_FORMAT)
            .build();

    private final Option chartDecimalFormatOption = Option
            .builder("cdf").longOpt("chart-decimal-format")
            .hasArg()
            .argName("decimal format")
            .type(String.class)
            .desc("the format of decimal values in Y axis of a chart" +
                    ", the default value: " + DEFAULT_CHART_DECIMAL_FORMAT)
            .build();

    private final Option elasticsearchHostOption = Option
            .builder("eh").longOpt("elasticsearch-host")
            .hasArg()
            .argName("host name")
            .type(String.class)
            .desc("the name of the host of an Elasticsearch node")
            .build();

    private final Option elasticsearchPortOption = Option
            .builder("ep").longOpt("elasticsearch-port")
            .hasArg()
            .argName("port")
            .type(Integer.class)
            .desc("the port of an Elasticsearch node")
            .build();

    private final Option elasticsearchIndexNameOption = Option
            .builder("ein").longOpt("elasticsearch-index-name")
            .hasArg()
            .argName("index name")
            .type(String.class)
            .desc("the name of an Elasticsearch index")
            .build();

    private final Option elasticsearchIndexTypeOption = Option
            .builder("eit").longOpt("elasticsearch-index-type")
            .hasArg()
            .argName("index type")
            .type(String.class)
            .desc("the type of an Elasticsearch index")
            .build();

    private final CommandLine commandLine;

    public Configuration(final String[] args) throws ConfigurationException, CommandLineHelpException {
        final Options options = new Options()
                .addOption(actionsOption)
                .addOption(jmxHostOption)
                .addOption(jmxPortOption)
                .addOption(jmxMBeanObjectNameOption)
                .addOption(jmxMBeanRegExIncludingFilterOption)
                .addOption(jmxMBeanRegExExcludingFilterOption)
                .addOption(takeEqualSamples)
                .addOption(sampleIntervalOption)
                .addOption(flushIntervalOption)
                .addOption(durationOption)
                .addOption(printerOption)
                .addOption(outputFileOption)
                .addOption(chartWidthOption)
                .addOption(chartHeightOption)
                .addOption(chartTitleOption)
                .addOption(chartXAxisTitleOption)
                .addOption(chartYAxisTitleOption)
                .addOption(chartDateFormatOption)
                .addOption(chartDecimalFormatOption)
                .addOption(elasticsearchHostOption)
                .addOption(elasticsearchPortOption)
                .addOption(elasticsearchIndexNameOption)
                .addOption(elasticsearchIndexTypeOption);

        if (args == null || args.length == 0) {
            throw new CommandLineHelpException(options);
        }

        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (final ParseException e) {
            throw new CommandLineHelpException(options, e);
        }
    }

    private boolean has(final Option option) {
        return commandLine.hasOption(option.getOpt()) || commandLine.hasOption(option.getLongOpt());
    }

    private String valueOf(final Option option) throws ConfigurationException {
        final String value = commandLine.getOptionValue(option.getOpt());
        if (value == null || value.isEmpty()) {
            throw new ConfigurationException("The command line option " + option.getLongOpt() + " has no value");
        } else {
            return value;
        }
    }

    private String valueOf(final Option option, final String defaultValue) {
        final String value = commandLine.getOptionValue(option.getOpt());
        if (value == null || value.isEmpty()) {
            return defaultValue;
        } else {
            return value;
        }
    }

    private String[] valuesOf(final Option option) throws ConfigurationException {
        final String[] values = commandLine.getOptionValues(option.getOpt());
        if (values == null || values.length == 0) {
            throw new ConfigurationException("The command line option " + option.getLongOpt() + " has no values");
        } else {
            return values;
        }
    }

    private String[] valuesOf(final Option option, final String[] defaultValue) {
        final String[] values = commandLine.getOptionValues(option.getOpt());
        if (values == null || values.length == 0) {
            return defaultValue;
        } else {
            return values;
        }
    }

    public ActionType action() throws ConfigurationException {
        return ActionType.valueOf(valueOf(actionsOption, DEFAULT_ACTION));
    }

    public String jmxHost() throws ConfigurationException {
        return valueOf(jmxHostOption, DEFAULT_JMX_HOST);
    }

    public int jmxPort() throws ConfigurationException {
        return Integer.valueOf(valueOf(jmxPortOption));
    }

    public ObjectName jmxMBeanObjectName() throws ConfigurationException, MalformedObjectNameException {
        return new ObjectName(valueOf(jmxMBeanObjectNameOption));
    }

    public JMXMBeanFilter jmxMBeanFilter() throws ConfigurationException {
        final String[] includingRegExs = valuesOf(jmxMBeanRegExIncludingFilterOption, null);
        final String[] excludingRegExs = valuesOf(jmxMBeanRegExExcludingFilterOption, null);
        if ((includingRegExs == null || includingRegExs.length == 0) &&
            (excludingRegExs == null || excludingRegExs.length == 0)) {
            return new JMXMBeanDummyFilter();
        } else {
            return new JMXMBeanRegExFilter(includingRegExs, excludingRegExs);
        }
    }

    public boolean takeEqualSamples() {
        return has(takeEqualSamples);
    }

    public long sampleInterval() throws ConfigurationException {
        return Long.valueOf(valueOf(sampleIntervalOption, DEFAULT_SAMPLE_INTERVAL));
    }

    public long flushInterval() throws ConfigurationException {
        return Long.valueOf(valueOf(flushIntervalOption, DEFAULT_FLUSH_INTERVAL));
    }

    public long duration() throws ConfigurationException {
        return Long.valueOf(valueOf(durationOption, DEFAULT_DURATION));
    }

    public JMXPrinter printer() throws ConfigurationException {
        return JMXPrinterType.valueOf(valueOf(printerOption, DEFAULT_PRINTER)).implementation(this);
    }

    public File outputFile() throws ConfigurationException {
        return new File(valueOf(outputFileOption));
    }

    public int chartWidth() throws ConfigurationException {
        return Integer.valueOf(valueOf(chartWidthOption, DEFAULT_CHART_WIDTH));
    }

    public int chartHeight() throws ConfigurationException {
        return Integer.valueOf(valueOf(chartHeightOption, DEFAULT_CHART_HEIGHT));
    }

    public String chartTitle() throws ConfigurationException {
        final String localDateTime = LocalDateTime.now().toString();
        final String chartTitle = valueOf(chartTitleOption, null);
        if (chartTitle == null || chartTitle.isEmpty()) {
            return localDateTime;
        } else {
            return chartTitle + " - " + localDateTime;
        }
    }

    public String chartXAxisTitle() throws ConfigurationException {
        return valueOf(chartXAxisTitleOption, DEFAULT_CHART_X_AXIS_TITLE);
    }

    public String chartYAxisTitle() throws ConfigurationException {
        return valueOf(chartYAxisTitleOption, DEFAULT_CHART_Y_AXIS_TITLE);
    }

    public String chartDateFormat() throws ConfigurationException {
        return valueOf(chartDateFormatOption, DEFAULT_CHART_DATE_FORMAT);
    }

    public String chartDecimalFormat() throws ConfigurationException {
        return valueOf(chartDecimalFormatOption, DEFAULT_CHART_DECIMAL_FORMAT);
    }

    public String elasticsearchHost() throws ConfigurationException {
        return valueOf(elasticsearchHostOption);
    }

    public int elasticsearchPort() throws ConfigurationException {
        return Integer.valueOf(valueOf(elasticsearchPortOption));
    }

    public String elasticsearchIndexName() throws ConfigurationException {
        return valueOf(elasticsearchIndexNameOption);
    }

    public String elasticsearchIndexType() throws ConfigurationException {
        return valueOf(elasticsearchIndexTypeOption);
    }
}
