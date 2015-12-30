package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private Context mContext;
    private ArrayList<Date> itemList;
    private int startCellPosition =0;
    private int endCellPosition =0;
    private boolean isActiveCell;
    private boolean isCurrentMonth =false;
    LayoutInflater inflater;
    int selectedPosition=-1;
    private LinkedHashMap<String,String> mYearCalendar;
    // today
    Date today;
    Calendar mCalendar;
    Calendar cal;
    private int mOffSet;
    private OnCalendarItemSelectListener mListener;
    private String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
//    private Drawable mDrawable;
    public CalendarAdapter(Context context, ArrayList<Date> itemList, OnCalendarItemSelectListener listener,LinkedHashMap<String,String> yearCalendar) {
        this.mContext = context;
        this.itemList = itemList;
         this.mYearCalendar=yearCalendar;
        mCalendar = Calendar.getInstance();
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
        String day_key=String.valueOf(cal.get(Calendar.YEAR)+"_"+String.valueOf(cal.get(Calendar.DAY_OF_YEAR)));
        if(day==1 && !isCurrentMonth){
            isCurrentMonth =true;
        }
        if (year == mCalendar.get(Calendar.YEAR)) {     //current year
            if (month == mCalendar.get(Calendar.MONTH) && isCurrentMonth) {       //current month
                if (day == mCalendar.get(Calendar.DAY_OF_MONTH)) {      //today
                    if(!isActiveCell) {
                        isActiveCell = true;
                        startCellPosition = position;
                        mListener.onItemSelect(position, startCellPosition,endCellPosition,day_key);
                    }
                    holder.dateView.setTextColor(0xff0066ff);
                }
            }else { //outside current month
                if(day==1){
                    if(!isActiveCell) {
                        isActiveCell = true;
                        startCellPosition = position;
                        mListener.onItemSelect(position, startCellPosition,endCellPosition,day_key);
                    }else {
                        endCellPosition=position-1;
                        isActiveCell =false;
                    }
                }

            }
            if (day == 1) {
                holder.monthView.setText(monthNames[cal.get(Calendar.MONTH)]);
                holder.monthView.setVisibility(View.VISIBLE);

            } else {
                holder.monthView.setVisibility(View.GONE);
            }
        }else {  //outside current year

            if (day == 1) {
                holder.monthView.setText(monthNames[cal.get(Calendar.MONTH)]);
                holder.monthView.setVisibility(View.VISIBLE);

                if(!isActiveCell) {
                    isActiveCell = true;
                    startCellPosition = position;
                    mListener.onItemSelect(position, startCellPosition,endCellPosition,day_key);

                }else {
                    endCellPosition=position-1;
                    isActiveCell = false;
                }

            } else {
                holder.monthView.setVisibility(View.GONE);
            }
        }
        if (isActiveCell) {
            holder.dotView.setVisibility(View.VISIBLE);
            holder.dateView.setTextColor(0xff000000);
        } else {
            holder.dotView.setVisibility(View.INVISIBLE);
            holder.dateView.setTextColor(0xffcccccc);
        }

        String key=mYearCalendar.get(day_key);
        if(key!=null){
            String[] keyArray=key.split(",");
            holder.dotView.removeAllViews();
            for (int j=0;j<keyArray.length;j++){
                View dot=inflater.inflate(R.layout.dot_view,holder.dotView,false);

                Drawable  mDrawable = mContext.getResources().getDrawable(R.drawable.bg_button_blue);
                mDrawable.setColorFilter(new
                        PorterDuffColorFilter(Utils.getSubjectColor(Integer.valueOf(keyArray[j])), PorterDuff.Mode.SRC_IN));
                dot.setBackgroundDrawable(mDrawable);
                holder.dotView.addView(dot);
            }
        }
        holder.dateView.setTag(key);
        holder.dateView.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        if (position==selectedPosition){
            holder.view.setCardBackgroundColor(0xffcccccc);
            mListener.onItemSelect(position, startCellPosition, endCellPosition, day_key);
        }else {
            holder.view.setCardBackgroundColor(0xffffffff);
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
        public CardView view;
        public CalendarViewHolder(View itemView) {
            super(itemView);
            dotView =  (LinearLayout)itemView.findViewById(R.id.calendar_dot_view);
            monthView = (TextView) itemView.findViewById(R.id.txt_month);
            dateView = (TextView) itemView.findViewById(R.id.txt_date);
            view=(CardView)itemView.findViewById(R.id.calendar_card_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String key=(String) (v.findViewById(R.id.txt_date)).getTag();
            if(key!=null) {
                selectedPosition = getLayoutPosition();
                isCurrentMonth = false;
                notifyDataSetChanged();
            }
            mListener.onItemSelect(getLayoutPosition(), startCellPosition, endCellPosition, key);

        }
    }

    public interface OnCalendarItemSelectListener {
        public void onItemSelect(int position,int startPosition,int endPosition,String itemKey);
    }
}
