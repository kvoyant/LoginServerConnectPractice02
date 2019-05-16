package com.tjeit.loginserverconnectpractice02;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tjeit.loginserverconnectpractice02.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {
//        로그인에 성공한 사람의 토큰을 받아오기.
        String token = getIntent().getStringExtra("userToken");

        Log.d("사용자토큰값", token);

//        받아온 토큰을 가지고 /v2/me_info API 호출, 사용자 데이터 표시
    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
