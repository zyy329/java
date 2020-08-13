package com.zyyApp.util.thread;

import com.zyyApp.util.thread.cmd.BaseSmpCmd;
import com.zyyApp.util.thread.distribute.CmdDistMgr;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ServerThread 和 CmdDistMgr 性能比较测试;
 * @author zyy
 * @date 2020-8-13
 */
public class CmdTest {
    private static final int POOL_SIZE = 10;
    private static final int TEST_NUM = 100_0000;

    private static CmdDistMgr distMgr = new CmdDistMgr(POOL_SIZE, 128, "CmdDist");
    private static ServerThread[] stLst = new ServerThread[POOL_SIZE];
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
        // 线程组;
        ThreadGroup group = new ThreadGroup("ThreadGroup");
        for (int i = 0; i < stLst.length; i++) {
            String threadName = "PlayerThread" + i;
            stLst[i] = new ServerThread(group, threadName);

            stLst[i].start();
        }

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

                        // CmdDistMgr
                        // 测试结果(ms): 4591, 4705, 4902, 4783, 4831
                        distMgr.addCommand(cmd.val, cmd);
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
