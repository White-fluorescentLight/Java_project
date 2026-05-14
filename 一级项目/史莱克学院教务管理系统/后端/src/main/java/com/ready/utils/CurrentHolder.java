package com.ready.utils;

/**
 * 使用ThreadLocal存储当前登录用户的ID
 */
public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    /**
     * 设置当前用户ID
     */
    public static void setCurrentId(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    /**
     * 获取当前用户ID
     */
    public static Integer getCurrentId() {
        return CURRENT_LOCAL.get();
    }

    /**
     * 移除当前用户ID
     */
    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
