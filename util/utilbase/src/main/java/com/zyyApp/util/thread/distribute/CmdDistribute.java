package com.zyyApp.util.thread.distribute;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.zyyApp.util.thread.NameThreadFactory;
import com.zyyApp.util.thread.cmd.ICommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多线程 指令转发器;
 * 内部使用 Disruptor 作为多线程指令转发;
 * 多生产者 模式
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
    public CmdDistribute(int buffSize, String threadNameBase, int csmNum) {
        if (buffSize <= 0 || csmNum <= 0) {
            throw new RuntimeException("CmdDistribute init param Error!!");
        }

        // 创建分发器;
        disruptor = new Disruptor<CmdEvent>(
                new CmdEvtFactory()
                , buffSize
                , new NameThreadFactory(threadNameBase)
                , ProducerType.MULTI            // 多生产者模式;
                ,new BlockingWaitStrategy()     // 消费者等待策略: 阻塞等待;
        );


        if (csmNum == 1) {
            // 事件处理器绑定; (单个消费者;)
            disruptor.handleEventsWith(new CmdEventHandler());
        } else {
            // 事件处理器绑定; (多个消费者 不 重复消费;)
            CmdWorkHandler[] consumers = new CmdWorkHandler[csmNum];
            for (int i = 0; i < consumers.length; i++) {
                consumers[i] = new CmdWorkHandler();
            }
            disruptor.handleEventsWithWorkerPool(consumers);
        }

        // 生产者类;
        producer = new CmdEvtProducer();
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
     * @param command,  待处理指令对象;
     */
    public void addCommand(ICommand command) {
        producer.onData(command);
    }
}
