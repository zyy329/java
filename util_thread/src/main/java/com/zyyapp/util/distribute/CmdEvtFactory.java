package com.zyyapp.util.distribute;

import com.lmax.disruptor.EventFactory;

/**
 * 指令事件工厂类
 * @author zyy
 * @date 2019-3-21
 */
public class CmdEvtFactory implements EventFactory<CmdEvent> {
    @Override
    public CmdEvent newInstance() {
        return new CmdEvent();
    }
}
