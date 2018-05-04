package com.adair.xsandroid.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * package：    com.adair.xsandroid.utils.constant
 * author：     XuShuai
 * date：       2017/12/6  13:37
 * version:     v1.0
 * describe：   存储相关常量
 */
public final class MemoryConstants {
    /**
     * Byte和Byte的倍数
     */
    public static final int BYTE = 1;

    /**
     * KB和Byte的倍数
     */
    public static final int KB = 1024;

    /**
     * MB和Byte的倍数
     */
    public static final int MB = 1048576;

    /**
     * GB和Byte的倍数
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {

    }
}
