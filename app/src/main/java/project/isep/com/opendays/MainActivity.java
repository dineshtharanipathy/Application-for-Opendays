package project.isep.com.opendays;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = MainActivity.class.getSimpleName();

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences prefs;

    private ImageView logo;
    private Animation logoanim;
    private ProgressBar loading;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            logo = (ImageView) findViewById(R.id.logo);
            loading = (ProgressBar)findViewById(R.id.loading);
            logoanim = AnimationUtils.loadAnimation(this, R.anim.logoanim);
            logoanim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    gcm(savedInstanceState);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            logo.startAnimation(logoanim);
            prefs = getSharedPreferences(Config.USER_PREFERENCE, MODE_PRIVATE);
            //EncDec encDec = new EncDec();
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    boolean success = prefs.getBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false);
                    if (success) {
                        Log.d(TAG, "GCM Registration complete");
                        prefs.edit().putInt(Config.REGISTER, 2).apply();
                        Intent intent1 = new Intent(MainActivity.this, StartingActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        finish();
                    } else {
                        Log.d(TAG, "GCM Registration Retrying");
                        if (checkPlayServices()) {
                            Intent intent1 = new Intent(MainActivity.this, RegistrationIntentService.class);
                            startService(intent1);
                        }
                    }
                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gcm(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            if (checkPlayServices()) {
                if(prefs.getInt(Config.REGISTER, 0) == 2){
                    Intent intent1 = new Intent(MainActivity.this, StartingActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                    finish();
                }else {
                    loading.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    startService(intent);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.GCM_REGISTRATION_COMPLETE));
        }catch(Exception e){

        }
    }

    protected void onPause(){
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
            super.onPause();
        }catch(Exception e){

        }
    }

    private boolean checkPlayServices(){
        try {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Log.i(TAG, "This device is not supported.");
                    finish();
                }
                return false;
            }
            return true;
        }catch(Exception e){

        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
        }catch(Exception e){

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        }catch(Exception e){

        }
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        }catch(Exception e){

        }
    }


}