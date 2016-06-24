package com.collegedekho.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/** Spinner extension that calls onItemSelected even when the selection is the same as its previous value */
public class ExamYearSpinner extends Spinner {

  public ExamYearSpinner(Context context)
  { super(context); }

  public ExamYearSpinner(Context context, AttributeSet attrs)
  { super(context, attrs); }

  public ExamYearSpinner(Context context, AttributeSet attrs, int defStyle)
  { super(context, attrs, defStyle); }

  @Override
 public void setSelection(int position, boolean animate) {
      boolean sameSelected = position == getSelectedItemPosition();
      super.setSelection(position, animate);
      if (sameSelected) {
          // Spinner does not ic_call_vector the OnItemSelectedListener if the same item is selected, so do it manually now
          if(getOnItemSelectedListener() != null)
          getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
      }
  }


    @Override
    public void setSelection(int position) {

        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {

        // Spinner does not ic_call_vector the OnItemSelectedListener if the same item is selected, so do it manually now
            if(getOnItemSelectedListener() != null)
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}