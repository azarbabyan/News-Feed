package com.example.newsfeed.manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.newsfeed.NewsFeedApplication;
import com.example.newsfeed.R;
import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NewsResponse;
import com.example.newsfeed.ui.activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsfeed.NewsFeedApplication.CHANNEL_ID;

public class NewsCheckService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NewsFeedApplication.getApiService().getNews(1,20).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call,@NonNull Response<NewsResponse> response) {
                if (response.body()!=null){
                    if (response.body().getResponse()!=null){
                        List<Result> results = response.body().getResponse().getResults();
                        if (results !=null){
                            Result resultById = NewsRepository.getInstance(NewsCheckService.this).getResultByIdForService(results.get(0).getId());
                            if (resultById==null){
                                if (MyLifecycleHandler.isApplicationVisible()) {
                                    NewsRepository.getInstance(NewsCheckService.this).insertResultsToDb(results);
                                }else {
                                    sendNotification();
                                }
                            }
                        }
                    }
                }
                stopSelf();
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call,@NonNull Throwable t) {
                stopSelf();
            }
        });
        return START_NOT_STICKY;
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("News")
                        .setContentText("You have a unread news")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(100 , notificationBuilder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, createNotification());
    }

    private Notification createNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("")
                        .setContentText("")
                        .setContentIntent(pendingIntent);
        return notificationBuilder.build();
    }

    @Override
    public void onDestroy() {
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            alarm.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 30000,
                    PendingIntent.getForegroundService(this, 0, new Intent(this, NewsCheckService.class), 0)
            );
        } else {
            alarm.set(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 30000,
                    PendingIntent.getService(this, 0, new Intent(this, NewsCheckService.class), 0)
            );
        }

        super.onDestroy();
    }
}