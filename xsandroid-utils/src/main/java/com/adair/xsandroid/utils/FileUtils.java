package com.adair.xsandroid.utils;

import com.adair.xsandroid.utils.constant.MemoryConstants;

import java.text.DecimalFormat;

/**
 * package：    com.adair.xsandroid.utils
 * author：     XuShuai
 * date：       2017/12/6  11:15
 * version:     v1.0
 * describe：   文件操作类
 */
public class FileUtils {
    /**
     * 文件大小单位转换
     *
     * @param size 文件大小，byte
     * @return 转换后的文件大小，带单位
     */
    public static String getDataSize(long size) {
        if (size < 0) {
            return "size error!";
        }
        DecimalFormat format = new DecimalFormat("#######.##");
        if (size < MemoryConstants.KB) {
            return size + "bytes";
        } else if (size < MemoryConstants.MB) {
            float kbSize = size * 1f / MemoryConstants.KB;
            return format.format(kbSize) + "KB";
        } else if (size < MemoryConstants.GB) {
            float mbSize = size * 1f / MemoryConstants.MB;
            return format.format(mbSize) + "MB";
        } else {
            float gbSize = size * 1f / MemoryConstants.GB;
            return format.format(gbSize) + "GB";
        }
    }
}
