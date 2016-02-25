package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.display.swipableList.view.CardContainer;
import com.collegedekho.app.display.swipableList.view.SimpleCardStackAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnCDRecommendedInstituteListener} interface
 * to handle interaction events.
 * Use the {@link CDRecommendedInstituteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  CDRecommendedInstituteListFragment extends BaseFragment implements SimpleCardStackAdapter.OnCDRecommendedAdapterInterface{
    public static final String TITLE = "CDRecommendedInstitutes";
    private static final String ARG_INSTITUTE = "cdrecommendedinstitute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String ARG_FILTER_COUNT = "filter_count";
    private static final String ARG_UNDECIDED_INSTITUTE_COUNT = "undecided_institute_count";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private SimpleCardStackAdapter mAdapter;
    private OnCDRecommendedInstituteListener mListener;
    private boolean IS_TUTE_COMPLETED = true;
    private CardContainer mCardContainer;
    private int mUndecidedCount;
    private TextView mUndecidedCountTV;
    private TextView mEmptyTextView;
    private boolean IS_UNDECIDED_INSTITUTES = false;

    public CDRecommendedInstituteListFragment() {
        // Required empty public constructor
    }

    public static CDRecommendedInstituteListFragment newInstance(ArrayList<Institute> institutes, String title, String next, int undecidedCount) {
        CDRecommendedInstituteListFragment fragment = new CDRecommendedInstituteListFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putInt(ARG_UNDECIDED_INSTITUTE_COUNT, undecidedCount);

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recommended_institute_listing, container, false);

        this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE, false);
        this.mCardContainer = (CardContainer) rootView.findViewById(R.id.layoutview);
        this.mUndecidedCountTV = (TextView)rootView.findViewById(R.id.fragment_recommended_institute_undecided_count);

        Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count : ", "", Constants.ANIM_SHORT_DURATION);

        this.mEmptyTextView = (TextView)rootView.findViewById(android.R.id.empty);

        if (this.mInstitutes == null || this.mInstitutes.size() <= 0) {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("No Recommended colleges found");
        }
        else
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mAdapter = new SimpleCardStackAdapter(this.getContext(), this);

        this.mAddCardInAdapter(this.mInstitutes);

        this.mCardContainer.setAdapter(this.mAdapter);
         this.mUndecidedCountTV.setOnClickListener(this);

        return rootView;
    }



    private void mAddCardInAdapter(List<Institute> list)
    {
        for (int i = list.size() - 1; i >= 0; i--)
        {
            CardModel model;

            model = new CardModel(list.get(i), this.getActivity());

            this.mAdapter.add(model);
        }
    }

    private void mAddNextCardInAdapter(List<Institute> list, SimpleCardStackAdapter adapter)
    {
        for (int i = list.size() - 1; i >= 0; i--)
        {
            CardModel model;

            model = new CardModel(list.get(i), this.getActivity());

            mAdapter.add(model);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                this.mListener = (OnCDRecommendedInstituteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnInstituteSelectedListener");
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
        if(!IS_UNDECIDED_INSTITUTES && !isIncrement) {
            this.mUndecidedCount = undecidedCount;
            }
        else if(IS_UNDECIDED_INSTITUTES && isIncrement){
            this.mUndecidedCount = mUndecidedCount-1;
        }
        Utils.SetCounterAnimation(this.mUndecidedCountTV, this.mUndecidedCount, "Undecided Count : ", "", Constants.ANIM_SHORT_DURATION);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        loading = false;
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

        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED)
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.GONE);
        }
        */
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fragment_recommended_institute_undecided_count:
                this.mListener.OnCDRecommendedLoadUndecidedInstitutes();
                break;
            default:
                break;
        }
    }

    public void updateList(List<Institute> institutes, String next) {
        this.mInstitutes.addAll(institutes);
        IS_UNDECIDED_INSTITUTES = false;
        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No Recommended colleges found");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mAdapter.clear();
            this.mAddNextCardInAdapter(this.mInstitutes, null);
            this.mNextUrl = next;
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
        }
    }

    public void showUndecidedInstitutes(List<Institute> institutes) {
        this.mInstitutes.clear();
        IS_UNDECIDED_INSTITUTES = true;
        this.mInstitutes.addAll(institutes);

        if (this.mInstitutes.size() == 0)
        {
            this.mEmptyTextView.setText("No Undecided colleges found");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mAdapter.clear();
            this.mAddNextCardInAdapter(this.mInstitutes, null);
            this.mAdapter.setLoadingNext(false);
            this.loading = false;
        }
    }

    @Override
    public void OnLoadNext() {
        if (mNextUrl == null || mNextUrl.equalsIgnoreCase("null"))
            return;
        this.loading = true;
        this.mAdapter.setLoadingNext(true);
        this.mListener.OnCDRecommendedLoadNext(this.mNextUrl);
    }

    @Override
    public void OnInstituteSelected(Institute institute) {
        this.mListener.OnCDRecommendedInstituteSelected(institute);
    }

    @Override
    public void OnInstituteLiked(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        this.mListener.OnCDRecommendedInstituteLiked(institute, isLastCard);
    }

    @Override
    public void OnInstituteDislike(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        this.mListener.OnCDRecommendedInstituteDislike(institute, isLastCard);
    }

    @Override
    public void OnDecideLater(Institute institute, boolean isLastCard) {
        this.mRemoveInstituteFromList();
        this.mListener.OnCDRecommendedInstituteDecideLater(institute, isLastCard);
    }

    @Override
    public void OnShowMessage(String message) {
        this.mEmptyTextView.setText(message);
    }

    private void mRemoveInstituteFromList()
    {
        try {
            this.mInstitutes.remove(0);

            if(this.mInstitutes.size() <= 0) {
                if (this.mNextUrl == null || this.mNextUrl.equalsIgnoreCase("null")) {
                    this.mEmptyTextView.setText("No Recommended colleges found");
                    this.mEmptyTextView.setVisibility(View.VISIBLE);
                    return;
                } else {
                    this.mEmptyTextView.setText("Loading...");
                    this.mEmptyTextView.setVisibility(View.VISIBLE);
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

    public interface OnCDRecommendedInstituteListener extends BaseListener{
        void OnCDRecommendedLoadNext(String nextURL);
        void OnCDRecommendedInstituteSelected(Institute institute);
        void OnCDRecommendedInstituteLiked(Institute institute, boolean isLastCard);
        void OnCDRecommendedInstituteDislike(Institute institute, boolean isLastCard);
        void OnCDRecommendedInstituteDecideLater(Institute institute, boolean isLastCard);
        void OnCDRecommendedLoadUndecidedInstitutes();
    }
}