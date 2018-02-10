//package com.chris.tatusafety;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//import android.widget.RemoteViews;
//
//import java.util.Random;
//
///**
// * Created by tiger on 30/10/17.
// */
//
//public class Widget extends AppWidgetProvider {
//
//    private static final String ACTION_CLICK = "ACTION_CLICK";
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//
//        super.onUpdate(context, appWidgetManager,appWidgetIds);
//
//        // Get all ids
//        ComponentName thisWidget = new ComponentName(context, Widget.class);
//        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
//        for (int widgetId : allWidgetIds) {
//            // create some random data
//            int number = (new Random().nextInt(100000));
//
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.widget_layout);
//            Log.e("WidgetExample", String.valueOf(number));
//            // Set the text
//            remoteViews.setTextViewText(R.id.openApp, String.valueOf(number));
//
//            // Register an onClickListener
//            Intent openapp = new Intent(context, MyWidgetProvider.class);
//
//            openapp.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            openapp.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openapp, 0);
//
//            remoteViews.setOnClickPendingIntent(R.id.openApp, pendingIntent);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://wingnity.com"));
//            PendingIntent myPIntent = PendingIntent.getActivity(context, 1, intent, 0);
//
//            remoteViews.setOnClickPendingIntent(R.id.buttonOpenWing, myPIntent);
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
//    }
//}
