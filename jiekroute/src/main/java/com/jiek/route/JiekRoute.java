package com.jiek.route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class JiekRoute {
    private static final String TAG = "JiekRoute";
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
//        逐个子库，注册注解统计的 Activity，但 JiekRoute 也要与其它子库隔离，固然下行也无法执行
//        new ActivityUtil().putActivity();

        //以下代码实现上行的按子库的逐个调用逻辑
        List<String> classNames = getClassName("com.jiek.util");
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                if (IJiekRoute.class.isAssignableFrom(aClass)) {
                    try {
                        IJiekRoute iJiekRoute = (IJiekRoute) aClass.newInstance();
                        Log.e(TAG, "IJiekRoute.putActivity: <" + className + " > ");//+iJiekRoute.getClass().getCanonicalName());
                        iJiekRoute.putActivity();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    private List<String> getClassName(String pkname) {
//        创建 class 对象集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {

//            通过包管理器，获取应用信息类，然后获取 APK 的完整路径
            path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
//           根据 Apk 完整路径，获取 dex 编译后的 dex 文件目录
            DexFile dexFile = new DexFile(path);
//            获取编译后的 dex 文件中的所有 class
            Enumeration entries = dexFile.entries();
            while (entries.hasMoreElements()) {
//                遍历所有 class 的包名
                String name = (String) entries.nextElement();
//                判断类的包名与注解生成文件指定包名是否一致
                if (name.contains(pkname)) {
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (key != null && clazz != null && !cacheMap.containsKey(key)) {
            cacheMap.put(key, clazz);
        }
    }

    public void jumpActivity(String key, Bundle bundle) {
        //需要编译时技术（注解、注解处理器）完成数据在编译时填充至 cacheMap 里。
        Class activityClass = cacheMap.get(key);
        Iterator<String> iterator = cacheMap.keySet().iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next())
//        }

        if (activityClass != null) {
            Intent intent = new Intent().setClass(context, activityClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        } else {
            Log.e("JiekRoute", key + ":activity 无法找到");
        }
    }
}
