package com.collegedekho.app.display.swipableList.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.collegedekho.app.display.swipableList.model.CardModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public abstract class CardStackAdapter extends BaseCardStackAdapter {
	private final Context mContext;

	/**
	 * Lock used to modify the content of {@link #mData}. Any write operation
	 * performed on the deque should be synchronized on this lock.
	 */
//	private final Object mLock = new Object();
	private Vector<CardModel> mData;

    private boolean mShouldFillCardBackground = false;

    public CardStackAdapter(Context context) {
		mContext = context;
		mData = new Vector<>();
	}

	public CardStackAdapter(Context context, Collection<? extends CardModel> items) {
		mContext = context;
		mData = new Vector<>(items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout wrapper = (FrameLayout) convertView;
		View cardView;
		if (wrapper == null) {
			cardView = getCardView(position, getCardModel(position), null, parent);
		} else {
			cardView = wrapper.getChildAt(0);
		}

		return cardView;
	}

	protected abstract View getCardView(int position, CardModel model, View convertView, ViewGroup parent);

    public void setShouldFillCardBackground(boolean isShouldFillCardBackground) {
        this.mShouldFillCardBackground = isShouldFillCardBackground;
    }

    public boolean shouldFillCardBackground() {
        return mShouldFillCardBackground;
    }

    public void add(CardModel item) {
			mData.add(item);
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<CardModel> item) {
			mData.addAll(item);
		notifyDataSetChanged();
	}

	public CardModel pop() {
		CardModel model;
			model = mData.remove(mData.size() - 1);
		notifyDataSetChanged();
		return model;
	}

	public void clear()
	{
			mData.clear();
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		return getCardModel(position);
	}

	public CardModel getCardModel(int position) {
			return mData.get(mData.size() - 1 - position);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	public Context getContext() {
		return mContext;
	}
}
