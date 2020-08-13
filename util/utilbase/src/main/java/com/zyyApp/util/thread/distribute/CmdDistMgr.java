package com.zyyApp.util.thread.distribute;

import com.zyyApp.util.thread.cmd.ICommand;

/**
 * 多线程 指令分发 管理器
 * 确保来自指定索引的消息, 一定被指定线程消费;
 * @author zyy
 * @date 2020-8-13
 */
public class CmdDistMgr {
    /** 消费转发器 数组 */
    private CmdDistribute[] csmLst;
    private int size;

    /**
     * @param csmThreadNum  消费者线程数量, 一经设定后不可再被改变; 内部实际是创建对应个数的 CmdDistribute(单消费者--单线程消费);
     * @param queSize       转发器, 环形队列长度;
     */
    public CmdDistMgr(int csmThreadNum, int queSize, String threadNameBase) {
        size = csmThreadNum;
        csmLst = new CmdDistribute[csmThreadNum];
        for (int i = 0; i < csmLst.length; i++) {
            csmLst[i] = new CmdDistribute(queSize, threadNameBase, 1);
        }
    }

    /** 启动 */
    public void start() {
        for (CmdDistribute distribute : csmLst) {
            distribute.start();
        }
    }

    /**
     * 添加指令(允许多线程添加)
     * @param csmIdx,   消费者索引; 保证指定索引每次投递指令都只会被同一线程消费;
     * @param command,  待处理指令对象;
     */
    public void addCommand(int csmIdx, ICommand command) {
        int idx = csmIdx % size;
        csmLst[idx].addCommand(command);
    }
}
