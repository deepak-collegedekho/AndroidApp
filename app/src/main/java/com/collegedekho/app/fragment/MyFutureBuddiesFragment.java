package com.collegedekho.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.MyFBCommentsListAdapter;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyFutureBuddiesFragment extends BaseFragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyFutureBuddy mMyFutureBuddies;
    private ArrayList<MyFutureBuddyComment> mMyFBCommentsSet;
    private OnMyFBInteractionListener mListener;
    private MyFBCommentsListAdapter mMyFBCommentsListAdapter;
    private EditText mChatText;
    private RecyclerView mCommentsListView;
    private TextView mEmptyTextView;
    private Timer mMyFbRefreshTimer;
    private MainActivity mMainActivity;
    private volatile boolean mSubmittingState = false;
    private static final Object mSubmitLock = new Object();
    private int mInitialCount;
    private int mIncrement;


    public static MyFutureBuddiesFragment newInstance(MyFutureBuddy myFutureBuddies, int commentsCount) {
        MyFutureBuddiesFragment fragment = new MyFutureBuddiesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, myFutureBuddies);
        args.putInt(ARG_PARAM2, commentsCount);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFutureBuddiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            this.mMyFutureBuddies = this.getArguments().getParcelable(ARG_PARAM1);
            this.mInitialCount = this.getArguments().getInt(ARG_PARAM2);
            this.mMyFBCommentsSet = this.mMyFutureBuddies.getFutureBuddiesCommentsSet();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_my_fb, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        ((TextView) rootView.findViewById(R.id.fb_title)).setText(this.mMyFutureBuddies.getInstitute_name());
        this.mChatText = (EditText) rootView.findViewById(R.id.fb_chat_input);

        (rootView.findViewById(R.id.fb_push_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int connectivityStatus= NetworkUtils.getConnectivityStatus();
                if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
                final String value = mChatText.getText().toString();
                if (value.trim().equals(""))
                    mListener.displayMessage(R.string.ENTER_YOUR_MESSAGE);
                else
                {
                    User user =MainActivity.user;
                    if (user == null || user.getName().isEmpty() || user.getName().equalsIgnoreCase("Anonymous user"))
                    {
                        mChatText.setText("");
                         // get name from my profile me
                        if(user.profileData[0] != null){
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(MainActivity.getResourceString(R.string.USER_NAME), user.profileData[0]);
                            mListener.onNameUpdated(hashMap, value.trim());
                            return;
                        }

                       // show dialog for name if user name is not present
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.setContentView(R.layout.name_dailog);
                        dialog.setCanceledOnTouchOutside(true);
                        TextView submit = (TextView)dialog.findViewById(R.id.name_submit);
                        dialog.show();

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String name =   ((EditText) dialog.findViewById(R.id.user_name)).getText().toString();
                                if(name == null || name.length() <= 0)
                                {
                                   mListener.displayMessage(R.string.NAME_EMPTY);
                                    return;
                                }
                                else if(!Utils.isValidName(name)){
                                    mListener.displayMessage(R.string.NAME_INVALID);
                                    return;
                                }

                                dialog.dismiss();
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(MainActivity.getResourceString(R.string.USER_NAME), name);
                                mListener.onNameUpdated(hashMap, value);
                            }
                        });

                        return;
                    }
                    mSubmittedChat(value);
                }

            }else {
                    mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                }
            }
        });

        if (this.mMyFBCommentsSet.size() == 0)
            (this.mEmptyTextView).setText("Say Hi to your Future Buddies");

        this.mMyFBCommentsListAdapter = new MyFBCommentsListAdapter(getActivity(), this.mMyFBCommentsSet);

        this.mCommentsListView = (RecyclerView) rootView.findViewById(R.id.my_fb_add_comment_list);
        this.mCommentsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mCommentsListView.setAdapter(this.mMyFBCommentsListAdapter);
        this.mCommentsListView.setItemAnimator(new DefaultItemAnimator());

        //mCommentsListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
    }

    private void mSubmittedChat(String value)
    {
        this.setmSubmittingState(true);

        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        MyFutureBuddyComment fbComment = new MyFutureBuddyComment();

        fbComment.setComment(value);
        fbComment.setToken(MainActivity.user.getToken());
        fbComment.setIndex(this.mMyFBCommentsSet.size());
        fbComment.setFbIndex(this.mMyFutureBuddies.getIndex());
        fbComment.setCommentSent(false);

        this.mMyFBCommentsSet.add(this.mMyFBCommentsSet.size(), fbComment);

        this.mMyFBCommentsListAdapter.notifyItemInserted(this.mMyFBCommentsSet.size() - 1);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();

        this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);

        this.mListener.onMyFBCommentSubmitted(this.mMyFutureBuddies.getResource_uri(), value, this.mMyFutureBuddies.getIndex(), this.mMyFBCommentsSet.size());
        this.mChatText.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
            this.mListener = (OnMyFBInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMyFBInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_PARAM1, this.mMyFutureBuddies);
        outState.putInt(ARG_PARAM2, this.mInitialCount);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //If number of comments received in the comments set is more than received in one
        // go then ic_call_vector the function to update the comments count value
        // TODO: track number of times next page is called (say N) and multiply Constants.NUMBER_OF_COMMENTS_IN_ONE_GO with the N, once next page provision is there
        if (this.mMyFBCommentsSet.size() > Constants.NUMBER_OF_COMMENTS_IN_ONE_GO)
        {
            int increment = this.mMyFBCommentsSet.size() - Constants.NUMBER_OF_COMMENTS_IN_ONE_GO;

            this.mListener.onMyFBUpdated(increment, this.mMyFutureBuddies.getIndex());
        }

        this.mListener = null;

        System.gc();
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

        //Declare the timer
        this.mMyFbRefreshTimer = new Timer();
        //Set the schedule function and rate
        this.mMyFbRefreshTimer.scheduleAtFixedRate(new TimerTask() {
                                                       @Override
                                                       public void run() {
                                                           //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                                           //update the comment list here after checking the internet connection
                                                           if (Constants.IS_CONNECTED_TO_INTERNET)
                                                               mMainActivity.networkUtils.networkData(Constants.TAG_REFRESH_MY_FB + "#" + String.valueOf(mMyFutureBuddies.getIndex()) + "#" + String.valueOf(mMyFBCommentsSet.size()), mMyFutureBuddies.getResource_uri(), null, Request.Method.GET);
                                                           //else
                                                           //Toast.makeText(mMainActivity, "Please connect to internet to receive messages..", Toast.LENGTH_SHORT).show();
                                                       }
                                                   },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                Constants.MY_FB_REFRESH_RATE);

        //scroll to the bottom position of comment's list
        if (this.mMyFBCommentsSet.size() > 0)
            this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if (this.mMyFbRefreshTimer != null)
        {
            this.mMyFbRefreshTimer.cancel();
            this.mMyFbRefreshTimer.purge();

            this.mMyFbRefreshTimer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        String resourceURI = this.mMyFutureBuddies.getResource_uri();
        String[] resourceURIArray = resourceURI.split("/");

        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_MY_FB_ENUMERATION + "/personalize/forums/" + resourceURIArray[resourceURIArray.length - 1]);

        AnalyticsUtils.AppIndexingView("CollegeDekho - MyFutureBuddy - " + this.mMyFutureBuddies.getInstitute_name(), val, val, (MainActivity) this.getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();

        String resourceURI = this.mMyFutureBuddies.getResource_uri();
        String[] resourceURIArray = resourceURI.split("/");

        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_MY_FB_ENUMERATION + "/personalize/forums/" + resourceURIArray[resourceURIArray.length - 1]);

        AnalyticsUtils.AppIndexingView("CollegeDekho - MyFutureBuddy - " + this.mMyFutureBuddies.getInstitute_name(), val, val, (MainActivity) this.getActivity(), false);
    }

    public void commentAdded(MyFutureBuddyComment comment)
    {
        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mMyFBCommentsSet.set(this.mMyFBCommentsSet.size() - 1, comment);

        this.mMyFBCommentsListAdapter.notifyItemChanged(this.mMyFBCommentsSet.size() - 1);
        //this.mMyFBCommentsListAdapter.notifyItemInserted(this.mMyFBCommentsSet.size() - 1);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();

        this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);

        this.setmSubmittingState(false);
    }
    public void sendChatRequest(String value)
    {
        this.mSubmittedChat(value);
    }

    public void updateChatPings(List<MyFutureBuddyComment> chatPings)
    {
        if (this.mSubmittingState)
            return;

        this.mMyFBCommentsSet.clear();

        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mMyFBCommentsSet.addAll(chatPings);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();
    }

    public void setmSubmittingState(boolean val) {
        synchronized (this.mSubmitLock) {
            this.mSubmittingState = val;
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    public interface OnMyFBInteractionListener {
        void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex);
        void onMyFBUpdated(int commentsSize, int myFbIndex);
        void onNameUpdated(HashMap params, String msg);
        void displayMessage(int messageId);
    }
}
