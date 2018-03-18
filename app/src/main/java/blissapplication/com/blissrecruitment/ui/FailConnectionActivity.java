package blissapplication.com.blissrecruitment.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.util.ConnectivityReceiver;
import blissapplication.com.blissrecruitment.util.ConnectivityVerify;

public class FailConnectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fail_connection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(mBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showCastConnection(ConnectivityVerify.isConnected(getApplicationContext()));
        }
    };

    private void showCastConnection(boolean isConnected) {
        if (isConnected) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

}
