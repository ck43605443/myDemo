package com.nwc.demo01.service;


public class ThreadLocalUtil {
    // 定义本地线程
    public static ThreadLocal<String> tl = new ThreadLocal<>();

    /**
     * 设置本地线程中的用户
     *
     */
    public static void set(String value) {
        tl.set(value);
    }

    /**
     * 获取本地线程中的用户
     *
     * @return
     */
    public static String get() {
        return tl.get();
    }

    /**
     * 删除本地线程中的用户
     */
    public static void remove() {
        tl.remove();
    }
}
