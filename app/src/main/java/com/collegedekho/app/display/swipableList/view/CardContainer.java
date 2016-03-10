package com.collegedekho.app.display.swipableList.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.display.swipableList.model.Orientations;
import com.collegedekho.app.resource.Constants;

import java.util.Random;

public class CardContainer extends AdapterView<ListAdapter> {
    public static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private static final double DISORDERED_MAX_ROTATION_RADIANS = Math.PI / 64;
    private int mNumberOfCards = -1;
    private ImageView mLikeImageView;
    private ImageView mDislikeImageView;
    private ImageView mUndecidedImageView;
    private TextView mTextView;
    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            clearStack();
            ensureFull();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            clearStack();
        }
    };
    private final Random mRandom = new Random();
    private final Rect boundsRect = new Rect();
    private final Rect childRect = new Rect();
    private final Matrix mMatrix = new Matrix();


    //TODO: determine max dynamically based on device speed
    private int mMaxVisible = 20;
    private GestureDetector mGestureDetector;
    private int mFlingSlop;
    private Orientations.OrientationType mOrientation;
    private ListAdapter mListAdapter;
    private float mLastTouchX;
    private float mLastTouchY;
    private View mTopCard;
    private int mTouchSlop;
    private int mGravity;
    private int mNextAdapterPosition;
    private boolean mDragging;
    private Context mContext;

    public CardContainer(Context context) {
        super(context);
        mContext = context;
        //setOrientation(Orientations.OrientationType.Disordered);
        setGravity(Gravity.CENTER);
        init();
    }

    public CardContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initFromXml(attrs);
        init();
    }

    public CardContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initFromXml(attrs);
        init();
    }

    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mFlingSlop = viewConfiguration.getScaledMinimumFlingVelocity();
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
    }

    private void initFromXml(AttributeSet attr) {
        TypedArray a = getContext().obtainStyledAttributes(attr,
                R.styleable.CardContainer);

        setGravity(a.getInteger(R.styleable.CardContainer_android_gravity, Gravity.CENTER));
        //int orientation = a.getInteger(R.styleable.CardContainer_orientationType, 1);
        //setOrientation(Orientations.OrientationType.fromIndex(orientation));

        a.recycle();
    }

    @Override
    public ListAdapter getAdapter() {
        return mListAdapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (mListAdapter != null)
            mListAdapter.unregisterDataSetObserver(mDataSetObserver);

        clearStack();
        mTopCard = null;
        mListAdapter = adapter;
        mNextAdapterPosition = 0;
        adapter.registerDataSetObserver(mDataSetObserver);

        ensureFull();
        /*int childCount = getChildCount();
        if (childCount != 0) {
            mTopCard = getChildAt(getChildCount() - 1);
            mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
        }*/
        mNumberOfCards = getAdapter().getCount();
        requestLayout();
    }

    private void ensureFull() {
        ViewGroup.LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Adapter.IGNORE_ITEM_VIEW_TYPE);
        while (mNextAdapterPosition < mListAdapter.getCount() && getChildCount() < mMaxVisible) {

            //View view = mListAdapter.getView(mNextAdapterPosition, null, this);
            //view.setLayerType(LAYER_TYPE_SOFTWARE, null);
            /*if(mOrientation == Orientations.OrientationType.Disordered) {
                view.setRotation(getDisorderedRotation());
            }*/
            addViewInLayout(mListAdapter.getView(mNextAdapterPosition, null, this), 0,lp, false);
            requestLayout();


            mNextAdapterPosition += 1;
        }

        int childCount = getChildCount();
        if (childCount != 0) {
            mTopCard = getChildAt(childCount - 1);
            if (mTopCard != null)
            {
                mAssignUICOmponentsForTopCard();
                mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
            }
        }
    }

    public void clearStack() {
        removeAllViewsInLayout();
        mNextAdapterPosition = 0;
        mTopCard = null;
    }

    public Orientations.OrientationType getOrientation() {
        return mOrientation;
    }

    public void setOrientation(Orientations.OrientationType orientation) {
        if (orientation == null)
            throw new NullPointerException("OrientationType may not be null");
        if(mOrientation != orientation) {
            this.mOrientation = orientation;
            if(orientation == Orientations.OrientationType.Disordered) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.setRotation(getDisorderedRotation());
                }
            }
            else {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.setRotation(0);
                }
            }
            requestLayout();
        }
    }

    private float getDisorderedRotation() {
        return (float) Math.toDegrees(mRandom.nextGaussian() * DISORDERED_MAX_ROTATION_RADIANS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //int requestedWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        //int requestedHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        //int childWidth, childHeight;

        /*if (mOrientation == Orientations.OrientationType.Disordered) {
            int R1, R2;
            if (requestedWidth >= requestedHeight) {
                R1 = requestedHeight;
                R2 = requestedWidth;
            } else {
                R1 = requestedWidth;
                R2 = requestedHeight;
            }
            childWidth = (int) ((R1 * Math.cos(DISORDERED_MAX_ROTATION_RADIANS) - R2 * Math.sin(DISORDERED_MAX_ROTATION_RADIANS)) / Math.cos(2 * DISORDERED_MAX_ROTATION_RADIANS));
            childHeight = (int) ((R2 * Math.cos(DISORDERED_MAX_ROTATION_RADIANS) - R1 * Math.sin(DISORDERED_MAX_ROTATION_RADIANS)) / Math.cos(2 * DISORDERED_MAX_ROTATION_RADIANS));
        } else {
            childWidth = requestedWidth;
            childHeight = requestedHeight;
        }*/

        //childWidth = requestedWidth - 100;
        //childHeight = requestedHeight - 40;

        int childWidthMeasureSpec, childHeightMeasureSpec;
        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - 100, MeasureSpec.AT_MOST);
        childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingLeft() - getPaddingRight() - 40, MeasureSpec.AT_MOST);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        for (int i = 0; i < getChildCount(); i++) {
            boundsRect.set(0, 0, getWidth(), getHeight());

            View view = getChildAt(i);
            int w, h;
            w = view.getMeasuredWidth();
            h = view.getMeasuredHeight();

            Gravity.apply(mGravity, w, h, boundsRect, childRect);
            view.layout(childRect.left, childRect.top, childRect.right, childRect.bottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTopCard == null) {
            return false;
        }
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d("Touch Event", MotionEvent.actionToString(event.getActionMasked()) + " ");
        }
        final int pointerIndex;
        final float x, y;
        final float dx, dy;
        mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
        //Log.e("CardContainer", mTopCard.isHardwareAccelerated() ? "it is Hardware Accelerated" : "it is not Hardware Accelerated");
        //Log.e("CardContainer", mTopCard.toString());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTopCard.getHitRect(childRect);

                pointerIndex = event.getActionIndex();
                x = event.getX(pointerIndex);
                y = event.getY(pointerIndex);

                if (!childRect.contains((int) x, (int) y)) {
                    mTopCard.setLayerType(View.LAYER_TYPE_NONE, null);
                    return false;
                }
                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = event.getPointerId(pointerIndex);


                float[] points = new float[]{x - mTopCard.getLeft(), y - mTopCard.getTop()};
                mTopCard.getMatrix().invert(mMatrix);
                mMatrix.mapPoints(points);
                mTopCard.setPivotX(points[0]);
                mTopCard.setPivotY(points[1]);


                break;
            case MotionEvent.ACTION_MOVE:

                pointerIndex = event.findPointerIndex(mActivePointerId);
                x = event.getX(pointerIndex);
                y = event.getY(pointerIndex);

                dx = x - mLastTouchX;
                dy = y - mLastTouchY;

                /*Log.e("CardContainer", "translationX :" + mTopCard.getTranslationX());
                Log.e("CardContainer", "translationY :" + mTopCard.getTranslationY());*/

                if(mTopCard.getTranslationX() <= -60){
                    //Log.e("CardContainer", "IN translationX <= -60");
                    //this.mfadeInOneFadeOutAllOthers(dislikeImageView.getId());
                    mLikeImageView.setVisibility(GONE);
                    mDislikeImageView.setVisibility(VISIBLE);
                    mUndecidedImageView.setVisibility(GONE);
                } else if(mTopCard.getTranslationX() >= 60){
                    //Log.e("CardContainer", "IN translationX >= 60");
                    //this.mfadeInOneFadeOutAllOthers(likeImageView.getId());
                    mLikeImageView.setVisibility(VISIBLE);
                    mDislikeImageView.setVisibility(GONE);
                    mUndecidedImageView.setVisibility(GONE);
                }
                else if (mTopCard.getTranslationY() <= -50)
                {
                    //Log.e("CardContainer", "IN translationY <= -50");
                    //this.mfadeInOneFadeOutAllOthers(undecidedImageView.getId());
                    mLikeImageView.setVisibility(GONE);
                    mDislikeImageView.setVisibility(GONE);
                    mUndecidedImageView.setVisibility(VISIBLE);
                }
                else if(mTopCard.getTranslationX() >= -40 && mTopCard.getTranslationX() <= 40)
                {
                    //Log.e("CardContainer", "IN translationX() >= -40 && translationX() <= 40");
                    //this.mfadeInOneFadeOutAllOthers(-1);
                    mLikeImageView.setVisibility(GONE);
                    mDislikeImageView.setVisibility(GONE);
                    mUndecidedImageView.setVisibility(GONE);
                }

                if (Math.abs(dx) > mTouchSlop || Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }

                if(!mDragging) {
                    //mTopCard.setLayerType(View.LAYER_TYPE_NONE, null);
                    return true;
                }

                mTopCard.setTranslationX(mTopCard.getTranslationX() + dx);
                mTopCard.setTranslationY(mTopCard.getTranslationY() + dy);

                //mTopCard.setRotation(40 * mTopCard.getTranslationX() / (getWidth() / 2.f));

                mLastTouchX = x;
                mLastTouchY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLikeImageView.setVisibility(GONE);
                mDislikeImageView.setVisibility(GONE);
                mUndecidedImageView.setVisibility(GONE);

                if (!mDragging) {
                    return true;
                }
                mDragging = false;
                mActivePointerId = INVALID_POINTER_ID;
                ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTopCard,
                        PropertyValuesHolder.ofFloat("translationX", 0),
                        PropertyValuesHolder.ofFloat("translationY", 0),
                        //PropertyValuesHolder.ofFloat("rotation", (float) Math.toDegrees(mRandom.nextGaussian() * DISORDERED_MAX_ROTATION_RADIANS)),
                        PropertyValuesHolder.ofFloat("pivotX", mTopCard.getWidth() / 2.f),
                        PropertyValuesHolder.ofFloat("pivotY", mTopCard.getHeight() / 2.f)
                ).setDuration(250);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);

                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);

                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
        }
        //mTopCard.setLayerType(View.LAYER_TYPE_NONE, null);
        return true;
    }

    private void mAssignUICOmponentsForTopCard()
    {
        this.mLikeImageView = (ImageView)mTopCard.findViewById(R.id.like_textview);
        this.mDislikeImageView = (ImageView)mTopCard.findViewById(R.id.dislike_textview);
        this.mUndecidedImageView = (ImageView)mTopCard.findViewById(R.id.decide_later_textview);
        this.mTextView = (TextView) mTopCard.findViewById(R.id.card_recommended_institute_detail);
    }

    private void mfadeInOneFadeOutAllOthers(int viewID)
    {
        switch(viewID)
        {
            case R.id.dislike_textview:
            {
                this.mDislikeImageView.animate().alpha(1.0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mDislikeImageView.setVisibility(VISIBLE);
                this.mLikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mLikeImageView.setVisibility(GONE);
                this.mUndecidedImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mUndecidedImageView.setVisibility(GONE);

                break;
            }
            case R.id.like_textview:
            {
                this.mLikeImageView.animate().alpha(1.0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mLikeImageView.setVisibility(VISIBLE);
                this.mDislikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mDislikeImageView.setVisibility(GONE);
                this.mUndecidedImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mUndecidedImageView.setVisibility(GONE);

                break;
            }
            case R.id.decide_later_textview:
            {
                this.mUndecidedImageView.animate().alpha(1.0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mUndecidedImageView.setVisibility(VISIBLE);
                this.mLikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mLikeImageView.setVisibility(GONE);
                this.mDislikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mDislikeImageView.setVisibility(GONE);

                break;
            }
            default:
            {
                this.mLikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mLikeImageView.setVisibility(GONE);
                this.mDislikeImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mDislikeImageView.setVisibility(GONE);
                this.mUndecidedImageView.animate().alpha(0f).setDuration(Constants.ANIM_SHORTEST_DURATION);
                this.mUndecidedImageView.setVisibility(GONE);

                break;
            }

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mTopCard == null) {
            return false;
        }
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        final int pointerIndex;
        final float x, y;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTopCard.getHitRect(childRect);

                CardModel cardModel = (CardModel)getAdapter().getItem(getChildCount()-1);

                if (cardModel.getOnClickListener() != null) {
                    cardModel.getOnClickListener().OnClickListener();
                }
                pointerIndex = event.getActionIndex();
                x = event.getX(pointerIndex);
                y = event.getY(pointerIndex);

                if (!childRect.contains((int) x, (int) y)) {
                    return false;
                }

                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = event.getPointerId(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = event.findPointerIndex(mActivePointerId);
                x = event.getX(pointerIndex);
                y = event.getY(pointerIndex);
                if (Math.abs(x - mLastTouchX) > mTouchSlop || Math.abs(y - mLastTouchY) > mTouchSlop) {
                    float[] points = new float[]{x - mTopCard.getLeft(), y - mTopCard.getTop()};
                    mTopCard.getMatrix().invert(mMatrix);
                    mMatrix.mapPoints(points);
                    mTopCard.setPivotX(points[0]);
                    mTopCard.setPivotY(points[1]);
                    return true;
                }
        }

        return false;
    }

    @Override
    public View getSelectedView() {
       return  mTopCard;
        //throw new UnsupportedOperationException();
    }

    @Override
    public void setSelection(int position) {
        throw new UnsupportedOperationException();
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        int viewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.viewType = viewType;
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.d("Fling", "Fling with " + velocityX + ", " + velocityY);
            final View topCard = mTopCard;
            float dx = e2.getX() - e1.getX();
            float dy = e2.getY() - e1.getY();
            if (Math.abs(dx) > mTouchSlop &&
                    Math.abs(velocityX) > Math.abs(velocityY) &&
                    Math.abs(velocityX) > mFlingSlop * 3) {
                float targetX = topCard.getX();
                float targetY = topCard.getY();
                long duration = 0;

                boundsRect.set(0 - topCard.getWidth() - 100, 0 - topCard.getHeight() - 100, getWidth() + 100, getHeight() + 100);

                while (boundsRect.contains((int) targetX, (int) targetY)) {
                    targetX += velocityX / 10;
                    targetY += velocityY / 10;
                    duration += 100;
                }

                duration = Math.min(500, duration);

                mTopCard = getChildAt(getChildCount() - 2);

                if(mTopCard != null)
                {
                    mAssignUICOmponentsForTopCard();
                    mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
                }

                CardModel cardModel = (CardModel) getAdapter().getItem(getChildCount() - 1);

                if (cardModel.getOnCardDismissedListener() != null) {
                    if ( targetX > 0 ) {
                        cardModel.getOnCardDismissedListener().onLike();
                    } else {
                        cardModel.getOnCardDismissedListener().onDislike();
                    }
                }

                topCard.animate()
                        .setDuration(duration)
                        .alpha(.75f)
                        .setInterpolator(new LinearInterpolator())
                        .x(targetX)
                        .y(targetY)
                        //.rotation(Math.copySign(45, velocityX))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                removeViewInLayout(topCard);
                                ensureFull();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                onAnimationEnd(animation);
                            }
                        });
                return true;
            }
            else if (Math.abs(dy) > mTouchSlop &&
                    Math.abs(velocityY) > Math.abs(velocityX) &&
                    Math.abs(velocityY) > mFlingSlop * 3) {
                float targetX = topCard.getX();
                float targetY = topCard.getY();
                long duration = 0;

                boundsRect.set(0 - topCard.getWidth() - 100, 0 - topCard.getHeight() - 100, getWidth() + 100, getHeight() + 100);

                while (boundsRect.contains((int) targetX, (int) targetY)) {
                    targetX += velocityX / 10;
                    targetY += velocityY / 10;
                    duration += 100;
                }

                duration = Math.min(500, duration);

                CardModel cardModel = (CardModel)getAdapter().getItem(getChildCount() - 1);

                if (cardModel.getOnCardDismissedListener() != null) {
                    if ( targetY < 0 ) {
                        cardModel.getOnCardDismissedListener().onUpSwipe();
                    }
                    else
                    {
                        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mTopCard,
                                PropertyValuesHolder.ofFloat("translationX", 0),
                                PropertyValuesHolder.ofFloat("translationY", 0),
                                //PropertyValuesHolder.ofFloat("rotation", (float) Math.toDegrees(mRandom.nextGaussian() * DISORDERED_MAX_ROTATION_RADIANS)),
                                PropertyValuesHolder.ofFloat("pivotX", mTopCard.getWidth() / 2.f),
                                PropertyValuesHolder.ofFloat("pivotY", mTopCard.getHeight() / 2.f)
                        ).setDuration(250);
                        animator.setInterpolator(new AccelerateInterpolator());
                        animator.start();

                        return false;
                    }
                }

                mTopCard = getChildAt(getChildCount() - 2);

                if(mTopCard != null)
                {
                    mAssignUICOmponentsForTopCard();
                    mTopCard.setLayerType(LAYER_TYPE_HARDWARE, null);
                }

                topCard.animate()
                        .setDuration(duration)
                        .alpha(.75f)
                        .setInterpolator(new LinearInterpolator())
                        .x(targetX)
                        .y(targetY)
                        //.rotation(Math.copySign(45, velocityY))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                removeViewInLayout(topCard);
                                ensureFull();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                onAnimationEnd(animation);
                            }
                        });

                return true;
            } else
                return false;
        }
    }
}
