package com.zyyapp.util.cmd;

import com.zyyapp.util.UtilLog;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 指令执行队列;
 * 接受来自多个线程的指令添加; 在指定的一个线程中去进行处理;
 *
 * @author zyy
 * @date 2020-1-4
 */
public class CmdQueue {
    /**
     * 指令执行队列
     */
    private ArrayBlockingQueue<ICommand> cmdQueue;
    /**
     * 执行计数; 用于指令处理监控;
     */
    private int loop;
    /**
     * 队列名; 用于 日志打印信息;
     */
    private String name;

    public CmdQueue(String name, int size) {
        this.name = name;
        this.loop = 0;
        this.cmdQueue = new ArrayBlockingQueue<>(size, true);
    }

    public void clear() {
        cmdQueue.clear();
    }

    /**
     * 执行一条指令;
     *
     */
    public void runCmd() {
        ICommand command = null;
        try {
            //获取不到指令将会阻塞该执行线程
            command = cmdQueue.take();
            loop++;
            long start = System.currentTimeMillis();
            command.action();
            long end = System.currentTimeMillis();

            if (end - start > command.warnTime()) {
                UtilLog.handler.info("{};超长处理时间指令: {} -- {}", log(), command.getClass(), end - start);
            }
            if (loop % 1000 == 0) {
                if (loop > 200000000) {
                    loop = 0;
                }
                UtilLog.handler.info("{}; 指令处理监控: [剩余: {}], [已执行: {}]", log(), cmdQueue.size(), loop);
            }
        } catch (Exception e) {
            UtilLog.excLog.error("{}", log(), e);
        } finally {
            if (command != null) {
                command.release();
            }
        }
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return cmdQueue.isEmpty();
    }

    /**
     * 添加待执行指令;
     *
     * @param command 指令
     */
    public void addCommand(ICommand command) {
        try {
            //增加指令, 已满会阻塞;
            cmdQueue.put(command);
        } catch (Exception e) {
            UtilLog.excLog.error("{}", log(), e);
        }
    }

    private String log() {
        return String.format("[threadName:%s - queName:%s]; ", Thread.currentThread().getName(), name);
    }
}
