package com.zyyapp.util.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 * 主要提供对线程名的设定
 * @author zyy
 * @date 2019-5-13
 */
@AllArgsConstructor
public class NameThreadFactory implements ThreadFactory {
    private static AtomicInteger idx = new AtomicInteger();
    private String nameBase;

    @Override
    public Thread newThread(Runnable r) {
        String name = String.format("%s_%d", nameBase, idx.getAndIncrement());
        return new Thread(r, name);
    }
}
