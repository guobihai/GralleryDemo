package com.h.grallerydemo.checkxp;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RootCheck {

    private static String LOG_TAG = "Wooo";

    public static void checkRoot() {
        try {

            Object obj = utils.invokeStaticMethod("android.os.SystemProperties", "get", new Class[]{String.class}, new Object[]{"ro.secure"}); // ro.secure  service.adb.root
            Log.i("Wooo", "checkRoot -> " + obj);
            if (obj != null) {
                if (obj.equals("1")) {
                    Log.i("Wooo", "checkRoot may not root");
                }
                if (obj.equals("0")) {
                    Log.i("Wooo", "checkRoot mast rooted");
                }
            }

            checkRelease();
            checkSUfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkRelease() {
        String buildTags = Build.TAGS;
        Log.i("Wooo", "cheeckRelease tag is : " + buildTags);
        if (buildTags != null && buildTags.contains("test-keys")) {
            Log.i("Wooo", "cheeckRelease -> debug");
        }
        if (buildTags != null && buildTags.contains("release-keys")) {
            Log.i("Wooo", "cheeckRelease -> release");
        }
    }

    private static void checkSUfile() {
        // "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
        String file[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        for (int i = 0; i < file.length; i++) {
            String sNm = file[i] + "su";
            File f = new File(sNm);
            if (f.exists()) {
                Log.i("Wooo", "checkRoot " + sNm + " file exists");
            } else {
                Log.i("Wooo", "checkRoot " + sNm + " file no exists");
            }
        }
    }

    public static boolean checkRootWhichSU() {
        String[] strCmd = new String[]{"/system/xbin/which", "su"};
        ArrayList<String> execResult = executeCommand(strCmd);
        if (execResult != null) {
            Log.i("Wooo", "execResult=" + execResult.toString());
            return true;
        } else {
            Log.i("Wooo", "execResult=null");
            return false;
        }
    }

    private static ArrayList<String> executeCommand(String[] shellCmd) {     // 执行 linux 的 shell 命令
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            Log.i(LOG_TAG, "to shell exec which for find su :");
            localProcess = Runtime.getRuntime().exec(shellCmd);
        } catch (Exception e) {
            return null;
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                Log.i(LOG_TAG, "–> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "–> Full response was: " + fullResponse);
        return fullResponse;
    }
}


