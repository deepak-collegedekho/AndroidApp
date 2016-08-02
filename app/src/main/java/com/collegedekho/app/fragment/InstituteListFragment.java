package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private View filterBtn, filters;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private ContactsCompletionView mCompletionView;
    private ArrayAdapter<String> tokenAdapter;
    private RecyclerView recyclerView;
    private View instituteView;
    private FrameLayout fab;
    private FloatingActionButton fabBtn;
    private int fabMargin;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_institute_listing, container, false);

        String definedFilters = getActivity().getSharedPreferences(((MainActivity) getActivity()).getResourceString(R.string.PREFS), getActivity().MODE_PRIVATE).getString(Constants.SELECTED_FILTERS, null);
        if (definedFilters != null && definedFilters != "") {

        }

        Animation animation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.simple_grow);

        //toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        //  FAB margin needed for animation
        fabMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        //toolbarHeight = Utils.getToolbarHeight(this.getActivity());

        //toolbarContainer = (AppBarLayout) getActivity().findViewById(R.id.app_bar_layout);
        //mainContainer = (LinearLayout) getActivity().findViewById(R.id.main_container);

        fab = (FrameLayout) rootView.findViewById(R.id.myfab_main);
<<<<<<< Updated upstream
        fabBtn = (ImageButton) rootView.findViewById(R.id.button_filter);
        fabBtn.setContentDescription("Click to Apply Filters");
=======
        fabBtn = (FloatingActionButton) rootView.findViewById(R.id.button_filter);
>>>>>>> Stashed changes
        //fabShadow = rootView.findViewById(R.id.myfab_shadow);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fabBtn.setBackground(this.getActivity().getDrawable(R.drawable.ripple_accent));
        }

        fab.startAnimation(animation);

        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.INSTITUTE_LIST_SCREEN_TUTE), false);

        if (IS_TUTE_COMPLETED)
            rootView.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);

        mCompletionView = (ContactsCompletionView)rootView.findViewById(R.id.searchView);
        mCompletionView.setAdapter(tokenAdapter);
        mCompletionView.setTokenListener(this);
        mCompletionView.setInputType(InputType.TYPE_NULL);
        mCompletionView.allowDuplicates(false);
        mCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);

        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL = (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_list);

        if(this.mViewType == Constants.VIEW_INTO_GRID)
            layoutManager = new GridLayoutManager(getActivity(), 2);
        else
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        recyclerView.setLayoutManager(layoutManager);
        updateViewTypeIcon(rootView, this.mViewType);
        mAdapter = new InstituteListAdapter(getActivity(), mInstitutes, this.mViewType);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        instituteView=rootView.findViewById(R.id.viewType);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);
        if (filterAllowed) {
            this.mSetFilterList();

            rootView.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.button_filter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onFilterButtonClicked();
                }
            });
        } else {
            rootView.findViewById(R.id.button_filter).setVisibility(View.GONE);
            rootView.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
        }
        filterBtn = rootView.findViewById(R.id.button_filter);
        filters = rootView.findViewById(R.id.filter_tokenLL);

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
        rootView.findViewById(R.id.institute_list_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.INSTITUTE_LIST_SCREEN_TUTE), true).apply();
                if(filterAllowed && mInstitutes!=null && mInstitutes.size()>0 && listType!=Constants.INSTITUTE_SEARCH_TYPE) {
                    rootView.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        return rootView;
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
        outState.putParcelableArrayList(ARG_INSTITUTE, this.mInstitutes);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
        outState.putBoolean(ARG_FILTER_ALLOWED, this.filterAllowed);
        outState.putInt(ARG_FILTER_COUNT, this.filterCount);
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
    }

    @Override
    public void onResume() {
        super.onResume();

       MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

        if (mInstitutes.size() == 0) {
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your search.");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.instituteView.setVisibility(View.GONE);
            View v = getView();
            if(v != null) {
                v.findViewById(R.id.button_filter).setVisibility(View.GONE);
                v.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
            }
        }
        else {
            this.mEmptyTextView.setVisibility(View.GONE);
            this.instituteView.setVisibility(View.VISIBLE);
            if(filterAllowed) {
                View v = getView();
                if(v != null) {
                    v.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
                }
            }
        }

        updateFilterButton(filterCount);
        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED)
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.VISIBLE);
            else
                view.findViewById(R.id.institute_list_tour_guide_image).setVisibility(View.GONE);
        }
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


        if (mInstitutes.size() == 0) {
            instituteView.setVisibility(View.GONE);
            this.mEmptyTextView.setText("Opps! Unable to find colleges for your search.");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            View v = getView();
            if(v != null) {
                v.findViewById(R.id.button_filter).setVisibility(View.GONE);
                v.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
            }
        } else {
            instituteView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            View v = getView();
            if(v != null && filterAllowed) {
                v.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);
                v.findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
            }
        }
        if(filterAllowed) {
            if (mCompletionView != null && mCompletionView.getObjects().size() > 0) {
                mCompletionView.clear();
            }
            this.mSetFilterList();
        }
    }

    public void updateSearchList(List<Institute> institutes, String next) {
        if(getView() == null)return;
        this.listType=Constants.INSTITUTE_SEARCH_TYPE;
        filterAllowed=false;
        filterBtn.setVisibility(View.GONE);
        filters.setVisibility(View.GONE);
        progressBarLL.setVisibility(View.GONE);
        mAdapter.lastPosition = -1;
        clearList();
        mInstitutes.addAll(institutes);
        mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;

        if (mInstitutes.size() == 0) {
            this.instituteView.setVisibility(View.GONE);
            this.mEmptyTextView.setText("Opps! No Search Result Found!");
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            View v = getView();
            if(v != null) {
                v.findViewById(R.id.button_filter).setVisibility(View.GONE);
                v.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
            }
        }else {
            this.instituteView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setVisibility(View.GONE);
            View v = getView();
            if(v != null &&filterAllowed) {
                    v.findViewById(R.id.button_filter).setVisibility(View.VISIBLE);
                    v.findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
            }
        }
        if(filterAllowed) {
            if (mCompletionView != null && mCompletionView.getObjects().size() > 0) {
                mCompletionView.clear();
            }
            this.mSetFilterList();
        }
    }

    public void updateLikeButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }

    public void updateShortlistButton(int position)
    {
       // mAdapter.updateShortlistStatus(position, this.filterAllowed,listType);
        if(!this.filterAllowed &&  !Constants.IS_RECOMENDED_COLLEGE && listType!=Constants.INSTITUTE_SEARCH_TYPE)
            if(mInstitutes != null && mInstitutes.size() > position)
                mInstitutes.remove(position);
            else
                this.mAdapter.notifyItemChanged(position);

        this.mAdapter.notifyDataSetChanged();
        if(mInstitutes != null && mInstitutes.size() <=0
                && (mNextUrl == null || mNextUrl.isEmpty() || mNextUrl.equalsIgnoreCase("null"))){
             updateList(mInstitutes, mNextUrl);
         }
    }

    public void updateFilterButton(int filterCount)
    {
        if(!filterAllowed) {
            return;
        }// do not need on my shortlist page

//        if(filterCount < 1){
//            filters.setVisibility(View.GONE);
//            return;
//        }

        View v = getView();
        if (v != null) {
            this.filterCount = filterCount;
//            if (filterCount <= 2) {
//                getView().findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
//                ((ImageView) v.findViewById(R.id.button_filter)).setImageDrawable(Utils.ApplyThemeToDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_filter_vector), this.getActivity().getResources().getColor(R.color.white)));
//            }
//            else {
                getView().findViewById(R.id.filter_tokenLL).setVisibility(View.VISIBLE);
                ((ImageView) v.findViewById(R.id.button_filter)).setImageDrawable(Utils.ApplyThemeToDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_filter), this.getActivity().getResources().getColor(R.color.primary_orange)));
//            }
            if(filtersApplied == 0){
                v.findViewById(R.id.filter_tokenLL).setVisibility(View.GONE);
                ((ImageView) v.findViewById(R.id.button_filter)).setImageDrawable(Utils.ApplyThemeToDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_filter), this.getActivity().getResources().getColor(R.color.white)));
            }
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
        if(listener != null)
            listener.onFilterApplied();
    }

    private void mSetFilterList()
    {
        filtersApplied = 0;
        ArrayList<Folder> folderList = ((MainActivity) getActivity()).getFilterList();
        if(folderList != null && !folderList.isEmpty()) {
            for (Folder f : folderList) {
                if (f.getLabel().equalsIgnoreCase("stream") || f.getLabel().equalsIgnoreCase("level"))
                    continue;

                for (Facet ft : f.getFacets()) {
                    if (ft.isSelected() == 1) {
                        mCompletionView.addObject(ft.getLabel());
                        filtersApplied++;
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
    public void show() {
        Log.e("InstituteListFragment", "Show");
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3)).start();
    }

    @Override
    public void hide() {
        Log.e("InstituteListFragment", "Hide");
        fab.animate().translationY(fab.getHeight() + fabMargin).setInterpolator(new AccelerateInterpolator(3)).start();
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
