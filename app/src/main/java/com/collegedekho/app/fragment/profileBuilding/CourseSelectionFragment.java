package com.collegedekho.app.fragment.profileBuilding;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CourseSelectionAdapter;
import com.collegedekho.app.entities.Courses;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseSelectionFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COURSES_LIST = "courses_list";
    private static final String ARG_NEXT = "arg_next";

    private ArrayList<Courses> mCourseList;
    private RecyclerView mCourseRecyclerView;
    private TextView mEmptyTV;
    private TextView mCourseSelectionTitle;
    private ImageButton mCourseSelectionFinish;
    private CourseSelectionAdapter mCourseSelectionAdapter;
    private String mCourseSelectionFragmentNextURL;

    public CourseSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param courseList Parameter 1.
     * @param next
     * @return A new instance of fragment CourseSelectionFragment.
     */
    public static CourseSelectionFragment newInstance(ArrayList<Courses> courseList, String next) {
        CourseSelectionFragment fragment = new CourseSelectionFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(COURSES_LIST, courseList);
        args.putString(ARG_NEXT, next);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mCourseList = getArguments().getParcelableArrayList(COURSES_LIST);
            this.mCourseSelectionFragmentNextURL = getArguments().getString(ARG_NEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mCourseRecyclerView = (RecyclerView) view.findViewById(R.id.courses_selection_list);
        this.mCourseSelectionFinish = (ImageButton) view.findViewById(R.id.course_selection_finish_button);
        this.mCourseSelectionTitle = (TextView) view.findViewById(R.id.courses_selection_title);
        this.mEmptyTV = (TextView) view.findViewById(R.id.empty);

        this.mCourseSelectionAdapter = new CourseSelectionAdapter(this.getContext(), this.mCourseList);

        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };

        super.layoutManager = new LinearLayoutManager(this.getContext());
        this.mCourseRecyclerView.setLayoutManager(super.layoutManager);
        this.mCourseRecyclerView.setAdapter(this.mCourseSelectionAdapter);
        this.mCourseRecyclerView.setHasFixedSize(true);
        this.mCourseRecyclerView.setItemAnimator(animator);
        this.mCourseRecyclerView.addOnScrollListener(super.scrollListener);

        this.mCourseSelectionFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_COURSE_FINALIZED, CourseSelectionFragment.this.mCourseSelectionAdapter.GetCourseSelected().getId(), null));
            }
        });

        this.mSetUIVisibility();
        SearchView courseSearchView =(SearchView) view.findViewById(R.id.courses_selection_search_view);
        courseSearchView.requestFocus(0, new Rect());
        courseSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_COURSES_SELECTION_SEARCH, null,query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void mSetUIVisibility()
    {
        if(this.mCourseList == null || this.mCourseList.isEmpty()){
            this.mEmptyTV.setVisibility(View.VISIBLE);
            this.mCourseRecyclerView.setVisibility(View.GONE);
            this.mCourseSelectionTitle.setVisibility(View.GONE);
            if(NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                this.mEmptyTV.setText(getString(R.string.internet_not_available));
            }else{
                this.mEmptyTV.setText(getString(R.string.no_info_available));
            }
        }else{
            this.mEmptyTV.setVisibility(View.GONE);
            this.mCourseRecyclerView.setVisibility(View.VISIBLE);
            this.mCourseSelectionTitle.setVisibility(View.VISIBLE);
        }
    }

    public void updateCourses(List<Courses> courseList, String next) {
        if (courseList != null)
        {
            this.mCourseList.clear();
            this.mCourseList.addAll(courseList);
            this.mCourseSelectionAdapter.notifyDataSetChanged();
        }
        this.mCourseSelectionAdapter.SetCourseUpdated();

        super.loading = false;
        super.mNextUrl = this.mCourseSelectionFragmentNextURL = next;

        this.mSetUIVisibility();
    }

    @Override
    public String getEntity() {
        return null;
    }

    @Subscribe
    public void onEvent(Event event) {
        if (event != null && event.getTag().equals(AllEvents.ACTION_COURSE_SELECTED)) {
            this.mToggleFinishButtonUI();
        }
    }

    private void mToggleFinishButtonUI()
    {
        this.mCourseSelectionFinish.setVisibility((this.mCourseSelectionFinish.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
    }
}
