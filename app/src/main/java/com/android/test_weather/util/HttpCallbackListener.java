package com.android.test_weather.util;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);

}
