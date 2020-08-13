package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.RingBuffer;
import com.zyyApp.util.thread.cmd.ICommand;

/**
 * 消息生产者类
 *
 * @author zyy
 * @date 2019-3-22
 */
public class CmdEvtProducer {
    private RingBuffer<CmdEvent> ringBuffer;

    public void init(RingBuffer<CmdEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将接收到的消息输出到ringBuffer
     * @param command,  待处理指令对象;
     */
    public void onData(ICommand command) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            CmdEvent event = ringBuffer.get(sequence);
            event.cmd = command;
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
