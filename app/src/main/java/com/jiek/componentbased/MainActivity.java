package com.jiek.componentbased;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiek.jiek_annotation.Route;
import com.jiek.login.LoginActivity;
import com.jiek.route.JiekRoute;

@Route("main/main")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JiekRoute.getInstance().init(this);//尽可能早上注册 Route
    }

    public void jumpLogin(View view) {
//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        JiekRoute.getInstance().jumpActivity("login", null);
    }
}
