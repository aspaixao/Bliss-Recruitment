package blissapplication.com.blissrecruitment.interfaces;

import android.view.View;

import blissapplication.com.blissrecruitment.model.Choice;
import blissapplication.com.blissrecruitment.model.Question;

// Created by Alexandre Paixao on 3/17/2018.

public interface IRecyclerOnClickListener {
    public void onClickListener(Question item, View v);
    public void onClickListenerChoice(Choice item);
}
