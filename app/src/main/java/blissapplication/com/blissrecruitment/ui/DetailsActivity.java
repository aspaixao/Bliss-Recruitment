package blissapplication.com.blissrecruitment.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.adapter.AdpRecChoices;
import blissapplication.com.blissrecruitment.interfaces.IBlissService;
import blissapplication.com.blissrecruitment.interfaces.IRecyclerOnClickListener;
import blissapplication.com.blissrecruitment.model.Choice;
import blissapplication.com.blissrecruitment.model.Health;
import blissapplication.com.blissrecruitment.model.Question;
import blissapplication.com.blissrecruitment.model.Share;
import blissapplication.com.blissrecruitment.util.App;
import blissapplication.com.blissrecruitment.util.ConnectivityVerify;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements IRecyclerOnClickListener{

    IBlissService blissService = App.getBlissService().getBlissService();

    TextView txtQuestion;
    ImageView imgQuestion;

    RecyclerView rcvChoices;
    FloatingActionButton btnShare;
    Dialog dialog;
    private ProgressBar loading;

    int questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

    }

    private void init() {
        loading = (ProgressBar)findViewById(R.id.progressBar1);
        dialog = new Dialog(this);
        txtQuestion = findViewById(R.id.tvQuestion);
        imgQuestion = findViewById(R.id.ivTitle);
        rcvChoices = (RecyclerView) findViewById(R.id.recycleChoices);
        btnShare = (FloatingActionButton) findViewById(R.id.fbShare);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(App.TAG, "Error: Extra empty");
            finish();
        } else {
            getQuestion(extras.getInt("ID"));
        }

    }

    private void getQuestion(int id) {
        loading.setVisibility(View.VISIBLE);

        Call<Question> questionCall = blissService.getQuestion(id);

        questionCall.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    Question question = response.body();

                    questionId = question.getId();

                    showDetails(question);

                } else {
                    Log.e(App.TAG, "Error: " + response.code());
                    loading.setVisibility(View.GONE);
                    Toast.makeText(DetailsActivity.this, "Fail to load this question, please try again!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Log.e(App.TAG, "Error: " + t);
                loading.setVisibility(View.GONE);
                Toast.makeText(DetailsActivity.this, "Fail to load this question, please try again!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void showChoices(List<Choice> choices, boolean update) {

        if (choices != null) {

            try {
                rcvChoices.setAdapter(new AdpRecChoices(getApplicationContext(), choices, this, update));

                RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false);
                rcvChoices.setLayoutManager(layout);

            } catch (Exception e) {
                Log.e(App.TAG, "Error: " + e.getMessage(), e);
            }

        }
        loading.setVisibility(View.GONE);

    }

    private void showDetails(Question q) {
        Picasso.get().load(q.getImage_url()).into(imgQuestion);
        txtQuestion.setText(q.getQuestion());

        List<Choice> cs = q.getChoices();
        showChoices(cs, false);

    }

    @Override
    public void onClickListener(Question item, View view) {

    }

    @Override
    public void onClickListenerChoice(Choice item) {
        loading.setVisibility(View.VISIBLE);
        Call<Question> question = blissService.putQuestion(questionId);

        question.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                if (response.isSuccessful()) {
                    Question question = response.body();

                    List<Choice> cs = question.getChoices();
                    showChoices(cs, true);
                    Toast.makeText(DetailsActivity.this, "Thank you for your vote!", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Log.e(App.TAG,"Error: " + t);
                Toast.makeText(DetailsActivity.this, "Error in your vote, please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void showShare() {

        //Mount the view
        View v;
        v  = View.inflate(this, R.layout.ly_custom_dialog, null);

        final TextView txtURL = (TextView) v.findViewById(R.id.tvSubTitle);
        TextView txtTitle= (TextView) v.findViewById(R.id.tvTitle);
        final EditText txtEmail = (EditText) v.findViewById(R.id.edEmail);
        Button btnOK = (Button) v.findViewById(R.id.btFirst);

        txtURL.setText(App.SHARE_QUESTION+String.valueOf(questionId));
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
                            Toast.makeText(DetailsActivity.this, "Link sharing with success!", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Have a problem with your sharing.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Health> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "Have a problem with your sharing.", Toast.LENGTH_SHORT).show();
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
        if (!isConnected) {
            startActivity(new Intent(DetailsActivity.this, FailConnectionActivity.class));
        }
    }
}
