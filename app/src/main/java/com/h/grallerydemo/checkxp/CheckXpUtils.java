package com.h.grallerydemo.checkxp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PipedReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 检查XP
 * 只能针对没有编译过的xposed 进行检测
 */
public class CheckXpUtils {
    /**
     * 检查是否安装了poxsed
     *
     * @return
     */
    public boolean checkXposed() {
        try {
            Object localObject = ClassLoader.getSystemClassLoader()
                    .loadClass("de.robv.android.xposed.XposedHelpers").newInstance();
            // 如果加载类失败 则表示当前环境没有xposed
            if (localObject != null) {
                return true;
            }
            return false;
        } catch (Throwable localThrowable) {
        }
        return false;
    }

    /**
     * 强制设置xposed 不生效
     */
    public void setUnEnableXposed() {

        try {
            Field v0_1 = ClassLoader.getSystemClassLoader()
                    .loadClass("de.robv.android.xposed.XposedBridge")
                    .getDeclaredField("disableHooks");
            v0_1.setAccessible(true);
            v0_1.set(null, Boolean.valueOf(true));
        } catch (Throwable v0) {
        }
    }


    /**
     * 检查当前线堆栈检测里面是否有 （StackTraceElement stackTraceElements[]=Thread.currentThread().getStackTrace();）
     *
     * @param stackTraceElementArr
     * @return
     */
    public static boolean checkXpByStack(StackTraceElement[] stackTraceElementArr) {
        int length = stackTraceElementArr.length;
        int i = 0;
        while (i < length) {
            String className = stackTraceElementArr[i].getClassName();
            if (className == null || !(className.contains("de.robv.android.xposed.XposedBridge") || className.contains("com.zte.heartyservice.SCC.FrameworkBridge"))) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * /system/bin/su
     *
     * @param str
     * @return
     */
    public static boolean isFileExist(String str) {
        try {
            boolean exists = new File(str).exists();
            return exists;
        } catch (Exception e) {
            return false;
        }
    }

    private static String TAG = "Wooo Xposed";
    private static StringBuffer sb = new StringBuffer();

    // https://blog.csdn.net/jiangwei0910410003/article/details/80037971
    // https://www.52pojie.cn/thread-691584-1-1.html
    // https://tech.meituan.com/android_anti_hooking.html
    // https://segmentfault.com/a/1190000009976827
    public static void checkXposed(Context ctx) {
        checkCache();
        checkJarClass();
        checkJarFile();
        disableHooks();
        checkMaps();
        checkPackage(ctx);
        checkException();
    }

    private static void checkPackage(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            if (applicationInfo.packageName.equals("de.robv.android.xposed.installer")) {
                Log.i(TAG, "found xposed package installed");
            }
        }
    }

    private static void checkException() {
        try {
            throw new Exception("xppp");
        } catch (Exception e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge")) {       // stackTraceElement.getMethodName()
                    Log.i(TAG, "found exception of xposed");
                }
            }
        }
    }



    private static void checkMaps() {
        String jarName = "XposedBridge.jar";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/" + android.os.Process.myPid() + "/maps"));
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) {
                    break;
                }
                if (str.endsWith("jar")) {
                    if (str.contains(jarName)) {
                        Log.i(TAG, "proc/pid/maps find Xposed.jar -> " + str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查root
     *
     * @return
     */
    public static boolean checkRoot() {
        try {
            String FRAMES_ID_SEPARATOR_3D = ":";
            String obj = System.getenv("PATH");
            if (TextUtils.isEmpty(obj)) {
                for (String file : obj.split(FRAMES_ID_SEPARATOR_3D)) {
                    File file2 = new File(file, "su");
                    if (file2.exists()) {
                        new StringBuilder("SuFile found : ").append(file2.toString());
                        return true;
                    }
                }
            } else if (new File("/system/bin/su").exists()) {
                return true;
            } else if (new File("/system/xbin/su").exists()) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private static void checkCache() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        String xpHelper = "de.robv.android.xposed.XposedHelpers";

        Log.i(TAG, "checkCache IN");
        try {
            Object XPHelpers = cl.loadClass(xpHelper).newInstance();        // 在 nexus6 的 7.1 系统上获取失败，抛出异常
            if (XPHelpers != null) {
                filterField(XPHelpers, "fieldCache");
                filterField(XPHelpers, "methodCache");
                filterField(XPHelpers, "constructorCache");
            } else {
                Log.i(TAG, "cannot find Xposed framework");
            }
            Log.i(TAG, "cache content -> " + sb.length() + " -> " + sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void filterField(Object xpHelper, String cacheName) {
        try {
            Field f = xpHelper.getClass().getDeclaredField(cacheName);
            f.setAccessible(true);
            HashMap caMap = (HashMap) f.get(xpHelper);
            if (caMap == null) {
                return;
            }
            Set caSet = caMap.keySet();
            if (caSet.isEmpty()) {
                return;
            }
            Log.i(TAG, "filter -> " + cacheName + " , size -> " + caSet.size());
            Iterator iterator = caSet.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                Log.i(TAG, "filter key -> " + key);
                if (key == null) {
                    continue;
                }
                key = key.toLowerCase();
                if (key.length() <= 0) {
                    continue;
                }
                if (key.startsWith("android.support")) {
                    continue;
                }
                if (key.startsWith("javax.")) {
                    continue;
                }
                if (key.startsWith("android.webkit")) {
                    continue;
                }
                if (key.startsWith("java.util")) {
                    continue;
                }
                if (key.startsWith("android.widget")) {
                    continue;
                }
                if (key.startsWith("sun.")) {
                    continue;
                }
                sb.append(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkJarFile() {
        File f = new File("/system/framework/XposedBridge.jar");
        if (f.exists()) {
            Log.i(TAG, "system may installed Xposed find jar file");
        } else {
            Log.i(TAG, "system not install Xposed cannot find jar file");
        }
    }

    private static void checkJarClass() {
        try {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            Class clazz = cl.loadClass("de.robv.android.xposed.XposedBridge");

            if (clazz != null) {
                Log.i(TAG, "system installed Xposed Class");
            } else {
                Log.i(TAG, "system not install Xposed Class");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // disable
    private static void disableHooks() {
        Object obj2 = utils.getStaticFieldOjbectCL("de.robv.android.xposed.XposedBridge", "disableHooks");
        Log.i(TAG, "disableHooks seted  -> " + obj2);

        Log.i(TAG, "disableHooks seted ");
        utils.setStaticOjbectCL("de.robv.android.xposed.XposedBridge", "disableHooks", true);

        Object obj = utils.getStaticFieldOjbectCL("de.robv.android.xposed.XposedBridge", "disableHooks");
        Log.i(TAG, "disableHooks seted  -> " + obj);
    }


    /**
     * 检查虚拟机
     *
     * @return
     */
    private static int checkVp() {
        return System.getProperty("vxp") != null ? 1 : 0;
    }


    /**
     * 检查有没有加载xposedHelpers
     *
     * @return
     */
    private static boolean checkXposedHelp() {
        try {
            ClassLoader.getSystemClassLoader().loadClass("de.robv.android.xposed.XposedHelpers");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}
