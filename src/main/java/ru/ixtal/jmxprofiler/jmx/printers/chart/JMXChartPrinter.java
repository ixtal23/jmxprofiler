/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.04.17 00:07.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.chart;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ixtal.jmxprofiler.jmx.core.mbean.JMXMBeanPathTrace;
import ru.ixtal.jmxprofiler.jmx.printers.JMXBasePrinter;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import javax.management.ObjectName;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class JMXChartPrinter extends JMXBasePrinter {
    private static final Logger log = LoggerFactory.getLogger(JMXChartPrinter.class);

    private final File outputFile;
    private final BitmapEncoder.BitmapFormat outputFileFormat;
    private final int width;
    private final int height;
    private final String title;
    private final String xAxisTitle;
    private final String yAxisTitle;
    private final String dateFormat;
    private final String decimalFormat;

    private String mBeanName;
    private Date mBeanTimestamp;

    private static final class SeriesData {
        public final List<Date> xTimestamps;
        public final List<Number> yAttributes;

        SeriesData() {
            xTimestamps = new ArrayList<>();
            yAttributes = new ArrayList<>();
        }
    }

    private final Map<String, Map<String, SeriesData>> mBeanSeries; // MBean Name -> (MBean Attribute -> SeriesData)

    private final JMXMBeanPathTrace mBeanPathTrace;

    private boolean invalidate;

    protected JMXChartPrinter(
            final JMXPrinterType type,
            final File outputFile,
            final BitmapEncoder.BitmapFormat outputFileFormat,
            final int width,
            final int height,
            final String title,
            final String xAxisTitle,
            final String yAxisTitle,
            final String dateFormat,
            final String decimalFormat
    ) {
        super(type);

        this.outputFile = outputFile;
        this.outputFileFormat = outputFileFormat;
        this.width = width;
        this.height = height;
        this.title = title;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
        this.dateFormat = dateFormat;
        this.decimalFormat = decimalFormat;
        this.mBeanSeries = new HashMap<>();
        this.mBeanPathTrace = new JMXMBeanPathTrace();
        this.invalidate = false;
    }

    @Override
    public void open() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
        plotChart();
    }

    @Override
    public void beginMBean(final String name, final Instant timestamp) throws Exception {
        mBeanName = name;
        mBeanTimestamp = Date.from(timestamp);

        if (!mBeanSeries.containsKey(mBeanName)) {
            mBeanSeries.put(mBeanName, new HashMap<>());
        }
    }

    @Override
    public void endMBean() throws Exception {
        mBeanName = null;
        mBeanTimestamp = null;
    }

    @Override
    public void beginCompositeData(final String name) throws Exception {
        mBeanPathTrace.push(name);
    }

    @Override
    public void beginCompositeData(final int index) throws Exception {
        mBeanPathTrace.push(index);
    }

    @Override
    public void endCompositeData()throws Exception  {
        mBeanPathTrace.pop();
    }

    @Override
    public void beginTabularData(final String name) throws Exception {
        mBeanPathTrace.push(name);
    }

    @Override
    public void beginTabularData(final int index) throws Exception {
        mBeanPathTrace.push(index);
    }

    @Override
    public void endTabularData() throws Exception {
        mBeanPathTrace.pop();
    }

    @Override
    public void beginArrayData(final String name) throws Exception {
        mBeanPathTrace.push(name);
    }

    @Override
    public void beginArrayData(final int index) throws Exception {
        mBeanPathTrace.push(index);
    }

    @Override
    public void endArrayData() throws Exception {
        mBeanPathTrace.pop();
    }

    @Override
    public void simpleData(final String name, final Void value) throws Exception {
        // Ignore Void values in a chart.
    }

    @Override
    public void simpleData(final String name, final Boolean value) throws Exception {
        // Ignore Boolean values in a chart.
    }

    @Override
    public void simpleData(final String name, final Character value) throws Exception {
        // Ignore Character values in a chart.
    }

    @Override
    public void simpleData(final String name, final Byte value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final Short value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final Integer value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final Long value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final Float value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final Double value) throws Exception {
        addValue(name, value);
    }

    @Override
    public void simpleData(final String name, final String value) throws Exception {
        // Ignore String values in a chart.
    }

    @Override
    public void simpleData(final String name, final Date value) throws Exception {
        // Ignore Date values in a chart.
    }

    @Override
    public void simpleData(final String name, final BigInteger value) throws Exception {
        // Ignore BigInteger values in a chart.
    }

    @Override
    public void simpleData(final String name, final BigDecimal value) throws Exception {
        // Ignore BigDecimal values in a chart.
    }

    @Override
    public void simpleData(final String name, final ObjectName value) throws Exception {
        // Ignore ObjectName values in a chart.
    }

    @Override
    public void simpleData(final int index, final Void value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Boolean value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Character value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Byte value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Short value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Integer value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Long value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Float value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Double value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final String value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final Date value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final BigInteger value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final BigDecimal value) throws Exception {
        // Ignore array values in a chart.
    }

    @Override
    public void simpleData(final int index, final ObjectName value) throws Exception {
        // Ignore array values in a chart.
    }

    private void addValue(final String name, final Number value) {
        mBeanPathTrace.push(name);
        try {
            final String attributeName = mBeanPathTrace.toString();

            final Map<String, SeriesData> attributeSeries = mBeanSeries.get(mBeanName);

            final SeriesData seriesData = attributeSeries.computeIfAbsent(attributeName, k -> new SeriesData());
            seriesData.xTimestamps.add(mBeanTimestamp);
            seriesData.yAttributes.add(value);

            invalidate = true;
        } finally {
            mBeanPathTrace.pop();
        }
    }

    @SuppressWarnings({"unchecked"})
    private void plotChart() throws IOException {
        if (!invalidate) {
            // Nothing to plot.
            return;
        }

        final XYChart chart = new XYChart(width, height);
        chart.setTitle(title);
        chart.setXAxisTitle(xAxisTitle);
        chart.setYAxisTitle(yAxisTitle);

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setTimezone(TimeZone.getDefault());
        chart.getStyler().setDatePattern(dateFormat);
        chart.getStyler().setDecimalPattern(decimalFormat);

        for (final Map.Entry<String, Map<String, SeriesData>> mBeanEntry : mBeanSeries.entrySet()) {
            final String mBeanName = mBeanEntry.getKey();
            final Map<String, SeriesData> attributeSeries = mBeanEntry.getValue();

            for (final Map.Entry<String, SeriesData> attributeEntry : attributeSeries.entrySet()) {
                final String attributeName = attributeEntry.getKey();
                final SeriesData seriesData = attributeEntry.getValue();

                final XYSeries series = chart.addSeries(mBeanName + ": " + attributeName, seriesData.xTimestamps, seriesData.yAttributes);
                series.setMarker(SeriesMarkers.NONE);
            }
        }

        BitmapEncoder.saveBitmap(chart, outputFile.toString(), outputFileFormat);

        invalidate = false;

        log.debug("Chart was updated");
    }
}
