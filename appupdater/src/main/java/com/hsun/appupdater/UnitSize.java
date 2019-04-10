package com.hsun.appupdater;

import java.text.DecimalFormat;

public class UnitSize {

    static String parse(long size) {
        if (size > 0) {
            String unitSize;
            double b = size;
            double k = size / 1024.0;
            double m = ((size / 1024.0) / 1024.0);
            double g = (((size / 1024.0) / 1024.0) / 1024.0);
            double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);
            DecimalFormat dec = new DecimalFormat("0.00");
            if (t > 1) {
                unitSize = dec.format(t).concat("TB");
            } else if (g > 1) {
                unitSize = dec.format(g).concat("GB");
            } else if (m > 1) {
                unitSize = dec.format(m).concat("MB");
            } else if (k > 1) {
                unitSize = dec.format(k).concat("KB");
            } else {
                unitSize = dec.format(b).concat("Bytes");
            }
            return unitSize;
        } else {
            return String.valueOf(size);
        }
    }
}
