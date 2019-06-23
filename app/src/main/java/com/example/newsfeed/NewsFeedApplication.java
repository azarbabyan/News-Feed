package com.example.newsfeed;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import com.example.newsfeed.manager.AlarmReceiver;
import com.example.newsfeed.manager.MyLifecycleHandler;
import com.example.newsfeed.manager.NewsCheckService;
import com.example.newsfeed.network.HeaderInterceptor;
import com.example.newsfeed.network.NewsApiService;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsfeed.utils.AppConstants.BASE_URL;

public class NewsFeedApplication extends Application {

    public static final String CHANNEL_ID = "channel";
    public static final String LOW_CHANNEL_ID = "low_channel";
    private static final String NAME ="name";
    private static final String LOW_CHANNEL_NAME ="low_channel_name";
    private static final String DESCRIPTION ="description";

    private static NewsApiService apiService;
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());
        createNotificationChannel();
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(new HeaderInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(NewsApiService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, NewsCheckService.class));
        } else {
            startService(new Intent(this, NewsCheckService.class));
        }
    }

    public static NewsApiService getApiService() {
        return apiService;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            int low = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, NAME, importance);
            NotificationChannel lowChannel = new NotificationChannel(LOW_CHANNEL_ID, LOW_CHANNEL_NAME, low);
            channel.setDescription(DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                notificationManager.createNotificationChannel(lowChannel);
            }
        }
    }
}
