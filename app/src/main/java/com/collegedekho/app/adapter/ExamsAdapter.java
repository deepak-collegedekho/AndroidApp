package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamHolderView> {

    private Context mContext;
    private ArrayList<Exam> mExamList;
    private int lastExamPosition=-1;
    private int lastPosition=-1;


    public ExamsAdapter(Context context, ArrayList<Exam> examList){
        this.mContext = context;
        this.mExamList = examList;
    }

    @Override
    public ExamHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.layout_exam_drop_down, parent, false);
        return new ExamHolderView(convertView);
    }

    @Override
    public void onBindViewHolder(final ExamHolderView holder, final int position) {

        final Exam exam = this.mExamList.get(position);
        int selectedPosition=-1;
        if(exam != null) {
            holder.mExamName.setText(exam.getExam_name());
            ArrayList<ExamDetail> examDetail = exam.getExam_details();
            if(examDetail != null){
                final int count = examDetail.size();
                final String year[] = new String[count];
                for (int i = 0; i < count; i++) {
                    ExamDetail obj = examDetail.get(i);
                    if(obj == null)continue;
                    if(i==0)obj.setSelected(true);
                    year[i] = obj.getYear();
                    if(obj.is_preparing()){
                        selectedPosition=i;
                    }
                }
                holder.mYearSpinner.setAdapter(new ArrayAdapter<>(this.mContext, R.layout.spinner_drop_down_item, year));
                holder.mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        lastExamPosition=holder.mYearSpinner.getSelectedItemPosition();
                        if(holder.mExamName.isSelected() && exam.getExam_details().get(lastExamPosition).isResult_out()) {
//                            lastSelectedExam = exam;
                            displayAlert(exam.getExam_details().get(lastExamPosition),holder);

                        }else{
                        }
                        // make all exam detail selected false;
                        ArrayList<ExamDetail> examDetail = exam.getExam_details();
                        if(examDetail != null) {
                            final int count = examDetail.size();
                            for (int i = 0; i < count; i++) {
                                ExamDetail obj = examDetail.get(i);
                                if (obj == null) continue;
                                obj.setSelected(false);
                            }
                            if(count>=position) {
                                examDetail.get(position).setSelected(true);
                            }
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        lastExamPosition=holder.mYearSpinner.getSelectedItemPosition();
                        if(holder.mExamName.isSelected() && exam.getExam_details().get(lastExamPosition).isResult_out()) {
                            displayAlert(exam.getExam_details().get(lastExamPosition),holder);

                        }else{
                        }
                    }
                });
                if (selectedPosition >= 0) {
//                    examDetail.get(selectedPosition).setSelected(true);
                    holder.mYearSpinner.setSelection(selectedPosition);
                    holder.mYearSpinner.setSelected(true);
                    holder.mExamName.setSelected(true);
                    holder.mExamName.setTextColor(ExamsAdapter.this.mContext.getResources().getColor(R.color.white));
                    holder.mExamName.setEnabled(false);
                    holder.mYearSpinner.setEnabled(false);
                }else {
                    holder.mExamName.setEnabled(true);
                    holder.mYearSpinner.setEnabled(true);
                }
                holder.mExamName.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
//                        if(lastSelectedExam!=null && lastExamPosition>=0){
//                        //TODO: Display dialog to enter exam marks
//                        displayAlert(lastSelectedExam.getExam_details().get(lastExamPosition));
//                            return;
//                        }
                        if (holder.mExamName.isSelected())
                        {
                            holder.mExamName.setSelected(false);
                            holder.mYearSpinner.setSelected(false);
                            holder.mExamName.setTextColor(ExamsAdapter.this.mContext.getResources().getColor(R.color.text_light_grey));
                        }
                        else{
                            lastExamPosition=holder.mYearSpinner.getSelectedItemPosition();
                            if(exam.getExam_details().get(lastExamPosition).isResult_out()) {
                                displayAlert(exam.getExam_details().get(lastExamPosition),holder);

                            }else{
                            }
                            holder.mExamName.setSelected(true);
                            holder.mExamName.setTextColor(ExamsAdapter.this.mContext.getResources().getColor(R.color.white));
                            if(count>1) {
                                holder.mYearSpinner.performClick();
                            }else{
                                //holder.mYearSpinner.setSelected(true);
                                if(exam.getExam_details().get(lastExamPosition).isResult_out()) {
                                    displayAlert(exam.getExam_details().get(lastExamPosition),holder);
                                }
                            }
                            holder.mYearSpinner.setSelected(v.isSelected());
                        }
                        exam.setSelected(holder.mExamName.isSelected());
                    }
                });
            }
        }

        this.setAnimation(holder.examCard, position);
    }

    @Override
    public void onViewAttachedToWindow(ExamHolderView holder) {
        holder.itemView.clearAnimation();
        super.onViewAttachedToWindow(holder);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return this.mExamList.size();
    }

    class ExamHolderView extends RecyclerView.ViewHolder{

        LinearLayout examCard;
        TextView mExamName;
        Spinner mYearSpinner;
        public ExamHolderView(View itemView) {
            super(itemView);
            examCard = (LinearLayout) itemView.findViewById(R.id.exam_card);
            mExamName = (TextView)itemView.findViewById(R.id.exam_name);
            mYearSpinner = (Spinner)itemView.findViewById(R.id.exam_year_spinner);

        }

    }

    private void displayAlert(final ExamDetail examDetail, final ExamHolderView holder){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final boolean[] gotUserresponse=new boolean[1];
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = inflater.inflate(R.layout.dialog_get_exam_marks, null);
        dialogBuilder.setView(dialogView);

        Button dialog_ok=(Button)dialogView.findViewById(R.id.dialog_ok);
        Button dialog_cancel=(Button)dialogView.findViewById(R.id.dialog_cancel);
        final EditText marksView=(EditText)dialogView.findViewById(R.id.dialog_marks);
        final AlertDialog alertDialog = dialogBuilder.create();
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marks=marksView.getText().toString();
                if(!marks.equals("")) {
                    examDetail.setExam_marks(marks);
                    gotUserresponse[0]=true;
                    alertDialog.dismiss();
                }else{
                    Utils.DisplayToast(mContext,"Enter marks first");
                }
            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUserresponse[0]=false;
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(!gotUserresponse[0]){
                    holder.mExamName.setSelected(false);
                    holder.mYearSpinner.setSelected(false);
                }
            }
        });
        alertDialog.show();
    }
}
