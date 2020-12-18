package com.marten.greendaodemo;

import android.app.Application;

import com.marten.greendaodemo.green_dao.DaoManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化DaoMaster
        DaoManager.getInstance().init(this);
        DaoManager.getInstance().getDaoMaster();
    }
}
