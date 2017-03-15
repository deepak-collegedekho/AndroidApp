package com.collegedekho.app.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.collegedekho.app.entities.Institute;

import java.util.List;

/**
 * Created by harshvardhan on 15/03/17.
 */

public class MyInstituteListDiffCallback extends DiffUtil.Callback {
    List<Institute> oldInstitutes;
    List<Institute> newInstitutes;

    public MyInstituteListDiffCallback(List<Institute> newInstitutes, List<Institute> oldInstitutes) {
        this.newInstitutes = newInstitutes;
        this.oldInstitutes = oldInstitutes;
    }


    @Override
    public int getOldListSize() {
        return oldInstitutes.size();
    }

    @Override
    public int getNewListSize() {
        return newInstitutes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldInstitutes.get(oldItemPosition).getId() == newInstitutes.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldInstitutes.get(oldItemPosition).equals(newInstitutes.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }}
