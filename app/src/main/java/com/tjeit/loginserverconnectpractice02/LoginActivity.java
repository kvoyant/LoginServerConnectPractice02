package com.tjeit.loginserverconnectpractice02;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tjeit.loginserverconnectpractice02.databinding.ActivityLoginBinding;
import com.tjeit.loginserverconnectpractice02.utils.ConnectServer;
import com.tjeit.loginserverconnectpractice02.utils.ContextUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
                String inputId = act.loginEdt.getText().toString();
                String inputPw = act.loginPwdEdt.getText().toString();

//                1-1 .입력받은 ID를 SharedPreference에 저장.
                ContextUtil.setUserInputId(mContext, inputId);

//                2.받아온 아이디와 비번이 정말로 올바른 회원인지? 검사
//                  아이디/비번이 모두 동일한 사람이 회원 명부에 있는지? 검사.
//                test123 / Test!123
//                iu0001 / Test!123
//                testorder1 / testorder1
//                gggg1111 / gggg1111
                ConnectServer.postRequestSignIn(mContext, inputId, inputPw, new ConnectServer.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int code = json.getInt("code");

                                    if(code == 200) {
//                                        로그인 성공 !

                                        JSONObject data = json.getJSONObject("data");
                                        String token = data.getString("token");

                                        if(act.autoLoginCheckBox.isChecked()) {
//                                            자동 로그인을 하려고 한다. 사용자가 표시
//                                            로그인 성공 토큰값을 SharedPreference에 저장
                                            ContextUtil.setUserToken(mContext, token);
                                        }

//                                        사용자 토큰 전달(메인액티비티로..)
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        intent.putExtra("userToken", token);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
//                                        로그인 실패. AlertDialog
                                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                        alert.setTitle("로그인 실패 알림");
                                        alert.setMessage(json.getString("message"));
                                        alert.setPositiveButton("확인", null);
                                        alert.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    public void setValues() {
        String saveUserId = ContextUtil.getUserInputId(mContext);

        act.loginEdt.setText(saveUserId);

        String savedToken = ContextUtil.getUserToken(mContext);
        Log.d("저장된 토큰값",String.format("토큰값 : %s",savedToken));
    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
