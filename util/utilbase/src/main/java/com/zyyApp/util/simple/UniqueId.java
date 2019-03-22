package com.zyyApp.util.simple;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义的简单 唯一ID 生成器;
 * 生成 long 类型的唯一ID
 * Created by zyy on 2017/5/9.
 */
public class UniqueId {
    // 位与运算标签;
    private static final long SIGN_A = 4095L;           // 数据段A 运算标签; 12位数据对应的 十进制数字
    private static final long SIGN_B = 1073741823L;     // 数据段B 运算标签; 30位数据对应的 十进制数字
    private static final long SIGN_C = 4095L;           // 数据段C 运算标签; 12位数据对应的 十进制数字
    private static final long SIGN_D = 1023L;           // 数据段D 运算标签; 10位数据对应的 十进制数字

    private static final int  POS_A  = 12;              // 数据段A 所占位数;
    private static final int  POS_B  = 30;              // 数据段B 所占位数;
    private static final int  POS_C  = 12;              // 数据段C 所占位数;
    private static final int  POS_D  = 10;              // 数据段D 所占位数;

    // 自增值;
    private AtomicInteger idx = new AtomicInteger(7);

    /**
     * 唯一ID 生成接口;
     * 1.确保同一 UniqueId 对象, 生成的ID 唯一;
     * 2.线程安全;
     *
     * 数据结构说明: {C-12位}{D-10位}{A-12位}{B-30位}  --- 共64位
     *  A: 应用层标识数据; 供应用层自由使用, 以标识区分不同 UniqueId 生成的ID对象 -- 只要确保该值不同, 就可做到全局唯一;
     *      当前共12位,  允许最大值为 4095;
     *  B: 时间数据, 单位秒;
     *      当前共30位, 最大可表达 1073741823 秒 约等于 34年; 即 相隔34年以上的数据才会重复;
     *  C: 多线程自增数据;  保证同一秒钟下, 生成的ID 不同; -- 只要同一秒内生成的ID数 不超过该数据允许的最大值, 即不会产生重复;
     *      当前共12位, 最大允许值为 4095
     *  D: 保留位; 可根据以后需要, 自由扩展 C 或 A区域;
     *
     *  @param sign; 数据段A 标记数据;
     */
    public long getUniqueId(int sign) {
        long curTimeSec = System.currentTimeMillis() / 1000L;   // 当前秒数;
        long id = 0L;
        int curIdx = idx.getAndIncrement();

        // 数据段 C;
        //id <<= POS_C;
        id |= curIdx & SIGN_C;

        // 数据段D;
        id <<= POS_D;

        // 数据段A;
        id <<= POS_A;
        id |= sign & SIGN_A;

        // 数据段B;
        id <<= POS_B;
        id |= curTimeSec & SIGN_B;

        return id;
    }
}
