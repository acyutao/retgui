/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * provide tools for operated string.
 * 
 * @version Seurat v1.0
 * @author Li Jielin, 2012-6-27
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static final int BASE_NUMBER = 36;

    /** default constructor. **/
    private StringUtils() {
    }

    /**
     * <p>
     * Checks if a String is empty ("") or null.
     * </p>
     * 
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     * 
     * <p>
     * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That
     * functionality is available in isBlank().
     * </p>
     * 
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * safe get string. if str is null return {@link StringUtils.EMPTY}.
     * 
     * @param str str
     * @return if str is null return {@link StringUtils.EMPTY}.
     */
    public static String getString(String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * concatenate strings.
     * 
     * <pre>
     * examples: 
     *  StringUtils.concat("a", "b", "c") = "abc"
     *  StringUtils.concat("a", null, "c") = "ac"
     * </pre>
     * 
     * @param values strings
     * @return string
     */
    public static String concat(String... values) {
        return concat("", values);
    }

    /**
     * concatenate string with separator.
     * 
     * <pre>
     * examples: 
     *  StringUtils.concat(",", new String[] { "a", "b", "c" }) = "a,b,c"
     *  StringUtils.concat(",", new String[] { "a", null, "c" }) = "a,c"
     *  StringUtils.concat(",", new String[] { "a", "", "c" }) = "a,c"
     * </pre>
     * 
     * @param separator separator string
     * @param values values
     * @return String
     */
    public static String concat(String separator, String[] values) {
        if (values == null || values.length == 0) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }
        StringBuilder sb = new StringBuilder(64);
        for (String s : values) {
            if (!isBlank(s)) {
                sb.append(s).append(separator);
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * translate value to float, and then calculate (value * multiplier).
     * 
     * <pre>
     * examples: 
     * StringUtils.getFloat("100", 3) = 300f
     * StringUtils.getFloat("aaa", 3) = 0f
     * </pre>
     * 
     * @param value string
     * @param multiplier multiplier
     * @return Float
     */
    public static Float multiToFloat(String value, int multiplier) {
        try {
            return Float.parseFloat(value) * multiplier;
        } catch (Exception e) {
        }
        return 0F;
    }

    /**
     * translate value to long, calculate (value * multiplier).
     * 
     * <pre>
     * examples: 
     * StringUtils.getLong("100", 5) = 500L
     * StringUtils.getLong("aaa", 5) = 0L
     * </pre>
     * 
     * @param value String
     * @param multiplier multiplier
     * @return Long
     */
    public static Long multiToLong(String value, int multiplier) {
        try {
            return Long.parseLong(value) * multiplier;
        } catch (Exception e) {
        }
        return 0L;
    }

    /**
     * Check that the given String is neither <code>null</code> nor of length 0. Note: Will return
     * <code>true</code> for a String that purely consists of whitespace.
     * <p>
     * 
     * <pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     * 
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check whether the given String has actual text. More specifically, returns <code>true</code>
     * if the string not <code>null</code>, its length is greater than 0, and it contains at least
     * one non-whitespace character.
     * <p>
     * 
     * <pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     * 
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not <code>null</code>, its length is greater than
     *         0, and it does not contain whitespace only
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * whether the string is decimal.
     * 
     * <pre>
     * StringUtils.isBigDecimal("")=false
     * StringUtils.isBigDecimal("abc")=false
     * StringUtils.isBigDecimal("123")=true;
     * StringUtils.isBigDecimal("123.4")=true;
     * </pre>
     * 
     * @param str string
     * @return boolean
     */
    public static boolean isBigDecimal(String str) {
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * when the object is null or blank string, returns true.
     * 
     * <pre>
     * StringUtils.isBlank(new Object())=false
     * StringUtils.isBlank(null)=true
     * StringUtils.isBlank("")=true
     * StringUtils.isBlank(" ")=true
     * StringUtils.isBlank("abc")=false
     * </pre>
     * 
     * @param value target instance
     * @return boolean
     */
    public static boolean isBlank(Object value) {
        return ((value == null) || ((value instanceof String) && ((String) value).trim().length() == 0));
    }

    /**
     * truncate string to specified length.
     * 
     * <pre>
     * StringUtils.truncate("abcdefghijklmn", 5, true)="ab..."
     * StringUtils.truncate("abcdefghijklmn", 5, false)="abcde"
     * StringUtils.truncate("", 0, false)=""
     * </pre>
     * 
     * @param str string
     * @param length length
     * @param indicator indicator
     * @return String
     */
    public static String truncate(String str, int length, boolean indicator) {
        if (isBlank(str) || str.length() <= length) {
            return str;
        }
        if (indicator) {
            return str.substring(0, length - 3) + "...";
        } else {
            return str.substring(0, length);
        }
    }

    /**
     * SubString.
     * 
     * <pre>
     * StringUtils.subString("abcdefghijklmn", 5, 18)="fghijklmn"
     * StringUtils.truncate("abcdefghijklmn", 5, 7)="fg"
     * </pre>
     * 
     * @param str String
     * @param beginIndex int
     * @param endIndex int
     * @return String
     */
    public static String subString(String str, int beginIndex, int endIndex) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return org.apache.commons.lang.StringUtils.substring(str, beginIndex, endIndex);

    }

    /**
     * SubString.
     * 
     * @param str str
     * @param isPrefix if use prefix of the merchant number this param is true,else set this to
     *            false.
     * @param length numLength
     * @return String
     */
    public static String subStr(String str, boolean isPrefix, int length) {
        String trimStr = str.trim();
        if (trimStr.length() > length) {
            if (isPrefix) {
                return trimStr.substring(0, length);
            } else {
                return trimStr.substring(trimStr.length() - length, trimStr.length());
            }
        }
        return trimStr;
    }

    /**
     * Trim All String Field in Object.
     * 
     * @param obj Object
     */
    public static void trimAllField(Object obj) {
        if (obj == null) {
            return;
        }

        Class clazz = obj.getClass();
        Class cls = clazz;
        while (!cls.equals(Object.class)) {

            try {

                Field[] fields = cls.getDeclaredFields();

                for (Field f : fields) {
                    f.setAccessible(true);

                    if (Modifier.isFinal(f.getModifiers())) {
                        continue;
                    }

                    Object value = f.get(obj);

                    if (value instanceof String) {
                        f.set(obj, org.apache.commons.lang.StringUtils.trim((String) value));
                    }
                }

            } catch (Exception e) {
                System.out.println("string utils errors");
            }

            cls = cls.getSuperclass();
        }
    }

    /**
     * org.apache.commons.lang.StringUtils.trim.
     * 
     * @param value String
     * @return String
     */
    public static String trim(String value) {
        return org.apache.commons.lang.StringUtils.trim(value);
    }

    /**
     * trim right blank.
     * 
     * @param value String
     * @return String
     */
    public static String rightTrim(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        while (value.lastIndexOf(" ") == value.length() - 1) {
            value = value.substring(0, value.length() - 1);
        }
        return value;

    }

    /**
     * trim right blank.
     * 
     * @param obj String
     */
    public static void rightTrimAllField(Object obj) {
        if (obj == null) {
            return;
        }

        Class clazz = obj.getClass();
        Class cls = clazz;
        while (!cls.equals(Object.class)) {

            try {

                Field[] fields = cls.getDeclaredFields();

                for (Field f : fields) {
                    f.setAccessible(true);

                    if (Modifier.isFinal(f.getModifiers())) {
                        continue;
                    }

                    Object value = f.get(obj);

                    if (value instanceof String) {
                        f.set(obj, rightTrim((String) value));
                    }
                }

            } catch (Exception e) {
                System.out.println("string utils errors");
            }

            cls = cls.getSuperclass();
        }

    }

//    public static void main(String[] args) {
//        String str = "   123   456   ";
//        System.out.println(str);
//        System.out.println(rightTrim(str));
//    }

    /**
     * format args by number. for example :
     * 
     * <pre>
     * String s = &quot;example&quot;;
     * int d = 12345;
     * printf(&quot;%-20s&quot;, s);
     * printf(&quot;%020d&quot;, d);
     * </pre>
     * 
     * @param format string format
     * @param args args
     * @return format string.
     */
    public static String printfNumber(String format, Object... args) {
        return printf(format, args);
    }

    private static int getLengthFromFormat(String str) {
        String s = "\\d+";

        Pattern pattern = Pattern.compile(s);
        Matcher ma = pattern.matcher(str);
        String length = "";
        while (ma.find()) {
            length += (ma.group());
        }
        return Integer.parseInt(length);
    }

    /**
     * format args by string. for example :
     * 
     * <pre>
     * String s = &quot;example&quot;;
     * int d = 12345;
     * printf(&quot;%-20s&quot;, s);
     * printf(&quot;%020d&quot;, d);
     * </pre>
     * 
     * @param format string format
     * @param args args
     * @return format string.
     */

    public static String printfString(String format, Object... args) {
        return printf(format, args);
    }

    /**
     * format args . for example :
     * 
     * <pre>
     * String s = &quot;example&quot;;
     * int d = 12345;
     * printf(&quot;%-20s&quot;, s);
     * printf(&quot;%020d&quot;, d);
     * </pre>
     * 
     * @param format string format
     * @param args args
     * @return format string.
     */
    public static String printf(String format, Object... args) {
        if (format == null) {
            return null;
        }
        if (args == null || args.length == 0 || args[0] == null) {
            if (format.indexOf("d") < 0) {
                return String.format(format, "");
            } else {
                return String.format(format, 0);
            }
        }

        // CSI COR FPTI do not need to trim
//        if (args.length == 1 && args[0] instanceof String) {
//            args[0] = ((String) args[0]).trim();
//        }
        if (args.length == 1 && args[0] instanceof Collection) {
            @SuppressWarnings("rawtypes")
            Collection list = (Collection) args[0];
            if (list.isEmpty()) {
                return format;
            } else {
                args = list.toArray();
            }
        }
        return String.format(format, args);
    }

    /**
     * zeroPrefixString.
     * 
     * @param length length
     * @param stringValue content
     * @return String
     * @see org.iata.seurat.app.report.core.ASCIIWriter#zeroPrefixString(int, java.lang.String)
     */
    public static String zeroPrefixString(int length, String stringValue) {
        String content = stringValue;
        if (content == null) {
            content = "";
        }
        if (content.length() > length) {
            return content.substring(0, length);
        } else if (content.length() == length) {
            return content;
        } else {
            int zeroNum = length - content.length();
            return printf("%0" + zeroNum + "d", 0).concat(content);
        }
    }

    /**
     * matchStrEquals.
     * 
     * @param matchStr String
     * @param otherStr String
     * @param pattern String
     * @return boolean
     */
    public static boolean matchStrEquals(String matchStr, String otherStr, String pattern) {

        if (matchStr == null && otherStr == null) {
            return true;
        }

        if (matchStr == null && otherStr != null) {
            return false;
        }

        if (matchStr != null && otherStr == null) {
            return false;
        }

        if (matchStr.equals(otherStr)) {
            return true;
        }

        if (matchStr.length() != otherStr.length()) {
            return false;
        }

        for (int i = 0; i < matchStr.length(); i++) {
            String matchSubStr = matchStr.substring(i, i + 1);
            if (!pattern.contains(matchSubStr)) {
                if (!matchSubStr.equals(otherStr.substring(i, i + 1))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Capitalizes a String changing the first letter to title case as per. No other letters are
     * changed.
     * 
     * <pre>
     * StringUtils.capitalize(null)  = null
     * StringUtils.capitalize("")    = ""
     * StringUtils.capitalize("cat") = "Cat"
     * StringUtils.capitalize("cAt") = "CAt"
     * </pre>
     * 
     * @param str the String to capitalize, may be null
     * @return the capitalized String, <code>null</code> if null String input
     */
    public static String capitalize(String str) {
        return org.apache.commons.lang.StringUtils.capitalize(str);
    }

    /**
     * StringUtils.ltrim("   cAt   ") = "CAt   "
     * 
     * @param s String
     * @return String
     */
    public static String ltrim(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                s = s.substring(i, s.length());
                break;
            }
        }
        return s;
    }

    /**
     * special Char Validate.
     * 
     * String seventh is '?', last char is '!'. Yes return true, No return false.
     * 
     * <pre>
     * StringUtils.specialCharValidate(&quot;1w2dsq6?8ffsee!&quot;) = true
     * </pre>
     * 
     * @param str String
     * @return boolean
     */
    public static boolean specialCharValidate(String str) {
        if (str == null) {
            return false;
        }

        String seventhChar = "";
        if (13 <= str.length() && str.length() <= 19) {
            seventhChar = String.valueOf(str.charAt(6));
        } else {
            return false;
        }

        String lastChar = String.valueOf(str.charAt(str.length() - 1));

        if ("?".equals(seventhChar) && "!".equals(lastChar)) {
            return true;
        }

        return false;
    }

    private static Long baseDecode(String baseCode) {
        return Long.parseLong(baseCode, BASE_NUMBER);
    }

    /**
     * Restore original PAN last 4 digits from given token This is never used currently.
     * 
     * @param token
     * @return
     */
    public static String extractPanTailFromToken(String token) {
        if (token.trim() == null || "".equals(token.trim()) || token.trim().isEmpty()) {
            return "";
        }
        String base36Code = token.substring(token.indexOf('?') + 1, token.length() - 1);
        String originNumber = String.valueOf(baseDecode(base36Code));
        return originNumber.substring(originNumber.length() - 4);
    }
}
