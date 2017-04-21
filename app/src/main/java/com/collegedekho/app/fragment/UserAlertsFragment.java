package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.UserAlertsAdapter;
import com.collegedekho.app.entities.MyAlertDate;

import java.util.ArrayList;

/**
 * Created by {Bashir} on {22/12/15}.
 */
public class UserAlertsFragment extends BaseFragment implements UserAlertsAdapter.OnUserAlertItemSelectListener {

    private static final String PARAM1="param1";
    private ArrayList<MyAlertDate> alertDates;
    private OnAlertItemSelectListener mListener;

    public static UserAlertsFragment newInstance(ArrayList<MyAlertDate> dates) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, dates);
        UserAlertsFragment fragment = new UserAlertsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.alertDates = bundle.getParcelableArrayList(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_alerts_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView alertRecycler = (RecyclerView) view.findViewById(R.id.user_alerts_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alertRecycler.setLayoutManager(layoutManager);
        UserAlertsAdapter userAlertsAdapter = new UserAlertsAdapter(getActivity(), alertDates, this);
        alertRecycler.setAdapter(userAlertsAdapter);
        if(alertDates.isEmpty()){
            view.findViewById(R.id.error_message).setVisibility(View.VISIBLE);
            alertRecycler.setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.error_message).setVisibility(View.GONE);
            alertRecycler.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity) {
                this.mListener = (OnAlertItemSelectListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnSubmitCalendarData");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onItemSelect(int position) {
        mListener.onItemSelected(position);
    }

    @Override
    public String getEntity() {
        return null;
    }

    public interface OnAlertItemSelectListener {
        void onItemSelected(int position);
    }

}
