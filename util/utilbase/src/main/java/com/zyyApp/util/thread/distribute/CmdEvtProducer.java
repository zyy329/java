package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.RingBuffer;
import com.zyyApp.util.thread.ICommand;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息生产者类
 *
 * @author zyy
 * @date 2019-3-22
 */
public class CmdEvtProducer {
    private RingBuffer<CmdEvent> ringBuffer;
    /** 处理索引, 当未指定proId时, 自动顺序生成一个; */
    private AtomicInteger proIdCreater = new AtomicInteger();
    /** 消费者总数量(处理线程数量) */
    private int consumerNum = 0;

    public CmdEvtProducer(int consumerNum) {
        this.consumerNum = consumerNum;
    }
    public void init(RingBuffer<CmdEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将接收到的消息输出到ringBuffer
     * @param proId,    事件 处理类型ID; < 0 时, 会自动顺序生成一个;
     * @param command,  待处理指令对象;
     */
    public void onData(int proId, ICommand command) {
        if (proId < 0) {
            proId = proIdCreater.getAndIncrement();
        }

        // 计算获得消费者Id;
        int consumerId = Math.abs(proId) % consumerNum;

        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            CmdEvent event = ringBuffer.get(sequence);
            event.consumerId = consumerId;
            event.cmd = command;
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
