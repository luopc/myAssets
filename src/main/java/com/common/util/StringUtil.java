package com.common.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 */
public final class StringUtil {

    /**
     * 字符串分隔符
     */
    public static final String SEPARATOR = String.valueOf((char) 29);

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * covert field name to column name userName --> user_name
     * covert class name to column name UserName -- > user_name
     * */
    public static String getUnderlineName(String propertyName) {
        if (null == propertyName) {
            return "";
        }

        StringBuilder sbl = new StringBuilder(propertyName);
        sbl.setCharAt(0, Character.toLowerCase(sbl.charAt(0)));
        propertyName = sbl.toString();

        char[] chars = propertyName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * covert field name to column name
     * */
    public static String getHumnName(String fieldName) {
        if (null == fieldName) {
            return "";
        }
        fieldName = fieldName.toLowerCase();
        char[] chars = fieldName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * 分割固定格式的字符串
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}