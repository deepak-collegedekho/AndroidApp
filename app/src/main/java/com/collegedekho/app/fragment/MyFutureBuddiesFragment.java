package com.collegedekho.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.collegedekho.app.activity.MainActivity.currentFragment;
import static com.collegedekho.app.activity.MainActivity.mProfile;
import static com.collegedekho.app.utils.AnalyticsUtils.SendAppEvent;

public class MyFutureBuddiesFragment extends BaseFragment{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = MyFutureBuddiesFragment.class.getSimpleName();
    private MyFutureBuddy mMyFutureBuddies;
    private ArrayList<MyFutureBuddyComment> mMyFBCommentsSet;
    private OnMyFBInteractionListener mListener;
    private MyFBCommentsListAdapter mMyFBCommentsListAdapter;
    private EditText mChatText;
    private RecyclerView mCommentsListView;
    private LinearLayoutManager mLayoutManager;
    private TextView mEmptyTextView;
    private Timer mMyFbRefreshTimer;
    private MainActivity mMainActivity;
    public int lastItemPosition;
    private volatile boolean mSubmittingState = false;
    private static final Object mSubmitLock = new Object();
    private int mInitialCount;
    private String mOtherAppSharedMessage;

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
            if(this.mMyFBCommentsSet == null){
                this.mMyFBCommentsSet = new ArrayList<>();
            }
            this.mOtherAppSharedMessage = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_my_fb, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mEmptyTextView = (TextView) view.findViewById(android.R.id.empty);
        String l3Number = mMyFutureBuddies.getL3_number();
        View phoneCallButton = view.findViewById(R.id.call_to_partner_college);
        phoneCallButton.setOnClickListener(this);
        if(mMyFutureBuddies.getIs_partner_college().equalsIgnoreCase("1")
                && l3Number != null && !l3Number.isEmpty()){
            phoneCallButton.setVisibility(View.VISIBLE);
        }else{
            phoneCallButton.setVisibility(View.GONE);
        }
        String instiFullName = mMyFutureBuddies.getInstitute_name();
        if(mMyFutureBuddies.getCity_name() != null && !mMyFutureBuddies.getCity_name().equalsIgnoreCase("null")){
            instiFullName = instiFullName + " | " + mMyFutureBuddies.getCity_name().trim();
        } else if (mMyFutureBuddies.getState_name() != null && !mMyFutureBuddies.getState_name().equalsIgnoreCase("null")){
            instiFullName = instiFullName + " | " + mMyFutureBuddies.getState_name().trim();
        }
        TextView headingTitleTV= (TextView) view.findViewById(R.id.fb_heading);
        TextView instituteNameTV= (TextView) view.findViewById(R.id.fb_title);
        if(mMyFutureBuddies != null ) {
            if (mMyFutureBuddies.isCounselor()) {
                instituteNameTV.setVisibility(View.GONE);
                headingTitleTV.setText(getString(R.string.counselor_chat));
            } else {
                instituteNameTV.setVisibility(View.VISIBLE);
                instituteNameTV.setText(instiFullName);
            }
        }

        this.mChatText = (EditText) view.findViewById(R.id.fb_chat_input);
        View chatSubmitButton = view.findViewById(R.id.fb_push_chat);
        chatSubmitButton.setOnClickListener(this);
        chatSubmitButton.setContentDescription("Click to send your message.");

        if (this.mMyFBCommentsSet.size() == 0) {
            if(mMyFutureBuddies.isCounselor()){
                (this.mEmptyTextView).setText("Ask your queries and discuss with our counselor");
            }else {
                (this.mEmptyTextView).setText("Say Hi to your Future Buddies");
            }
        }

        this.mMyFBCommentsListAdapter = new MyFBCommentsListAdapter(getActivity(), this.mMyFBCommentsSet);

        this.mCommentsListView = (RecyclerView) view.findViewById(R.id.my_fb_add_comment_list);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.mCommentsListView.setLayoutManager(this.mLayoutManager);
        this.mCommentsListView.setAdapter(this.mMyFBCommentsListAdapter);
        this.mCommentsListView.setItemAnimator(new DefaultItemAnimator());

        this.mCommentsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if( dy < 0){
                    int topVisiblePosition = MyFutureBuddiesFragment.this.mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if(topVisiblePosition == 0){
                        if(MyFutureBuddiesFragment.this.mListener != null && MyFutureBuddiesFragment.this.mLayoutManager != null){
                            if(mMyFutureBuddies != null && mMyFutureBuddies.getNext() != null && !mMyFutureBuddies.getNext().equalsIgnoreCase("null")){
                                MyFutureBuddiesFragment.this.lastItemPosition = MyFutureBuddiesFragment.this.mLayoutManager.findLastVisibleItemPosition();
                                String next = mMyFutureBuddies.getNext();
                                mMyFutureBuddies.setNext(null);
                                mListener.onScrolledToTop(next);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fb_push_chat:
                this.onChatSubmitClick();
                break;
            case R.id.call_to_partner_college:
                this.callToPartnerCollege();
                break;
            default:
                break;

        }
    }
    private void callToPartnerCollege(){
        if(mMyFutureBuddies == null){
            return;
        }
        String l3Number = mMyFutureBuddies.getL3_number();
        if(l3Number == null || l3Number.isEmpty()){
            return;
        }
        Uri number = Uri.parse("tel:" + l3Number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
        //Events
        Map<String, Object> eventValue = new HashMap<>();
        eventValue.put(getString(R.string.ACTION_USER_PREFERENCE),getString(R.string.ACTION_L3_PARTNER_COLLEGE_CALL_SELECTED));
        SendAppEvent(getString(R.string.CATEGORY_PREFERENCE), getString(R.string.ACTION_L3_PARTNER_COLLEGE_CALL_SELECTED), eventValue, getActivity());

    }

    private void onChatSubmitClick(){
        int connectivityStatus = NetworkUtils.getConnectivityStatus(getContext());
        if (connectivityStatus == Constants.TYPE_NOT_CONNECTED) {
            mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        final String value = mChatText.getText().toString();
        if (value.trim().equals("")) {
            mListener.displayMessage(R.string.ENTER_YOUR_MESSAGE);
            return;
        }
        Profile profile = mProfile;
        if (profile != null) {
            mChatText.setText("");
            if(mMyFutureBuddies != null && mMyFutureBuddies.isCounselor()){
                if(mProfile.getIs_verified() == ProfileMacro.NUMBER_VERIFIED) {
                    if (profile.getName() == null || profile.getName().isEmpty() ||
                            profile.getName().equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {
                        mAskForName(value);
                        return;
                    }
                }else {
                    mListener.onRequestNumberVerification(mMyFutureBuddies.getResource_uri(),mMyFutureBuddies.getIndex(), mMyFBCommentsSet.size(),value);
                    return;
                }
            }else if(profile.getName() == null || profile.getName().isEmpty() ||
                    profile.getName().equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {
                mAskForName(value);
                return;
            }
            mSubmittedChat(value);
        }
    }

    private void mAskForName(final String chatMsg){
            // show dialog for name if user name is not present
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_name);
            dialog.setCanceledOnTouchOutside(true);
            TextView submit = (TextView) dialog.findViewById(R.id.name_submit);
            dialog.show();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = ((EditText) dialog.findViewById(R.id.user_name)).getText().toString();
                    if (name.length() <= 0) {
                        mListener.displayMessage(R.string.NAME_EMPTY);
                        return;
                    } else if (!Utils.isValidName(name)) {
                        mListener.displayMessage(R.string.NAME_INVALID);
                        return;
                    }

                    dialog.dismiss();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(getString(R.string.USER_NAME), name);
                    mListener.onNameUpdated(hashMap, chatMsg);
                }
            });
    }
    private void mHandleOtherAppMessage(){
        int connectivityStatus= NetworkUtils.getConnectivityStatus(getContext());
        if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {

            final String value = this.mOtherAppSharedMessage;
            Log.e("chat_text :: ",value);
            if (value.trim().equals("")) {
                mListener.displayMessage(R.string.ENTER_YOUR_MESSAGE);
            } else {
                Profile profile = mProfile;
                if (profile == null || profile.getName() == null || profile.getName().isEmpty() || profile.getName().equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {

                    mChatText.setText("");
                    // show dialog for name if mDeviceProfile name is not present
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.dialog_name);
                    dialog.setCanceledOnTouchOutside(true);
                    TextView submit = (TextView)dialog.findViewById(R.id.name_submit);
                    dialog.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name =   ((EditText) dialog.findViewById(R.id.user_name)).getText().toString();
                            if( name.length() <= 0)
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
                            hashMap.put(getString(R.string.USER_NAME), name);
                            mListener.onNameUpdated(hashMap, value);
                        }
                    });

                    return;
                }
                mSubmittedChat(value);
            }
        } else {
            mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
        }
    }

    private void mSubmittedChat(String value) {
        this.setmSubmittingState(true);

        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        MyFutureBuddyComment fbComment = new MyFutureBuddyComment();

        fbComment.setComment(value);
        fbComment.setToken(mProfile.getToken());
        fbComment.setIndex(this.mMyFBCommentsSet.size());
        fbComment.setFbIndex(this.mMyFutureBuddies.getIndex());
        fbComment.setCommentSent(false);

        this.mMyFBCommentsSet.add(this.mMyFBCommentsSet.size(), fbComment);
        this.mMyFBCommentsListAdapter.notifyItemInserted(this.mMyFBCommentsSet.size() - 1);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();
        this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);
        this.mListener.onMyFBCommentSubmitted(this.mMyFutureBuddies.getResource_uri(), value, this.mMyFutureBuddies.getIndex(), this.mMyFBCommentsSet.size(), mMyFutureBuddies.isCounselor());
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

        if (this.mMainActivity != null) {
            currentFragment = this;
            this.mOtherAppSharedMessage = mMainActivity.getOtherAppSharedMessage();
        }

        if(mOtherAppSharedMessage != null){
            mHandleOtherAppMessage();
            mOtherAppSharedMessage = null;
            mMainActivity.setOtherAppSharedMessage(null);
//            mMainActivity.setIsSharedOtherApp(false);
        }

        //Declare the timer
        this.mMyFbRefreshTimer = new Timer();
        //Set the schedule function and rate
        this.mMyFbRefreshTimer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        //Called each time when 1000 milliseconds (1 second) (the period parameter)
                        //update the comment list here after checking the internet connection
                        if (Constants.IS_CONNECTED_TO_INTERNET) {
                            String tag = Constants.TAG_REFRESH_MY_FB;
                            if(mMyFutureBuddies.isCounselor()){
                                tag = Constants.TAG_COUNSELOR_REFRESH;
                            }
                            MainActivity.mNetworkUtils.networkData(tag+ "#"
                                            + String.valueOf(mMyFutureBuddies.getIndex()) + "#" + String.valueOf(MyFutureBuddiesFragment.this.mInitialCount)
                                    , mMyFutureBuddies.getResource_uri(), null, Request.Method.GET);
                        }
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
    public void onPause(){
        super.onPause();
        if (this.mMyFbRefreshTimer != null)   {
            this.mMyFbRefreshTimer.cancel();
            this.mMyFbRefreshTimer.purge();
            this.mMyFbRefreshTimer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String resourceURI = this.mMyFutureBuddies.getResource_uri();
        if (resourceURI != null) {
            String[] resourceURIArray = resourceURI.split("/");
            Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_MY_FB_ENUMERATION + "/personalize/forums/" + resourceURIArray[resourceURIArray.length - 1]);
            //web_uri_val is null since there is no web counterpart for MyFB
            AnalyticsUtils.AppIndexingView("CollegeDekho - MyFutureBuddy - " + this.mMyFutureBuddies.getInstitute_name(), null, app_uri_val, (MainActivity) this.getActivity(), true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        String resourceURI = this.mMyFutureBuddies.getResource_uri();
        if (resourceURI != null) {
            String[] resourceURIArray = resourceURI.split("/");
            Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_MY_FB_ENUMERATION + "/personalize/forums/" + resourceURIArray[resourceURIArray.length - 1]);

            //web_uri_val is null since there is no web counterpart for MyFB
            AnalyticsUtils.AppIndexingView("CollegeDekho - MyFutureBuddy - " + this.mMyFutureBuddies.getInstitute_name(), null, app_uri_val, (MainActivity) this.getActivity(), false);
        }
    }

    public void commentAdded(MyFutureBuddyComment comment)
    {
        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);
        if(mMyFBCommentsSet.size() == 0) {
            this.mMyFBCommentsSet.add(comment);
            this.mMyFBCommentsListAdapter.notifyDataSetChanged();
        }else {
            this.mMyFBCommentsSet.set(this.mMyFBCommentsSet.size() - 1, comment);
            this.mMyFBCommentsListAdapter.notifyItemChanged(this.mMyFBCommentsSet.size() - 1);
            this.mMyFBCommentsListAdapter.notifyDataSetChanged();
            this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);
        }
        this.setmSubmittingState(false);
    }

    public void sendChatRequest(String value)
    {
        this.mSubmittedChat(value);
    }

    public void updateChatPings(List<MyFutureBuddyComment> chatPings, int newCommentCount)
    {
        if(!isAdded()){
            return;
        }
        if (this.mSubmittingState || chatPings == null )
            return;

        if(this.mMyFBCommentsSet == null){
            this.mMyFBCommentsSet = new ArrayList<>();
        }else {
            this.mMyFBCommentsSet.clear();
        }

        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mMyFBCommentsSet.addAll(chatPings);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();
        int netNewComments = newCommentCount - mInitialCount;
        this.mInitialCount = newCommentCount;
        if(netNewComments > 0){
           if(this.mCommentsListView != null){
               this.mCommentsListView.scrollToPosition(this.mMyFBCommentsSet.size() - 1);
            }
        }

        if(mMainActivity != null){
            mMainActivity.speakMessageForAccessibility("You have "+String.valueOf(netNewComments)+" new Comments");
        }
    }

    public void setmSubmittingState(boolean val) {
        synchronized (this.mSubmitLock) {
            this.mSubmittingState = val;
        }
    }

    public void mDisplayPreviousComments(MyFutureBuddy myFB){
        if(myFB != null) {
            ArrayList<MyFutureBuddyComment> previousCommentsList = myFB.getFutureBuddiesCommentsSet();
            if(previousCommentsList != null && !previousCommentsList.isEmpty()) {
                int lastCommentPosition = previousCommentsList.size() + this.lastItemPosition;
                previousCommentsList.addAll(this.mMyFBCommentsSet);
                this.mMyFBCommentsSet = previousCommentsList;
                this.mMyFutureBuddies.setNext(myFB.getNext());
                this.mMyFBCommentsListAdapter.setmMyFBCommentList(mMyFBCommentsSet);
                this.mMyFBCommentsListAdapter.notifyDataSetChanged();
                this.mCommentsListView.scrollToPosition(lastCommentPosition);
            }
        }
    }



    @Override
    public String getEntity() {
        if (this.mMyFutureBuddies != null){
            String[] resourceURISplit = this.mMyFutureBuddies.getResource_uri().split("/");
            return resourceURISplit[resourceURISplit.length - 1];
        }
        else
            return null;
    }



    public void updateMyFBFromNotification(MyFutureBuddy myFutureBuddy) {
        this.mMyFutureBuddies = myFutureBuddy;
        this.mInitialCount =0;
        this.mOtherAppSharedMessage = null;

        String instiFullName = mMyFutureBuddies.getInstitute_name();
        if(mMyFutureBuddies.getCity_name() != null && !mMyFutureBuddies.getCity_name().equalsIgnoreCase("null")){
            instiFullName = instiFullName + " | " + mMyFutureBuddies.getCity_name().trim();
        } else if (mMyFutureBuddies.getState_name() != null && !mMyFutureBuddies.getState_name().equalsIgnoreCase("null")){
            instiFullName = instiFullName + " | " + mMyFutureBuddies.getState_name().trim();
        }
        if(getView() != null) {
            ((TextView) getView().findViewById(R.id.fb_title)).setText(instiFullName);
        }
       updateChatPings(mMyFutureBuddies.getFutureBuddiesCommentsSet(),myFutureBuddy.getComments_count());

    }

    public interface OnMyFBInteractionListener {
        void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex, boolean counselor);
        void onMyFBUpdated(int commentsSize, int myFbIndex);
        void onNameUpdated(Map<String, String> params, String msg);
        void onRequestNumberVerification(String uri, int index, int size, String msg);
        void displayMessage(int messageId);
        void onScrolledToTop(String next);
    }
}
