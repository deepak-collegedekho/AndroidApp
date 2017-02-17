package com.collegedekho.app.display.feedViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by sureshsaini on 16/2/17.
 */

public class QnaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public  TextView questionText;
    public  TextView askedByUser;
    public  View questionDivider;

    public QnaViewHolder(View itemView) {
        super(itemView);
        questionText = (TextView) itemView.findViewById(R.id.feed_question_text);
        questionDivider = itemView.findViewById(R.id.question_divider);
        questionDivider.setVisibility(View.GONE);
        askedByUser = (TextView) itemView.findViewById(R.id.asked_by_user);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EventBus.getDefault().post(new Event(AllEvents.ACTION_SIMILAR_QUESTION_CLICK, null, null));
    }

}
