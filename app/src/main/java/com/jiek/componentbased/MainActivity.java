package com.jiek.componentbased;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiek.login.LoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpLogin(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
