package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamHolderView> {

    private Context mContext;
    private ArrayList<Exam> mExamList;

    public ExamsAdapter(Context context, ArrayList<Exam> examList){
        this.mContext = context;
        this.mExamList = examList;
    }

    @Override
    public ExamHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.layout_exam_drop_down, parent, false);

        return new ExamHolderView(convertView);
    }

    @Override
    public void onBindViewHolder(final ExamHolderView holder, int position) {

        final Exam exam = this.mExamList.get(position);
        if(exam != null) {
            holder.mExamName.setText(exam.getExam());
            ArrayList<ExamDetail> examDetail = exam.getExam_details();
            if(examDetail != null){
                int count = examDetail.size();
                String year[] = new String[count];
                for (int i = 0; i < count; i++) {
                    ExamDetail obj = examDetail.get(i);
                    if(obj == null)continue;
                    year[i] = obj.getYear();
                }
                holder.mYearSpinner.setAdapter(new ArrayAdapter<>(this.mContext, android.R.layout.simple_spinner_dropdown_item, year));
                holder.mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        exam.setSelected(true);
                        holder.mExamName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_exam_border_orange));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            if (exam.isSelected())
                 holder.mExamName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_exam_border_orange));
            else
                holder.mExamName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_exam_border));


        }
    }

    @Override
    public int getItemCount() {
        return this.mExamList.size();
    }

    class ExamHolderView extends RecyclerView.ViewHolder{

        TextView mExamName;
        Spinner mYearSpinner;
        public ExamHolderView(View itemView) {
            super(itemView);
            mExamName = (TextView)itemView.findViewById(R.id.exam_name);
            mYearSpinner = (Spinner)itemView.findViewById(R.id.exam_year_spinner);

        }
    }
}
