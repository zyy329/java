package com.zyyApp.util.leetcode;

import java.util.Random;

/**
 * 力扣 随机 0/1 二进制 测试用例 生成工具;
 * @author zyy
 * @date 2020-8-10
 */
public class ByteStr {
    /** 构建一个由 随机 0/1 组成的 字符串 */
    public static String genByteStr() {
        Random r = new Random();
        int size = r.nextInt(1000000)+1;
        if (size == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(r.nextInt(2));
        }
        return sb.toString();
    }
}
