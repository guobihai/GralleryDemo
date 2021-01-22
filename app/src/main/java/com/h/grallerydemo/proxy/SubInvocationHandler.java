package com.h.grallerydemo.proxy;

import android.support.annotation.NonNull;

import com.h.grallerydemo.proxy.entry.MethodParam;
import com.h.grallerydemo.proxy.interfaces.InvokeCallBackInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 共有业务类
 */
public class SubInvocationHandler<T> implements InvocationHandler {

    private T subject;

    private InvokeCallBackInterface methodInvokeInterface;


    public SubInvocationHandler(@NonNull T subject,InvokeCallBackInterface methodInvokeInterface) {
        this.subject = subject;
        this.methodInvokeInterface = methodInvokeInterface;
        if (this.subject.getClass().getInterfaces().length <= 0) {
            throw new IllegalArgumentException(subject.getClass().getName() + " 没有实现对应的代理接口");
        }
    }


    public T newProxyInstance() {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), subject.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParam param = new MethodParam();
        if (null != methodInvokeInterface) {
            param.setArgs(args);
            methodInvokeInterface.beforeMethod(param);
        }
        Object object = method.invoke(subject, args);
        if (null != methodInvokeInterface) {
            param.setArgs(args);
            param.setMethod(method);
            param.setResult(object);
            methodInvokeInterface.afterMethod(param);
        }
        return object;//返回值
    }


}
