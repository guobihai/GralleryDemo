package com.h.grallerydemo.uitls;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClickToast {
    public static void showToast(final Context context, int duration) {
        Toast mToast = null;
        if (mToast == null) {
            TextView tv = new TextView(context);
            tv.setText("这是测试的Toast 是否能点击");
            tv.setLongClickable(true);
            tv.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    Log.i("ClickToast", "ddddddddddddddddd");
                    return true;
                }
            });
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ClickToast", "这是测试的Toast");
                }
            });
            mToast = Toast.makeText(context.getApplicationContext(),"", duration);
            mToast.setView(tv);
            mToast.setGravity(Gravity.TOP, 0, 300);
        }

        try {
            Object mTN;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    // Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                    // 设置viewgroup宽高
                    params.width = 200; // 设置Toast宽度为屏幕宽度
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT; // 设置高度
                    Field field =    mParams.getClass().getDeclaredField("hideTimeoutMilliseconds");
                    if(field !=null){
                        field.setAccessible(true);
                        field.setLong(mParams,60*10000);
                        Log.i("ClickToast", "这是测试的Toast111:"+field.get(mParams));
                    }
                }
                //TN对象中获得了show方法
//                Method method = mTN.getClass().getDeclaredMethod("show", IBinder.class);
////                调用show方法来显示Toast信息提示框
//                method.invoke(mTN, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mToast.show();
    }

    //反射获取filed
    private static Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

}
