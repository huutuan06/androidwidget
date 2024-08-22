package com.example.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiWorker extends Worker {

    public ApiWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Perform the API call
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.example.com/data";

        Request request = new Request.Builder()
                .url(url)
                .build();

        Context context = getApplicationContext();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();

                // Update the widget
                updateUI(context, responseData);
            } else {
                updateUI(context, "ERROR");
            }

            return Result.success();
        } catch (IOException e) {
            updateUI(context, "ERROR");
            e.printStackTrace();
            return Result.failure();
        }
    }

    void updateUI(Context context, String responseData) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget_provider);
        views.setTextViewText(R.id.appwidget_third_text, responseData);

        ComponentName widget = new ComponentName(context, SimpleWidgetProvider.class);
        appWidgetManager.updateAppWidget(widget, views);
    }
}
