package com.zyyApp.util.thread;

public interface ICommand extends Cloneable {
	// 执行命令
	public void action();

	// 获得创建时间
	public long getCreateTime();

	// 复制命令
	public Object clone() throws CloneNotSupportedException;

	// 释放接口;
	public void release();
}
