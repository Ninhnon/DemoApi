package com.example.demoapi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthenticationApiClient {
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String API_URL = "https://flow-fbmj.onrender.com/auth/local";

    public void authenticateUser(String username, String password, Context context) {
        OkHttpClient client = new OkHttpClient();

        JSONObject requestBody = new JSONObject();
        try {
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
                    // Authentication successful
                    String responseData = response.body().string();
                    showToast(context, "Access Success");
                    showToast(context, responseData);
                } else {
                    // Authentication failed
                    String errorBody = response.body().string();
                    showToast(context, "Access Failure");
                    showToast(context, errorBody);
                }
                response.close();
            }
        });
    }

    // Helper method to show Toast messages from a background thread
    private void showToast(final Context context, final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}