package blissapplication.com.blissrecruitment.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.interfaces.IBlissService;
import blissapplication.com.blissrecruitment.model.Health;
import blissapplication.com.blissrecruitment.util.App;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {


    Button btnFail;
    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        loading = (ProgressBar)findViewById(R.id.progressBar1);
        init();
    }

    private void init() {

        btnFail = (Button) findViewById(R.id.btFail);

        loading.setVisibility(View.VISIBLE);

        final IBlissService blissService = App.getBlissService().getBlissService();

        blissService.getServiceHealth().enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                if (!response.isSuccessful()) {
                    Log.e(App.TAG, "Error on service: " + response.code());
                    btnFail.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    loading.setVisibility(View.GONE);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                Log.e(App.TAG, "Error: " + t);
                btnFail.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
        });

        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });


    }

    private void tryAgain() {
        btnFail.setVisibility(View.GONE);
        init();
    }

}
