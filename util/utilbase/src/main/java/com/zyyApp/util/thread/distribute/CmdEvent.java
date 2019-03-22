package com.zyyApp.util.thread.distribute;

import com.zyyApp.util.thread.ICommand;

/**
 * 指令分发事件 结构体;
 * @author zyy
 * @date 2019-3-22
 */
public class CmdEvent {
    /** 指定处理该事件的 消费者Id; 用于确保相同 类型ID 的事件, 始终会在相同的处理线程中进行处理;*/
    public int consumerId;
    /** 指令对象 */
    public ICommand cmd;
}
