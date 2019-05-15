package com.tjeit.loginserverconnectpractice02.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectServer {

    //    서버의 근본 주소
    private final static String BASE_URL = "http://delivery-dev-389146667.ap-northeast-2.elb.amazonaws.com";

    //    서버의 응답을 처리하는 역할을 담당하는 인터페이스
    public interface JsonResponseHandler {
        void onResponse(JSONObject json);
    }

    public static void postRequestSignIn(Context context, String user_id, String password, final JsonResponseHandler handler) {
//      클라이언트 역할은 무슨 메소드이던간에 동일, 복붙하면됨.

//        POST메소드 urlBuilder가 아니라, RequestBody를 build.
//        formData에 파라미터를 첨부하는 코드

        RequestBody requestBody = new FormBody.Builder()
                .add("user_id",user_id)
                .add("password", password)
                .build();

//        실제 Request를 생성, 서버로 떠날준비
        Request request = new Request.Builder()
                .url(BASE_URL+"/auth")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();

                Log.d("서버 응답 내용",responseContent);

                try {
                    JSONObject json = new JSONObject(responseContent);

                    if(handler != null) {
//                        화면에서 처리하는 코드가 있으면 실행시켜줌
                        handler.onResponse(json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
