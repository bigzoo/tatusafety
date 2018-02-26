package com.chris.tatusafety.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tatusafety.MainActivity;
import com.chris.tatusafety.R;
import com.chris.tatusafety.maps.FindMeActivity;
import com.chris.tatusafety.maps.MapsActivity;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 1;
    @Bind(R.id.phone_login_button) Button mPhone;
    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.email_login_button) Button mLogin;
    @Bind(R.id.anonymous_login_button) Button mAnony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //check for an access token
        AccessToken accessToken = AccountKit.getCurrentAccessToken();


        if (accessToken != null)
        {
            //if user was logged in it will not be null hence log in into application
            loginSuccess();
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    1);
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    1);
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    1);
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    1);
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
            }
            return;
        }

    @Override
    protected void onActivityResult (final int requestCode, final int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode ,data);

        //confirm that this response matches your request
        if (requestCode == APP_REQUEST_CODE){
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null ){
                //display a login error
                String toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            }else if (loginResult.getAccessToken() != null){
                //on succesfull login, proceed
                loginSuccess();
            }
        }
    }



    private void onLogin(final LoginType loginType){
        //acount kit activity
        final Intent accountKit = new Intent(this,AccountKitActivity.class);

        //configure login type and response type
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder=
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,AccountKitActivity.ResponseType.TOKEN
                );
        final AccountKitConfiguration configuration = configurationBuilder.build();

        //launch account kit activity
        accountKit.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configuration);
        startActivityForResult(accountKit,APP_REQUEST_CODE);

    }

    public void onPhoneLogin(View view){
        onLogin(LoginType.PHONE);
    }

    public void onEmailLogin(View view){
        onLogin(LoginType.EMAIL);
    }



    private void loginSuccess() {

        Intent mainActivity = new Intent(this,MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void onAnonymousLogin(View view) {
        AccessToken accessToken = null;
        loginSuccess();
    }

}
