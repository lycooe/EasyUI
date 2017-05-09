package com.lewis.easyui.samples;

import android.app.Application;

import com.lewis.easyui.EasyUI;
import com.lewis.easyui.util.EasyLog;

/**
 * Created by Lewis on 2017/4/7.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        EasyUI.init(getApplicationContext());
        EasyLog.setDebugMode(true);

    }
}
