package com.zyyapp.util.thread;

import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zyy
 * @date 2020-8-13
 */
@AllArgsConstructor
public class CmdThreadFactory implements ThreadFactory {
    private static AtomicInteger idx = new AtomicInteger();
    private String nameBase;

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
