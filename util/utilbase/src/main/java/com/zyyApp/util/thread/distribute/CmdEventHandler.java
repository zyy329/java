package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.EventHandler;

/**
 * 消息事件处理类，这里只打印消息
 * @author zyy
 * @date 2019-3-21
 */
public class CmdEventHandler implements EventHandler<CmdEvent> {
    @Override
    public void onEvent(CmdEvent msgEvent, long l, boolean b) throws Exception {
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
