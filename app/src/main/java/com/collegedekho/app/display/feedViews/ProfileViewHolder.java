package com.collegedekho.app.display.feedViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;

import org.greenrobot.eventbus.EventBus;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * Created by sureshsaini on 15/2/17.
 */

public class ProfileViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    private final TextView profileProgress;
    private final Button completeButton;
    private final ProgressBar profileCompletionProgress;
    private final ImageView profileCompletionCross;
    private final Context mContext;

    public ProfileViewHolder(View itemView, Context mContext) {
        super(itemView);
        this.profileProgress = (TextView) itemView.findViewById(R.id.profile_progress);
        this.profileCompletionCross = (ImageView) itemView.findViewById(R.id.profile_complete_cross);
        this.profileCompletionProgress = (ProgressBar) itemView.findViewById(R.id.profile_completion_progress);
        this.completeButton = (Button) itemView.findViewById(R.id.complete_your_profile);
        this.completeButton.setOnClickListener(this);
        this.profileCompletionCross.setOnClickListener(this);

        this.mContext = mContext;
    }

    public void updateProfileCompletionBar() {
        if(mProfile == null)
            return;

        int profileProgress = mProfile.getProgress();
        this.profileProgress.setText(String.valueOf(profileProgress)+"%");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.profileCompletionProgress.setProgress(profileProgress, true);
        }else{
            this.profileCompletionProgress.setProgress(profileProgress);
        }
        int progressColor = Color.GREEN;
        if(profileProgress <= 40){
            progressColor = Color.RED;

        }else if( profileProgress <= 70){
            progressColor = Color.rgb(255,128,0);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.profileCompletionProgress.setProgressTintList(ColorStateList.valueOf(progressColor));
        }else{
            this.profileCompletionProgress.getProgressDrawable().setColorFilter(progressColor
                    , android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.complete_your_profile:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_PROFILE_COMPLETION_CLICK,null, null ));
                break;
            case R.id.profile_complete_cross:
                EventBus.getDefault().post(new Event(AllEvents.ACTION_REMOVE_PROFILE_COMPLETION_CLICK,null, null ));
                break;
            default:
                break;
        }
    }
}
