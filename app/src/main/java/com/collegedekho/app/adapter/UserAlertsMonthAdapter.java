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
import com.collegedekho.app.entities.MyAlertDateDescription;
import com.collegedekho.app.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class UserAlertsMonthAdapter extends RecyclerView.Adapter<UserAlertsMonthAdapter.AlertItemViewHolder> {

    private Context mContext;
    private ArrayList<Date> itemList;
    private int startCellPosition = 0;
    private int endCellPosition = 0;
    private boolean isActiveCell;
    private boolean isCurrentMonth=false;
    LayoutInflater inflater;
    private HashMap<String, Integer> keys = new HashMap<>();
    private HashMap<String, ArrayList<Integer>>codes=new HashMap<>();
    boolean isNotified;
    Calendar mCalendar;
    Calendar cal;
    int selectedPosition=-1;
    private OnCalendarItemSelectListener mListener;
    private String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public UserAlertsMonthAdapter(Context context, ArrayList<Date> itemList, OnCalendarItemSelectListener listener, ArrayList<MyAlertDateDescription> dateDesc) {
        this.mContext = context;
        this.itemList = itemList;
        mCalendar = Calendar.getInstance();
        cal = Calendar.getInstance();
        mListener = listener;
        int size = dateDesc.size();
        for (int i = 0; i < size; i++) {
            if (keys.get(dateDesc.get(i).getDate()) == null) {
                keys.put(dateDesc.get(i).getDate(), 1);
                ArrayList<Integer>codesArray=new ArrayList<>();
                codesArray.add(dateDesc.get(i).getExam_id());
                codes.put(dateDesc.get(i).getDate(),codesArray);
            } else {
                keys.put(dateDesc.get(i).getDate(), keys.get(dateDesc.get(i).getDate()) + 1);
                codes.get(dateDesc.get(i).getDate()).add(dateDesc.get(i).getExam_id());
            }
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isNotified = false;
    }

    @Override
    public AlertItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.calendar_view_card_layout, parent, false);

        return new AlertItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlertItemViewHolder holder, int position) {

        Date date = itemList.get(position);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String mth = month + 1 + "";
        if (mth.length() < 2) {
            mth = "0" + mth;
        }
        String dy = day + "";
        if (dy.length() < 2) {
            dy = "0" + dy;
        }
        String day_key = year + "-" + mth + "-" + dy;

        if (day == 1 && !isCurrentMonth) {
            isCurrentMonth = true;
        } else if (day == 1) {
            isCurrentMonth = false;
            isActiveCell = false;
        }
        if (isCurrentMonth) {
            if (month != mCalendar.get(Calendar.MONTH) || year != mCalendar.get(Calendar.YEAR)) {
                if (day == 1 && !isActiveCell) {
                    isActiveCell = true;
                    startCellPosition = position;
                    mListener.onItemSelect(position, startCellPosition, endCellPosition, day_key);
                } else if (day == 1 && isActiveCell) {
                    isActiveCell = false;
                    endCellPosition = position - 1;
                }
            } else if (month == mCalendar.get(Calendar.MONTH) && year == mCalendar.get(Calendar.YEAR)) {
                if (day == mCalendar.get(Calendar.DAY_OF_MONTH) && !isActiveCell) {
                    isActiveCell = true;
                    startCellPosition = position;
                    mListener.onItemSelect(position, startCellPosition, endCellPosition, day_key);
                } else if (day == 1 && isActiveCell) {
                    isActiveCell = false;
                    endCellPosition = position - 1;
                }
            }
        }

        if (year == mCalendar.get(Calendar.YEAR)) {     //current year
            if (month == mCalendar.get(Calendar.MONTH) && isCurrentMonth) {       //current month
                if (day == mCalendar.get(Calendar.DAY_OF_MONTH)) {      //today
                    holder.dateView.setTextColor(0xff0066ff);
                }
            }

        }

        if (day == 1) {
            holder.monthView.setText(monthNames[cal.get(Calendar.MONTH)]);
            holder.monthView.setVisibility(View.VISIBLE);

        } else {
            holder.monthView.setVisibility(View.GONE);
        }

        if (isActiveCell) {
            holder.dotView.setVisibility(View.VISIBLE);
            holder.dateView.setTextColor(0xff000000);
        } else {
            holder.dotView.setVisibility(View.INVISIBLE);
            holder.dateView.setTextColor(0xffcccccc);
        }

        int count = 0;
        if (keys.get(day_key) != null) {
            count = keys.get(day_key);
            holder.dateView.setTag(day_key);
            if (!isNotified) {
                selectedPosition = position;
                isNotified = true;
            }
        }
        holder.dotView.removeAllViews();
        for (int j = 0; j < count; j++) {
            try {
                View dot = inflater.inflate(R.layout.layout_dot_view, holder.dotView, false);

                Drawable mDrawable = mContext.getResources().getDrawable(R.drawable.bg_button_blue);
                mDrawable.setColorFilter(new
                        PorterDuffColorFilter(Utils.getSubjectColor(codes.get(day_key).get(j)), PorterDuff.Mode.SRC_IN));
                dot.setBackgroundDrawable(mDrawable);
                holder.dotView.addView(dot);
                holder.dotView.setVisibility(View.VISIBLE);
            } catch (Exception e) {

            }
        }
        if (position == selectedPosition) {
            holder.view.setCardBackgroundColor(0xffcccccc);
            mListener.onItemSelect(position, startCellPosition, endCellPosition, day_key);
        } else {
            holder.view.setCardBackgroundColor(0xffffffff);
        }
        holder.dateView.setTag(day_key);
        String dateString = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        holder.dateView.setText(dateString);

        String description = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)
                + " " + monthNames[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
        if(isActiveCell) {
            if (position != selectedPosition) {
                if (count > 0) {
                    description = description + " has " + count + " events click to see details of events.";
                } else {
                    description = "No event on " + description;
                }
            } else {
                description = "selected date " + description;
            }
            holder.mDateContainerView.setContentDescription(description);
        } else {
            holder.mDateContainerView.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class AlertItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout dotView;
        public TextView monthView;
        public TextView dateView;
        public CardView view;
        public View mDateContainerView;

        public AlertItemViewHolder(View itemView) {
            super(itemView);
            dotView = (LinearLayout) itemView.findViewById(R.id.calendar_dot_view);
            monthView = (TextView) itemView.findViewById(R.id.txt_month);
            dateView = (TextView) itemView.findViewById(R.id.txt_date);
            view=(CardView) itemView.findViewById(R.id.calendar_card_view);
            itemView.setOnClickListener(this);
            mDateContainerView = itemView;
        }

        @Override
        public void onClick(View v) {
            String key = (String) (v.findViewById(R.id.txt_date)).getTag();
//            mListener.onItemSelect(getLayoutPosition(), startCellPosition, endCellPosition, key);
            if(key!=null && keys.containsKey(key)) {
                selectedPosition = getLayoutPosition();
                isCurrentMonth=false;
                notifyDataSetChanged();
            }
        }
    }

    public interface OnCalendarItemSelectListener {
        void onItemSelect(int position, int startPosition, int endPosition, String itemKey);
    }
}
