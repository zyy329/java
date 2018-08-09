package com.zyyApp.util.thread;

public interface ICommand {
	// 执行命令
	public void action();

	// 获得创建时间
	public long getCreateTime();

	// 释放接口;
	public void release();
}
