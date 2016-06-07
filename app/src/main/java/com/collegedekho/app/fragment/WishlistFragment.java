package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.WishlistInstituteListAdapter;
import com.collegedekho.app.display.peekandpop.PeekAndPop;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WishlistFragment.WishlistInstituteInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends BaseFragment {

    public static final String TITLE = "Wishlist Institutes";
    private static final String ARG_INSTITUTE = "wishlist_institute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String TAG = "Wishlist Institute Fragment";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private boolean filterAllowed;
    private TextView mEmptyTextView;
    private boolean IS_TUTE_COMPLETED = true;
    private RecyclerView recyclerView;
    //private View instituteView;
    private CardView wishlistInstituteCard;
    private PeekAndPop peekAndPop;
    private WishlistInstituteListAdapter mWishlistInstituteListAdapter;

    public WishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WishlistFragment.
     */
    public static WishlistFragment newInstance(ArrayList<Institute> institutes, String title, String next, boolean filterAllowed, int listType) {
        WishlistFragment fragment = new WishlistFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putBoolean(ARG_FILTER_ALLOWED, filterAllowed);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
            this.filterAllowed = getArguments().getBoolean(ARG_FILTER_ALLOWED);
        }
    }

    public void RemoveInstitute(int position)
    {
        this.mInstitutes.remove(position);
        this.mWishlistInstituteListAdapter.notifyItemRemoved(position);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL = (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.wishlist_institute_grid);

        layoutManager = new GridLayoutManager(getActivity(), 3);

        //this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 0, false));
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addOnScrollListener(scrollListener);

        this.setupPeekAndPopStandard();

        return rootView;
    }

    private void setupPeekAndPopStandard(){
        peekAndPop = new PeekAndPop.Builder(this.getActivity(), WishlistFragment.this)
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                .build();
        
        this.mWishlistInstituteListAdapter = new WishlistInstituteListAdapter(this.getActivity(), this.mInstitutes, Constants.TYPE_STANDARD, peekAndPop);

        recyclerView.setAdapter(this.mWishlistInstituteListAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_INSTITUTE, this.mInstitutes);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
        outState.putBoolean(ARG_FILTER_ALLOWED, this.filterAllowed);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof  MainActivity)
                listener = (WishlistInstituteInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement WishlistInstituteInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onPause() {
        super.onPause();
        loading=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

/*
        if (mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your preferences, please change your filters in ‘*Resource Buddy*’!");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            this.mEmptyTextView.setVisibility(View.GONE);
        }
*/
    }

    @Override
    public void onDestroy() {
        this.peekAndPop.destroy();
        super.onDestroy();
    }

    public void clearList() {
        if(this.mInstitutes == null || this.mInstitutes.size() == 0)return;
        this.mInstitutes.clear();
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    public void updateLastList(List<Institute> institutes, String next)
    {
        if(mInstitutes != null){
            mInstitutes.clear();
            updateList(institutes, next);
        }
    }

    public void updateList(List<Institute> institutes, String next) {
        progressBarLL.setVisibility(View.GONE);
        this.mWishlistInstituteListAdapter.lastPosition = this.mInstitutes.size() - 1;
        mInstitutes.addAll(institutes);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;

/*
        if (mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your preferences, please change your filters in ‘*Resource Buddy*’!");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            this.mEmptyTextView.setVisibility(View.GONE);
        }
*/
    }


    public interface WishlistInstituteInteractionListener extends BaseListener {

        void OnWishlistInstituteSelected(Institute institute);
        void OnWishlistInstituteApplied(Institute institute, int position);
        void OnWishlistInstituteRemoved(Institute institute, int position);

        @Override
        void onEndReached(String next, int type);
        @Override
        void onInstituteLikedDisliked(int position, int liked);
        void displayMessage(int messageId);
    }
}