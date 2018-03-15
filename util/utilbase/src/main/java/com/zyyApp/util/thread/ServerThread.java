package com.zyyApp.util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 游戏服务器主线程
 */
public class ServerThread extends Thread {
	// 日志
	private static Logger log =  LoggerFactory.getLogger(ServerThread.class);
	private static Logger handlerlog = LoggerFactory.getLogger("HandlerLog");
	// 命令执行队列
	private LinkedBlockingQueue<ICommand> command_queue = new LinkedBlockingQueue<ICommand>();
	// 线程名称
	protected String threadName;
	// 运行标志
	private boolean stop;

	private boolean processingCompleted = false;

	public ServerThread(ThreadGroup group, String threadName) {
		super(group, threadName);
		this.threadName = threadName;
		this.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.error("Main Thread UncaughtExceptionHandler:" + e.getMessage(), e);
				stop = true;
				command_queue.clear();
			}
		});
	}

	public void run() {

		stop = false;
		int loop = 0;
		while (!stop) {
			ICommand command = command_queue.poll();
			if (command == null) {
				try {
					synchronized (this) {
						loop = 0;
						processingCompleted = true;
						wait();
					}
				} catch (Exception e) {
					log.error("Main Thread " + threadName + " Exception1:" + e.getMessage(), e);
				}
			} else {
				try {
					loop++;
					processingCompleted = false;
					long start = System.currentTimeMillis();
					command.action();

					long end = System.currentTimeMillis();
					if (end - start > 10)
						handlerlog.info(
								this.getName() + "-->" + command.getClass().getSimpleName() + " run:" + (end - start));
					if (loop % 1000 == 0) {
						if (loop > 200000000)
							loop = 0;
						handlerlog.info(this.getName() + "剩余指令数量:" + command_queue.size() + ", 已经执行指令数量:" + loop);
						// try {
						// Thread.sleep(1);
						// } catch (Exception e) {
						// log.error(e, e);
						// }
					}
				} catch (Exception e) {
					log.error("Main Thread " + threadName + " Exception2:" + e.getMessage(), e);
				}

				command.release();
			}
		}
	}

	public void stop(boolean flag) {
		// log.error("服务器线程" + threadName + "停止。");
		// try {
		// throw new Exception();
		// } catch (Exception e) {
		// log.error(e, e);
		// }
		stop = flag;
		this.command_queue.clear();
		try {
			synchronized (this) {
				if (processingCompleted) {
					processingCompleted = false;
					notify();
				}
			}
		} catch (Exception e) {
			log.error("Main Thread " + threadName + " Notify Exception1:" + e.getMessage(), e);
		}
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
			this.command_queue.add(command);
			if (!processingCompleted) {
				return;
			}
			synchronized (this) {
				if (processingCompleted) {
					processingCompleted = false;
					notify();
				}
			}
		} catch (Exception e) {
			log.error("Main Thread " + threadName + " Notify Exception2:" + e.getMessage(), e);
		}
	}

	public String getThreadName() {
		return threadName;
	}

	public boolean isStop() {
		return stop;
	}
}
