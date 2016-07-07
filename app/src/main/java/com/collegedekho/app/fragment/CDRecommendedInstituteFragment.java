package com.collegedekho.app.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.WishlistInstituteListAdapter;
import com.collegedekho.app.display.peekandpop.PeekAndPop;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.display.swipableList.view.CardContainer;
import com.collegedekho.app.display.swipableList.view.SimpleCardStackAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CDRecommendedInstituteFragment extends BaseFragment implements SimpleCardStackAdapter.OnCDRecommendedAdapterInterface, CardContainer.OnSwipeDirectionListener {
    public static final String TITLE = "CDRecommendedInstitutes";
    private static final String ARG_INSTITUTE = "cdrecommendedinstitute";
    private static final String ARG_UNDECIDED_INSTITUTE_COUNT = "undecided_institute_count";
    private static final String ARG_WISHLIST_INSTITUTE_COUNT = "wishlist_institute_count";
    private static final String ARG_BUZZLIST_INSTITUTE_COUNT = "buzzlist_institute_count";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private SimpleCardStackAdapter mAdapter;
    private OnCDRecommendedInstituteListener mListener;
    private boolean IS_TUTE_COMPLETED = true;
    private boolean IS_WISHLIST_TUTE_COMPLETED = true;
    private CardContainer mCardContainer;
    private TextView mPageTitleTV;
    private TextView mEmptyTextView;
    private boolean IS_UNDECIDED_INSTITUTES = false;
    private ImageView mLikeImageView;
    private ImageView mDislikeImageView;
    private ImageView mUndecidedImageView;
    private int CARD_CATEGORY;
    private final StringBuilder cardState=new StringBuilder();
    private View questionLayout;
    private Animation cardMinimizeAnimation;
    private RecyclerView wishListRecyclerView;
    private PeekAndPop peekAndPop;
    private WishlistInstituteListAdapter mWishlistInstituteListAdapter;
    private TextView mUndecidedCountText;
    private TextView mBuzzListCountText;
    private TextView mRecommendedCountText;
    private TextView mWishListCountText;
    private int mUndecidedCount;
    private int mRecomendedCount;
    private int mWishListCount;
    private int mBuzzListCount;
    private View currentTab;
    private MainActivity mMainActivity;


    public CDRecommendedInstituteFragment() {
        // Required empty public constructor
    }

    public static CDRecommendedInstituteFragment newInstance(ArrayList<Institute> institutes, String title,String next,
                                                             int undecidedCount, int wishListCount, int  buzzListCount, int category) {
        CDRecommendedInstituteFragment fragment = new CDRecommendedInstituteFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, undecidedCount);
        args.putInt(ARG_WISHLIST_INSTITUTE_COUNT, wishListCount);
        args.putInt(ARG_BUZZLIST_INSTITUTE_COUNT, buzzListCount);
        args.putInt("CARD_CATEGORY",category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitutes = this.getArguments().getParcelableArrayList(ARG_INSTITUTE);
            this.mTitle = this.getArguments().getString(ARG_TITLE);
            this.mNextUrl = this.getArguments().getString(ARG_NEXT);
            if(mInstitutes != null)
            this.mRecomendedCount = this.mInstitutes.size();
            this.mUndecidedCount = this.getArguments().getInt(ARG_UNDECIDED_INSTITUTE_COUNT);
            this.mWishListCount = this.getArguments().getInt(ARG_WISHLIST_INSTITUTE_COUNT);
            this.mBuzzListCount = this.getArguments().getInt(ARG_BUZZLIST_INSTITUTE_COUNT);
            this.CARD_CATEGORY =this.getArguments().getInt("CARD_CATEGORY");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recommended_institute_listing, container, false);

        this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), false);
        if(!IS_TUTE_COMPLETED) {
            rootView.findViewById(R.id.recommended_tute_image).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.VISIBLE);
        }
        else {
            rootView.findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
            rootView.findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
        }

        listType = Constants.WISH_LIST_TYPE;
        this.mCardContainer = (CardContainer) rootView.findViewById(R.id.fragment_recommended_institute_cards_container);
        this.mCardContainer.setListener(this);

        this.mPageTitleTV           = (TextView)rootView.findViewById(R.id.recommended_page_title);
        this.mEmptyTextView         = (TextView)rootView.findViewById(android.R.id.empty);
        this.mLikeImageView         = (ImageView) rootView.findViewById(R.id.like_textview);
        this.mDislikeImageView      = (ImageView) rootView.findViewById(R.id.dislike_textview);
        this.mUndecidedImageView    = (ImageView) rootView.findViewById(R.id.decide_later_textview);
        this.mUndecidedCountText    = (TextView)rootView.findViewById(R.id.badge_counter);
        this.mBuzzListCountText     = (TextView)rootView.findViewById(R.id.cd_reco_buzzlist_count);
        this.mRecommendedCountText  = (TextView)rootView.findViewById(R.id.cd_reco_recommended_count);
        this.mWishListCountText     = (TextView)rootView.findViewById(R.id.cd_reco_wishlist_count);
        this.questionLayout         = rootView.findViewById(R.id.ask_user_layout);
        this.wishListRecyclerView   = (RecyclerView) rootView.findViewById(R.id.cd_reco_wish_list_institute_grid);
        this.progressBarLL          = (LinearLayout)rootView.findViewById(R.id.progressBarLL);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        this.wishListRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 0, false));
        this.wishListRecyclerView.setLayoutManager(layoutManager);
        this.wishListRecyclerView.setHasFixedSize(true);
        this.wishListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.wishListRecyclerView.addOnScrollListener(scrollListener);

        this.mUndecidedCountText.setText(""+this.mUndecidedCount);
        this.mRecommendedCountText.setText(""+this.mRecomendedCount);
        this.mWishListCountText.setText(""+this.mWishListCount);
        this.mBuzzListCountText.setText(""+this.mBuzzListCount);

        if(CARD_CATEGORY ==Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal()) {
            this.mCardContainer.setVisibility(View.GONE);
            this.wishListRecyclerView.setVisibility(View.VISIBLE);
        }else {
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.wishListRecyclerView.setVisibility(View.GONE);
        }

        if (this.mInstitutes == null || this.mInstitutes.size() <= 0) {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            this.wishListRecyclerView.setVisibility(View.GONE);
            this.mEmptyTextView.setText("No CD Recommended colleges found");
        }else {
            this.mEmptyTextView.setVisibility(View.GONE);
        }

        if(this.mAdapter == null){
            this.mAdapter = new SimpleCardStackAdapter(getActivity(), this.getContext(), this, CARD_CATEGORY);
        }
        this.mAddCardInAdapter(this.mInstitutes);
        this.mCardContainer.setAdapter(this.mAdapter);

        if(this.IS_UNDECIDED_INSTITUTES) {
            this.mUndecidedCountText.setClickable(false);
            this.mPageTitleTV.setText("CD Recommended Colleges - Decide Later");
        }else{
            this.mPageTitleTV.setText(mTitle);
        }
        getMinimizeAnimation();

        setupPeekAndPopStandard();

        rootView.findViewById(R.id.badge_counter_layout).setOnClickListener(this);
        rootView.findViewById(R.id.request_for_undecided_ok).setOnClickListener(this);
        rootView.findViewById(R.id.request_for_undecided_no).setOnClickListener(this);
        rootView.findViewById(R.id.tab_buzzlist).setOnClickListener(this);
        rootView.findViewById(R.id.tab_recommended).setOnClickListener(this);
        rootView.findViewById(R.id.tab_wishlist).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_tute_image).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_tute_frame).setOnClickListener(this);
        rootView.findViewById(R.id.wishlist_tute_image).setOnClickListener(this);
        rootView.findViewById(R.id.wishlist_tute_frame).setOnClickListener(this);

        return rootView;
    }

    private void mAddCardInAdapter(List<Institute> list)
    {
        final  ArrayList<CardModel> modelArrayList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--)
        {
            CardModel  model = new CardModel(list.get(i), this.getActivity());
            modelArrayList.add(model);
        }
        mAdapter.addAll(modelArrayList);
    }

    public void setDrawableBorder(){
        if(mAdapter != null)
            mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_orange_border_box));
    }

    private void setupPeekAndPopStandard(){
        peekAndPop = new PeekAndPop.Builder(this.getActivity())
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(wishListRecyclerView)
                .build();

        this.mWishlistInstituteListAdapter = new WishlistInstituteListAdapter(this.getActivity(), this.mInstitutes, Constants.TYPE_STANDARD, peekAndPop);

        wishListRecyclerView.setAdapter(this.mWishlistInstituteListAdapter);
    }

    private void showWishListUI(boolean isShow){
        if(isShow){
            mCardContainer.setVisibility(View.GONE);
        }else{
            wishListRecyclerView.setVisibility(View.GONE);
            progressBarLL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                this.mListener = (OnCDRecommendedInstituteListener) context;
                listener = (OnCDRecommendedInstituteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCDRecommendedInstituteListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_INSTITUTE, this.mInstitutes);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
        outState.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, this.mUndecidedCount);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
        listener = null;
        if (getActivity() != null)
            ((MainActivity) getActivity()).setCurrentInstitute(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getView() != null)
            getView().setLayerType(View.LAYER_TYPE_NONE, null);
        loading = false;
        if(currentTab != null){
            currentTab.animate().translationYBy(0f).scaleX(1.0f).scaleY(1.0f).setDuration(0).start();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null)
            getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if(CARD_CATEGORY ==Constants.CDRecommendedInstituteType.UNBAISED.ordinal()){
            currentTab = getView().findViewById(R.id.tab_recommended);
//            currentTab.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(0).start();
            mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_blue_border_box));
        } else if (CARD_CATEGORY ==Constants.CDRecommendedInstituteType.BUZZLIST.ordinal()){
            currentTab = getView().findViewById(R.id.tab_buzzlist);
//            currentTab.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(0).start();
            mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_orange_border_box));
        } else if (CARD_CATEGORY ==Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal()){
            currentTab = getView().findViewById(R.id.tab_wishlist);
//            currentTab.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(0).start();
        }

        this.mMainActivity = (MainActivity) this.getActivity();
        if (this.mMainActivity != null)
            this.mMainActivity.currentFragment = this;

        mMainActivity.translateAnimation(currentTab,null);

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

            this.mMainActivity.setCurrentInstitute(null);
        }
    }

    @Override
    public void onDestroy()
    {
        if (getActivity() != null)
            ((MainActivity) getActivity()).setCurrentInstitute(null);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.wishlist_tute_image:
            case R.id.wishlist_tute_frame:
                v.setVisibility(View.GONE);
                if(getView() != null){
                    getView().findViewById(R.id.wishlist_tute_image).setVisibility(View.GONE);
                    getView().findViewById(R.id.wishlist_tute_frame).setVisibility(View.GONE);
                }
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean("Wishlist tute", true).apply();
                break;
            case R.id.badge_counter_layout:
                if(mUndecidedCount <= 0){
                    Utils.DisplayToast(getActivity(), "You don't have any undecided Institute.");
                    return;
                }
                showWishListUI(false);
                CARD_CATEGORY = Constants.CDRecommendedInstituteType.UNDECIDED.ordinal();
                mRequestForUndecidedInstitutes();
                break;
            case R.id.recommended_tute_image:
            case R.id.recommended_tute_frame:
                v.setVisibility(View.GONE);
                if(getView() != null){
                    getView().findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
                    getView().findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
                }
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), true).apply();
                break;
            case R.id.tab_buzzlist:
                if(v.getId() != currentTab.getId()) {
                    mMainActivity.translateAnimation(v,currentTab);
//                    v.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(1000).start();
//                    currentTab.animate().translationYBy(10f).scaleX(1.0f).scaleY(1.0f).setDuration(1000).start();
                    mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_orange_border_box));
                }
                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.BUZZLIST.ordinal()) {
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.BUZZLIST.ordinal();
                    showWishListUI(false);
                    mEmptyTextView.setText("Loading for featured institutes...");
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mListener.onClickBuzzList();
                }
                currentTab = v;
                break;
            case R.id.tab_recommended:
                if(v.getId() != currentTab.getId()){
                    mMainActivity.translateAnimation(v,currentTab);
//                    v.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(1000).start();
//                    currentTab.animate().translationYBy(10f).scaleX(1.0f).scaleY(1.0f).setDuration(1000).start();
                    mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_blue_border_box));
                }


                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.UNBAISED.ordinal()) {
                    /*if(cardState != null && !cardState.toString().equals("MINIMIZED")
                            && mAdapter.getCardCategory() != Constants.CDRecommendedInstituteType.UNDECIDED.ordinal()) {
                        mCardContainer.startAnimation(cardMinimizeAnimation);
                    }*/
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.UNBAISED.ordinal();
                    showWishListUI(false);
                    mEmptyTextView.setText("Looking for recommended institutes...");
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mListener.onClickRecommendedList();
                }

                currentTab =v;
                break;
            case R.id.tab_wishlist:
                this.IS_WISHLIST_TUTE_COMPLETED= getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean("Wishlist tute", false);
                View view = getView();
                if(view != null){
                    if(!IS_WISHLIST_TUTE_COMPLETED) {
                        getView().findViewById(R.id.wishlist_tute_image).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.wishlist_tute_frame).setVisibility(View.VISIBLE);
                    }
                    else {
                        getView().findViewById(R.id.wishlist_tute_image).setVisibility(View.GONE);
                        getView().findViewById(R.id.wishlist_tute_frame).setVisibility(View.GONE);
                    }
                }

                if(v.getId() != currentTab.getId()) {
                    mMainActivity.translateAnimation(v,currentTab);
//                    v.animate().translationYBy(-10f).scaleX(1.1f).scaleY(1.1f).setDuration(1000).start();
//                    currentTab.animate().translationYBy(10f).scaleX(1.0f).scaleY(1.0f).setDuration(1000).start();
                }

                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal()) {
                    /*if(cardState!=null && !cardState.toString().equals("MINIMIZED")
                            && mAdapter.getCardCategory() != Constants.CDRecommendedInstituteType.UNDECIDED.ordinal()) {
                        mCardContainer.startAnimation(cardMinimizeAnimation);
                    }*/
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal();
                    showWishListUI(true);
                    mEmptyTextView.setText("Looking for shortlisted institutes...");
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mListener.onClickWishList();
                }

                currentTab =v;
                break;
            case R.id.request_for_undecided_no:
                if(IS_UNDECIDED_INSTITUTES) {
                    if (mNextUrl != null && !mNextUrl.equalsIgnoreCase("null"))
                        mListener.OnCDRecommendedLoadUndecidedInstitutes(mNextUrl);
               /* }else if(CARD_CATEGORY ==Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal()) {
                    mListener.onNextWishList();*/
                }else {
                    mListener.OnCDRecommendedLoadNext();
                }
                questionLayout.setVisibility(View.INVISIBLE);
                mEmptyTextView.setText("Looking for more institutes...");
                mEmptyTextView.setVisibility(View.VISIBLE);

                break;
            case R.id.request_for_undecided_ok:
                questionLayout.setVisibility(View.INVISIBLE);
                mRequestForUndecidedInstitutes();
                break;
            default:
                break;
        }
    }



    @Override
    public void OnLoadNext() {
        if (this.mNextUrl == null || this.mNextUrl.equalsIgnoreCase("null"))
            return;
        this.loading = true;
        this.mAdapter.setLoadingNext(true);
        this.mListener.OnCDRecommendedLoadNext();
    }

    @Override
    public void OnInstituteSelected(Institute institute) {
        this.mListener.OnCDRecommendedInstituteSelected(institute);
    }

    @Override
    public void OnInstituteLiked(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        if(isLastCard){
            if(!IS_UNDECIDED_INSTITUTES) {
                if(mUndecidedCount >= 1) {
                    isLastCard = false;
                    questionLayout.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                }else{
                    questionLayout.setVisibility(View.GONE);
                    mCardContainer.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
            }else if(this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null")){
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            }else{
                if(this.mUndecidedCount >= 1){
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRequestForUndecidedInstitutes();
                        }
                    }, 300);
                }
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }

        this.mListener.OnCDRecommendedInstituteLiked(institute, isLastCard, this.IS_UNDECIDED_INSTITUTES);
    }


    @Override
    public void OnInstituteDislike(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        if(isLastCard){
            if(!IS_UNDECIDED_INSTITUTES) {
                if(mUndecidedCount >= 1) {
                    isLastCard = false;
                    questionLayout.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                }
                else{
                    questionLayout.setVisibility(View.GONE);
                    this.mCardContainer.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
            }else if(this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null")){
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            }else{
                if(this.mUndecidedCount >= 1){
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRequestForUndecidedInstitutes();
                        }
                    }, 300);
                }
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }
        this.mListener.OnCDRecommendedInstituteDislike(institute, isLastCard,IS_UNDECIDED_INSTITUTES);
    }

    @Override
    public void OnDecideLater(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        if(isLastCard){
            if(!IS_UNDECIDED_INSTITUTES) {
                if(mUndecidedCount >= 1) {
                    isLastCard = false;
                    questionLayout.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                }
                else{
                    questionLayout.setVisibility(View.GONE);
                    this.mCardContainer.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
            }else if(this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null")){
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            }else {
                mRequestForUndecidedInstitutes();
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }

        this.mListener.OnCDRecommendedInstituteDecideLater(institute, isLastCard, IS_UNDECIDED_INSTITUTES);
    }

    @Override
    public void OnShowMessage(String message) {
        this.mEmptyTextView.setText(message);
        //questionLayout.setVisibility(View.VISIBLE);
        //mEmptyTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnAppliedInstitute(Institute institute) {
        if(mListener != null) {
            boolean flag = false;
            if(mInstitutes != null &&  mInstitutes.size() ==1){
                this.mEmptyTextView.setVisibility(View.INVISIBLE);
                questionLayout.setVisibility(View.VISIBLE);
                this.mEmptyTextView.setText("Looking for more institutes...");
                this.mCardContainer.setVisibility(View.GONE);

            }
            this.mListener.OnAppliedInstitute(institute,flag);

            if(institute.getGroups_exists()==1) {
                removeTopCard(institute);
            }
        }
    }


    public void removeTopCard(Institute institute){
        if(mInstitutes != null && mInstitutes.size() > 0 && mCardContainer != null &&
                institute!=null && mInstitutes.contains(institute)) {
            mInstitutes.remove(institute);
            mCardContainer.removeTopCard();

           /* if(IS_UNDECIDED_INSTITUTES) {
                this.mUndecidedCount = mUndecidedCount-1;
                Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count - ", "", Constants.ANIM_SHORT_DURATION);

            }*/

        }
    }


    private void mRequestForUndecidedInstitutes(){

        mEmptyTextView.setText("Loading Undecided institutes...");
        mEmptyTextView.setVisibility(View.VISIBLE);
        if(mListener != null)
            this.mListener.OnCDRecommendedLoadUndecidedInstitutes(Constants.BASE_URL + "personalize/shortlistedinstitutes/?action=3");

    }

    private void mRemoveInstituteFromList()
    {
        try {
            this.mInstitutes.remove(0);
            if(this.mInstitutes.size() <= 0) {
                if (this.mNextUrl == null || this.mNextUrl.equalsIgnoreCase("null")) {
                    this.mEmptyTextView.setText("No CD Recommended colleges found");
                    this.mEmptyTextView.setVisibility(View.VISIBLE);
                    this.mCardContainer.setVisibility(View.GONE);
                    questionLayout.setVisibility(View.INVISIBLE);
                } else {
                    questionLayout.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.INVISIBLE);
                    this.mEmptyTextView.setText("Looking for more institutes...");
                    this.mEmptyTextView.setVisibility(View.VISIBLE);
                    this.mCardContainer.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception e){
            Log.e(TITLE, e.getMessage());
        }
        if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.UNBAISED.ordinal()){
            this.mRecomendedCount = this.mInstitutes.size();
            this.mRecommendedCountText.setText(""+mRecomendedCount);
        }else if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.BUZZLIST.ordinal()){
            this.mBuzzListCount = this.mInstitutes.size();
            this.mBuzzListCountText.setText(""+mBuzzListCount);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onActionDislike() {
        mLikeImageView.setVisibility(View.GONE);
        mDislikeImageView.setVisibility(View.VISIBLE);
        mUndecidedImageView.setVisibility(View.GONE);
    }

    @Override
    public void onActionLike() {
        mLikeImageView.setVisibility(View.VISIBLE);
        mDislikeImageView.setVisibility(View.GONE);
        mUndecidedImageView.setVisibility(View.GONE);
    }

    @Override
    public void onActionDecideLater() {
        mLikeImageView.setVisibility(View.GONE);
        mDislikeImageView.setVisibility(View.GONE);
        mUndecidedImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActionCancel() {
        mLikeImageView.setVisibility(View.GONE);
        mDislikeImageView.setVisibility(View.GONE);
        mUndecidedImageView.setVisibility(View.GONE);
    }


    public interface OnCDRecommendedInstituteListener extends BaseListener{
        void OnCDRecommendedLoadNext();
        void OnCDRecommendedInstituteSelected(Institute institute);
        void OnCDRecommendedInstituteLiked(Institute institute, boolean isLastCard, boolean isUndecided);
        void OnCDRecommendedInstituteDislike(Institute institute, boolean isLastCard, boolean isUndecided);
        void OnCDRecommendedInstituteDecideLater(Institute institute, boolean isLastCard, boolean isUndecided);
        void OnCDRecommendedLoadUndecidedInstitutes(String url);
        void OnAppliedInstitute(Institute institute, boolean flag);
        void onClickBuzzList();
        void onClickWishList();
        void onClickRecommendedList();
        void onNextBuzzList();
        void OnWishlistInstituteSelected(Institute institute, boolean isFromCard);
        void OnWishlistInstituteApplied(Institute institute, int position);
        void OnWishlistInstituteRemoved(Institute institute, int position);
        @Override
        void onEndReached(String next, int type);
        @Override
        void onInstituteLikedDisliked(int position, int liked);
        void displayMessage(int messageId);
    }


    public void mUpdateUndecidedCount(int undecidedCount,boolean isIncrement)
    {
        if (!IS_UNDECIDED_INSTITUTES && !isIncrement){
            this.mUndecidedCount = undecidedCount;

        }else if (IS_UNDECIDED_INSTITUTES && isIncrement){
            this.mUndecidedCount = mUndecidedCount-1;
        }

        if (mUndecidedCount < 0)
            return;

        else if (IS_UNDECIDED_INSTITUTES && mUndecidedCount == 0)
        {
            this.mEmptyTextView.setText("No CD Recommended - Decide Later colleges found");
            this.questionLayout.setVisibility(View.GONE);
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
        }
        mUndecidedCountText.setText(""+this.mUndecidedCount);
        Animation animation=AnimationUtils.loadAnimation(getActivity(),R.anim.pulse);
        mUndecidedCountText.startAnimation(animation);
    }
    public void mUpdateWishListCount() {
        mWishListCount++;
        mWishListCountText.setText(""+this.mWishListCount);
    }

    public void updateList(List<Institute> institutes, String next) {
        if(getView() == null)
            return;
        this.mInstitutes.clear();
        this.mInstitutes.addAll(institutes);
        this.IS_UNDECIDED_INSTITUTES = false;
        this.mUndecidedCountText.setClickable(true);
        this.mRecomendedCount = mInstitutes.size();
        this.mRecommendedCountText.setText(""+mRecomendedCount);
        this.mTitle = "CD Recommended Colleges";
        this.mPageTitleTV.setText(mTitle);
        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No CD Recommended colleges found");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
        }
        else
        {
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mAdapter.clear();
            this.mAddCardInAdapter(this.mInstitutes);
            this.mNextUrl = next;
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
           /* if (canAnimate) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCardContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                                R.anim.window_minimize_animation));
                        mEmptyTextView.setVisibility(View.GONE);
                        cardState.delete(0,cardState.length());
                    }
                }, 2000);
            }*/
        }
        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    public void showUndecidedInstitutes(List<Institute> institutes, String next) {
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = true;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(false);
        this.mPageTitleTV.setText("CD Recommended Colleges - Decide Later");
        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("Looking for more institutes...");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            questionLayout.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            if(mListener != null) {
                mListener.OnCDRecommendedLoadNext();
            }
        }else{
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.questionLayout.setVisibility(View.INVISIBLE);
            this.mAdapter.clear();
            this.mAddCardInAdapter(this.mInstitutes);
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
            this.mNextUrl = next;
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    public void updateBuzzList(List<Institute> institutes, String next) {
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = false;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(true);
        this.mBuzzListCount = this.mInstitutes.size();
        this.mBuzzListCountText.setText(""+mBuzzListCount);
        mTitle = "Featured Colleges";
        this.mPageTitleTV.setText(mTitle);
        //this.mCardContainer.setListener(null);
        // boolean canAnimate = mAdapter.getCardCategory() != Constants.CDRecommendedInstituteType.BUZZLIST.ordinal();
        //this.mAdapter.setCardCategory(Constants.CDRecommendedInstituteType.BUZZLIST.ordinal());
        if (this.mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("No more featured institutes...");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            questionLayout.setVisibility(View.INVISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            if (mListener != null) {
                mListener.onNextBuzzList();
            }

        } else {
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mAdapter.clear();
            this.mAddCardInAdapter(this.mInstitutes);
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
            this.mNextUrl = next;
            /*if (canAnimate) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation = AnimationUtils.loadAnimation(getActivity(),
                                R.anim.window_maximize_animation);
                        mCardContainer.startAnimation(animation);
                        mEmptyTextView.setVisibility(View.GONE);
                        cardState.delete(0,cardState.length());
                    }
                }, 500);
            }*/
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();

    }


    public void updateWishList(List<Institute> institutes, String next) {
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = false;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(true);
        this.mWishListCount = this.mInstitutes.size();
        this.mWishListCountText.setText(""+mWishListCount);
        mTitle = "Shortlisted Colleges";
        this.mPageTitleTV.setText(mTitle);
        if (this.mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("No more shortlisted institutes...");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.questionLayout.setVisibility(View.INVISIBLE);

        } else {
            progressBarLL.setVisibility(View.GONE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.questionLayout.setVisibility(View.GONE);
            wishListRecyclerView.setVisibility(View.VISIBLE);
            this.mWishlistInstituteListAdapter.lastPosition = this.mInstitutes.size() - 1;
            this.mWishlistInstituteListAdapter.notifyDataSetChanged();
            loading = false;
            mNextUrl = next;
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }


    public void RemoveInstitute(int position)
    {
        this.mInstitutes.remove(position);
        this.mWishListCountText.setText(""+this.mInstitutes.size());
        this.mWishlistInstituteListAdapter.notifyItemRemoved(position);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    public void UpdateAppliedStatus(int position)
    {
        this.mInstitutes.get(position).setIs_applied(true);
        this.mWishlistInstituteListAdapter.notifyItemChanged(position);
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }


    public void getMinimizeAnimation(){

        cardMinimizeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.window_minimize_animation);
        cardMinimizeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCardContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardContainer.setVisibility(View.GONE);
                cardState.delete(0,cardState.length());
                cardState.append("MINIMIZED");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
