package com.zyyapp.util.cmd;

public interface ICommand {
	/** 执行命令 */
	void action();

	/** 释放接口; */
	void release();

	/** 警告打印时间点 (毫秒);
	 * @return 超长处理 警告打印时间;  指令处理时间超过该值时, 将打印警告信息;
	 * */
	int warnTime();
}
