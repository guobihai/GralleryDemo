package com.h.grallerydemo.proxy.interfaces;

import com.h.grallerydemo.proxy.entry.MethodParam;

/**
 * 接口切入
 */
public interface InvokeCallBackInterface {

    /**
     * 执行前
     * @param param
     */
    void beforeMethod(MethodParam param);

    /**
     * 执行后
     * @param param
     */
    void afterMethod(MethodParam param);
}
