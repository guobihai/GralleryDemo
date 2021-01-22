package com.h.grallerydemo.checkxp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class utils {

    public static Object getStaticFieldOjbectCL(String className, String method) {
        Field field = null;
        try {
            field = ClassLoader.getSystemClassLoader()
                    .loadClass(className)
                    .getDeclaredField(method);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        v0_1.setAccessible(true);true
//        v0_1.set(null, Boolean.valueOf(true));

        return null;

    }

    public static Object invokeStaticMethod(String className, String method, Class<?>[] parameterTypes, Object[] parameter) {

        try {
            Class<?> threadClazz = Class.forName(className);
            Method mt = threadClazz.getMethod(method, parameterTypes);
            mt.invoke(null, parameter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setStaticOjbectCL(String className, String method, Object value) {
        Field field = null;
        try {
            field = ClassLoader.getSystemClassLoader()
                    .loadClass(className)
                    .getDeclaredField(method);
            field.setAccessible(true);
            field.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
