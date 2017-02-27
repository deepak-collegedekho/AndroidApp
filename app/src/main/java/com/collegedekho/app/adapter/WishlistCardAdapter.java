package com.collegedekho.app.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.FadeInImageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public final class WishlistCardAdapter {

    private final ImageLoader mImageLoader;
    private boolean mLoadingNext;
    private Context mContext;
    private Institute mInstitute;
    private String examTag = "";
    private TextView mfacilityText;
    private View mCardPeekView;
    private int cardCategory;
    private RecyclerView mFacilitiesRecycler;
    private int mFacilitiesViewHeight;
    private int mMarginHorizontal;
    private int mMarginVertical;

    public WishlistCardAdapter(Context context) {
        this.mImageLoader = MySingleton.getInstance(context).getImageLoader();
        this.mContext = context;

        this.mFacilitiesViewHeight = (int) getContext().getResources().getDimension(R.dimen.m40dp);
        this.mMarginHorizontal = (int) getContext().getResources().getDimension(R.dimen.m10dp);
        this.mMarginVertical = (int) getContext().getResources().getDimension(R.dimen.m6dp);
    }

    public void ShowInstituteCard(Institute institute, View peekview) {
        this.mInstitute = institute;
        this.mCardPeekView = peekview;

        this.mResetViews();
        this.mInstantiateView();

        //showing the info panel first
        this.ToggleWishlistInstituteDetails();
    }

    private void mInstantiateView() {
        setVectorDrawable(this.mCardPeekView);

        this.mfacilityText = (TextView) (this.mCardPeekView.findViewById(R.id.wishlist_institute_facility_toast));

        this.mParseAndPopulateCards();

        if (this.mInstitute.getGroups_exists() == 1)
        {
            //Show Call button number exists
            if (this.mInstitute.getL3_number() != null && !this.mInstitute.getL3_number().isEmpty() && this.mInstitute.getL3_number() != " ")
                this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setVisibility(View.VISIBLE);
            else
                this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setVisibility(View.GONE);

            //Show Apply Now if Partner and not yet applied
            if (!this.mInstitute.is_applied())
                this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_apply_now).setVisibility(View.VISIBLE);
            else
                this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_apply_now).setVisibility(View.GONE);
        }
        else
        {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setVisibility(View.GONE);
            this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_apply_now).setVisibility(View.GONE);
        }
    }

    private void mParseAndPopulateCards() {
        final FadeInImageView fadeInImageView;
        ImageLoader.ImageListener imageListener;

        // set institute name
        try {
            String instituteName= new String(this.mInstitute.getName().getBytes("ISO-8859-1"),"UTF-8");
            ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_title)).setText(instituteName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_title)).setText(this.mInstitute.getName());
        }

        //setting location
        String text = "";
        if (this.mInstitute.getCity_name() != null)
            text += this.mInstitute.getCity_name() + ", ";
        if (this.mInstitute.getState_name() != null)
            text += this.mInstitute.getState_name();

        if (text == "" || text.isEmpty())
            this.mCardPeekView.findViewById(R.id.wishlist_institute_description).setVisibility(View.GONE);
        else {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_description).setVisibility(View.VISIBLE);
            ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_description)).setText(text);
        }

        fadeInImageView = ((FadeInImageView) this.mCardPeekView.findViewById(R.id.wishlist_institute_image));
     /*   if (BitMapHolder.DEFAULT_BANNER != null)
            fadeInImageView.setLocalImageBitmap(BitMapHolder.DEFAULT_BANNER, true);
        else*/
            fadeInImageView.setBackgroundResource(R.drawable.default_banner);

        TextView streamTV = ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_recommended_streams));
        TextView examsTv = (TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_recommended_exams);
        TextView maxSalary = ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_max_sal_val));
        TextView minSalary = ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_min_sal_val));
        TextView avgSalary = ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_avg_sal_val));
        TextView placementPercent = ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_placement_percentage));
        TextView feesText = (TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_fees_range_text);
        TextView applicationStatus = (TextView) this.mCardPeekView.findViewById(R.id.wishlist_txt_interested);
        TextView examsAccepted = (TextView) this.mCardPeekView.findViewById(R.id.wishlist_institute_txt_exam_accepted);
        TextView applicationEndDate = (TextView) this.mCardPeekView.findViewById(R.id.wishlist_txt_month_value);
        double averageSal = 0;
        double maxSal = 0;
        double minSal = 0;
        try {
            int markerMargin = 0;
            int markerHeight = 0;
            float deviceDensity = getContext().getResources().getDimension(R.dimen.m58dp);
            try {
                averageSal = this.mInstitute.getAvg_salary();
                avgSalary.setText(Utils.rupeeFormatter(averageSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }

            try {
                maxSal = this.mInstitute.getMax_salary();
                maxSalary.setText(Utils.rupeeFormatter(maxSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }

            try {
                minSal = this.mInstitute.getMin_salary();
                minSalary.setText(Utils.rupeeFormatter(minSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }
            if (maxSal == 0 && minSal == 0 && averageSal == 0) {
                this.mCardPeekView.findViewById(R.id.wishlist_institute_salary_layout).setVisibility(View.GONE);
            } else {
                this.mCardPeekView.findViewById(R.id.wishlist_institute_salary_layout).setVisibility(View.VISIBLE);
                if (maxSal > 0 && averageSal > 0) {
                    double avgPercent = (averageSal / maxSal) * 100;
                    markerMargin = (int) (avgPercent * deviceDensity) / 100;
                    View averageSalMark = this.mCardPeekView.findViewById(R.id.wishlist_institute_avg_sal_mark);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) averageSalMark.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, markerMargin);
                    averageSalMark.setLayoutParams(layoutParams);
                }
                if (maxSal > 0 && minSal > 0) {
                    double minPercent = (minSal / maxSal) * 100;
                    markerHeight = (int) (minPercent * deviceDensity) / 100;
                    View minSalMark = this.mCardPeekView.findViewById(R.id.wishlist_institute_min_sal_bar);
                    RelativeLayout.LayoutParams minLayoutParams = (RelativeLayout.LayoutParams) minSalMark.getLayoutParams();
                    minLayoutParams.height = markerHeight;
                    minSalMark.setLayoutParams(minLayoutParams);
                }
            }
        } catch (NumberFormatException e) {

        } catch (Exception e) {

        }
        if (this.mInstitute.getPlacement_percentage() != null && !this.mInstitute.getPlacement_percentage().isEmpty()) {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_placement_layout).setVisibility(View.VISIBLE);
            placementPercent.setText("" + this.mInstitute.getPlacement_percentage() + "%");
        } else {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_placement_layout).setVisibility(View.GONE);
//                placementPercent.setText("---");
        }
        ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_applied_count)).setText("" + (this.mInstitute.getUpvotes() + this.mInstitute.getShortlist_count()));
        mFacilitiesRecycler = (RecyclerView) this.mCardPeekView.findViewById(R.id.wishlist_institute_facilities_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
        applicationStatus.setText(this.mInstitute.getApplication_status());
        mFacilitiesRecycler.setLayoutManager(layoutManager);
        View seeAllButton = this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_layout);
        final ArrayList<Facility> facilityArrayList = this.mInstitute.getFacilities();
        this.mResetFacilityView((ViewGroup) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_card_layout));
        if (facilityArrayList != null && !facilityArrayList.isEmpty()) {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_facilities_layout).setVisibility(View.VISIBLE);
            mFacilitiesRecycler.setAdapter(new FacilitiesAdapter(facilityArrayList, this.mfacilityText));
            if (facilityArrayList.size() <= 7) {
                seeAllButton.setVisibility(View.GONE);
            } else {
                seeAllButton.setVisibility(View.VISIBLE);
            }
        } else {
            seeAllButton.setVisibility(View.GONE);
            this.mCardPeekView.findViewById(R.id.wishlist_institute_facilities_layout).setVisibility(View.GONE);
        }

        if (this.mInstitute.getFees() != null && !this.mInstitute.getFees().isEmpty()) {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_salaries_layout).setVisibility(View.VISIBLE);
            feesText.setText("" + this.mInstitute.getFees());
        } else {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_salaries_layout).setVisibility(View.GONE);
        }

        String examsText = "";
        for (String exam : this.mInstitute.getExams()) {
            examsText = examsText + exam + ", ";
        }

        if (!examsText.trim().isEmpty()) {
            examsAccepted.setText(examsText.substring(0, examsText.length() - 2));
        }

        String userExamsText = "";
        for (String exam : this.mInstitute.getUser_exams()) {
            userExamsText = userExamsText + exam + ",";
        }

        //TODO: Check if we have to make it visible in any case
        streamTV.setVisibility(View.GONE);
        //Showing max of two exams here
        if (!userExamsText.trim().isEmpty()) {
            String[] userExams = userExamsText.split(",");
            String examsStr = userExams[0];
            if (userExams.length >= 2) {
                examsStr += ", " + userExams[1];
            }
            if (examsStr != null && !examsStr.trim().isEmpty()) {
                examsTv.setVisibility(View.VISIBLE);
                examsTv.setText("Exam: " + examsStr);
            } else {
                examsTv.setVisibility(View.GONE);
            }
        }else{
             examsTv.setText("Exam: Not Available");
        }

        if (this.mInstitute.getApplication_end_date() != null && !this.mInstitute.getApplication_end_date().trim().isEmpty()) {
            try {
                String[] arr = this.mInstitute.getApplication_end_date().split(" ");
                ((TextView) this.mCardPeekView.findViewById(R.id.wishlist_txt_month)).setText(arr[0]);
                applicationEndDate.setText(arr[1]);
                this.mCardPeekView.findViewById(R.id.wishlist_clock_layout).setVisibility(View.VISIBLE);
                this.mCardPeekView.findViewById(R.id.wishlist_txt_last_application_text).setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.mCardPeekView.findViewById(R.id.wishlist_clock_layout).setVisibility(View.INVISIBLE);
            this.mCardPeekView.findViewById(R.id.wishlist_txt_last_application_text).setVisibility(View.INVISIBLE);
        }

        imageListener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    fadeInImageView.setLocalImageBitmap(response.getBitmap(), false);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        if (this.mInstitute.getImages().get("Banner") != null) {
            mImageLoader.get(this.mInstitute.getImages().get("Banner"), imageListener);
        } else if (this.mInstitute.getImages().get("Primary") != null) {
            mImageLoader.get(this.mInstitute.getImages().get("Primary"), imageListener);
        } else if (this.mInstitute.getImages().get("Student") != null) {
            mImageLoader.get(this.mInstitute.getImages().get("Student"), imageListener);
        } else if (this.mInstitute.getImages().get("Infra") != null) {
            mImageLoader.get(this.mInstitute.getImages().get("Infra"), imageListener);
        } else if (this.mInstitute.getImages().get("Other") != null) {
            mImageLoader.get(this.mInstitute.getImages().get("Other"), imageListener);
        }

        if (this.mInstitute.getL3_number() != null && !this.mInstitute.getL3_number().trim().matches("")) {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setVisibility(View.VISIBLE);
            this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri number = Uri.parse("tel:" + WishlistCardAdapter.this.mInstitute.getL3_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    mContext.startActivity(callIntent);
                }
            });
        } else {
            this.mCardPeekView.findViewById(R.id.wishlist_institute_btn_call_now).setVisibility(View.GONE);
        }
    }

    public void ToggleFacilitiesLayout()
    {
        ViewGroup.LayoutParams layoutParams = mFacilitiesRecycler.getLayoutParams();
        ViewGroup facilitiesView = (ViewGroup) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_card_layout);
        if (layoutParams != null && layoutParams.height == mFacilitiesViewHeight) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(facilitiesView);
                RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                lParams.addRule(RelativeLayout.BELOW, R.id.wishlist_institute_facilities_text);
                lParams.setMargins(mMarginHorizontal, mMarginVertical, mMarginHorizontal, 0);
                (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(-1f);
                mFacilitiesRecycler.setLayoutParams(lParams);
            } else {
                mFacilitiesRecycler.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                final int targetedHeight = mFacilitiesRecycler.getMeasuredHeight();
                mFacilitiesRecycler.getLayoutParams().height = 0;
                mFacilitiesRecycler.setVisibility(View.VISIBLE);
                (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(-1f);
                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        mFacilitiesRecycler.getLayoutParams().height = interpolatedTime == 1
                                ? RelativeLayout.LayoutParams.MATCH_PARENT
                                : (int) (targetedHeight * interpolatedTime);
                        mFacilitiesRecycler.requestLayout();
                    }

                    @Override
                    public boolean willChangeBounds() {
                        return true;
                    }
                };
                animation.setDuration((int) (targetedHeight / mFacilitiesRecycler.getContext().getResources().getDisplayMetrics().density));
                mFacilitiesRecycler.startAnimation(animation);
            }
            ((TextView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_text)).setText("SEE FEWER\nFACILITIES");
        } else {
            final RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mFacilitiesViewHeight);
            lParams.addRule(RelativeLayout.BELOW, R.id.wishlist_institute_facilities_text);
            lParams.setMargins(mMarginHorizontal, mMarginVertical, mMarginHorizontal, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(facilitiesView);
                lParams.addRule(RelativeLayout.BELOW, R.id.wishlist_institute_facilities_text);
                (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(1f);
                mFacilitiesRecycler.setLayoutParams(lParams);
            } else {
                final int initialHeight = mFacilitiesRecycler.getMeasuredHeight();
                Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        if (interpolatedTime == 1) {
                            mFacilitiesRecycler.setLayoutParams(lParams);
                        } else {
                            mFacilitiesRecycler.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                            mFacilitiesRecycler.requestLayout();
                        }
                    }

                    @Override
                    public boolean willChangeBounds() {
                        return true;
                    }
                };
                animation.setDuration((int) (initialHeight / mFacilitiesRecycler.getContext().getResources().getDisplayMetrics().density));
                mFacilitiesRecycler.startAnimation(animation);
            }
            (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(1f);
            ((TextView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_text)).setText("SEE ALL\nFACILITIES");
        }
    }

    public void ToggleWishlistInstituteDetails()
    {
        if (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_overlay_layout).getVisibility() == View.VISIBLE) {
            WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_overlay_layout).setVisibility(View.GONE);
            ((ImageView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_btn_details)).setImageResource(R.drawable.ic_wishlist_information);
        } else {
            WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_overlay_layout).setVisibility(View.VISIBLE);
            ((ImageView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_btn_details)).setImageResource(R.drawable.ic_wish_list_close_info);
        }
    }

    private Drawable drawableLike ;
    private Drawable drawableFees ;
    private Drawable drawablePlacement ;
    private Drawable drawableInfo ;
    private Drawable drawableBubble ;
    private Drawable drawableHeart ;
    private Drawable drawableFacilityArrow ;
    public static Bitmap like_bitmap;
    public static Bitmap placement_bitmap;
    public static Bitmap fees_bitmap;
    public static Bitmap info_bitmap;
    public static Bitmap facilityArrow_bitmap;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setVectorDrawable(View view){

        if(this.drawableLike == null)
            this.drawableLike = ContextCompat.getDrawable(mContext, R.drawable.ic_like);
        if(this.drawableFees == null)
            this.drawableFees = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_fees);
        //if(this.drawablePlacement == null)
        //  this. drawablePlacement = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_placement);
        if(this.drawableInfo == null)
            this. drawableInfo = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_information);
        if(this.drawableBubble == null)
            this. drawableBubble = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_bubble);
        if(this.drawableHeart == null)
            this. drawableHeart = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_heart);
        if(this.drawableFacilityArrow == null)
            this. drawableFacilityArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_arrow_down);

        if(like_bitmap == null)
            like_bitmap   =  Utils.getBitmapDrawable(this.drawableLike);
        // if(placement_bitmap == null)
        //   placement_bitmap  =  Utils.getBitmapDrawable(this.drawablePlacement);
        if(fees_bitmap == null)
            fees_bitmap =  Utils.getBitmapDrawable(this.drawableFees);
        if(info_bitmap == null)
            info_bitmap =  Utils.getBitmapDrawable(this.drawableInfo);
        if(facilityArrow_bitmap == null)
            facilityArrow_bitmap =  Utils.getBitmapDrawable(this.drawableFacilityArrow);

        if(view != null){
            ((ImageView) view.findViewById(R.id.wishlist_institute_fees_range_image)).setImageBitmap(fees_bitmap);
            ((ImageView) view.findViewById(R.id.wishlist_btn_details)).setImageBitmap(info_bitmap);
            (view.findViewById(R.id.wishlist_heart_layout)).setBackground(drawableHeart);
            ((ImageView) view.findViewById(R.id.wishlist_institute_see_all_image)).setImageBitmap(facilityArrow_bitmap);

        }

    }

    public boolean isLoadingNext() {
        return mLoadingNext;
    }

    public void setLoadingNext(boolean mLoadingNext) {
        this.mLoadingNext = mLoadingNext;
    }

    public Context getContext() {
        return mContext;
    }

    class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilitiesViewHolder> {
        ArrayList<Facility> facilitiesList;
        ImageLoader imageLoader;
        TextView facilityText;
        //AnimationSet mAnimationSet = new AnimationSet(false);

        FacilitiesAdapter(ArrayList<Facility> facilities, TextView tempFacilityText) {
            facilitiesList = facilities;
            facilityText = tempFacilityText;
            imageLoader = MySingleton.getInstance(getContext()).getImageLoader();

            /*Animation fadeInAnimation = AnimationUtils.loadAnimation(SimpleCardStackAdapterNew.this.mContext, R.anim.fade_in);
            Animation fadeOutAnimation = AnimationUtils.loadAnimation(SimpleCardStackAdapterNew.this.mContext, R.anim.fade_out);
            mAnimationSet.addAnimation(fadeInAnimation);
            mAnimationSet.addAnimation(fadeOutAnimation);*/
        }

        @Override
        public FacilitiesAdapter.FacilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RelativeLayout imageView = (RelativeLayout) LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.item_facility_40dp, parent, false);
            return new FacilitiesViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(final FacilitiesAdapter.FacilitiesViewHolder holder, int position) {
            //Removing trailing whitespaces
            String facility = facilitiesList.get(holder.getAdapterPosition()).tag;
            facility = facility.replaceFirst("\\s+$", "");

            holder.image.setImageUrl(facilitiesList.get(holder.getAdapterPosition()).image_new, imageLoader);
            holder.image.setTag(facilitiesList.get(holder.getAdapterPosition()).tag);
            holder.text.setText(facility);

            holder.image.setOnHoverListener(new View.OnHoverListener(){

                @Override
                public boolean onHover(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER)
                    {
                        facilityText.clearAnimation();
                        facilityText.setText((String) holder.image.getTag());
                        animateView(facilityText);
                    }

                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return facilitiesList.size();
        }

        class FacilitiesViewHolder extends RecyclerView.ViewHolder {
            NetworkImageView image;
            TextView text;

            public FacilitiesViewHolder(ViewGroup itemView) {
                super(itemView);
                image = (NetworkImageView) itemView.findViewById(R.id.facility_image);
                text = (TextView) itemView.findViewById(R.id.facility_text);
            }
        }

        private void animateView(final View view)
        {
            final ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
            final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                fadeIn.setAutoCancel(true);
                fadeOut.setAutoCancel(true);
            }

            fadeIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.e("SimpleCardStack", "fade in started");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e("SimpleCardStack", "fade in ended");
                    fadeOut.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
            fadeIn.setInterpolator(new LinearInterpolator());


            fadeOut.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.e("SimpleCardStack", "fade out started");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.e("SimpleCardStack", "fade out ended");
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            fadeOut.setInterpolator(new LinearInterpolator());
            fadeOut.setStartDelay(1000);

            fadeIn.start();
        }
    }
    public void setCardCategory(int category){
        this.cardCategory=category;
    }

    public int getCardCategory() {
        return cardCategory;
    }

    private void mResetViews()
    {
        //Making details gone
        WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_overlay_layout).setVisibility(View.GONE);
        ((ImageView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_btn_details)).setImageResource(R.drawable.ic_wishlist_information);
    }

    private void mResetFacilityView(ViewGroup facilitiesView)
    {
        //Resetting facilities recylerview layout to single line layout

        final RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mFacilitiesViewHeight);
        lParams.addRule(RelativeLayout.BELOW, R.id.wishlist_institute_facilities_text);
        lParams.setMargins(mMarginHorizontal, mMarginVertical, mMarginHorizontal, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(facilitiesView);
            lParams.addRule(RelativeLayout.BELOW, R.id.wishlist_institute_facilities_text);
            (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(1f);
            mFacilitiesRecycler.setLayoutParams(lParams);
        } else {
            final int initialHeight = mFacilitiesRecycler.getMeasuredHeight();
            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        mFacilitiesRecycler.setLayoutParams(lParams);
                    } else {
                        mFacilitiesRecycler.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        mFacilitiesRecycler.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            animation.setDuration((int) (initialHeight / mFacilitiesRecycler.getContext().getResources().getDisplayMetrics().density));
            mFacilitiesRecycler.startAnimation(animation);
        }
        (WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_image)).setScaleY(1f);
        ((TextView) WishlistCardAdapter.this.mCardPeekView.findViewById(R.id.wishlist_institute_see_all_text)).setText("SEE ALL\nFACILITIES");
    }
}