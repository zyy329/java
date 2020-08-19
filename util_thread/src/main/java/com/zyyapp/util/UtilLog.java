package com.zyyapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具包中 日志对象集中管理类;
 *
 * @author zyy
 * @date 2020/08/13
 */
public class UtilLog {
    /** slf4j 日志; normal 日志 */
    public static Logger nm = LoggerFactory.getLogger("normal");
    /** 异常日志; */
    public static final Logger excLog = LoggerFactory.getLogger("exception");

    /** 指令监控日志 */
    public static final Logger handler = LoggerFactory.getLogger("handler");
}
