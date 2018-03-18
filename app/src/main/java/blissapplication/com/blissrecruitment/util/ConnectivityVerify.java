package blissapplication.com.blissrecruitment.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// Created by Alexandre Paixao 03.18.2018

public class ConnectivityVerify extends ContextWrapper {

    public ConnectivityVerify(Context base) {
        super(base);
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

