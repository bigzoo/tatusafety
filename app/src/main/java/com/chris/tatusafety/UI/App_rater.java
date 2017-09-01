package com.chris.tatusafety.UI;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.MainActivity;

/**
 * Created by Josephine Menge on 01/09/2017.
 */

public class App_rater  {
    private final static String APP_TITLE = "Tatu Safety";// App Name
    private final static String APP_PNAME = "com.chris.tatusafety";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis()>= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Feedback");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);



        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(mContext);
        tv.setText(" How's your experience with the app so far ?");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);

        Button b1 = new Button(mContext);
        b1.setText( "Great");
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
                Toast.makeText(mContext,"Good to hear , why not leave a rating",Toast.LENGTH_LONG).show();
            }
        });
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Not Great");
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(mContext,"Tell us what's wrong by filling this form",Toast.LENGTH_LONG).show();
                mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://goo.gl/forms/K0Cbx2kh39d709mG2")));
            }
        });
        ll.addView(b2);
        Button b3 = new Button(mContext);
        b3.setText("Ask me later");
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b3);
        Button b4 = new Button(mContext);
        b4.setText("Don't ask again");
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b4);

        dialog.setContentView(ll);
        dialog.show();


    }
}
//