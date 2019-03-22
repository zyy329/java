package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.zyyApp.util.thread.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/**
 * 多线程 指令分发器;
 * 确保相同 处理类型ID(proId) 的事件, 始终会在相同的处理线程中进行处理;
 * 内部使用 Disruptor 作为多线程分发器;
 * @author zyy
 * @date 2019-3-22
 */
public class CmdDistribute {
    public static Logger log =  LoggerFactory.getLogger(CmdDistribute.class);

    /** Disruptor 分发器 */
    private Disruptor<CmdEvent> disruptor = null;
    /** 消息生产者类 */
    private CmdEvtProducer producer = null;

    /** 构造初始化 */
    public CmdDistribute(int buffSize, int procThreadNum) {
        if (procThreadNum <= 0) {
            log.error("初始化参数错误; procThreadNum:{}", procThreadNum);
            return;
        }

        // 创建分发器;
        disruptor = new Disruptor<CmdEvent>(
                new CmdEvtFactory()
                , buffSize
                , Executors.defaultThreadFactory()
                , ProducerType.MULTI            // 多生产者模式;
                ,new BlockingWaitStrategy()     // 消费者等待策略: 阻塞等待;
        );

        // 事件处理器绑定; (多个消费者重复消费;)
        for (int i = 0; i < procThreadNum; i++) {
            disruptor.handleEventsWith(new CmdEventHandler(i));
        }

        // 生产者类;
        producer = new CmdEvtProducer(procThreadNum);
    }

    /**
     * 启动分发器;
     */
    public void start() {
        // 开始; Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<CmdEvent> ringBuffer = disruptor.getRingBuffer();

        // 生产者类绑定 ringBuffer
        producer.init(ringBuffer);
    }

    /**
     * 添加指令(允许多线程添加)
     * @param proId,    事件 处理类型ID, 用于确保相同 类型ID 的事件, 始终会在相同的处理线程中进行处理;
     *                  < 0 时, 会自动顺序生成一个;
     * @param command,  待处理指令对象;
     */
    public void addCommand(int proId, ICommand command) {
        producer.onData(proId, command);
    }
}
