package com.example.project_1200308_1201738.Shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManagerAdmin {

    private static SharedPrefManagerAdmin instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefManagerAdmin(Context context) {
        sharedPreferences = context.getSharedPreferences("admin_pref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManagerAdmin getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManagerAdmin(context);
        }
        return instance;
    }

    public void writeString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String readString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void removeKey(String key) {
        editor.remove(key);
        editor.apply();
    }
}
