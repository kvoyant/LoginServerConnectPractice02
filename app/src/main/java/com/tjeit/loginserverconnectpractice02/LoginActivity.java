package com.tjeit.loginserverconnectpractice02;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.tjeit.loginserverconnectpractice02.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        act.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                1.아이디와 비번 입력 받아오기
//                2.받아온 아이디와 비번이 정말로 올바른 회원인지? 검사
//                  아이디/비번이 모두 동일한 사람이 회원 명부에 있는지? 검사.
//                test123 / test123
//                iu0001 / Test!123
//                testorder1 / testorder1
//                gggg1111 / gggg1111
            }
        });
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
