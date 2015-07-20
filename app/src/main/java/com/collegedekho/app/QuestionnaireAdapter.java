package com.collegedekho.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Mayank Gautam
 *         Created: 20/07/15
 */
public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireHolder> {
    @Override
    public QuestionnaireHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(QuestionnaireHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class QuestionnaireHolder extends RecyclerView.ViewHolder {
        TextView

        public QuestionnaireHolder(View itemView) {
            super(itemView);

        }
    }
}
