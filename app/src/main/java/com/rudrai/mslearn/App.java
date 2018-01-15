package com.rudrai.mslearn;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Tanishq Bhatia on 1/9/2018 at 2:31 PM.
 * Contact at tanishqbhatia1995@gmail.com or +919780702709
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initPrefs();
    }

    private void initPrefs() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
