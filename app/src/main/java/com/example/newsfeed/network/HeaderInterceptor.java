package com.example.newsfeed.network;

import android.support.annotation.NonNull;

import com.example.newsfeed.utils.AppConstants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("api-key", AppConstants.API_KEY)
                .build();
        HttpUrl url = request.url();
        request = request.newBuilder()
                .url(url.newBuilder()
                        .addQueryParameter("show-fields", "thumbnail")
                        .build())
                .build();

        return chain.proceed(request);
    }

}
