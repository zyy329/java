package com.zyyapp.util.distribute;

import com.lmax.disruptor.WorkHandler;

/**
 * @author zyy
 * @date 2020-8-13
 */
public class CmdWorkHandler implements WorkHandler<CmdEvent> {
    @Override
    public void onEvent(CmdEvent cmdEvent) throws Exception {
        // 事件处理;
        try {
            cmdEvent.cmd.action();
        } catch (Exception e) {
            CmdDistribute.log.error("", e);
        } finally {
            cmdEvent.cmd.release();
        }
    }
}
