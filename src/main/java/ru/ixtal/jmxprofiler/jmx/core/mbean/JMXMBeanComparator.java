/*
 * Project jmxprofiler.
 *
 * Created by Maxim Morozov on 2017.05.07 18:38.
 *
 * Copyright (c) 2017 Maxim Morozov. All rights reserved.
 */
package ru.ixtal.jmxprofiler.jmx.core.mbean;

import javax.management.Attribute;
import javax.management.AttributeList;
import java.util.Arrays;

public final class JMXMBeanComparator {
    private final AttributeList attributes;

    public JMXMBeanComparator(final AttributeList attributes) {
        this.attributes = attributes;
    }

    public boolean compareTo(final AttributeList otherAttributes) {
        if (this.attributes == otherAttributes) {
            return true;
        }

        if (this.attributes == null || otherAttributes == null) {
            return false;
        }

        if (this.attributes.size() != otherAttributes.size()) {
            return false;
        }

        for (int i = 0, n = this.attributes.size(); i < n; ++i) {
            if (!compare(this.attributes.get(i), otherAttributes.get(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean compare(final Object o1, final Object o2) {
        if (o1 == o2) {
            return true;
        }

        if (o1 == null || o2 == null) {
            return false;
        }

        if (!o1.getClass().equals(o2.getClass())) {
            return false;
        }

        if (o1 instanceof Attribute) {
            // equals method of Attribute class does not work correctly
            // if the values of comparison attributes are arrays.
            // In this case that method calls equals method of Object class
            // that just compares their references but not the content of the arrays.
            // Therefore I implemented the correct comparison here.
            return compare((Attribute) o1, (Attribute) o2);
        } else if (o1.getClass().isArray()) {
            return Arrays.deepEquals((Object[]) o1, (Object[]) o2);
        } else {
            return o1.equals(o2);
        }
    }

    private boolean compare(final Attribute a1, final Attribute a2) {
        if (!a1.getName().equals(a2.getName())) {
            return false;
        }

        final Object v1 = a1.getValue();
        final Object v2 = a2.getValue();

        if (v1 == v2) {
            return true;
        }

        if (v1 == null || v2 == null) {
            return false;
        }

        if (!v1.getClass().equals(v2.getClass())) {
            return false;
        }

        if (v1.getClass().isArray()) {
            return Arrays.deepEquals(new Object[] {v1}, new Object[] {v2});
        } else {
            return v1.equals(v2);
        }
    }
}
