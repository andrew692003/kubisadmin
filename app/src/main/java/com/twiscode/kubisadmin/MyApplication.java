package com.twiscode.kubisadmin;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Crusader on 7/29/2016.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Locale locale = new Locale("id");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

}
