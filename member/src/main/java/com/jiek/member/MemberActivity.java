package com.jiek.member;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jiek.jiek_annotation.Route;

@Route("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}
