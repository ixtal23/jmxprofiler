/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.31 21:03.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler;

public final class Version {
    private String title;
    private String version;

    public Version(final String defaultTitle) {
        final Package classPackage = getClass().getPackage();
        if (classPackage != null) {
            title = classPackage.getImplementationTitle();
            version = classPackage.getImplementationVersion();
        }

        if (title == null) {
            title = defaultTitle;
            if (title == null) {
                title = "";
            }
        }

        if (version == null) {
            version = "";
        }
    }

    @Override
    public String toString() {
        if (title.isEmpty()) {
            return "";
        }

        if (version.isEmpty()) {
            return title;
        }

        return title + " " + version;
    }
}
