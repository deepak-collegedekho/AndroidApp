package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static final String TITLE = "Shortlist Institutes";
    private static final String ARG_INSTITUTE = "wishlist_institute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String TAG = "Wishlist Fragment";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private boolean filterAllowed;
    private TextView mEmptyTextView;
    private RecyclerView recyclerView;
    private PeekAndPop peekAndPop;
    private WishlistInstituteListAdapter mWishlistInstituteListAdapter;
    private MainActivity mMainActivity;

    public WishlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WishlistFragment.
     */
    public static WishlistFragment newInstance(ArrayList<Institute> institutes, String title, String next, boolean filterAllowed) {
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
            listType = Constants.WISH_LIST_TYPE;
        }
    }

    public void RemoveInstitute(int position)
    {

        this.mInstitutes.remove(position);
        this.mWishlistInstituteListAdapter.notifyItemRemoved(position);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();

        if(mInstitutes == null || mInstitutes.size() <= 0){
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(getString(R.string.you_do_not_have_any_shortlisted_college));
        }else{
            mEmptyTextView.setVisibility(View.GONE);
        }
    }

    public void UpdateAppliedStatus(int position)
    {
        this.mInstitutes.get(position).setIs_applied(true);
        this.mWishlistInstituteListAdapter.notifyItemChanged(position);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        boolean isTuteCompleted = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("Wishlist tute", false);
        if(!isTuteCompleted) {
            rootView.findViewById(R.id.recommended_tute_image).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.VISIBLE);
            getActivity().invalidateOptionsMenu();
        }
        else {
            rootView.findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
            rootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
        }

        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL = (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.wishlist_institute_grid);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 0, true));
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addOnScrollListener(scrollListener);

        this.setupPeekAndPopStandard();

        if(mInstitutes == null || mInstitutes.size() <= 0){
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(getString(R.string.you_do_not_have_any_shortlisted_college));
        }else{
            mEmptyTextView.setVisibility(View.GONE);
        }
        rootView.findViewById(R.id.recommended_tute_image).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_tute_frame).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.recommended_tute_image:
            case R.id.recommended_tute_frame:
                v.setVisibility(View.GONE);
                if(getView() != null){
                    getView().findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
                    getView().findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
                }
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean("Wishlist tute", true).apply();
                getActivity().invalidateOptionsMenu();
                break;
        }
    }

    private void setupPeekAndPopStandard(){
        peekAndPop = new PeekAndPop.Builder(this.getActivity())
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(recyclerView)
                .build();
        
        this.mWishlistInstituteListAdapter = new WishlistInstituteListAdapter(this.getActivity(), this.mInstitutes, Constants.TYPE_STANDARD, peekAndPop);

        this.recyclerView.setAdapter(this.mWishlistInstituteListAdapter);
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
    public String getEntity() {
        return null;
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
        this.mMainActivity = (MainActivity) this.getActivity();

        if (this.mMainActivity != null)
        {
            this.mMainActivity.currentFragment = this;

            Institute institute = this.mMainActivity.getCurrentInstitute();

            if (institute != null && institute.getPosition() >= 0)
            {
                //removed from shortlist
                if (institute.getIs_shortlisted() == 0)
                    this.mInstitutes.remove(institute.getPosition());
                //applied
                else
                    this.mInstitutes.set(institute.getPosition(), institute);

                //update the list
                this.mWishlistInstituteListAdapter.notifyDataSetChanged();

                //this.mMainActivity.setCurrentInstitute(null);
                institute.setPosition(-1);

                if(mInstitutes == null || mInstitutes.size() <= 0){
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mEmptyTextView.setText(getString(R.string.you_do_not_have_any_shortlisted_college));
                }else{
                    mEmptyTextView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        this.peekAndPop.destroy();
        //this.mMainActivity.setCurrentInstitute(null);
        Institute institute = this.mMainActivity.getCurrentInstitute();

        if (institute != null)
            institute.setPosition(-1);

        super.onDestroy();
    }

    public void clearList() {
        if(this.mInstitutes == null || this.mInstitutes.size() == 0)return;
        this.mInstitutes.clear();
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    public void updateLastList(List<Institute> institutes, String next)
    {
        if(this.mInstitutes != null){
            this.mInstitutes.clear();
            updateList(institutes, next);
        }
    }

    public void updateList(List<Institute> institutes, String next) {
        this.progressBarLL.setVisibility(View.GONE);
        this.mWishlistInstituteListAdapter.lastPosition = this.mInstitutes.size() - 1;
        this.mInstitutes.addAll(institutes);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
        this.loading = false;
        this.mNextUrl = next;
    }

    public interface WishlistInstituteInteractionListener extends BaseListener {

        void OnWishlistInstituteSelected(Institute institute, boolean isFromCard);
        void OnWishlistInstituteApplied(Institute institute, int position);
        void OnWishlistInstituteRemoved(Institute institute, int position);

        @Override
        void onEndReached(String next, int type);
        @Override
        void onInstituteLikedDisliked(int position, int liked);
        void displayMessage(int messageId);
    }
}
