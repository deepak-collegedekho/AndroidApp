package com.collegedekho.app.listener;

import android.support.v7.widget.SearchView;

import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;

import java.util.ArrayList;

/**
 * Created by root on 21/6/16.
 */
public class ExamOnQueryListener implements SearchView.OnQueryTextListener {

    ArrayList<Exam>  mExamList;
    ExamsAdapter mExamAdapter;

    public ExamOnQueryListener(){

    }

    public ExamOnQueryListener(ArrayList<Exam>  mExamList, ExamsAdapter mExamAdapter){
        this.mExamList = mExamList;
        this.mExamAdapter = mExamAdapter;
    }

    public void setmExamList(ArrayList<Exam> mExamList) {
        this.mExamList = mExamList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try{

            ArrayList<Exam> searchResults = new ArrayList<>();
            for(Exam exam : mExamList){
                if((exam.getExam_name().toLowerCase()).contains((newText.toString().toLowerCase())) || (exam.getExam_short_name().toLowerCase()).contains((newText.toString().toLowerCase()))){
                    searchResults.add(exam);
                }
            }
//            if(searchResults.size() != 0){
            mExamAdapter.updateExamList(searchResults);
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
