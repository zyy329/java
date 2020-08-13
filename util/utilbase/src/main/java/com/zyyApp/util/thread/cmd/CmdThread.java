package com.zyyApp.util.thread.cmd;

import com.zyyApp.util.UtilBaseLog;

/**
 * 通用 指令 处理线程;
 * 在一个单独的线程, 顺序执行一系列加入的指令
 * 可以用于服务器消息处理;
 */
public class CmdThread extends Thread {
	/** 默认队列大小 */
	private static final int DEFAULT_QUEUE_SIZE = 1024;

	/** 指令执行队列 */
	private CmdQueue queue;
	// 线程名称
	private String threadName;
	// 运行标志
	private boolean stop;


	public CmdThread(ThreadGroup group, String threadName) {
		this(group, threadName, DEFAULT_QUEUE_SIZE);
	}
	public CmdThread(ThreadGroup group, String threadName, int queueSize) {
		super(group, threadName);
		this.threadName = threadName;
		this.queue = new CmdQueue("", queueSize);

		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				UtilBaseLog.excLog.error("threadName:{} UncaughtExceptionHandler:", threadName, e);
				stopThread(true);
			}
		});
	}

	@Override
	public void run() {
		stop = false;
		while (!stop) {
			queue.runCmd();
		}
	}

	public void stopThread(boolean flag) {
	 	UtilBaseLog.nm.info("服务器线程{} 停止。", threadName);
		stop = flag;
		queue.clear();
	}

	/**
	 * 添加命令
	 * 
	 * @param command
	 *            命令
	 */
	public void addCommand(ICommand command) {
		if (stop) {
			return;
		}
		try {
			queue.addCommand(command);
		} catch (Exception e) {
			UtilBaseLog.excLog.error("ThreadName:{}", threadName, e);
		}
	}

	public boolean isStop() {
		return stop;
	}
}
