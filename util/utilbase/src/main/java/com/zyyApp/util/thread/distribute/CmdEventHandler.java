package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.EventHandler;

/**
 * 消息事件处理类，这里只打印消息
 * @author zyy
 * @date 2019-3-21
 */
public class CmdEventHandler implements EventHandler<CmdEvent> {
    /** 消费者Id号; */
    private int consumerId;

    public CmdEventHandler(int consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public void onEvent(CmdEvent msgEvent, long l, boolean b) throws Exception {
        // 排除不相符的 consumerId; 确保 相同 类型ID 的事件, 始终会在相同的处理线程中进行处理;
        if (msgEvent.consumerId != this.consumerId) {
            return;
        }

        // 事件处理;
        try {
            msgEvent.cmd.action();
        } catch (Exception e) {
            CmdDistribute.log.error("", e);
        } finally {
            msgEvent.cmd.release();
        }
    }
}
