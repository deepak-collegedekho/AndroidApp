package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * @author harshvardhan
 *         Created: 15/08/15
 */
public class MyFBEnumerationAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MyFBEnumerationAdapter";
    private ArrayList<MyFutureBuddiesEnumeration> mMyFBEnumeration;
    private Context mContext;
    private int lastPosition = -1;

    public MyFBEnumerationAdapter(Context context, ArrayList<MyFutureBuddiesEnumeration> fbEnumeration) {
        this.mMyFBEnumeration = fbEnumeration;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.my_fb_institute_item, parent, false);
        try {
            return new MyFBEnumerationHolder(rootView, (MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement OnMyFBSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MyFutureBuddiesEnumeration myFBEnumeration = mMyFBEnumeration.get(position);

        MyFBEnumerationHolder myFBEnumerationHolder = (MyFBEnumerationHolder) holder;

        String text = "";
        if (myFBEnumeration.getCity_name() != null)
            text += myFBEnumeration.getCity_name() + ", ";
        if (myFBEnumeration.getState_name() != null)
            text += myFBEnumeration.getState_name();
        if(myFBEnumeration.getUnread_count() > 0)
        {
            myFBEnumerationHolder.unreadCountLayout.setVisibility(View.VISIBLE);
            myFBEnumerationHolder.unreadCount.setText(""+myFBEnumeration.getUnread_count());
        }

        myFBEnumerationHolder.instituteName.setText(myFBEnumeration.getInstitute_name());
        myFBEnumerationHolder.instituteLocation.setText(text);
        //myFBEnumerationHolder.commentsCount.setText(String.valueOf(myFBEnumeration.getComments_count()) + " chats");
        myFBEnumerationHolder.membersCount.setText(String.valueOf(myFBEnumeration.getMembers_count()));

        this.setAnimation(myFBEnumerationHolder.container, position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return this.mMyFBEnumeration.size();
    }

    private class MyFBEnumerationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView instituteName;
        TextView instituteLocation;
        //TextView commentsCount;
        TextView membersCount;
        RelativeLayout unreadCountLayout;
        TextView unreadCount;
        CardView container;

        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener mListener;

        public MyFBEnumerationHolder(View itemView, MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener listener) {
            super(itemView);

            this.instituteName = (TextView) itemView.findViewById(R.id.fb_institute_name);
            this.instituteLocation = (TextView) itemView.findViewById(R.id.fb_institute_location);
            //this.commentsCount = (TextView) itemView.findViewById(R.id.fb_comments_count);
            this.membersCount = (TextView) itemView.findViewById(R.id.fb_members_count);
            this.unreadCount = (TextView)itemView.findViewById(R.id.unread_count);
            this.unreadCountLayout= (RelativeLayout)itemView.findViewById(R.id.unread_notify_layout);
            this.container = (CardView) itemView.findViewById(R.id.card_fb_enumeration_container);

            this.mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int connectivityStatus=new NetworkUtils(v.getContext(), null).getConnectivityStatus();
            if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
                MyFutureBuddiesEnumeration myFbEnumration = mMyFBEnumeration.get(this.getAdapterPosition());
                myFbEnumration.setUnread_count(0);
                this.mListener.onMyFBSelected(myFbEnumration, this.getAdapterPosition(), mMyFBEnumeration.get(this.getAdapterPosition()).getComments_count());
            }else {
                this.mListener.onNoInternetConnection();
            }
        }
    }
}
