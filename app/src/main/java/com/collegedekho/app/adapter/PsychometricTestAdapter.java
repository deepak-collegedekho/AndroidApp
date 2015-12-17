package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.PsychometricTestQuestion;

import java.util.ArrayList;

/**
 * Created by bashir on 11/12/15.
 */
public class PsychometricTestAdapter extends RecyclerView.Adapter<PsychometricTestAdapter.PsychometricTestViewHolder> {

    private Context mContext;
    private ArrayList<PsychometricTestQuestion> itemList;
    private OnItemClickListener mClickListener;
    public PsychometricTestAdapter(Context context, ArrayList<PsychometricTestQuestion> itemList,OnItemClickListener clickListener) {
        this.mContext = context;
        this.itemList = itemList;
        this.mClickListener=clickListener;
    }

    @Override
    public PsychometricTestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.psychometric_test_card, parent, false);

        return new PsychometricTestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PsychometricTestViewHolder holder, int position) {
        PsychometricTestQuestion question = itemList.get(position);
        holder.txt_serial_no.setText(question.getId()+") ");
        holder.txt_question_text.setText(question.getQuestion());
        holder.radioGroup.check(question.getCheckedId());

//        ((RadioButton)holder.radioGroup.getChildAt(0)).setText("Text One");
//        ((RadioButton)holder.radioGroup.getChildAt(1)).setText("Text Two");
//        ((RadioButton)holder.radioGroup.getChildAt(2)).setText("Text Three");

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class PsychometricTestViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {

        TextView txt_serial_no;
        TextView txt_question_text;
        RadioGroup radioGroup;

        public PsychometricTestViewHolder(View itemView) {
            super(itemView);
            txt_serial_no = (TextView) itemView.findViewById(R.id.question_sno);
            txt_question_text = (TextView) itemView.findViewById(R.id.question_text);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.answer_options);
            radioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int position = this.getLayoutPosition();
            PsychometricTestQuestion question = itemList.get(position);

            switch (checkedId) {
                case R.id.option_always:
                    question.setAnswer("2");
                    break;
                case R.id.option_sometimes:
                    question.setAnswer("1");
                    break;

                case R.id.option_never:
                    question.setAnswer("0");
                    break;

                default:
                    break;
            }
            question.setCheckedId(checkedId);
            int size=getItemCount();
            int count=0;
            for (int i=0;i<size;i++){
                if(itemList.get(i).getCheckedId()!=0){
                    count++;
                }
            }
            if(count==size) {
                mClickListener.onItemClicked(position);
            }
        }
    }

    public interface OnItemClickListener{
        public void onItemClicked(int position);
    }
}
