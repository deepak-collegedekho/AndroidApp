package com.collegedekho.app.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.ExamYearSpinner;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamHolderView> {

    private Context mContext;
    private ArrayList<Exam> mExamList = new ArrayList<>();
    private int lastPosition=-1;
    private int textColorId;
    private boolean mShowAllExams ;


    public ExamsAdapter(Context context, ArrayList<Exam> examList){
        this.mContext = context;
        this.mExamList.addAll(examList);

        if(mExamList != null && !mExamList.isEmpty()){
            int examCount = mExamList.size();
            for (int i = 0; i < examCount; i++) {
                Exam exam = mExamList.get(i);
                ArrayList<ExamDetail> examDetailList = exam.getExam_details();
                if(examDetailList == null)continue;
                int count = examDetailList.size();
                for (int j = 0; j < count; j++) {
                    ExamDetail obj = examDetailList.get(j);
                    if (obj == null) continue;
                   if (obj.is_preparing()) {
                        obj.setSelected(true);
                       exam.setSelected(true);
                    }
                }
            }
        }
        textColorId = this.mContext.getResources().getColor(R.color.text_light_grey);
    }

    @Override
    public ExamHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
      //  LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_exam_drop_down, parent, false);
        return new ExamHolderView(convertView);
    }

    @Override
    public void onBindViewHolder(final ExamHolderView holder, final int position) {

        Exam exam = mExamList.get(position);
        if(exam == null)
            return;

        holder.mExamName.setText(exam.getExam_name());

        // exam position is taged to get this position while clicked
        holder.mExamName.setTag(position);
        holder.mYearSpinner.setTag(position);

        ArrayList<ExamDetail> examDetail = exam.getExam_details();
        if(examDetail == null)
            return;

        int selectedPosition=-1;
        final int count = examDetail.size();
        final String year[] = new String[count];
        for (int i = 0; i < count; i++) {
            ExamDetail obj = examDetail.get(i);
            if(obj == null)continue;
            year[i] = obj.getYear();
            // if user is preparing for the exam then set exam as preparing true
            //  by this we can disable click on this exam while updating exams.
            if(obj.is_preparing()){
                selectedPosition=i;
               // obj.setSelected(true);
               // exam.setSelected(true);
               // exam.setPreparing(true);
            }
        }

        holder.mYearSpinner.setAdapter(new ArrayAdapter<>(mContext, R.layout.spinner_drop_down_item, year));

        if (exam.isSelected()) {
            // already selected exams will show as selected exams
            // and preparing exams click will be disable while updating exams.
            holder.mExamName.setSelected(true);
            holder.mYearSpinner.setSelected(true);
            // this will set exam year which was selected last time
            int spinnerItemCount = holder.mYearSpinner.getCount();
            if(selectedPosition > spinnerItemCount)
                selectedPosition =0;
            holder.mYearSpinner.setSelection(selectedPosition);
            holder.mExamName.setTextColor(Color.WHITE);
           /* if(exam.isPreparing()){
                holder.mExamName.setEnabled(false);
                holder.mYearSpinner.setEnabled(false);
            }else{
                holder.mExamName.setEnabled(true);
                holder.mYearSpinner.setEnabled(true);
            }*/
        }else {
            holder.mExamName.setSelected(false);
            holder.mYearSpinner.setSelected(false);
           // holder.mExamName.setEnabled(true);
            //holder.mYearSpinner.setEnabled(true);
            holder.mExamName.setTextColor(textColorId);
        }
        // show exam layout
        if(mShowAllExams) {
            if (position == 0) {
                if (exam.getExam_type() == ProfileMacro.OTHER_EXAM) {
                    holder.mExamTypeHeader.setText("Other Exams");
                    holder.mExamTypeHeader.setVisibility(View.VISIBLE);
                } else {
                    holder.mExamTypeHeader.setVisibility(View.GONE);
                }
            } else if (exam.getExam_type() == ProfileMacro.OTHER_EXAM) {
                if (mExamList.get(position - 1).getExam_type() == ProfileMacro.STREAM_EXAM) {
                    holder.mExamTypeHeader.setText("Other Exams");
                    holder.mExamTypeHeader.setVisibility(View.VISIBLE);
                } else {
                    holder.mExamTypeHeader.setVisibility(View.GONE);
                }
            } else {
                holder.mExamTypeHeader.setVisibility(View.GONE);
            }
        }

        holder.mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int  itemPosition = -1;
                try{
                    itemPosition = Integer.parseInt(parent.getTag().toString()
                    );
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                if(itemPosition == -1){
                    return;
                }
                Exam exam = mExamList.get(itemPosition);
                ArrayList<ExamDetail> examDetailList = exam.getExam_details();
                if(examDetailList != null) {

                    final int count = examDetailList.size();
                    for (int i = 0; i < count; i++) {
                        ExamDetail examDetailObj = examDetailList.get(i);
                        if (examDetailObj == null) continue;
                        examDetailObj.setSelected(false);
                        examDetailObj.setStatus(ProfileMacro.EXAM_PREPARING);
                    }


                    // make all exam detail selected false;
                    // if exam name text is selected then on selecting of year
                    // it will set selected true to object of exam detail.
                    if(holder.mExamName.isSelected() && count>=position) {
                        ExamDetail examDetailObj = examDetailList.get(position);
                        examDetailObj.setSelected(true);
                        if (examDetailObj.isResult_out()) {
                            displayAlert(examDetailObj, holder, exam);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        holder.mExamName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int  itemPosition = -1;
                try{
                    itemPosition = Integer.parseInt(v.getTag().toString());
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
                if(itemPosition == -1){
                    return;
                }
                Exam exam = mExamList.get(itemPosition);

                if (v.isSelected()) {

                    v.setSelected(false);
                    exam.setSelected(false);
                    holder.mYearSpinner.setSelected(false);
                    holder.mExamName.setTextColor(textColorId);
                    ArrayList<ExamDetail> examDetailList = exam.getExam_details();
                    if(examDetailList != null) {
                        final int count = examDetailList.size();
                        for (int i = 0; i < count; i++) {
                            ExamDetail examDetailObj = examDetailList.get(i);
                            if (examDetailObj == null) continue;
                            examDetailObj.setSelected(false);
                            examDetailObj.setStatus(ProfileMacro.EXAM_PREPARING);
                        }
                    }
                }else{

                    v.setSelected(true);
                    exam.setSelected(true);
                    holder.mYearSpinner.setSelected(true);
                    holder.mExamName.setTextColor(Color.WHITE);

                    ExamDetail examDetailObj = exam.getExam_details().get(0);
                    examDetailObj.setSelected(true);

                    if(count>1)
                        holder.mYearSpinner.performClick();
                    else{
                        if(examDetailObj.isResult_out())
                            displayAlert(examDetailObj,holder, exam);
                    }

                }
            }
        });
      // this.setAnimation(holder.examCard, position);
    }

    @Override
    public void onViewAttachedToWindow(ExamHolderView holder) {
        holder.itemView.clearAnimation();
        super.onViewAttachedToWindow(holder);
    }

    public void updateExamsList(ArrayList<Exam> newExamList){
       //TODO:: commented code for testing to ask marks for those exams which results is already out.
       /* int count1 = newExamList.size();
        for (int i = 0; i < count1; i++) {
            Exam examObj = newExamList.get(i);
             ArrayList<ExamDetail> oldDetaillist = examObj.getExam_details();
            int oldDetailCount = oldDetaillist.size();
            for (int j = 0; j < oldDetailCount; j++) {
                if (j==0)
                  oldDetaillist.get(j).setResult_out(true);
            }
        }*/

        //TODO:: this commented code for filter based on stream.

       /* int count = mExamList.size();
        for (int i = 0; i < count; i++) {
            Exam examObj = mExamList.get(i);
            if(!examObj.isSelected())
                continue;
            ArrayList<ExamDetail> oldDetaillist =  examObj.getExam_details();
            ExamDetail selectedExamDetail = null;
            int oldDetailCount = oldDetaillist.size();
            for (int j = 0; j < oldDetailCount; j++) {
                if(oldDetaillist.get(j).isSelected()) {
                    selectedExamDetail = oldDetaillist.get(j);
                    break;
                }

            }
            if(selectedExamDetail == null)
                continue;

            int examCount = newExamList.size();
            for (int j = 0; j <  examCount; j++) {
               Exam newExamObj = newExamList.get(j);
                if(newExamObj == null)
                    continue;
                ArrayList<ExamDetail> newDetaillist =  newExamObj.getExam_details();
                int newDetailCount = newDetaillist.size();
                for (int k = 0; k < newDetailCount; k++) {
                    ExamDetail newExamDetailObj =newDetaillist.get(k);
                    if(newExamDetailObj.getId().equalsIgnoreCase(selectedExamDetail.getId()))
                    {
                        newExamDetailObj.setSelected(true);
                        newExamObj.setSelected(true);
                        break;
                    }

                }
            }

        }*/
        this.mExamList.clear();
        this.mExamList.addAll(newExamList);
        notifyDataSetChanged();
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

    public ArrayList<Exam> getExamsList() {
        return this.mExamList;
    }

    class ExamHolderView extends RecyclerView.ViewHolder{

        LinearLayout examCard;
        TextView mExamName;
        ExamYearSpinner mYearSpinner;
        TextView mExamTypeHeader;

        public ExamHolderView(View itemView) {
            super(itemView);
            examCard = (LinearLayout) itemView.findViewById(R.id.exam_card);
            mExamName = (TextView)itemView.findViewById(R.id.exam_name);
            mYearSpinner = (ExamYearSpinner)itemView.findViewById(R.id.exam_year_spinner);
            mExamTypeHeader = (TextView) itemView.findViewById(R.id.user_exam_heading);
        }

    }

    private void displayAlert(final ExamDetail examDetail, final ExamHolderView holder,final Exam exam){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final boolean[] gotUserResponse = new boolean[1];
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
                    examDetail.setScore(Integer.parseInt(marks));
                    gotUserResponse[0]=true;
                    alertDialog.dismiss();
                }else{
                    Utils.DisplayToast(mContext,"Enter marks first");
                }
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            }
        });
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotUserResponse[0]=false;
                alertDialog.dismiss();
            }
        });
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if(!gotUserResponse[0]){
                    holder.mExamName.setSelected(false);
                    exam.setSelected(false);
                    holder.mYearSpinner.setSelected(false);
                    holder.mExamName.setTextColor(textColorId);
                    ArrayList<ExamDetail> examDetail = exam.getExam_details();
                    if(examDetail != null) {
                        final int count = examDetail.size();
                        for (int i = 0; i < count; i++) {
                            ExamDetail obj = examDetail.get(i);
                            if (obj == null) continue;
                            obj.setSelected(false);
                            obj.setStatus(ProfileMacro.EXAM_PREPARING);
                        }
                    }
                }else{
                    ArrayList<ExamDetail> examDetail = exam.getExam_details();
                    if(examDetail != null) {
                        final int count = examDetail.size();
                        for (int i = 0; i < count; i++) {
                            ExamDetail obj = examDetail.get(i);
                            if (obj == null) continue;
                            if(obj.isSelected()) {
                                obj.setStatus(ProfileMacro.EXAM_GIVEN);
                            }
                        }
                    }
                }
            }
        });
        alertDialog.show();
    }

    /**
     *  This method is used to show header for other exams
     * @param mShowAllExams
     */

    public void setShowAllExams(boolean mShowAllExams) {
        this.mShowAllExams = mShowAllExams;
    }
}
