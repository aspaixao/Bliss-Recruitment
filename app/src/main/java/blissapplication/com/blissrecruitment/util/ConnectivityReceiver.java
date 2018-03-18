package blissapplication.com.blissrecruitment.util;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    private IConnectivityReceiverListener listener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if (listener != null) {
            listener.onConnectionChanged(isConnected());
        }

    }

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                App.getInstance().getApplicationContext().getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public interface IConnectivityReceiverListener {
        void onConnectionChanged(boolean isConnected);
    }

}