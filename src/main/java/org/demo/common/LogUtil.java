package org.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类，封装SLF4J日志操作
 */
public class LogUtil {

    /**
     * 获取指定类的日志记录器
     *
     * @param clazz 类
     * @return 日志记录器
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * 获取指定名称的日志记录器
     *
     * @param name 日志记录器名称
     * @return 日志记录器
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * 获取指定对象的日志记录器
     * @param logger 对象
     * @return 日志记录器
     */
    public static Logger getLogger(Object logger) {
        return logger instanceof String ? getLogger(logger.toString()) : getLogger(logger.getClass());
    }


    /**
     * 记录TRACE级别日志
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param args 参数
     */
    public static void trace(Object logger, String message, Object... args) {
        getLogger(logger).trace(message, args);
    }

    /**
     * 记录TRACE级别日志，带异常
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param t 异常
     */
    public static void trace(Object logger, String message, Throwable t) {
        getLogger(logger).trace(message, t);
    }

    /**
     * 记录DEBUG级别日志
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param args 参数
     */
    public static void debug(Object logger, String message, Object... args) {
        getLogger(logger).debug(message, args);
    }

    /**
     * 记录DEBUG级别日志，带异常
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param t 异常
     */
    public static void debug(Object logger, String message, Throwable t) {
        getLogger(logger).debug(message, t);
    }

    /**
     * 记录INFO级别日志
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param args 参数
     */
    public static void info(Object logger, String message, Object... args) {
        getLogger(logger).info(message, args);
    }

    /**
     * 记录INFO级别日志，带异常
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param t 异常
     */
    public static void info(Object logger, String message, Throwable t) {
        getLogger(logger).info(message, t);
    }

    /**
     * 记录WARN级别日志
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param args 参数
     */
    public static void warn(Object logger, String message, Object... args) {
        getLogger(logger).warn(message, args);
    }

    /**
     * 记录WARN级别日志，带异常
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param t 异常
     */
    public static void warn(Object logger, String message, Throwable t) {
        getLogger(logger).warn(message, t);
    }

    /**
     * 记录ERROR级别日志
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param args 参数
     */
    public static void error(Object logger, String message, Object... args) {
        getLogger(logger).error(message, args);
    }

    /**
     * 记录ERROR级别日志，带异常
     *
     * @param logger 日志记录器对象或名称
     * @param message 日志消息
     * @param t 异常
     */
    public static void error(Object logger, String message, Throwable t) {
        getLogger(logger).error(message, t);
    }


}
