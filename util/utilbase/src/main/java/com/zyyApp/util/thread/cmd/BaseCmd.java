package com.zyyApp.util.thread.cmd;

import com.zyyApp.util.simple.ReUseBase;

/**
 * 普通处理指令 基类;
 *
 * @author zyy
 * @date 2018-8-7
 */
public abstract class BaseCmd extends ReUseBase implements ICommand {
    @Override
    public int warnTime() {
        return 30;
    }
}
