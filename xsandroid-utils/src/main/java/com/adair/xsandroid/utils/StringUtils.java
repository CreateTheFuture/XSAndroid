package com.adair.xsandroid.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:20
 * version:     v1.0
 * describe：   String工具类
 */
public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为空或者长度为0
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 判断判断字符串是否为空或者长度为0，去除空格
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * 格式化带参数String
     *
     * @param resId String resId
     * @param args  参数
     * @return 目标String
     */
    public static String Format(int resId, Object... args) {
        return Utils.getContext().getString(resId, args);
    }

    /**
     * 判断两个String 是否相同
     *
     * @param arg1 the arg 1
     * @param arg2 the arg 2
     * @return boolean
     */
    public static boolean isEquals(String arg1, String arg2) {
        if (arg1 == null) {
            return arg2 == null;
        }
        return arg1.equals(arg2);
    }

    /**
     * 获取字符串长度
     *
     * @param arg1 the arg 1
     * @return int int
     */
    public static int length(CharSequence arg1) {
        return arg1 == null ? 0 : arg1.length();
    }


    /**
     * 将String 进行utf-8编码转换
     *
     * @param str the str
     * @return string
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != length(str)) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
