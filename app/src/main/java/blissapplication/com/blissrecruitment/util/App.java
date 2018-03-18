package blissapplication.com.blissrecruitment.util;

import android.app.Application;

import blissapplication.com.blissrecruitment.services.BlissService;

public class App extends Application {

    public static final String TAG = "ASP";
    public static final int TOTAL_ITEM_LIST = 10;
    public static String SHARE_LIST = "blissrecruitment://questions?question_filter=";
    public static String SHARE_QUESTION = "blissrecruitment://questions?question_id=";

    private static boolean isConnected;
    private static BlissService blissService;
    private static App mInstance;

    public App() {
    }

    public static boolean isIsConnected() {
        return isConnected;
    }
    public static void setIsConnected(boolean isConnected) {
        App.isConnected = isConnected;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static BlissService getBlissService() {
        if (blissService == null) {
            blissService = new BlissService();
        }

        return blissService;
    }


}
