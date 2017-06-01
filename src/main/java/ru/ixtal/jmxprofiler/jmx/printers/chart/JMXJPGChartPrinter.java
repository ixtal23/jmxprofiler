/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.11 23:13.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.printers.chart;

import org.knowm.xchart.BitmapEncoder;
import ru.ixtal.jmxprofiler.jmx.printers.JMXPrinterType;

import java.io.File;

public final class JMXJPGChartPrinter extends JMXChartPrinter {
    public JMXJPGChartPrinter(
            final File outputFile,
            final int width,
            final int height,
            final String title,
            final String xAxisTitle,
            final String yAxisTitle,
            final String dateFormat,
            final String decimalFormat
    ) {
        super(
                JMXPrinterType.jpg_chart,
                outputFile,
                BitmapEncoder.BitmapFormat.JPG,
                width,
                height,
                title,
                xAxisTitle,
                yAxisTitle,
                dateFormat,
                decimalFormat
        );
    }
}
