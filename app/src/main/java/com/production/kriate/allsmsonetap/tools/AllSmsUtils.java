package com.production.kriate.allsmsonetap.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.production.kriate.allsmsonetap.MainActivity;

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

    public static void showSnackbar(Context context, String text, boolean isLong) {
        if (context instanceof Activity) {
            View rootView = ((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0);
            if (rootView instanceof CoordinatorLayout)
                showSnackbar((CoordinatorLayout) rootView, text, isLong);
        }
    }

    public static void showSnackbar(CoordinatorLayout layout, String text, boolean isLong) {
        Snackbar snack = Snackbar.make(layout, text,
                isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
        snack.setAction(android.R.string.ok, v -> {
        });
        snack.show();
    }


}