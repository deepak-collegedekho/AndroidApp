package com.collegedekho.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.entities.StepByStepQuestion;

import java.util.ArrayList;

/**
 * Created by Bashir on 6/4/16.
 */
public class GCMQuestionListAdapter extends RecyclerView.Adapter<GCMQuestionListAdapter.GCMQuestionViewHolder> {

    public GCMQuestionListAdapter(ArrayList<StepByStepQuestion> questionsList) {

    }

    @Override
    public GCMQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(GCMQuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class GCMQuestionViewHolder extends RecyclerView.ViewHolder {

        public GCMQuestionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
