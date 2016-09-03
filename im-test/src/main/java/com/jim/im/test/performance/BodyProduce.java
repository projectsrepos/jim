/*
 * Copyright (c) 2016 by XuanWu Wireless Technology Co.Ltd. All rights reserved
 */
package com.jim.im.test.performance;

import java.util.Random;

/**
 * 
 * @author <a href="huzhiwen@wxchina.com">huzhiwen</a>
 * @date 2016年5月20日
 * @version 1.0.0
 */
public class BodyProduce {

    private static Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;

    private static char getRandomHan() {
        return (char) (0x4e00 + ran.nextInt(delta));
    }

    /**
     * random create Length String
     * 
     * @param length
     * @return
     */
    public static byte[] randomLengthString(int length) {
        return generateDynamicLengthContent(length).getBytes();
    }

    public static String generateDynamicLengthContent(int length) {
        if (length == 0)
            return "";
        char[] chs = new char[length];
        for (int j = 0; j < chs.length; j++) {
            chs[j] = getRandomHan();
        }
        return new String(chs);
    }
}
