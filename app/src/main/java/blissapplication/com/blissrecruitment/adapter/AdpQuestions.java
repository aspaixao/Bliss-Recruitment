package blissapplication.com.blissrecruitment.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import blissapplication.com.blissrecruitment.R;
import blissapplication.com.blissrecruitment.model.Question;

// Created by Alexandre Paixao on 3/17/2018.

public class AdpQuestions extends ArrayAdapter {

    private final Context ctx;

    TextView txtTitle;
    ImageView imgThumbs;

    public AdpQuestions(Context context, List<Question> list) {
        super(context, R.layout.ly_question_list, list);
        this.ctx = context;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = LayoutInflater.from(ctx)
                    .inflate(R.layout.ly_question_list, parent, false);
        } else {
            view = convertView;
        }

        txtTitle = (TextView) view.findViewById(R.id.tvTitle);
        imgThumbs = (ImageView) view.findViewById(R.id.ivThumb);

        Question question = (Question) getItem(position);
        txtTitle.setText(question.getQuestion());
        imgThumbs.setImageURI(Uri.parse(question.getThumb_url()));

        return view;
    }
}
