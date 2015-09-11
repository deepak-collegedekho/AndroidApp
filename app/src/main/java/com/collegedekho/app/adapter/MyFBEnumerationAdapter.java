package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.MyFutureBuddiesEnumeration;
import com.collegedekho.app.fragment.MyFutureBuddiesEnumerationFragment;

import java.util.ArrayList;

/**
 * @author harshvardhan
 *         Created: 15/08/15
 */
public class MyFBEnumerationAdapter extends RecyclerView.Adapter {

    private static final String TAG = "MyFBEnumerationAdapter";
    private ArrayList<MyFutureBuddiesEnumeration> mMyFBEnumeration;
    private Context mContext;

    public MyFBEnumerationAdapter(Context context, ArrayList<MyFutureBuddiesEnumeration> fbEnumeration) {
        this.mMyFBEnumeration = fbEnumeration;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.card_my_fb_enumeration, parent, false);
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

        myFBEnumerationHolder.instituteName.setText(myFBEnumeration.getInstitute_name());
        myFBEnumerationHolder.instituteLocation.setText(text);
        //myFBEnumerationHolder.commentsCount.setText(String.valueOf(myFBEnumeration.getComments_count()) + " chats");
        myFBEnumerationHolder.membersCount.setText(String.valueOf(myFBEnumeration.getMembers_count()) + " friends");
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

        MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener mListener;

        public MyFBEnumerationHolder(View itemView, MyFutureBuddiesEnumerationFragment.OnMyFBSelectedListener listener) {
            super(itemView);

            this.instituteName = (TextView) itemView.findViewById(R.id.fb_institute_name);
            this.instituteLocation = (TextView) itemView.findViewById(R.id.fb_institute_location);
            //this.commentsCount = (TextView) itemView.findViewById(R.id.fb_comments_count);
            this.membersCount = (TextView) itemView.findViewById(R.id.fb_members_count);

            this.mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.mListener.onMyFBSelected(mMyFBEnumeration.get(this.getAdapterPosition()), this.getAdapterPosition(), mMyFBEnumeration.get(this.getAdapterPosition()).getComments_count());
        }
    }
}
