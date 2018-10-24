package com.bupt.forum.global;

import android.app.Application;
import android.content.Context;


public class ContextApplication extends Application
{
    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return ContextApplication.context;
    }


}
