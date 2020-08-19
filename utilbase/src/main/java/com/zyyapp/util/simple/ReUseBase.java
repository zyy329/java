package com.zyyapp.util.simple;

/**
 * Created by zyy on 2018-8-27.
 */
public abstract class ReUseBase {
    public void release() {
        ReUsePool.Push(this);
    }
}
