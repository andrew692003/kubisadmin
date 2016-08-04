package com.twiscode.kubisadmin;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Crusader on 7/29/2016.
 */
public class MyApplication extends Application {

    private Long[] limit={0L};

    @Override
    public void onCreate() {
        super.onCreate();

        Locale locale = new Locale("id");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public Long getLimit() {
        return limit[0];
    }

    public void setLimit(Long[] limit) {
        this.limit = limit;
    }

}
