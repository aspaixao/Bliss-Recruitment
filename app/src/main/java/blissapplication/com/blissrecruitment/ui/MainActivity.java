package blissapplication.com.blissrecruitment.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.adapter.AdpRecQuestions;
import blissapplication.com.blissrecruitment.interfaces.IBlissService;
import blissapplication.com.blissrecruitment.interfaces.IRecyclerOnClickListener;
import blissapplication.com.blissrecruitment.model.Choice;
import blissapplication.com.blissrecruitment.model.Health;
import blissapplication.com.blissrecruitment.model.Question;
import blissapplication.com.blissrecruitment.model.Share;
import blissapplication.com.blissrecruitment.util.App;
import blissapplication.com.blissrecruitment.util.ConnectivityReceiver;
import blissapplication.com.blissrecruitment.util.ConnectivityVerify;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IRecyclerOnClickListener {
    private Toolbar mToolbar;
    private ProgressBar loading;
    RecyclerView rcvQuestions;
    FloatingActionButton btnShare;
    SearchView searchView;

    Dialog dialog;

    private IBlissService blissService = App.getBlissService().getBlissService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading = (ProgressBar)findViewById(R.id.progressBar1);
        init();
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rcvQuestions = (RecyclerView) findViewById(R.id.recycleQuestions);
        btnShare = (FloatingActionButton) findViewById(R.id.fbShare);

        dialog = new Dialog(this);

        setSupportActionBar(mToolbar);

        execSearch("");

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

    }

    public void showShare() {

        //Mount the view
        View v;
        v  = View.inflate(this, R.layout.ly_custom_dialog, null);

        final TextView txtURL = (TextView) v.findViewById(R.id.tvSubTitle);
        TextView txtTitle= (TextView) v.findViewById(R.id.tvTitle);
        final EditText txtEmail = (EditText) v.findViewById(R.id.edEmail);
        Button btnOK = (Button) v.findViewById(R.id.btFirst);

        txtURL.setText(App.SHARE_LIST+searchView.getQuery().toString());
        txtTitle.setText(getResources().getString(R.string.sharingList));

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share share = new Share();
                share.setDestination_email(txtEmail.getText().toString());
                share.setContent_url(txtURL.getText().toString());

                Call<Health> ret = blissService.postShare(share);

                ret.enqueue(new Callback<Health>() {
                    @Override
                    public void onResponse(Call<Health> call, Response<Health> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Link sharing with success!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Have a problem with your sharing.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Health> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Have a problem with your sharing.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        //show dialog
        dialog.setTitle(null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(v);
        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.search_bar);

        searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                execSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                execSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void execSearch(String q) {
        loading.setVisibility(View.VISIBLE);
        try {
            Call<List<Question>> questions = blissService.getQuestions(10,0,"");

            questions.enqueue(new Callback<List<Question>>() {
                @Override
                public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                    if (response.isSuccessful()) {
                        List<Question> qs = response.body();
                        updateList(qs);
                    } else {
                        Log.e(App.TAG, "Error: " + response.body());
                        loading.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<List<Question>> call, Throwable t) {
                    Log.e(App.TAG, "Error: " + t);
                    loading.setVisibility(View.GONE);
                }
            });


        } catch (Exception e) {
            Log.e(App.TAG, "Error: " + e.getMessage());
            loading.setVisibility(View.GONE);
        }

    }

    private void updateList(List<Question> qs) {
        //show listView with result
        loading.setVisibility(View.VISIBLE);
        if (qs != null) {

            try {
                rcvQuestions.setAdapter(new AdpRecQuestions(MainActivity.this, qs,this));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
                rcvQuestions.setLayoutManager(layout);


            } catch (Exception e) {
                Log.e(App.TAG, "Error: " + e.getMessage(), e);
            }
        }
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        super.onBackPressed();
    }

    @Override
    public void onClickListener(Question item) {
        loading.setVisibility(View.VISIBLE);
        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
        i.putExtra("ID", item.getId());
        startActivity(i);
        //Toast.makeText(this, "Click on list: " + String.valueOf(item.getId()), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClickListenerChoice(Choice item) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loading.setVisibility(View.GONE);
    }

    @Override
    protected void onPostResume() {
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
        if (!isConnected) {
            startActivity(new Intent(MainActivity.this, FailConnectionActivity.class));
        }
    }
}
