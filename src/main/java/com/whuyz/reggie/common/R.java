package com.whuyz.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

//通用返回结果类，服务端响应的数据都会封装成此对象，返回给前端
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    //成功:return返回结果类
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }
    //失败:返回状态码和错误信息
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
   //添加键值对到集合中并返回该对象
    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}