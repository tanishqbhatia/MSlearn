package com.rudrai.mslearn;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;
import com.rudrai.mslearn.interfaces.Server;
import com.rudrai.mslearn.utils.Cons;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tanishq Bhatia on 1/9/2018 at 2:31 PM.
 * Contact at tanishqbhatia1995@gmail.com or +919780702709
 */

public class App extends Application {

    private static Retrofit retrofit = null;

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

    public static Server getServer() {
        return getClient(Cons.WEBSITE_URL).create(Server.class);
    }

    private static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
