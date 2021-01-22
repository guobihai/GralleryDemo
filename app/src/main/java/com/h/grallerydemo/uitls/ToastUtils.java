package com.h.grallerydemo.uitls;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ToastUtils {

    public static  void showToast(Context context) {
        Toast toast = Toast.makeText(context, "永不消失的Toast", Toast.LENGTH_SHORT);
//设置Toast信息提示框显示的位置（在屏幕顶部水平居中显示）
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        try {
//从Toast对象中获得mTN变量
            Field field = toast.getClass().getDeclaredField("mTN");
            field.setAccessible(true);
            Object obj = field.get(toast);
//TN对象中获得了show方法
            Method method = obj.getClass().getDeclaredMethod("show", null);
//调用show方法来显示Toast信息提示框
            method.invoke(obj, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void hide(Object obj) {
        try {
//需要将前面代码中的obj变量变成类变量。这样在多个地方就都可以访问了
            Method method = obj.getClass().getDeclaredMethod("hide", null);
            method.invoke(obj, null);
        } catch (Exception e) {
        }
    }

//    public void  showOrHide(Field field, Toast toast){
//        ITransientNotification notification=(ITransientNotification)field.get(toast);
////显示Toast信息提示框
//        notification.show();
////关闭Toast信息提示框
//        notification.hide();
//    }
    /**
     * 显示系统Toast
     */
    private static Object iNotificationManagerObj;
    private static void showSystemToast(Toast toast) {
        try {
            Method getServiceMethod = Toast.class.getDeclaredMethod("getService");
            getServiceMethod.setAccessible(true);
            //hook INotificationManager
            if (iNotificationManagerObj == null) {
                iNotificationManagerObj = getServiceMethod.invoke(null);

                Class iNotificationManagerCls = Class.forName("android.app.INotificationManager");
                Object iNotificationManagerProxy = Proxy.newProxyInstance(toast.getClass().getClassLoader(), new Class[]{iNotificationManagerCls}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //强制使用系统Toast
                        if ("enqueueToast".equals(method.getName())
                                || "enqueueToastEx".equals(method.getName())) {  //华为p20 pro上为enqueueToastEx
                            args[0] = "android";
                        }
                        return method.invoke(iNotificationManagerObj, args);
                    }
                });
                Field sServiceFiled = Toast.class.getDeclaredField("sService");
                sServiceFiled.setAccessible(true);
                sServiceFiled.set(null, iNotificationManagerProxy);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
