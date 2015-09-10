package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.MyFBCommentsListAdapter;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.widget.DividerItemDecoration;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class MyFutureBuddiesFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private MyFutureBuddy mMyFutureBuddies;
    private ArrayList<MyFutureBuddyComment> mMyFBCommentsSet;
    private OnMyFBInteractionListener mListener;
    private MyFBCommentsListAdapter mMyFBCommentsListAdapter;
    private EditText mchatText;
    private RecyclerView mCommentsListView;
    private TextView mEmptyTextView;

    public static MyFutureBuddiesFragment newInstance(MyFutureBuddy myFutureBuddies) {
        MyFutureBuddiesFragment fragment = new MyFutureBuddiesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, myFutureBuddies);
        fragment.setArguments(args);
        return fragment;
    }

    public MyFutureBuddiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMyFutureBuddies = getArguments().getParcelable(ARG_PARAM1);
            mMyFBCommentsSet = mMyFutureBuddies.getFutureBuddiesCommentsSet();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_my_fb, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        ((TextView) rootView.findViewById(R.id.fb_title)).setText(mMyFutureBuddies.getInstitute_name());
        this.mchatText = (EditText) rootView.findViewById(R.id.fb_chat_input);

        ((FloatingActionButton) rootView.findViewById(R.id.fb_push_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mchatText.getText().toString();
                if (value.equals("") || value.equals(" "))
                {
                    Toast.makeText(getActivity(),"Please enter your message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mListener.onMyFBCommentSubmitted(mMyFutureBuddies.getResource_uri(), value, mMyFutureBuddies.getIndex(), mMyFBCommentsSet.size());
                    mchatText.setText("");
                }
            }
        });

        if (mMyFBCommentsSet.size() == 0)
            (this.mEmptyTextView).setText("Say Hi to your Future Buddies");

        this.mMyFBCommentsListAdapter = new MyFBCommentsListAdapter(getActivity(), mMyFBCommentsSet);

        mCommentsListView = (RecyclerView) rootView.findViewById(R.id.my_fb_add_comment_list);
        mCommentsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommentsListView.setAdapter(this.mMyFBCommentsListAdapter);
        mCommentsListView.setItemAnimator(new DefaultItemAnimator());
        //mCommentsListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMyFBInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMyFBInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        System.gc();
    }

    public void commentAdded(MyFutureBuddyComment comment)
    {
        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mMyFBCommentsSet.add(mMyFBCommentsSet.size(), comment);
        this.mMyFBCommentsListAdapter.notifyItemInserted(mMyFBCommentsSet.size() - 1);
        this.mMyFBCommentsListAdapter.notifyDataSetChanged();

        this.mCommentsListView.scrollToPosition(mMyFBCommentsSet.size() - 1);

    }

    public interface OnMyFBInteractionListener {
        void onMyFBCommentSubmitted(String myFbURI, String commentText, int myFbIndex, int myFbCommentIndex);
    }
}
