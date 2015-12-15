package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.CalendarParentFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Context mContext;
    private ArrayList<Date> itemList;
    private int startCellPosition =0;
    private int endCellPosition =0;
    private boolean isActiveSet;
    private boolean caIncreament;
    LayoutInflater inflater;
    // today
    Date today;
    Calendar mCalendar;
    Calendar cal;
    private int mOffSet;
    private OnCalendarItemSelectListener mListener;
    String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    int[]subjectColors={0xff0066ff,0xffff5c33,0xffff8533,0xffff3399,0xffffc61a,0xff8533ff};

    public CalendarAdapter(Context context, ArrayList<Date> itemList, OnCalendarItemSelectListener listener,int offSet) {
        this.mContext = context;
        this.itemList = itemList;
         this.mOffSet=offSet;
        mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.MONTH,offSet);
        cal = Calendar.getInstance();
        mListener = listener;
        inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.calendar_view_card_layout, parent, false);

        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {

        Date date = itemList.get(position);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if(month== mCalendar.get(Calendar.MONTH) && day== mCalendar.get(Calendar.DAY_OF_MONTH) &&year== mCalendar.get(Calendar.YEAR)){
            if(!isActiveSet) {
                isActiveSet = true;
                caIncreament=true;
                startCellPosition = position;
            }
        }else if(day==1 && month!= mCalendar.get(Calendar.MONTH)){
            if(!isActiveSet) {
                isActiveSet = true;
                caIncreament=true;
                startCellPosition = position;
            }
            if(isActiveSet){
                caIncreament=false;
                endCellPosition=position-1;
            }
        }
        if (month != mCalendar.get(Calendar.MONTH) || year != mCalendar.get(Calendar.YEAR)) {
            // if this day is outside current month, grey it out
            holder.dateView.setTextColor(0xffcccccc);
            holder.dotView.setVisibility(View.INVISIBLE);
        } else if (day == mCalendar.get(Calendar.DAY_OF_MONTH)) {
            // if it is today, set it to blue/bold
            holder.dateView.setTextColor(0xff0066ff);
        } else if (day < mCalendar.get(Calendar.DAY_OF_MONTH)) {
            holder.dotView.setVisibility(View.INVISIBLE);

        }

        if (day == 1) {
            holder.monthView.setText(monthNames[cal.get(Calendar.MONTH)]);
            holder.monthView.setVisibility(View.VISIBLE);

        } else {
            holder.monthView.setVisibility(View.INVISIBLE);
        }
        int i=0;
        for(String key:CalendarParentFragment.subjectsMap.keySet()){
            float dayCount=Float.valueOf(CalendarParentFragment.subjectsMap.get(key));
            if(dayCount>CalendarParentFragment.numDays){
                View dot=inflater.inflate(R.layout.dot_view,holder.dotView,false);
                dot.setBackgroundColor(subjectColors[i++]);
                holder.dotView.addView(dot);
            }

        }

        holder.dateView.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        if(caIncreament) {
            CalendarParentFragment.numDays++;
            CalendarParentFragment.daysIncreamented++;
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout dotView;
        public TextView monthView;
        public TextView dateView;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            dotView =  (LinearLayout)itemView.findViewById(R.id.calendar_dot_view);
            monthView = (TextView) itemView.findViewById(R.id.txt_month);
            dateView = (TextView) itemView.findViewById(R.id.txt_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemSelect(getLayoutPosition(), startCellPosition,endCellPosition);
        }
    }

    public interface OnCalendarItemSelectListener {
        public void onItemSelect(int position,int startPosition,int endPosition);
    }
}
