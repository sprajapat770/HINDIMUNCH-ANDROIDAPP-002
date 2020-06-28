package com.example.hindimunch.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.RatingBar;
import android.widget.Toast;

public class CommonMethods {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String nullSafe(String strValue, String defaultValue) {
        return strValue == null || strValue.trim().length() <= 0 || strValue.equalsIgnoreCase("null") ? defaultValue : strValue;
    }

    public static void setPref(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);

    }

    public static void clearAllPref(Context context) {
        SharedPreferences settings = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    public void setStarColor(RatingBar ratingBar) {
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FDDD4A"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#c7c7c7"), PorterDuff.Mode.SRC_ATOP);
    }
}
