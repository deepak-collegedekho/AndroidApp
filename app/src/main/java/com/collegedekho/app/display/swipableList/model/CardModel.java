/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image_new and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.collegedekho.app.display.swipableList.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.collegedekho.app.entities.Institute;

public class CardModel {

	private String   title;
	private String   description;
	private Drawable cardImageDrawable;
	private Drawable cardLikeImageDrawable;
	private Drawable cardDislikeImageDrawable;
	private Institute institute;
	private int mTag;

    private OnCardDismissedListener mOnCardDismissedListener = null;
    private OnClickListener mOnClickListener = null;
    private View.OnTouchListener mOnTouchListener = null;

    public interface OnCardDismissedListener {
        void onLike(CardModel model);
        void onDislike(CardModel model);
		void onUpSwipe(CardModel model);
    }

    public interface OnClickListener {
        void OnClickListener();
    }

    public interface onTouchListener{
        void OnTouchListener();
    }

	public CardModel() {
		this(null, null, (Drawable)null);
	}

    public CardModel(Institute institute, Context context) {
        this.institute = institute;
    }

    public CardModel(String title, String description, Drawable cardImage) {
		this.title = title;
		this.description = description;
		this.cardImageDrawable = cardImage;
	}

	public CardModel(String title, String description, Bitmap cardImage) {
		this.title = title;
		this.description = description;
		this.cardImageDrawable = new BitmapDrawable(null, cardImage);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Drawable getCardImageDrawable() {
		return cardImageDrawable;
	}

	public void setCardImageDrawable(Drawable cardImageDrawable) {
		this.cardImageDrawable = cardImageDrawable;
	}

	public Drawable getCardLikeImageDrawable() {
		return cardLikeImageDrawable;
	}

	public void setCardLikeImageDrawable(Drawable cardLikeImageDrawable) {
		this.cardLikeImageDrawable = cardLikeImageDrawable;
	}

	public Drawable getCardDislikeImageDrawable() {
		return cardDislikeImageDrawable;
	}

	public void setCardDislikeImageDrawable(Drawable cardDislikeImageDrawable) {
		this.cardDislikeImageDrawable = cardDislikeImageDrawable;
	}

    public void setOnCardDismissedListener( OnCardDismissedListener listener ) {
        this.mOnCardDismissedListener = listener;
    }

    public OnCardDismissedListener getOnCardDismissedListener() {
       return this.mOnCardDismissedListener;
    }

    public void setOnClickListener( OnClickListener listener ) {
        this.mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public View.OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    public void setOnTouchListener(View.OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

	public int getTag() {
		return mTag;
	}

	public void setTag(int mTag) {
		this.mTag = mTag;
	}
}
