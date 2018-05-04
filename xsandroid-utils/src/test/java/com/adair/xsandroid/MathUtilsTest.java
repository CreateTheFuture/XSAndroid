package com.adair.xsandroid;

import com.adair.xsandroid.utils.MathUtils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * package：    com.adair.xsandroid
 * author：     XuShuai
 * date：       2018/1/5  10:36
 * version:     v1.0
 * describe：
 */
public class MathUtilsTest {

    @Test
    public void addTest() {
        assertEquals(0.345666, MathUtils.add(0.345665, 0.000001), 0.00000);
    }
}
