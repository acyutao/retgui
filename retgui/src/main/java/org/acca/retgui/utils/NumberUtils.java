/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.activation.UnsupportedDataTypeException;

/**
 * Number tools.
 * 
 * @version Seurat v1.0
 * @author Yang Xiaolong, 2012-6-19
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

    /**
     * Increase in the number of string type or reduce i.
     * 
     * <pre>
     * <code>
     * NumberUtils.add("00012", 12) = "00024"
     * </code>
     * </pre>
     * 
     * @param numStr the number of string type
     * @param rangeNum the changes of size
     * @return String
     */
    public static String add(String numStr, int rangeNum) {
        StringBuffer result = new StringBuffer();
        int strLen = numStr.length();
        int num = toInt(numStr);
        num = num + rangeNum;
        int resultLen = Integer.toString(num).length();
        int zeroNum = strLen - resultLen;
        for (int i = 0; i < zeroNum; i++) {
            result.append("0");
        }
        return result.append(num).toString();
    }

    /**
     * Compare the size of the two strings types of digital(only).
     * 
     * <pre>
     * <code>
     * NumberUtils.compare("2.2", "1") = 1
     * NumberUtils.compare("1", "2") = -1
     * NumberUtils.compare("2", "2") = 0
     * </code>
     * </pre>
     * 
     * @param leftStr the first number
     * @param rightStr the second number
     * @return int It returns -1 if the first value is less than the second. It returns +1 if the
     *         first value is greater than the second. It returns 0 if the values are equal.
     * @throws UnsupportedDataTypeException Exception thrown when the wrong data type
     */
    public static int compare(String leftStr, String rightStr) throws UnsupportedDataTypeException {
        if (!isNumber(leftStr) || !isNumber(rightStr)) {
            throw new UnsupportedDataTypeException("The wrong data type");
        }
        double lNum = toDouble(leftStr);
        double rNum = toDouble(rightStr);

        return compare(lNum, rNum);
    }

    /**
     * Comparative Regional, parameter is a long of type number Inside of A in B.
     * 
     * <pre>
     * Return value ：
     * true  : (destFrom, destTo) overlap (srcFrom, srcTo);
     * false : (destFrom, destTo) outside(srcFrom, srcTo);
     * 
     * Example: 
     * (srcFrom,srcTo)=(00001000,00002000) 
     * if (destFrom,destTo)=(00000001,00000999), then return false; 
     * if (destFrom,destTo)=(00000001,00001999), then return true;
     * </pre>
     * 
     * @param srcFrom The beginning of the first range
     * @param srcTo The end of the first range
     * @param destFrom The beginning of the second range
     * @param destTo The end of the second range
     * @return if overlap return true;else false
     */
    private static boolean overlap(Long srcFrom, Long srcTo, Long destFrom, Long destTo) {
        int sfdt = srcFrom.compareTo(destTo);
        int stdf = srcTo.compareTo(destFrom);
        if (sfdt > 0 || stdf < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Comparative Regional, parameter is a string of type number Inside of A in B.
     * 
     * <pre>
     * Return value ：
     * true  : (destFrom, destTo) overlap (srcFrom, srcTo);
     * false : (destFrom, destTo) outside(srcFrom, srcTo);
     * 
     * Examples:
     * NumberUtils.overlapCompareForString("0000000050","0000000100", "0000000001", "0000001000") = true
     * NumberUtils.overlapCompareForString("0000001001","0000001999", "0000000001", "0000001000") = false
     * NumberUtils.overlapCompareForString("0000000000","00000019999", "0000000001", "0000001000") = true
     * </pre>
     * 
     * @param srcFrom a number type string
     * @param srcTo a number type string
     * @param destFrom a number type string
     * @param destTo a number type string
     * @return if overlap return true;else false
     * @author ljl
     */
    public static boolean overlapCompareForString(String srcFrom, String srcTo, String destFrom,
                                                  String destTo) {

        return overlap(Long.valueOf(srcFrom), Long.valueOf(srcTo), Long.valueOf(destFrom),
                Long.valueOf(destTo));

    }

    /**
     * Rounding the target figure in accordance with a given accuracy.
     * 
     * <pre>
     *  NumberUtils.round(123.454, 2) = 123.45;
     *  NumberUtils.round(123.456, 2) = 123.46);
     * </pre>
     * 
     * @param number the given number
     * @param precision the given precision
     * @return double
     */
    public static double roundHalfUp(double number, int precision) {
        BigDecimal bigDecimal = new BigDecimal(number)
                .setScale(precision, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * 
     * getLong. if null --> 0 ,other output itself.
     * 
     * @param origValue Long
     * @return Long
     */
    public static Long getLong(Long origValue) {
        return origValue == null ? 0 : origValue;
    }

    /**
     * 年月日-小时分钟秒毫秒-随机数(1-1000).
     * 
     * @return String
     */
    public static String getRandomNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date now = new Date();
        String s = sdf.format(now);
        Random rd = new Random();
        int rdNum = rd.nextInt(1000);
        String subDir = s + String.valueOf(rdNum);
        return subDir;
    }
}
