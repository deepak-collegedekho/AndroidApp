package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstituteListAdapter;
import com.collegedekho.app.entities.Facet;
import com.collegedekho.app.entities.Folder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.collegedekho.app.widget.tag.textview.ContactsCompletionView;
import com.collegedekho.app.widget.tag.textview.FilteredArrayAdapter;
import com.collegedekho.app.widget.tag.textview.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInstituteSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteListFragment extends BaseFragment implements TokenCompleteTextView.TokenListener {
    public static final String TITLE = "Institutes";
    private static final String ARG_INSTITUTE = "institute";
    private static final String ARG_FILTER_ALLOWED = "filter_allowed";
    private static final String ARG_FILTER_COUNT = "filter_count";
    private static final String TAG = "Institute List Fragment";
    private ArrayList<Institute> mInstitutes;
    private String mTitle;
    private InstituteListAdapter mAdapter;
    private boolean filterAllowed;
    private int filterCount;
    private TextView mEmptyTextView;
    private boolean IS_TUTE_COMPLETED = true;
    //private View  filters;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private ContactsCompletionView mCompletionView;
    private ArrayAdapter<String> tokenAdapter;
    private RecyclerView recyclerView;
    private View instituteView, mFilterTokelLL;
    private FloatingActionButton floatingActionButton;
    private View mTuteView;
    int filtersApplied = 0;
    public InstituteListFragment() {
        // Required empty public constructor
    }

    public static InstituteListFragment newInstance(ArrayList<Institute> institutes, String title, String next, boolean filterAllowed, int filterCount,int listType) {
        InstituteListFragment fragment = new InstituteListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INSTITUTE, institutes);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        args.putBoolean(ARG_FILTER_ALLOWED, filterAllowed);
        args.putInt(ARG_FILTER_COUNT, filterCount);
        args.putInt("list_type",listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstitutes = getArguments().getParcelableArrayList(ARG_INSTITUTE);
            mTitle = getArguments().getString(ARG_TITLE);
            mNextUrl = getArguments().getString(ARG_NEXT);
            filterAllowed = getArguments().getBoolean(ARG_FILTER_ALLOWED);
            filterCount = getArguments().getInt(ARG_FILTER_COUNT);
            listType = getArguments().getInt("list_type");
            if(listType==0){
                listType=Constants.INSTITUTE_TYPE;
            }else if (listType==Constants.INSTITUTE_SEARCH_TYPE){
                filterAllowed=false;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_institute_listing, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.simple_grow);
        //  FAB margin needed for animation

        this.floatingActionButton = (FloatingActionButton)getActivity().findViewById(R.id.fabButton);
        this.floatingActionButton.setOnClickListener(this);
        this.floatingActionButton.setImageResource(R.drawable.ic_filter);
        this.floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.primary_color));
        this.floatingActionButton.setContentDescription("Click to Apply Filters");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.floatingActionButton.setBackground(this.getActivity().getDrawable(R.drawable.ripple_accent));
        }

        this.floatingActionButton.startAnimation(animation);

        this.IS_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.INSTITUTE_LIST_SCREEN_TUTE), false);

        this.mCompletionView = (ContactsCompletionView)view.findViewById(R.id.searchView);
        this.mCompletionView.setAdapter(tokenAdapter);
        this.mCompletionView.setTokenListener(this);
        this.mCompletionView.setInputType(InputType.TYPE_NULL);
        this.mCompletionView.allowDuplicates(false);
        this.mCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

        view.findViewById(R.id.view_into_grid).setOnClickListener(this);
        view.findViewById(R.id.view_into_list).setOnClickListener(this);

        ((TextView) view.findViewById(R.id.textview_page_title)).setText(mTitle);
        this.progressBarLL = (LinearLayout)view.findViewById(R.id.progressBarLL);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.institute_list);

        if(this.mViewType == Constants.VIEW_INTO_GRID)
            layoutManager = new GridLayoutManager(getActivity(), 2);
        else
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        this.recyclerView.setLayoutManager(layoutManager);
        updateViewTypeIcon(view, this.mViewType);
        mAdapter = new InstituteListAdapter(getActivity(), mInstitutes, this.mViewType);
        this.mEmptyTextView = (TextView) view.findViewById(android.R.id.empty);
        instituteView=view.findViewById(R.id.viewType);

        this.recyclerView.setAdapter(mAdapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.addOnScrollListener(scrollListener);
        this.mFilterTokelLL = view.findViewById(R.id.filter_tokenLL);
        if (filterAllowed) {
            this.mSetFilterList();

            this.floatingActionButton.setVisibility(View.VISIBLE);
            this.mFilterTokelLL.setVisibility(View.VISIBLE);
            this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onFilterButtonClicked();
                }
            });
        } else {
            this.floatingActionButton.setVisibility(View.GONE);
            this.mFilterTokelLL.setVisibility(View.GONE);
        }

        tokenAdapter = new FilteredArrayAdapter<String>(getActivity(), R.layout.contact_token, new String[]{}) {
            @Override
            protected boolean keepObject(String obj, String mask) {
                Log.e("", "");
                return false;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.contact_token, parent, false);
                }
                return convertView;
            }
        };
        this.mTuteView = view.findViewById(R.id.institute_list_tour_guide_image);
        this.mTuteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.INSTITUTE_LIST_SCREEN_TUTE), true).apply();
                if(filterAllowed && mInstitutes!=null && mInstitutes.size()>0 && listType!=Constants.INSTITUTE_SEARCH_TYPE) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof  MainActivity)
                listener = (OnInstituteSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        int osVersion = android.os.Build.VERSION.SDK_INT;
        if ( osVersion < Build.VERSION_CODES.N) {
            outState.putParcelableArrayList(ARG_INSTITUTE, this.mInstitutes);
            outState.putString(ARG_TITLE, this.mTitle);
            outState.putString(ARG_NEXT, this.mNextUrl);
            outState.putBoolean(ARG_FILTER_ALLOWED, this.filterAllowed);
            outState.putInt(ARG_FILTER_COUNT, this.filterCount);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        loading=false;
        if(this.floatingActionButton != null){
            this.floatingActionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

       MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
        if (!IS_TUTE_COMPLETED) {
            this.mTuteView.setVisibility(View.VISIBLE);
            this.floatingActionButton.setVisibility(View.GONE);
        } else {
            this.mTuteView.setVisibility(View.GONE);
            this.floatingActionButton.setVisibility(View.VISIBLE);
        }

        if (this.mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your search.");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.instituteView.setVisibility(View.GONE);
            this.floatingActionButton.setVisibility(View.GONE);
            this.mFilterTokelLL.setVisibility(View.GONE);

        }else {
            this.mEmptyTextView.setVisibility(View.GONE);
            this.instituteView.setVisibility(View.VISIBLE);
            this.floatingActionButton.setVisibility(View.VISIBLE);
            if(this.filterAllowed) {
                this.mFilterTokelLL.setVisibility(View.VISIBLE);
            }
        }
        updateFilterButton(filterCount);

    }

    public void clearList() {
        if(this.mInstitutes == null || this.mInstitutes.size() == 0)return;
        mInstitutes.clear();
        mAdapter.notifyDataSetChanged();
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
        mAdapter.lastPosition = this.mInstitutes.size() - 1;
        mInstitutes.addAll(institutes);
        mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;


        if (this.mInstitutes.size() == 0) {
            this.instituteView.setVisibility(View.GONE);
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your search.");
            this.mEmptyTextView.setVisibility(View.VISIBLE);

                this.floatingActionButton.setVisibility(View.GONE);
                this.mFilterTokelLL.setVisibility(View.GONE);
        } else {
            this.instituteView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            if(this.filterAllowed) {
                this.floatingActionButton.setVisibility(View.VISIBLE);
                this.mFilterTokelLL.setVisibility(View.VISIBLE);
            }
        }
        if(this.filterAllowed) {
            if (this.mCompletionView != null && this.mCompletionView.getObjects().size() > 0) {
                this.mCompletionView.clear();
            }
            this.mSetFilterList();
        }
    }

    public void updateSearchList(List<Institute> institutes, String next) {
        if(getView() == null)return;
        this.listType=Constants.INSTITUTE_SEARCH_TYPE;
        this.filterAllowed=false;
        this.floatingActionButton.setVisibility(View.GONE);
        this.mFilterTokelLL.setVisibility(View.GONE);
        this.progressBarLL.setVisibility(View.GONE);
        this.mAdapter.lastPosition = -1;
        clearList();
        this.mInstitutes.addAll(institutes);
        this.mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;

        if (mInstitutes.size() == 0) {
            this.instituteView.setVisibility(View.GONE);
            this.mEmptyTextView.setText("Opps! No Search Result Found!");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.floatingActionButton.setVisibility(View.GONE);
            this.mFilterTokelLL.setVisibility(View.GONE);
        }else {
            this.instituteView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            if(this.filterAllowed) {
                this.floatingActionButton.setVisibility(View.VISIBLE);
                this.mFilterTokelLL.setVisibility(View.VISIBLE);
            }
        }
        if(this.filterAllowed) {
            if (this.mCompletionView != null && this.mCompletionView.getObjects().size() > 0) {
                this.mCompletionView.clear();
            }
            this.mSetFilterList();
        }
    }

    public void updateLikeButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }

    public void updateShortlistButton(int position)
    {
        if(!this.filterAllowed &&  !Constants.IS_RECOMENDED_COLLEGE && listType!=Constants.INSTITUTE_SEARCH_TYPE)
            if(this.mInstitutes != null && this.mInstitutes.size() > position)
               this. mInstitutes.remove(position);
            else
                this.mAdapter.notifyItemChanged(position);

        this.mAdapter.notifyDataSetChanged();
        if(this.mInstitutes != null && this.mInstitutes.size() <=0 &&
                (super.mNextUrl == null || super.mNextUrl.isEmpty()
                || super.mNextUrl.equalsIgnoreCase("null"))){
             updateList(this.mInstitutes, super.mNextUrl);
         }
    }

    public void updateFilterButton(int filterCount)
    {
        if(!this.filterAllowed) {
            return;
        }
        this.filterCount = filterCount;
        this.mFilterTokelLL.setVisibility(View.VISIBLE);
        this.floatingActionButton.setImageDrawable(Utils.ApplyThemeToDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_filter), this.getActivity().getResources().getColor(R.color.primary_orange)));

        if(this.filtersApplied == 0){
            this.mFilterTokelLL.setVisibility(View.GONE);
            this.floatingActionButton.setImageDrawable(Utils.ApplyThemeToDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_filter), this.getActivity().getResources().getColor(R.color.white)));
        }
    }

    private void updateFilterTokenConfirmation(Object token) {

        ArrayList<Folder> folderList = ((MainActivity) getActivity()).getFilterList();
        if(folderList != null && !folderList.isEmpty()) {
            for (Folder f : folderList) {
                for (Facet ft : f.getFacets())
                    if (ft.getLabel().equalsIgnoreCase(token.toString())) {
                        ft.deselect();
                        break;
                    }
            }
        }
        if(super.listener != null)
            super.listener.onFilterApplied();
    }

    private void mSetFilterList()
    {
        filtersApplied = 0;
        if(getActivity() != null) {
            ArrayList<Folder> folderList = ((MainActivity) getActivity()).getFilterList();
            if (folderList != null && !folderList.isEmpty()) {
                for (Folder f : folderList) {
                    if (f.getLabel().equalsIgnoreCase("stream") || f.getLabel().equalsIgnoreCase("level"))
                        continue;
                    ArrayList<Facet> facetList = f.getFacets();
                    if(facetList != null && !facetList.isEmpty()) {
                        for (Facet ft : facetList) {
                            if (ft.isSelected() == 1) {
                                mCompletionView.addObject(ft.getLabel());
                                filtersApplied++;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onTokenAdded(Object token) {
    }

    @Override
    public void onTokenRemoved(Object token)
    {
        if(!Constants.SEND_REQUEST)return;
        updateFilterTokenConfirmation(token);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.view_into_grid:
                View rootView = getView();
                if(rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_list);
                    layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(layoutManager);
                    this.mAdapter = new InstituteListAdapter(getActivity(), this.mInstitutes, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.addOnScrollListener(scrollListener);
                }

                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.institute_list);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    this.mAdapter = new InstituteListAdapter(getActivity(), this.mInstitutes, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.addOnScrollListener(scrollListener);
                }
                break;
            default:
                break;
        }
        updateViewTypeIcon(getView(), this.mViewType);
    }

    @Override
    public String getEntity() {
        return null;
    }

    public interface OnInstituteSelectedListener extends BaseListener {
        void onInstituteSelected(int position);

        @Override
        void onInstituteLikedDisliked(int position, int liked);

        void onFilterButtonClicked();

        void onFilterApplied();

        void onInstituteShortlisted(int position);
        void displayMessage(int messageId);

        @Override
        void onEndReached(String next, int type);
    }

    public boolean getFilterAllowed()
    {
        return filterAllowed;
    }
}
