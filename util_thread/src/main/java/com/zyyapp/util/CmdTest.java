package com.zyyapp.util;

import com.zyyapp.util.cmd.BaseSmpCmd;
import com.zyyapp.util.distribute.CmdDistMgr;
import com.zyyapp.util.thread.CmdThread;
import com.zyyapp.util.thread.ServerThread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ServerThread, CmdThread, CmdDistMgr 性能比较测试;
 * @author zyy
 * @date 2020-8-13
 */
public class CmdTest {
    private static final int POOL_SIZE = 10;
    private static final int TEST_NUM = 100_0000;

    private static ServerThread[] stLst = new ServerThread[POOL_SIZE];
    private static CmdThread[] cmdTLst = new CmdThread[POOL_SIZE];
    private static CmdDistMgr distMgr = new CmdDistMgr(POOL_SIZE, 128, "CmdDist");

    private static long testBeg = 0;
    private static AtomicInteger atoI = new AtomicInteger(0);

    static class TCmd extends BaseSmpCmd {
        public int val;
        TCmd(int val) {
            this.val = val;
        }

        @Override
        public void action() {
            int cur = atoI.incrementAndGet();
            System.out.println(val);
            if (cur >= TEST_NUM) {
                long endT = System.currentTimeMillis();
                System.out.println("useTime:" + (endT-testBeg));

                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("ThreadGroup");

        // 老式 分发线程组;
        for (int i = 0; i < stLst.length; i++) {
            String threadName = "OldThread" + i;
            stLst[i] = new ServerThread(group, threadName);

            stLst[i].start();
        }

        // 指令 分发线程组;
        for (int i = 0; i < cmdTLst.length; i++) {
            String threadName = "CmdThread" + i;
            cmdTLst[i] = new CmdThread(group, threadName);

            cmdTLst[i].start();
        }

        // Disruptor 分发器;
        distMgr.start();

        // 测试数据;
        TCmd[] testLst = new TCmd[TEST_NUM];
        for (int i = 0; i < testLst.length; i++) {
            testLst[i] = new TCmd(i);
        }

        // 生产者线程;
        Thread[] produces = new Thread[3];
        for (int i = 0; i < produces.length; i++) {
            final int prodStart = i;
            produces[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = prodStart; j < testLst.length; j+=produces.length) {
                        TCmd cmd = testLst[j];

                        // ServerThread
                        // 测试结果(ms): 4276, 4483, 4724, 4683, 4898
//                        int idx = cmd.val % stLst.length ;
//                        stLst[idx].addCommand(cmd);

//                        // cmdTLst
                        // 测试结果(ms): 5133, 5603, 5527, 5449, 5802
                        int idx = cmd.val % cmdTLst.length ;
                        cmdTLst[idx].addCommand(cmd);

                        // CmdDistMgr
                        // 测试结果(ms): 4591, 4705, 4902, 4783, 4831
//                        distMgr.addCommand(cmd.val, cmd);
                    }
                }
            });
        }

        // 开始测试
        testBeg = System.currentTimeMillis();
        for (Thread prod : produces) {
            prod.start();
        }

    }
}
