package com.collegedekho.app.listener;

import android.support.v7.widget.SearchView;

import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ProfileSpinnerItem;

import java.util.ArrayList;

/**
 * Created by root on 21/6/16.
 */
public class StreamOnQueryListener implements SearchView.OnQueryTextListener {

    ArrayList<ProfileSpinnerItem>  mStreamList;
    ExamStreamAdapter mExamStreamAdapter;

    public StreamOnQueryListener(){

    }

    public StreamOnQueryListener(ArrayList<ProfileSpinnerItem>  mStreamList, ExamStreamAdapter mExamStreamAdapter){
        this.mStreamList = mStreamList;
        this.mExamStreamAdapter = mExamStreamAdapter;
    }

    public void setmExamStreamAdapter(ExamStreamAdapter mExamStreamAdapter){
        this.mExamStreamAdapter = mExamStreamAdapter;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try{

            ArrayList<ProfileSpinnerItem> searchResults = new ArrayList<>();
            for(ProfileSpinnerItem stream : mStreamList){
                if((stream.getName().toLowerCase()).contains((newText.toString().toLowerCase()))){
                    searchResults.add(stream);
                }
            }
//            if(searchResults.size() != 0){
            mExamStreamAdapter.setStreamList(searchResults);
            mExamStreamAdapter.notifyDataSetChanged();
//            } else {
//
//            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
