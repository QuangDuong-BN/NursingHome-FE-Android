package com.example.nursinghome_android.valueStatic;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class APIClient {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public interface CallbackListener {
        void onSuccess(JSONObject jsonResponse);
        void onFailure(IOException e);
    }

    public void callAPIWithJWT(String url, String jwtToken, CallbackListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + jwtToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseData);
                    listener.onSuccess(jsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Xử lý khi có lỗi xảy ra trong việc xử lý JSON response
                }
            }
        });
    }
}


