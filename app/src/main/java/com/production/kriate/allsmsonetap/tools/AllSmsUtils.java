package com.production.kriate.allsmsonetap.tools;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Общие функции для программы
 */
public class AllSmsUtils {
    public static String getVersionName(Context ctx) {
        String versionName = null;
        try {
            versionName = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}