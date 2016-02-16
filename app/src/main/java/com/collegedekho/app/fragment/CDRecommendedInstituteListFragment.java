package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.display.swipableList.view.CardContainer;
import com.collegedekho.app.display.swipableList.view.SimpleCardStackAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.tag.textview.ContactsCompletionView;
import com.google.android.youtube.player.internal.e;

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
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private SimpleCardStackAdapter mAdapter;
    private OnCDRecommendedInstituteListener mListener;
    private TextView mEmptyTextView;
    private boolean IS_TUTE_COMPLETED = true;
    private CardContainer mCardContainer;

    public CDRecommendedInstituteListFragment() {
        // Required empty public constructor
    }

    public static CDRecommendedInstituteListFragment newInstance(ArrayList<Institute> institutes, String title, String next) {
        CDRecommendedInstituteListFragment fragment = new CDRecommendedInstituteListFragment();
        Bundle args = new Bundle();

        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);

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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recommended_institute_listing, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.RECOMMENDED_INSTITUTE_LIST_SCREEN_TUTE, false);
        this.mCardContainer = (CardContainer) rootView.findViewById(R.id.layoutview);

        this.mAdapter = new SimpleCardStackAdapter(this.getContext(), this);

        this.mAddCardInAdapter(this.mInstitutes);

        this.mCardContainer.setAdapter(this.mAdapter);

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

            adapter.add(model);
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
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
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

        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED)
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.GONE);
        }
        */
    }

    public void clearList() {
        if(this.mInstitutes == null || this.mInstitutes.size() == 0)
            return;

        for (int i = 0; i < this.mInstitutes.size(); i++)
            this.mAdapter.pop();

        this.mInstitutes = new ArrayList<>();
    }

    public void updateList(List<Institute> institutes, String next) {

        getView().findViewById(android.R.id.empty).setVisibility(View.GONE);

        this.mInstitutes.addAll(institutes);

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this.getContext(), this);
        this.mAddNextCardInAdapter(this.mInstitutes, adapter);
        this.mCardContainer.setAdapter(adapter);
        this.mAdapter = adapter;
        this.mNextUrl = next;
        this.mAdapter.setLoadingNext(false);
        this.loading = false;
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
    public void OnInstituteLiked(Institute institute) {
        this.mRemoveInstituteFromList(institute);
        this.mListener.OnCDRecommendedInstituteLiked(institute);
    }

    @Override
    public void OnInstituteDislike(Institute institute) {
        this.mRemoveInstituteFromList(institute);
        this.mListener.OnCDRecommendedInstituteDislike(institute);
    }

    private void mRemoveInstituteFromList(Institute institute)
    {
        try {

            if(this.mInstitutes.contains(institute))
            this.mInstitutes.remove(institute);

            if(mInstitutes.size() <= 0) {
                if (mNextUrl == null || mNextUrl.equalsIgnoreCase("null")) {
                    TextView tv = (TextView) getView().findViewById(android.R.id.empty);
                    tv.setText("No Recommended colleges found");
                    tv.setVisibility(View.VISIBLE);
                    return;
                } else {
                    TextView tv = (TextView) getView().findViewById(android.R.id.empty);
                    tv.setText("Loading...");
                    tv.setVisibility(View.VISIBLE);
                }
            }
        }
        catch (Exception e){
            Log.e(TITLE, e.getMessage());
        }
    }

    public interface OnCDRecommendedInstituteListener extends BaseListener{
        void OnCDRecommendedLoadNext(String nextURL);
        void OnCDRecommendedInstituteSelected(Institute institute);
        void OnCDRecommendedInstituteLiked(Institute institute);
        void OnCDRecommendedInstituteDislike(Institute institute);
    }
}
