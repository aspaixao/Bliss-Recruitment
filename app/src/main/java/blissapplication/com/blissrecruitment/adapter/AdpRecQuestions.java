package blissapplication.com.blissrecruitment.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.interfaces.ILoadMore;
import blissapplication.com.blissrecruitment.interfaces.IRecyclerOnClickListener;
import blissapplication.com.blissrecruitment.model.Question;

// Created by Alexandre Paixao on 3/17/2018.

public class AdpRecQuestions extends RecyclerView.Adapter<AdpQuestionsVH> {

    private List<Question> questions;
    private Context ctx;
    private IRecyclerOnClickListener iRecyclerOnClickListener;

    public AdpRecQuestions(Context context, List<Question> q, IRecyclerOnClickListener listener) {
        this.questions = q;
        this.ctx = context;
        iRecyclerOnClickListener = listener;
    }

    @Override
    public AdpQuestionsVH onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(ctx).inflate(R.layout.ly_question_list, parent, false);
            AdpQuestionsVH holder = new AdpQuestionsVH(view);
            return holder;
    }

    @Override
    public void onBindViewHolder(AdpQuestionsVH viewHolder, int position) {

        Question question = questions.get(position);

        viewHolder.txtTitle.setText(question.getQuestion());
        Picasso.get().load(question.getThumb_url()).into(viewHolder.imgThumbs);

        viewHolder.bind(question, iRecyclerOnClickListener);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}


class AdpQuestionsVH extends RecyclerView.ViewHolder {

    TextView txtTitle;
    ImageView imgThumbs;

    public AdpQuestionsVH(final View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.tvQuestionList);
        imgThumbs = (ImageView) itemView.findViewById(R.id.ivThumb);
    }

    public void bind(final Question item, final IRecyclerOnClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickListener(item);
            }
        });
    }


}