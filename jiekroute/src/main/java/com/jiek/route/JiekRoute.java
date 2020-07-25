package com.jiek.route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class JiekRoute {
    private volatile static JiekRoute instance;
    private Context context;

    private Map<String, Class<? extends Activity>> cacheMap;

    public JiekRoute() {
        cacheMap = new HashMap<>();
    }

    public static JiekRoute getInstance() {
        if (instance == null) {
            synchronized (JiekRoute.class) {
                if (instance == null) {
                    instance = new JiekRoute();
                }
            }
        }
        return instance;
    }

    //尽可能早初始化，最好在 Application 中进行
    public JiekRoute init(Context app) {
        this.context = app;
        return this;
    }

    public void jumpActivity(String key, Bundle bundle) {
        //需要编译时技术（注解、注解处理器）完成数据在编译时填充至 cacheMap 里。
        Class activityClass = cacheMap.get(key);

        if (activityClass != null) {
            Intent intent = new Intent().setClass(context, activityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }
}
