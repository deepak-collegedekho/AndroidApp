package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.display.swipableList.view.CardContainerNew;
import com.collegedekho.app.display.swipableList.view.SimpleCardStackAdapterNew;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.BitMapHolder;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class CDRecommendedInstituteListFragment extends BaseFragment implements SimpleCardStackAdapterNew.OnCDRecommendedAdapterInterface,CardContainerNew.OnSwipeDirectionListener {
    public static final String TITLE = "CDRecommendedInstitutes";
    private static final String ARG_INSTITUTE = "cdrecommendedinstitute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String ARG_FILTER_COUNT = "filter_count";
    private static final String ARG_UNDECIDED_INSTITUTE_COUNT = "undecided_institute_count";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private SimpleCardStackAdapterNew mAdapter;
    private OnCDRecommendedInstituteListener mListener;
    private boolean IS_TUTE_COMPLETED = true;
    private CardContainerNew mCardContainer;
    private int mUndecidedCount;
    private TextView mUndecidedCountTV;
    private TextView mPageTitleTV;
    private TextView mEmptyTextView;
    private boolean IS_UNDECIDED_INSTITUTES = false;
    private static boolean IS_FIRST_SHOW = true;
    private ImageView mLikeImageView;
    private ImageView mDislikeImageView;
    private ImageView mUndecidedImageView;
    private ImageView mRightArrowImageView;
    private ImageView mLeftArrowImageView;
    String examTag;
    public CDRecommendedInstituteListFragment() {
        // Required empty public constructor
    }

    public static CDRecommendedInstituteListFragment newInstance(ArrayList<Institute> institutes, String title, String next, int undecidedCount,String examTag) {
        CDRecommendedInstituteListFragment fragment = new CDRecommendedInstituteListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, undecidedCount);
        args.putString("EXAM_TAG",examTag);
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
            this.mUndecidedCount = this.getArguments().getInt(ARG_UNDECIDED_INSTITUTE_COUNT);
            this.examTag=this.getArguments().getString("EXAM_TAG");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recommended_institute_listing, container, false);

        this.mCardContainer = (CardContainerNew) rootView.findViewById(R.id.fragment_recommended_institute_cards_container);
        this.mCardContainer.setListener(this);

        this.mUndecidedCountTV = (TextView)rootView.findViewById(R.id.fragment_recommended_institute_undecided_count);
        this.mPageTitleTV   = (TextView)rootView.findViewById(R.id.recommended_page_title);
        this.mEmptyTextView = (TextView)rootView.findViewById(android.R.id.empty);
        this.mRightArrowImageView = (ImageView) rootView.findViewById(R.id.cd_reco_right_arrow);
        this.mLeftArrowImageView = (ImageView) rootView.findViewById(R.id.cd_reco_left_arrow);
        this.mLikeImageView     =(ImageView) rootView.findViewById(R.id.like_textview);
        this.mDislikeImageView  =(ImageView) rootView.findViewById(R.id.dislike_textview);
        this.mUndecidedImageView=(ImageView) rootView.findViewById(R.id.decide_later_textview);

        try{
            mLikeImageView.setImageBitmap(BitMapHolder.SHORTLISTED_BITMAP);
            mDislikeImageView.setImageBitmap(BitMapHolder.UNSHORTLISTED_BITMAP);
            mUndecidedImageView.setImageBitmap(BitMapHolder.UNDECIDED_BITMAP);

        }catch (Exception e){
            e.printStackTrace();
        }
        Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count - ", "", Constants.ANIM_SHORT_DURATION);


        if (this.mInstitutes == null || this.mInstitutes.size() <= 0) {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("No CD Recommended colleges found");
            this.mCardContainer.setVisibility(View.GONE);
            this.mRightArrowImageView.setVisibility(View.INVISIBLE);
            this.mLeftArrowImageView.setVisibility(View.INVISIBLE);

        }else {
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mRightArrowImageView.setVisibility(View.VISIBLE);
            this.mLeftArrowImageView.setVisibility(View.VISIBLE);
        }

        this.mAdapter = new SimpleCardStackAdapterNew(getActivity(), this.getContext(), this,examTag);
        this.mAddCardInAdapter(this.mInstitutes);

        this.mCardContainer.setAdapter(this.mAdapter);
        this.mUndecidedCountTV.setOnClickListener(this);

        if(this.IS_UNDECIDED_INSTITUTES) {
            mUndecidedCountTV.setClickable(false);
            mPageTitleTV.setText("CD Recommended Colleges - Decide Later");
        }

        rootView.findViewById(R.id.recommended_tute_image).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_tute_frame).setOnClickListener(this);

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                this.mListener = (OnCDRecommendedInstituteListener) context;
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

    public void mUpdateUndecidedCount(int undecidedCount,boolean isIncrement)
    {
        if (!IS_UNDECIDED_INSTITUTES && !isIncrement)
        {
            this.mUndecidedCount = undecidedCount;
        }
        else if (IS_UNDECIDED_INSTITUTES && isIncrement){
            this.mUndecidedCount = mUndecidedCount-1;
        }
        if (mUndecidedCount < 0)
            return;
        else if (IS_UNDECIDED_INSTITUTES && mUndecidedCount == 0)
        {
            this.mEmptyTextView.setText("No CD Recommended - Decide Later colleges found");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
        }

        Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count - ", "", Constants.ANIM_SHORT_DURATION);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getView() != null)
            getView().setLayerType(View.LAYER_TYPE_NONE, null);

        loading = false;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

        View view =  getView();
        if(view != null ){
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), false);
            if(!IS_TUTE_COMPLETED) {
                view.findViewById(R.id.recommended_tute_image).setVisibility(View.VISIBLE);
                view.findViewById(R.id.recommended_tute_frame).setVisibility(View.VISIBLE);
            }
            else {
                view.findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
                view.findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
            }
        }
        if(mInstitutes == null || mInstitutes.size() <=0){
            if(mListener != null) {
                this.mEmptyTextView.setText("Looking for more institutes...");
                mListener.OnCDRecommendedLoadNext();
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fragment_recommended_institute_undecided_count:
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(Constants.BASE_URL + "personalize/shortlistedinstitutes/" + "?action=3");
                break;
            case R.id.recommended_tute_image:
            case R.id.recommended_tute_frame:
                v.setVisibility(View.GONE);
                View view = getView();
                if(view != null){
                    view.findViewById(R.id.recommended_tute_image).setVisibility(View.GONE);
                    view.findViewById(R.id.recommended_tute_frame).setVisibility(View.GONE);
                }
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE), true).apply();

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
        if(isLastCard && this.IS_UNDECIDED_INSTITUTES) {
            if (this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null"))
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            else
            {
                if(this.mUndecidedCount >= 1){
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            sendRequestForUndecided();
                        }
                    }, 300);
                }

                this.mRightArrowImageView.setVisibility(View.INVISIBLE);
                this.mLeftArrowImageView.setVisibility(View.INVISIBLE);
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }

        this.mListener.OnCDRecommendedInstituteLiked(institute, isLastCard, this.IS_UNDECIDED_INSTITUTES);
    }

    @Override
    public void OnInstituteDislike(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        if(isLastCard && IS_UNDECIDED_INSTITUTES) {
            if (this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null"))
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            else{

                if(this.mUndecidedCount >= 1){
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            sendRequestForUndecided();
                        }
                    }, 300);
                }

                this.mRightArrowImageView.setVisibility(View.INVISIBLE);
                this.mLeftArrowImageView.setVisibility(View.INVISIBLE);
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }

        this.mListener.OnCDRecommendedInstituteDislike(institute, isLastCard,IS_UNDECIDED_INSTITUTES);
    }

    @Override
    public void OnDecideLater(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        if(isLastCard && IS_UNDECIDED_INSTITUTES) {
            if (this.mNextUrl != null && !this.mNextUrl.equalsIgnoreCase("null"))
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes(this.mNextUrl);
            else
            {
                sendRequestForUndecided();
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mCardContainer.setVisibility(View.GONE);
            }
        }

        this.mListener.OnCDRecommendedInstituteDecideLater(institute, isLastCard, IS_UNDECIDED_INSTITUTES);
    }

    @Override
    public void OnShowMessage(String message) {
        this.mEmptyTextView.setText(message);
    }

    @Override
    public void OnAppliedInstitute(Institute institute) {
        if(mListener != null) {
            boolean flag = false;
            if(mInstitutes != null &&  mInstitutes.size() ==1){
                flag = true;
                this.mEmptyTextView.setVisibility(View.VISIBLE);
                this.mEmptyTextView.setText("Looking for more institutes...");
                this.mCardContainer.setVisibility(View.GONE);
                this.mRightArrowImageView.setVisibility(View.INVISIBLE);
                this.mLeftArrowImageView.setVisibility(View.INVISIBLE);

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

            if(IS_UNDECIDED_INSTITUTES) {
                this.mUndecidedCount = mUndecidedCount-1;
                Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count - ", "", Constants.ANIM_SHORT_DURATION);

            }

        }
    }

    public void updateList(List<Institute> institutes, String next) {
        if(getView() == null)
            return;

        this.mInstitutes.addAll(institutes);
        IS_UNDECIDED_INSTITUTES = false;
        mUndecidedCountTV.setClickable(true);
        mPageTitleTV.setText("CD Recommended Colleges");
        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No CD Recommended colleges found");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            this.mRightArrowImageView.setVisibility(View.INVISIBLE);
            this.mLeftArrowImageView.setVisibility(View.INVISIBLE);
        }
        else
        {
            this.mRightArrowImageView.setVisibility(View.VISIBLE);
            this.mLeftArrowImageView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mAdapter.clear();
            this.mAddCardInAdapter(this.mInstitutes);
            this.mNextUrl = next;
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    public void showUndecidedInstitutes(List<Institute> institutes, String next) {
        this.mInstitutes.clear();
        this.IS_UNDECIDED_INSTITUTES = true;
        this.mInstitutes.addAll(institutes);
        this.mUndecidedCountTV.setClickable(false);
        this.mPageTitleTV.setText("CD Recommended Colleges - Decide Later");
        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("Looking for more institutes...");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mCardContainer.setVisibility(View.GONE);
            this.mRightArrowImageView.setVisibility(View.INVISIBLE);
            this.mLeftArrowImageView.setVisibility(View.INVISIBLE);
            if(mListener != null) {
               mListener.OnCDRecommendedLoadNext();
            }

        }
        else
        {
            this.mCardContainer.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mRightArrowImageView.setVisibility(View.VISIBLE);
            this.mLeftArrowImageView.setVisibility(View.VISIBLE);
            this.mAdapter.clear();
            this.mAddCardInAdapter(this.mInstitutes);
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
            this.mNextUrl = next;
        }

        if (getActivity() != null)
            ((MainActivity) getActivity()).hideProgressDialog();
    }

    private void sendRequestForUndecided(){
        if(mListener != null)
            this.mListener.OnCDRecommendedLoadUndecidedInstitutes(Constants.BASE_URL + "personalize/shortlistedinstitutes/" + "?action=3");

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
                } else {
                    this.mEmptyTextView.setText("Looking for more institutes...");
                    this.mEmptyTextView.setVisibility(View.VISIBLE);
                    this.mCardContainer.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception e){
            Log.e(TITLE, e.getMessage());
        }
    }

    private TextView addStatusText(ViewGroup container) {
        TextView result = new TextView(getActivity());
        result.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        result.setTextColor(0xFFFFFFFF);
        container.addView(result);
        return result;
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
    }
}
