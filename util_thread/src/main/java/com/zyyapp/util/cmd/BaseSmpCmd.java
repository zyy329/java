package com.zyyapp.util.cmd;

/**
 * 简单普通处理指令 基类;
 * 默认 release() 为空;
 *
 * @author zyy
 * @date 2018-8-7
 */
public abstract class BaseSmpCmd implements ICommand {
    @Override
    public void release() {}

    @Override
    public int warnTime() {
        return 30;
    }
}
