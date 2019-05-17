package com.tjeit.loginserverconnectpractice02;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.tjeit.loginserverconnectpractice02.databinding.ActivityMainBinding;
import com.tjeit.loginserverconnectpractice02.utils.ConnectServer;

import org.json.JSONException;
import org.json.JSONObject;

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
        ConnectServer.getRequestMeInfo(mContext, token, new ConnectServer.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int code = json.getInt("code");

                            if(code == 200) {
//                                정상수신
                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");

//                                프사경로
                                String profile_image = user.getString("profile_image");
//                                사용자 이름
                                String name = user.getString("name");
//                                보유금액
                                int balance = user.getInt("balance");
//                                은행로고
                                JSONObject bank_code = user.getJSONObject("bank_code");
                                String logo = bank_code.getString("logo");
//                                은행이름
                                String bankName  = bank_code.getString("name");
//                                계좌번호
                                String billing_account = user.getString("billing_account");

                            }
                            else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
