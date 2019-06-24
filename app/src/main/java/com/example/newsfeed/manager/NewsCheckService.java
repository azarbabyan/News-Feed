package com.example.newsfeed.manager;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.newsfeed.NewsFeedApplication;
import com.example.newsfeed.R;
import com.example.newsfeed.data.NewsRepository;
import com.example.newsfeed.data.eventbusmodels.UpdateDbEvent;
import com.example.newsfeed.network.data.Result;
import com.example.newsfeed.network.models.NewsResponse;
import com.example.newsfeed.ui.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.newsfeed.NewsFeedApplication.CHANNEL_ID;
import static com.example.newsfeed.NewsFeedApplication.LOW_CHANNEL_ID;

public class NewsCheckService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NewsFeedApplication.getApiService().getNews(1).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    if (response.body().getResponse() != null) {
                        List<Result> results = response.body().getResponse().getResults();
                        if (results != null) {
                            Result resultById = NewsRepository.getInstance(NewsCheckService.this).getResultByIdForService(results.get(0).getId());
                            if (resultById == null) {
                                NewsRepository.getInstance(NewsCheckService.this).insertResultsToDb(results);
                                if (MyLifecycleHandler.isApplicationInForeground()) {
                                    sendNotification();
                                }else {
                                    EventBus.getDefault().post(new UpdateDbEvent());
                                }
                            }
                        }
                    }
                }
                stopSelf();
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                stopSelf();
            }
        });
        return START_NOT_STICKY;
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("You have a fresh news")
                        .setContentText("Tap to open app")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Objects.requireNonNull(notificationManager).notify(100, notificationBuilder.build());
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

    @TargetApi(Build.VERSION_CODES.N)
    private Notification createNotification() {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, LOW_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Checking for fresh news")
                        .setContentText("")
                        .setPriority(NotificationManager.IMPORTANCE_LOW);
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
