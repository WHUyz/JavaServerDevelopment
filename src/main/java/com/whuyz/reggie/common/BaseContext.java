package com.whuyz.reggie.common;
//基于ThreadLocal封装的工具了哦，用户保存和获取当前登录用户id
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    //设置值
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    //获取值
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
