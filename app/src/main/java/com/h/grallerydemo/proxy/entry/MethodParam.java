package com.h.grallerydemo.proxy.entry;

import java.lang.reflect.Member;

/**
 * 拦截返回的实体
 */
public class MethodParam {
    public Object[] args = null;
    private Member method;
    private Object result;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Member getMethod() {
        return method;
    }

    public void setMethod(Member method) {
        this.method = method;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
