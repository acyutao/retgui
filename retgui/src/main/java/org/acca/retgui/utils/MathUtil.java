/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

/**
 * MathUtil.
 * 
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public class MathUtil {

    private MathUtil() {

    }

    /**
     * roundDouble.
     * 
     * @param val double
     * @param precision int
     * @return Double
     */
    public static Double roundDouble(double val, int precision) {

        double factor = Math.pow(10, precision);
        return Math.floor(val * factor + 0.5) / factor;
    }

    /**
     * isDigits.
     * 
     * @param str String
     * @return boolean
     */
    public static boolean isDigits(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0, len = str.length(); i < len; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
