package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Build;
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
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.List;


public class CDRecommendedInstituteFragment extends BaseFragment implements SimpleCardStackAdapter.OnCDRecommendedAdapterInterface, CardContainer.OnSwipeDirectionListener {
    public static final String TITLE = "CDRecommendedInstitutes";
    private static final String ARG_INSTITUTE = "cdrecommendedinstitute";
    private static final String ARG_CARD_CATEGORY = "CARD_CATEGORY";
    private static final String ARG_RECOMMENDED_INSTITUTE_COUNT = "recommended_institute_count";
    private static final String ARG_WISHLIST_INSTITUTE_COUNT    = "wishlist_institute_count";
    private static final String ARG_BUZZLIST_INSTITUTE_COUNT    = "buzzlist_institute_count";
    private static final String ARG_UNDECIDED_INSTITUTE_COUNT   = "undecided_institute_count";

    private ArrayList<Institute> mInstitutes  = new ArrayList<>();
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
    private View mNoMoreFeaturedLayout;
    private RecyclerView wishListRecyclerView;
    private WishlistInstituteListAdapter mWishlistInstituteListAdapter;
    private TickerView mUndecidedCountText;
    private TextView mBuzzListCountText;
    private TextView mRecommendedCountText;
    private TextView mShortListCountText;
    private int mUndecidedCount;
    private int mRecommendedCount;
    private int mShortListCount;
    private int mBuzzListCount;
    private View currentTab;
    public int currentTabId;
    private MainActivity mMainActivity;
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();


    public CDRecommendedInstituteFragment() {
        // Required empty public constructor
    }

    public static CDRecommendedInstituteFragment newInstance(ArrayList<Institute> institutes, String title,String next,
                                                              int recommendedCount, int wishListCount, int  buzzListCount, int undecidedCount, int category) {

        CDRecommendedInstituteFragment    sInstance = new CDRecommendedInstituteFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(ARG_INSTITUTE, institutes);
            args.putString(ARG_TITLE, title);
            args.putString(ARG_NEXT, next);
            args.putInt(ARG_RECOMMENDED_INSTITUTE_COUNT, recommendedCount);
            args.putInt(ARG_WISHLIST_INSTITUTE_COUNT, wishListCount);
            args.putInt(ARG_BUZZLIST_INSTITUTE_COUNT, buzzListCount);
            args.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, undecidedCount);
            args.putInt(ARG_CARD_CATEGORY, category);
            sInstance.setArguments(args);
            return sInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<Institute> institutes = args.getParcelableArrayList(ARG_INSTITUTE);
            if(institutes != null){
                this.mInstitutes.addAll(institutes);
            }
            this.mRecommendedCount  = args.getInt(ARG_RECOMMENDED_INSTITUTE_COUNT);
            this.mShortListCount  = args.getInt(ARG_WISHLIST_INSTITUTE_COUNT);
            this.mBuzzListCount   = args.getInt(ARG_BUZZLIST_INSTITUTE_COUNT);
            this.mUndecidedCount  = args.getInt(ARG_UNDECIDED_INSTITUTE_COUNT);
            this.CARD_CATEGORY    = args.getInt(ARG_CARD_CATEGORY);
            this.mTitle     = args.getString(ARG_TITLE);
            this.mNextUrl   = args.getString(ARG_NEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recommended_institute_listing, container, false);
        /**
         * Get the Tutorial Status. Show tute layout if tute has not been performed.
         * */
        this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), false);
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
        if(mTitle != null) {
            this.mPageTitleTV.setText(mTitle);
        }
        this.mEmptyTextView         = (TextView)rootView.findViewById(android.R.id.empty);
        this.mLikeImageView         = (ImageView) rootView.findViewById(R.id.like_textview);
        this.mDislikeImageView      = (ImageView) rootView.findViewById(R.id.dislike_textview);
        this.mUndecidedImageView    = (ImageView) rootView.findViewById(R.id.decide_later_textview);
        this.mUndecidedCountText    = (TickerView)rootView.findViewById(R.id.badge_counter);
        this.mUndecidedCountText.setCharacterList(NUMBER_LIST);
        this.mBuzzListCountText     = (TextView)rootView.findViewById(R.id.cd_reco_buzzlist_count);
        this.mRecommendedCountText  = (TextView)rootView.findViewById(R.id.cd_reco_recommended_count);
        this.mShortListCountText = (TextView)rootView.findViewById(R.id.cd_reco_wishlist_count);
        this.questionLayout         = rootView.findViewById(R.id.ask_user_layout);
        this.mNoMoreFeaturedLayout  = rootView.findViewById(R.id.no_more_featured_layout);
        this.wishListRecyclerView   = (RecyclerView) rootView.findViewById(R.id.cd_reco_wish_list_institute_grid);
        this.progressBarLL          = (LinearLayout)rootView.findViewById(R.id.progressBarLL);

        /**
         * Setting the wishlist adapter and layout for showing wishlist institutes.
         * */
        layoutManager = new GridLayoutManager(getActivity(), 3);
        this.wishListRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 0, false));
        this.wishListRecyclerView.setLayoutManager(layoutManager);
        this.wishListRecyclerView.setHasFixedSize(true);
        this.wishListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.wishListRecyclerView.addOnScrollListener(scrollListener);

        /**
         * Updating the count of the institutes. The same process has been repeated in the onResume
         * method also. We may remove this functionality.
         * */
        this.mUndecidedCountText.setText(String.valueOf(mUndecidedCount));
        this.mRecommendedCountText.setText(String.valueOf(mRecommendedCount));
        this.mShortListCountText.setText(String.valueOf(mShortListCount));
        this.mBuzzListCountText.setText(String.valueOf(mBuzzListCount));



        if(this.mAdapter == null){
            this.mAdapter = new SimpleCardStackAdapter(this.getContext(), this, CARD_CATEGORY);
        }

        /**
         * Setting up the swipe card adapter.
         * */
        this.setUpStackAdapter();
        this.mCardContainer.setAdapter(this.mAdapter);


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

    /**
     * This method creates and adds input cards in the swipe adapter.
     * */
    private void mAddCardInAdapter(List<Institute> list)
    {
         ArrayList<CardModel> modelArrayList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--)
        {
            CardModel  model = new CardModel(list.get(i), this.getActivity());
            modelArrayList.add(model);
        }
        mAdapter.addAll(modelArrayList);
    }

    private void setupPeekAndPopStandard() {

        PeekAndPop peekAndPop = new PeekAndPop.Builder(this.getActivity())
                .blurBackground(true)
                .peekLayout(R.layout.peek_view)
                .parentViewGroupToDisallowTouchEvents(wishListRecyclerView)
                .build();

        this.mWishlistInstituteListAdapter = new WishlistInstituteListAdapter(this.getActivity()
                , this.mInstitutes, Constants.TYPE_STANDARD, peekAndPop);

        wishListRecyclerView.setAdapter(this.mWishlistInstituteListAdapter);
    }

    /**
     * This method is used to switch the display from shortlist to swipe layout and vice versa.
     * */
    private void showWishListUI(boolean isShow){
        if(isShow){
            mCardContainer.setVisibility(View.GONE);
            wishListRecyclerView.setVisibility(View.VISIBLE);
        }else{
            mCardContainer.setVisibility(View.VISIBLE);
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
            this.mMainActivity = (MainActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCDRecommendedInstituteListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        int osVersion = android.os.Build.VERSION.SDK_INT;
        if ( osVersion < Build.VERSION_CODES.N) {
            outState.putParcelableArrayList(ARG_INSTITUTE, mInstitutes);
            outState.putString(ARG_TITLE, mTitle);
            outState.putString(ARG_NEXT, mNextUrl);
            outState.putInt(ARG_RECOMMENDED_INSTITUTE_COUNT, mRecommendedCount);
            outState.putInt(ARG_WISHLIST_INSTITUTE_COUNT, mShortListCount);
            outState.putInt(ARG_BUZZLIST_INSTITUTE_COUNT, mBuzzListCount);
            outState.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, mUndecidedCount);
            outState.putInt(ARG_CARD_CATEGORY, CARD_CATEGORY);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
        listener = null;
        if (getActivity() != null)
        {
            //((MainActivity) getActivity()).setCurrentInstitute(null);
            Institute institute = ((MainActivity) getActivity()).getCurrentInstitute();
            if (institute != null)
                institute.setPosition(-1);
        }
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

        /**
         * If the user has selected the institute from inside the shortlist and then comes back then
         * CAF has to be set false. The actual functionality of this method has been handled in the
         * MainActivity.
         * */
        if(Constants.IS_CAF_LOADED) {
            Constants.IS_CAF_LOADED = false;
        }

        if (getView() != null)
            getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if(mMainActivity != null && mMainActivity.mDrawerToggle != null){
            mMainActivity.mDrawerToggle.setDrawerIndicatorEnabled(false);
                mMainActivity.setNavigationDrawerItemSelected(-1);
        }
        /**
         * This updates the count in the Layout and Displays the count.
         * */
        updateCount(mMainActivity.getUndecidedInstituteCount(),mMainActivity.getShortlistedInstituteCount()
                ,mMainActivity.getFeaturedInstituteCount(),mMainActivity.getmRecommendedInstituteCount());

        /**
         * The Below code handles the tab animation ie which tab to bring up and when it basically
         * calls translate animation method of MainActivity. The border edge color of swipe card is
         * also set in the below code.
         * */
        if(CARD_CATEGORY ==Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal()){
            if(getView() != null) {
                View v = getView().findViewById(R.id.tab_recommended);
                mMainActivity.translateAnimation(v, currentTab);
                currentTab = v;
            }
            mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_blue_border_box));
        } else if (CARD_CATEGORY ==Constants.CDRecommendedInstituteType.FEATURED.ordinal()){
            if(getView() != null) {
                View v = getView().findViewById(R.id.tab_buzzlist);
                mMainActivity.translateAnimation(v, currentTab);
                currentTab = v;
            }
            mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_orange_border_box));
        } else if (CARD_CATEGORY ==Constants.CDRecommendedInstituteType.SHORTLIST.ordinal()){
            if(getView() != null) {
                View v = getView().findViewById(R.id.tab_wishlist);
                mMainActivity.translateAnimation(v, currentTab);
                currentTab  = v;
            }
        } else if (CARD_CATEGORY ==Constants.CDRecommendedInstituteType.UNDECIDED.ordinal()){
            mMainActivity.translateAnimation(null,currentTab);
        }

        /**
         * If we have come back to the Shortlist Page then we need to show the WishlistUI or else we
         * need to show the SwipeUI
         * */
        if(CARD_CATEGORY ==Constants.CDRecommendedInstituteType.SHORTLIST.ordinal()){
            mEmptyTextView.setVisibility(View.GONE);
            questionLayout.setVisibility(View.GONE);
            showWishListUI(true);
        } else {
            showWishListUI(false);
            mEmptyTextView.setVisibility(View.GONE);
            questionLayout.setVisibility(View.GONE);
        }

        /**
         * If user goes inside institute detail from shortlisted institute and deleted the institute
         * then this functionality handles the deletion in CD recommended fragment.
         * */
        if (this.mMainActivity != null) {
            this.mMainActivity.currentFragment = this;
            mMainActivity.translateAnimation(currentTab, null);

            // Get the institute to be removed.
            Institute institute = this.mMainActivity.getCurrentInstitute();
            if (institute != null && institute.getPosition() >= 0)
            {
                //removed from shortlist
                if (institute.getIs_shortlisted() == 0)
                {
                    if(mInstitutes .size() > institute.getPosition()) {
                        this.mInstitutes.remove(institute.getPosition());
                        this.mWishlistInstituteListAdapter.notifyItemRemoved(institute.getPosition());
                    }
                    this.mShortListCount--;
                    this.mShortListCountText.setText(String.valueOf(mShortListCount));

                    if(mInstitutes == null || mInstitutes.size() <= 0){
                        mEmptyTextView.setVisibility(View.VISIBLE);
                        mEmptyTextView.setText("You don't have any Shortlisted college. Please " +
                                "Shortlist colleges from Listing !");
                    }else{
                        mEmptyTextView.setVisibility(View.GONE);
                    }
                }
                //applied
                else {
                    if(mInstitutes .size() >= institute.getPosition()) {
                        this.mInstitutes.set(institute.getPosition(), institute);
                    }
                }
                //update the list
                this.mWishlistInstituteListAdapter.notifyDataSetChanged();
                //this.mMainActivity.setCurrentInstitute(null);
                institute.setPosition(-1);
                //this logic is here so that if there is any institute removed from shortlisting from inside of InstituteDetailPage. We can remove that institute from screen.

            }
        }

        /**
         * Set up the Swipe stack adapter. If returning to some other page than the shortlist page
         * */
        setUpStackAdapter();
    }

    @Override
    public void onDestroy()
    {
        if (getActivity() != null)
        {
            Institute institute = ((MainActivity) getActivity()).getCurrentInstitute();
            if (institute != null)
                institute.setPosition(-1);
        }
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
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.WISHLIST_TUTE), true).apply();
                getActivity().invalidateOptionsMenu();
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
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), true).apply();
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.tab_buzzlist:
                mMainActivity.translateAnimation(v,currentTab);
                mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_orange_border_box));
                currentTab = v;

                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.FEATURED.ordinal()) {
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.FEATURED.ordinal();
                    showWishListUI(false);
                    mListener.onClickBuzzList();
                }

                break;
            case R.id.tab_recommended:
                mNoMoreFeaturedLayout.setVisibility(View.GONE);
                mMainActivity.translateAnimation(v,currentTab);
                mAdapter.setDrawableBorderBackground(getActivity().getResources().getDrawable(R.drawable.bg_rounded_blue_border_box));
                currentTab =v;

                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal()) {
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal();
                    showWishListUI(false);
                    mCardContainer.setVisibility(View.GONE);
                    mListener.onClickRecommendedList();
                }

                break;
            case R.id.tab_wishlist:
                mNoMoreFeaturedLayout.setVisibility(View.GONE);
                this.IS_WISHLIST_TUTE_COMPLETED= getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.WISHLIST_TUTE), false);
                View view = getView();
                currentTabId = 2;
                if(view != null){
                    if(!IS_WISHLIST_TUTE_COMPLETED) {
                        getView().findViewById(R.id.wishlist_tute_image).setVisibility(View.VISIBLE);
                        getView().findViewById(R.id.wishlist_tute_frame).setVisibility(View.VISIBLE);
                        getActivity().invalidateOptionsMenu();
                    }
                    else {
                        getView().findViewById(R.id.wishlist_tute_image).setVisibility(View.GONE);
                        getView().findViewById(R.id.wishlist_tute_frame).setVisibility(View.GONE);
                    }
                }
                mMainActivity.translateAnimation(v,currentTab);

                if (CARD_CATEGORY != Constants.CDRecommendedInstituteType.SHORTLIST.ordinal()) {
                    CARD_CATEGORY = Constants.CDRecommendedInstituteType.SHORTLIST.ordinal();
                    mEmptyTextView.setText("Looking for institutes...");
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mListener.onClickWishList();
                }
                currentTab =v;
                break;
            case R.id.request_for_undecided_no:
                if(IS_UNDECIDED_INSTITUTES) {
                    if (mNextUrl != null && !mNextUrl.equalsIgnoreCase("null"))
                        mListener.OnCDRecommendedLoadUndecidedInstitutes(mNextUrl);
                } else {
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

    /**
     * This method handles the functionality of institute being selected from the swipe list.
     * */
    @Override
    public void OnInstituteSelected(Institute institute) {
        if(institute != null){
            this.mListener.OnCDRecommendedInstituteSelected(institute);
        }
    }

    /**
     * This method handles functionality of right swipe.
     * */
    @Override
    public void OnInstituteLiked(Institute institute, boolean isLastCard) {
        if(institute == null){
            return;
        }
        // Get the type of card swiped and set the adapter accordingly if the card is last or not.
        String cardCategory = getCardCategoryTagAndSetAdapter(isLastCard);
        // Send the shortlist action of institute to the backend keeping in mind what tye of card is
        // sent and whether it is the last card
        this.mListener.OnCDRecommendedInstituteLiked(institute, isLastCard, cardCategory ,this.mNextUrl);
    }

    /**
     * This method handles functionality of left swipe. It is similar to OnInstituteLiked.
     * */
    @Override
    public void OnInstituteDislike(Institute institute, boolean isLastCard) {
        if(institute == null){
            return;
        }
        String cardCategory = getCardCategoryTagAndSetAdapter(isLastCard);
        this.mListener.OnCDRecommendedInstituteDislike(institute, isLastCard,cardCategory, mNextUrl);
    }

    /**
     * This method handles functionality of up swipe. It is similar to OnInstituteLiked.
     * */
    @Override
    public void OnDecideLater(Institute institute, boolean isLastCard) {
        if(institute == null){
            return;
        }
        String cardCategory = getCardCategoryTagAndSetAdapter(isLastCard);
        this.mListener.OnCDRecommendedInstituteDecideLater(institute, isLastCard, cardCategory, mNextUrl);
    }

    /**
     * This method return the tag associated with the type of card. It removed the swiped institute.
     * It also set's up the adapter accordingly. Depending on whether the institute was last or not
     * it shows the message.
     * */
    public String getCardCategoryTagAndSetAdapter(boolean isLastCard){
        this.mRemoveInstituteFromList();
        this.setUpStackAdapter();
        String cardCategory = null;
        if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal()){
            cardCategory = Constants.TAG_CD_RECOMMENDED;
        } else if (CARD_CATEGORY == Constants.CDRecommendedInstituteType.FEATURED.ordinal()) {
            cardCategory = Constants.TAG_CD_FEATURED;
        } else if (CARD_CATEGORY == Constants.CDRecommendedInstituteType.UNDECIDED.ordinal()) {
            cardCategory = Constants.TAG_CD_UNDECIDED;
        } else if (CARD_CATEGORY == Constants.CDRecommendedInstituteType.SHORTLIST.ordinal()){
            cardCategory = Constants.TAG_CD_SHORTLIST;
        }
        if(isLastCard){
            this.OnShowMessage("Looking for institutes");
        }else {
            this.OnShowMessage("");
        }
        return cardCategory;
    }

    @Override
    public void OnShowMessage(String message) {
        if(message != null && !message.equalsIgnoreCase("")){
            this.mEmptyTextView.setText(message);
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            this.questionLayout.setVisibility(View.GONE);
        } else if (mInstitutes != null && !mInstitutes.isEmpty()){
            mEmptyTextView.setVisibility(View.GONE);
            mCardContainer.setVisibility(View.VISIBLE);
            questionLayout.setVisibility(View.GONE);
        }
    }

    /**
     * This method handles the apply functionality.
     * */
    @Override
    public void OnAppliedInstitute(Institute institute) {
        if(institute == null){
            return;
        }
        if(mListener != null) {
            boolean flag = false;
            if(mInstitutes != null &&  mInstitutes.size() ==1){
                flag = true;
                this.mEmptyTextView.setVisibility(View.INVISIBLE);
                setUpStackAdapter();
                this.mEmptyTextView.setText("Looking for more institutes...");
                this.mCardContainer.setVisibility(View.GONE);

            }
            // Setup the adapter and remove the institute get the card category.
            String cardCategory = getCardCategoryTagAndSetAdapter(flag);
            // Send the apply request to the backend.
            this.mListener.OnAppliedInstitute(institute,flag,cardCategory);

            if(institute.getGroups_exists()==1) {
                removeTopCard(institute);
            }
        }
    }

    @Override
    public boolean isLast(){
        if(mInstitutes.size() <= 1){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This removes the top card institute.
     * */
    public void removeTopCard(Institute institute){
        if(mInstitutes != null && mInstitutes.size() > 0 && mCardContainer != null &&
                institute!=null && mInstitutes.contains(institute)) {
            mInstitutes.remove(institute);
            mCardContainer.removeTopCard();
        }
        setUpStackAdapter();
    }

    /**
     * This method request the undecided institutes from the backend.
     * */
    private void mRequestForUndecidedInstitutes(){
        mEmptyTextView.setText("Loading Undecided institutes...");
        mEmptyTextView.setVisibility(View.VISIBLE);
        if(mListener != null)
            this.mListener.OnCDRecommendedLoadUndecidedInstitutes(ApiEndPonits.API_UNDECIDED_INSTITUTES);
    }

    /**
     * This method removed the institute being swiped.
     * */
    private void mRemoveInstituteFromList() {
        try {
            int total = mInstitutes.size();
            this.mInstitutes.remove(total-1);
        }catch (Exception e){
            Log.e(TITLE, e.getMessage());
        }
    }

    @Override
    public String getEntity() {
        return null;
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
        void OnCDRecommendedInstituteLiked(Institute institute, boolean isLastCard, String cardCategory, String nextUrl);
        void OnCDRecommendedInstituteDislike(Institute institute, boolean isLastCard, String cardCategory, String nextUrl);
        void OnCDRecommendedInstituteDecideLater(Institute institute, boolean isLastCard, String cardCategory, String nextUrl);
        void OnCDRecommendedLoadUndecidedInstitutes(String url);
        void OnCDRecommendedLoadMoreBuzzlist();
        void OnAppliedInstitute(Institute institute, boolean flag, String cardCategory);
        void onClickBuzzList();
        void onClickWishList();
        void onClickRecommendedList();
        @Override
        void onEndReached(String next, int type);
        @Override
        void onInstituteLikedDisliked(int position, int liked);
        void displayMessage(int messageId);
    }

    /**
     * This method updates and displays the count of the institutes.
     * */
    public void updateCount(int mUndecidedInstitutesCount, int mShortListInstituteCount, int mFeaturedInstituteCount, int mRecommendedInstituteCount){
        if(mUndecidedInstitutesCount >= 0){
            this.mUndecidedCount = mUndecidedInstitutesCount;
            try{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mUndecidedCountText != null)
                            mUndecidedCountText.setText(String.valueOf(mUndecidedCount));
                    }
                }, 200);
            } catch (Exception e){ e.printStackTrace(); }
        }

        if(mShortListInstituteCount >= 0){
            this.mShortListCount = mShortListInstituteCount;
            try{
                if(!mShortListCountText.getText().equals(String.valueOf(mShortListCount)))
                    this.mShortListCountText.setText(String.valueOf(mShortListCount));
            } catch (Exception e){ e.printStackTrace(); }
        }

        if(mFeaturedInstituteCount >= 0) {
            this.mBuzzListCount = mFeaturedInstituteCount;
            try{
                if(!mBuzzListCountText.getText().equals(String.valueOf(mBuzzListCount)))
                    this.mBuzzListCountText.setText(mBuzzListCount);
            } catch (Exception e){ e.printStackTrace(); }
        }

        if(mRecommendedInstituteCount >= 0) {
            this.mRecommendedCount = mRecommendedInstituteCount;
            try{
                if(!mRecommendedCountText.getText().equals(String.valueOf(mRecommendedInstituteCount)))
                    this.mRecommendedCountText.setText(String.valueOf(mRecommendedCount));
            } catch (Exception e){ e.printStackTrace(); }
        }
    }

    /**
     * This method removes insitute in case of removing it from shortlist.
     * */
    public void removeInstituteFromShortList(int position){
        if(position >= 0 && mInstitutes!=null && position < mInstitutes.size()){
            this.mInstitutes.remove(position);
            this.mShortListCount--;
            this.mShortListCountText.setText(""+mShortListCount);
            this.mWishlistInstituteListAdapter.notifyItemRemoved(position);
            this.mWishlistInstituteListAdapter.notifyDataSetChanged();
            mCardContainer.setVisibility(View.GONE);
            if(mInstitutes == null || mInstitutes.isEmpty()){
                wishListRecyclerView.setVisibility(View.GONE);
                OnShowMessage("You don't have any Shortlisted college. Please Shortlist from college Listing !");
            } else {
                wishListRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * In case of last card of recommended type this method is called. Here we handle the response
     * from the server. We display institute or message accordingly.
     * */
    public void updateRecommendedList(List<Institute> institutes, String next,int mRecommendedCount) {
        CARD_CATEGORY = Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal();
        if(getView() == null)
            return;
        this.mInstitutes.clear();
        this.mInstitutes.addAll(institutes);
        this.mTitle = "CD Recommended Colleges";
        this.mPageTitleTV.setText(mTitle);
        this.mUndecidedCountText.setClickable(true);

        this.mRecommendedCount = mRecommendedCount;
        this.mRecommendedCountText.setText(""+ this.mRecommendedCount);
        if(mInstitutes != null){
            int size = mInstitutes.size();
            if(this.mRecommendedCount <= 20 && size != mRecommendedCount){
                this.mRecommendedCount = size;
                this.mRecommendedCountText.setText("" + mRecommendedCount);
            }
        }
        setLayoutAndShowMessage(next);
        setUpStackAdapter();
        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    /**
     * In case of last card of undecided type this method is called. Here we handle the response
     * from the server. We display institute or message accordingly.
     * */
    public void showUndecidedInstitutes(List<Institute> institutes, String next) {
        CARD_CATEGORY = Constants.CDRecommendedInstituteType.UNDECIDED.ordinal();
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = true;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(false);
        this.mPageTitleTV.setText("CD Recommended Colleges - Decide Later");
        setLayoutAndShowMessage(next);
        setUpStackAdapter();
        mMainActivity.translateAnimation(null,currentTab);
        currentTab = null;

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    /**
     * In case of last card of featured type this method is called. Here we handle the response
     * from the server. We display institute or message accordingly.
     * */
    public void updateBuzzList(List<Institute> institutes, String next, int buzzListCount) {
        CARD_CATEGORY = Constants.CDRecommendedInstituteType.FEATURED.ordinal();
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = false;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(true);
       // if(this.mBuzzListCount <= 0) {
            this.mBuzzListCount = buzzListCount;
            this.mBuzzListCountText.setText(""+this.mBuzzListCount);
       // }
        if(mInstitutes != null){
            int size = mInstitutes.size();
            if(mBuzzListCount <= 20 && size != mBuzzListCount){
                this.mBuzzListCount = size;
                this.mBuzzListCountText.setText("" + mBuzzListCount);
            }
        }
        mTitle = "Featured Colleges";
        this.mPageTitleTV.setText(mTitle);
        setLayoutAndShowMessage(next);
        setUpStackAdapter();
        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();

    }

    /**
     * This method handles the functionality of showing shortlist or swipe layout and showing
     * animation of tab.
     * */
    public void setLayoutAndShowMessage(String next){
        if (this.mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("No more institutes");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
        } else {
            if(getView() != null){
                View v = null;
                if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.RECOMMENDED.ordinal()){
                    v = getView().findViewById(R.id.tab_recommended);
                } else if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.FEATURED.ordinal()){
                    v = getView().findViewById(R.id.tab_buzzlist);
                } else if(CARD_CATEGORY == Constants.CDRecommendedInstituteType.SHORTLIST.ordinal()){
                    v = getView().findViewById(R.id.tab_wishlist);
                }
                mMainActivity.translateAnimation(v,currentTab);
                currentTab = v;
            }

            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mNextUrl = next;
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
        }
    }

    public void updateWishList(List<Institute> institutes, String next) {
        CARD_CATEGORY = Constants.CDRecommendedInstituteType.SHORTLIST.ordinal();
        this.mInstitutes.clear();
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountText.setClickable(true);
        mTitle = "Shortlisted Colleges";
        this.mPageTitleTV.setText(mTitle);
        this.mCardContainer.setVisibility(View.GONE);
        if (this.mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("You don't have any Shortlisted college. Please Shortlist from college Listing !");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.questionLayout.setVisibility(View.INVISIBLE);

        } else {
            progressBarLL.setVisibility(View.GONE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.questionLayout.setVisibility(View.GONE);
            wishListRecyclerView.setVisibility(View.VISIBLE);

            if(getView() != null){
                View v = getView().findViewById(R.id.tab_wishlist);
                mMainActivity.translateAnimation(v,currentTab);
                currentTab = v;
            }

            this.mWishlistInstituteListAdapter.lastPosition = this.mInstitutes.size() - 1;
            this.mWishlistInstituteListAdapter.notifyDataSetChanged();
            loading = false;
            mNextUrl = next;
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    public void UpdateAppliedStatus(int position) {
        this.mInstitutes.get(position).setIs_applied(true);
       // this.mWishlistInstituteListAdapter.notifyItemChanged(position);// crash due to this line commented by suresh
        this.mWishlistInstituteListAdapter.notifyDataSetChanged();
    }

    public void getMinimizeAnimation(){
        Animation cardMinimizeAnimation = AnimationUtils.loadAnimation(getActivity(),
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

    /**
     * This method set's up the wipe adapter. It puts maximum 2 institutes at a time.
     * */
    public void setUpStackAdapter(){
        this.mAdapter.clear();

        if(this.mInstitutes != null && !this.mInstitutes.isEmpty()){
            List<Institute> list = new ArrayList<>();
            if(mInstitutes.size() >= 2) {
                for (int i = 0; i < 2 && i < mInstitutes.size(); i++) {
                    list.add(mInstitutes.get(mInstitutes.size()-2+i));
                }
            } else if (mInstitutes.size() == 1){
                list.add(mInstitutes.get(0));
            }
            mAddCardInAdapter(list);
            questionLayout.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.GONE);
        } else {
            showWishListUI(false);
            mCardContainer.setVisibility(View.GONE);
            if(mUndecidedCount > 0){
                questionLayout.setVisibility(View.VISIBLE);
                mEmptyTextView.setVisibility(View.GONE);
            } else {
                questionLayout.setVisibility(View.GONE);
                mEmptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
