package com.zyyApp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具包中 日志对象集中管理类;
 *
 * @author zyy
 * @date 2020/08/13
 */
public class UtilBaseLog {
    /** slf4j 日志; normal 日志 */
    public static Logger nm = LoggerFactory.getLogger("normal");
    /** 数据库日志; */
    public static final Logger dbLog = LoggerFactory.getLogger("db");
    /** 异常日志; */
    public static final Logger excLog = LoggerFactory.getLogger("exception");

    /** 连接创建日志 */
    public static final Logger createLog = LoggerFactory.getLogger("connect");
    /** 连接闭数量日志 */
    public static final Logger closeLog = LoggerFactory.getLogger("disconnect");
    /** 指令监控日志 */
    public static final Logger handler = LoggerFactory.getLogger("handler");
    /** 消息调试日志 */
    public static final Logger debugMsg = LoggerFactory.getLogger("debugMsg");
}
