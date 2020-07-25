package com.jiek.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jiek.jiek_annotation.Route;
import com.jiek.route.JiekRoute;

@Route("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpMember(View view) {
        JiekRoute.getInstance().jumpActivity("member/member", null);
    }
}
