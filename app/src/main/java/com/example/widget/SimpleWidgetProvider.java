package com.example.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Implementation of App Widget functionality.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.simple_widget_provider);
            view.setTextViewText(R.id.appwidget_third_text, "Loading data ...");
            appWidgetManager.updateAppWidget(appWidgetId, view);

            SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jsonString = sharedPreferences.getString("myJsonData", "");

            if (jsonString.isEmpty()) return;

            // Parse the JSON string
            Data data = (new Gson()).fromJson(jsonString, Data.class);


            // Update the widget's RemoteViews
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_widget_provider);
            views.setTextViewText(R.id.appwidget_first_text, data.getValue1());
            views.setTextViewText(R.id.appwidget_second_text, data.getValue2());

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);


            // trigger API
//                OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(ApiWorker.class)
//                        .build();
//                WorkManager.getInstance(context).enqueue(workRequest1);

        }
    }
}