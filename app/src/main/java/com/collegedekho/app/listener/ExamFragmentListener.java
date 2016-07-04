package com.collegedekho.app.listener;

import com.collegedekho.app.entities.Exam;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 4/7/16.
 */

public interface ExamFragmentListener {
    void updateQueryExamList(ArrayList<Exam> queryExamList);
}
