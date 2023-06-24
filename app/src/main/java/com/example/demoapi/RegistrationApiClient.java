package com.example.demoapi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistrationApiClient {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String API_URL = "https://flow-fbmj.onrender.com/users";

    public void registerUser(String email, String username, String password, Context context) {
        OkHttpClient client = new OkHttpClient();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, requestBody.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Registration successful
                    String responseData = response.body().string();
                    showToast(context, "Register Success");
                    // Handle response data
                } else {
                    // Registration failed
                    String errorBody = response.body().string();
                    showToast(context, "Register Failure");
                    // Handle error response
                }
                response.close();
            }
        });

    }
    private void showToast(final Context context, final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

