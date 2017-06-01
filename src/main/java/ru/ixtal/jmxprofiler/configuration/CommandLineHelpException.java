/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.14 16:58.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.configuration;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.PrintWriter;

@SuppressWarnings({"DeserializableClassInSecureContext", "SerializableClassInSecureContext"})
public final class CommandLineHelpException extends Throwable {
    private static final String HELP_SYNTAX_PREFIX = "Usage: ";
    private static final String HELP_SYNTAX = "java -Dlog4j.configurationFile=./config/log4j2.xml -jar ./lib/jmxprofiler-<version>.jar";
    private static final String HELP_HEADER = "Options:";
    private static final String HELP_FOOTER = "";
    private static final int HELP_WIDTH = 160;
    private static final int HELP_LEFT_PADDING = 1;
    private static final int HELP_DESC_PADDING = 1;

    final Options options;
    final Throwable throwable;

    public CommandLineHelpException(final Options options) {
        this.options = options;
        this.throwable = null;
    }

    public CommandLineHelpException(final Options options, final Throwable throwable) {
        this.options = options;
        this.throwable = throwable;
    }

    public void printHelp() {
        if  (throwable != null) {
            System.out.println("Failed to parse the command line");
            System.out.println(throwable.getMessage());
        }

        final PrintWriter writer = new PrintWriter(System.out);

        final HelpFormatter formatter = new HelpFormatter();
        formatter.setSyntaxPrefix(HELP_SYNTAX_PREFIX);
        formatter.printHelp(
                writer,
                HELP_WIDTH,
                HELP_SYNTAX,
                HELP_HEADER,
                options,
                HELP_LEFT_PADDING,
                HELP_DESC_PADDING,
                HELP_FOOTER,
                true
        );

        writer.flush();
    }
}
