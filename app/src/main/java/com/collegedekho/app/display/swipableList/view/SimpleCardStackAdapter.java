package com.collegedekho.app.display.swipableList.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.FadeInImageView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

public final class SimpleCardStackAdapter extends BaseAdapter {

    private final ImageLoader imageLoader;
    private OnCDRecommendedAdapterInterface mListener;
    private boolean mLoadingNext;
    private boolean isLast;
    private Context mContext;
    private Vector<CardModel> mData;
    private TextView mFacilityText;
    private Drawable backgroundBorder;

    // drawables
    private Drawable drawableLike ;
    private Drawable drawableFees ;
    private Drawable drawablePlacement ;
    private Drawable drawableInfo ;
    private Drawable drawableBubble ;
    private Drawable drawableHeart ;
    private Drawable drawableFacilityArrow ;
    public static Bitmap like_bitmap;
    //public static Bitmap placement_bitmap;
    public static Bitmap fees_bitmap;
    public static Bitmap info_bitmap;
    public static Bitmap facilityArrow_bitmap;


    //private int cardCategory;
    public SimpleCardStackAdapter(Context context, OnCDRecommendedAdapterInterface listener, int cardCategory) {
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
        this.mListener = listener;
        this.mContext = context;
        this.mData = new Vector<>();
        if(cardCategory == Constants.CDRecommendedInstituteType.UNBAISED.ordinal()){
            backgroundBorder = ContextCompat.getDrawable(mContext, R.drawable.bg_rounded_blue_border_box);
        } else if(cardCategory == Constants.CDRecommendedInstituteType.BUZZLIST.ordinal()){
            backgroundBorder = ContextCompat.getDrawable(mContext, R.drawable.bg_rounded_orange_border_box);
        }
            this.drawableLike = ContextCompat.getDrawable(mContext, R.drawable.ic_like);
            this.drawableFees = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_fees);
        //  this. drawablePlacement = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_placement);
            this. drawableInfo = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_information);
            this. drawableBubble = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_bubble);
            this. drawableHeart = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_heart);
            this. drawableFacilityArrow = ContextCompat.getDrawable(mContext, R.drawable.ic_wishlist_arrow_down);


        // this.cardCategory =cardCategory;
    }

    public void addAll(ArrayList<CardModel> item) {
        mData.addAll(item);
        notifyDataSetChanged();
    }

    public void setDrawableBorderBackground(Drawable drawable){
        this.backgroundBorder = drawable;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.wishlist_card_layout, parent, false);
        }

        convertView.findViewById(R.id.institute_card_layout).setBackground(backgroundBorder);
        convertView.setTag(position);

        setVectorDrawable(convertView);

        this.mFacilityText = (TextView) (convertView.findViewById(R.id.facility_toast));

        CardModel model = getCardModel(position);
        model.setTag(mData.size()-1-position);

        this.mParseAndPopulateCards(model, convertView);

        //card_recommended_institute_container
        (convertView.findViewById(R.id.share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView applyNowTV = (TextView) convertView.findViewById(R.id.btn_apply_now);
        applyNowTV.setTag(position);
        applyNowTV.setText("APPLY NOW");

        (convertView.findViewById(R.id.btn_apply_now)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Institute institute = ((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute();
               /* if (cardCategory == Constants.CDRecommendedInstituteType.UNBAISED.ordinal()
                        || cardCategory == Constants.CDRecommendedInstituteType.UNDECIDED.ordinal()){*/
                mListener.OnAppliedInstitute(institute);
               /* } else if (cardCategory == Constants.CDRecommendedInstituteType.SHORTLISTED.ordinal()) {
                    mListener.onRemoveShortlisted(institute);
                }*/
            }
        });
        (convertView.findViewById(R.id.header_view)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
            }
        });

        model.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike(CardModel model) {
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext() && SimpleCardStackAdapter.this.mListener.isLast()) {
                    SimpleCardStackAdapter.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                }else {
                    SimpleCardStackAdapter.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), false);
                }
//                SimpleCardStackAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onDislike(CardModel model) {
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext() && SimpleCardStackAdapter.this.mListener.isLast()) {
                    SimpleCardStackAdapter.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapter.this.getItem(getCount() - 1 - position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                } else {
                    SimpleCardStackAdapter.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapter.this.getItem(getCount() - 1 - position)).getInstitute(), false);
                }
//                SimpleCardStackAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onUpSwipe(CardModel model) {
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext() && SimpleCardStackAdapter.this.mListener.isLast()) {
                    SimpleCardStackAdapter.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                }else {
                    SimpleCardStackAdapter.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), false);
                }
//                SimpleCardStackAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }


    private void mParseAndPopulateCards(final CardModel model, final View convertView) {

        Log.e("getView "," step 1 time is "+System.currentTimeMillis());
        final FadeInImageView fadeInImageView;
        ImageLoader.ImageListener imageListener;
        final Institute institute = model.getInstitute();
        // set institute name

        try {
            String instituteName= new String(institute.getName().getBytes("ISO-8859-1"),"UTF-8");
            ((TextView) convertView.findViewById(R.id.title)).setText(instituteName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) convertView.findViewById(R.id.title)).setText(institute.getName());
        }

        //setting location
        String text = "";
        if (model.getInstitute().getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (model.getInstitute().getState_name() != null)
            text += institute.getState_name();

        if (text == "" || text.isEmpty())
            convertView.findViewById(R.id.description).setVisibility(View.GONE);
        else
            ((TextView) convertView.findViewById(R.id.description)).setText(text);


        fadeInImageView = ((FadeInImageView) convertView.findViewById(R.id.institute_image));
       /* if (BitMapHolder.DEFAULT_BANNER != null)
            fadeInImageView.setLocalImageBitmap(BitMapHolder.DEFAULT_BANNER, true);
        else*/
        fadeInImageView.setBackgroundResource(R.drawable.default_banner);


        TextView streamTV = ((TextView) convertView.findViewById(R.id.recommended_streams));
        TextView examsTv   =(TextView)convertView.findViewById(R.id.recommended_exams);
        TextView maxSalary = ((TextView) convertView.findViewById(R.id.max_sal_val));
        TextView minSalary = ((TextView) convertView.findViewById(R.id.min_sal_val));
        TextView avgSalary = ((TextView) convertView.findViewById(R.id.avg_sal_val));
        TextView placementPercent = ((TextView) convertView.findViewById(R.id.placement_percentage));
        TextView feesText=(TextView)convertView.findViewById(R.id.fees_range_text);
        TextView applicationStatus=(TextView)convertView.findViewById(R.id.txt_interested);
        TextView examsAccepted=(TextView)convertView.findViewById(R.id.txt_exam_accepted);
        TextView applicationEndDate=(TextView)convertView.findViewById(R.id.txt_month_value);
        double averageSal=0;
        double maxSal=0;
        double minSal=0;
        try {
            int markerMargin = 0;
            int markerHeight = 0;
            float deviceDensity = getContext().getResources().getDimension(R.dimen.m50dp);
            try {
                averageSal = institute.getAvg_salary();
                avgSalary.setText(Utils.rupeeFormatter(averageSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }

            try {
                maxSal = institute.getMax_salary();
                maxSalary.setText(Utils.rupeeFormatter(maxSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }

            try {
                minSal = institute.getMin_salary();
                minSalary.setText(Utils.rupeeFormatter(minSal));
            } catch (NumberFormatException ne) {

            } catch (Exception e) {

            }
            if(maxSal==0&&minSal==0&&averageSal==0){
                convertView.findViewById(R.id.salary_layout).setVisibility(View.GONE);
            }else {

                if (maxSal > 0 && averageSal > 0) {
                    double avgPercent = (averageSal / maxSal) * 100;
                    markerMargin = (int) (avgPercent *  deviceDensity) / 100;
                    View averageSalMark = convertView.findViewById(R.id.avg_sal_mark);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) averageSalMark.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, markerMargin);
                    averageSalMark.setLayoutParams(layoutParams);
                }
                if (maxSal > 0 && minSal > 0) {
                    double minPercent = (minSal / maxSal) * 100;
                    markerHeight = (int) (minPercent *  deviceDensity) / 100;
                    View minSalMark = convertView.findViewById(R.id.min_sal_bar);
                    RelativeLayout.LayoutParams minLayoutParams = (RelativeLayout.LayoutParams) minSalMark.getLayoutParams();
                    minLayoutParams.height = markerHeight;
                    minSalMark.setLayoutParams(minLayoutParams);
                }
            }
        } catch (NumberFormatException e) {

        } catch (Exception e) {

        }
        if (institute.getPlacement_percentage() != null && !institute.getPlacement_percentage().isEmpty()) {
            placementPercent.setText("" + institute.getPlacement_percentage() + "%");
        }else {
            convertView.findViewById(R.id.placement_layout).setVisibility(View.GONE);
//                placementPercent.setText("---");
        }
        ((TextView)convertView.findViewById(R.id.applied_count)).setText(""+(institute.getUpvotes()+institute.getShortlist_count()));
        final RecyclerView facilitiesRecycler = (RecyclerView) convertView.findViewById(R.id.facilities_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
        applicationStatus.setText(institute.getApplication_status());
        facilitiesRecycler.setLayoutManager(layoutManager);
        View seeAllButton = convertView.findViewById(R.id.see_all_layout);
        final ArrayList<Facility> facilityArrayList = institute.getFacilities();
        if (facilityArrayList != null && !facilityArrayList.isEmpty()) {
            facilitiesRecycler.setAdapter(new FacilitiesAdapter(facilityArrayList, this.mFacilityText));
            if (facilityArrayList.size() > 7) {
                seeAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewGroup.LayoutParams layoutParams = facilitiesRecycler.getLayoutParams();
                        final int height = (int) getContext().getResources().getDimension(R.dimen.m33dp);
                        int marginLeft = (int) getContext().getResources().getDimension(R.dimen.m10dp);
                        int marginTop = (int) getContext().getResources().getDimension(R.dimen.m4dp);
                        ViewGroup facilitiesView = (ViewGroup) convertView.findViewById(R.id.institute_card_layout);
                        if (layoutParams != null && layoutParams.height == height) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                TransitionManager.beginDelayedTransition(facilitiesView);
                                RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                lParams.addRule(RelativeLayout.BELOW, R.id.facilities_text);
                                lParams.setMargins(marginLeft, marginTop, marginLeft, 0);
                                (convertView.findViewById(R.id.see_all_image)).setScaleY(-1f);
                                facilitiesRecycler.setLayoutParams(lParams);
                            } else {
                                facilitiesRecycler.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                final int targetedHeight = facilitiesRecycler.getMeasuredHeight();
                                facilitiesRecycler.getLayoutParams().height = 0;
                                facilitiesRecycler.setVisibility(View.VISIBLE);
                                (convertView.findViewById(R.id.see_all_image)).setScaleY(-1f);
                                Animation animation = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        facilitiesRecycler.getLayoutParams().height = interpolatedTime == 1
                                                ? RelativeLayout.LayoutParams.MATCH_PARENT
                                                : (int) (targetedHeight * interpolatedTime);
                                        facilitiesRecycler.requestLayout();
                                    }

                                    @Override
                                    public boolean willChangeBounds() {
                                        return true;
                                    }
                                };
                                animation.setDuration((int) (targetedHeight / facilitiesRecycler.getContext().getResources().getDisplayMetrics().density));
                                facilitiesRecycler.startAnimation(animation);
                            }
                            ((TextView) convertView.findViewById(R.id.see_all_text)).setText("SEE FEWER\nFACILITIES");
                        } else {
                            final RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
                            lParams.addRule(RelativeLayout.BELOW, R.id.facilities_text);
                            lParams.setMargins(marginLeft, marginTop, marginLeft, 0);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                TransitionManager.beginDelayedTransition(facilitiesView);
                                lParams.addRule(RelativeLayout.BELOW, R.id.facilities_text);
                                (convertView.findViewById(R.id.see_all_image)).setScaleY(1f);
                                facilitiesRecycler.setLayoutParams(lParams);
                            } else {
                                final int initialHeight = facilitiesRecycler.getMeasuredHeight();
                                Animation animation = new Animation() {
                                    @Override
                                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                                        if (interpolatedTime == 1) {
                                            facilitiesRecycler.setLayoutParams(lParams);
                                        } else {
                                            facilitiesRecycler.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                                            facilitiesRecycler.requestLayout();
                                        }
                                    }

                                    @Override
                                    public boolean willChangeBounds() {
                                        return true;
                                    }
                                };
                                animation.setDuration((int) (initialHeight / facilitiesRecycler.getContext().getResources().getDisplayMetrics().density));
                                facilitiesRecycler.startAnimation(animation);
                            }
                            (convertView.findViewById(R.id.see_all_image)).setScaleY(1f);
                            ((TextView) convertView.findViewById(R.id.see_all_text)).setText("SEE ALL\nFACILITIES");
                        }
                    }
                });
            } else {
                seeAllButton.setVisibility(View.INVISIBLE);
            }
        }else {
            seeAllButton.setVisibility(View.INVISIBLE);
            convertView.findViewById(R.id.facilities_layout).setVisibility(View.GONE);
        }

        if (institute.getFees() != null && !institute.getFees().isEmpty()) {
            feesText.setText("" + institute.getFees());
        }else {
            convertView.findViewById(R.id.salaries_layout).setVisibility(View.GONE);
        }

        String examsText="";
        for (String exam:institute.getExams()){
            examsText = examsText + exam + ", ";
        }

        if(!examsText.trim().isEmpty()) {
            examsAccepted.setText(examsText.substring(0, examsText.length() - 2));
        }

        String userExamsText="";
        for (String exam:institute.getUser_exams()){
            userExamsText=userExamsText+exam+",";
        }

        streamTV.setVisibility(View.GONE);
        if(!userExamsText.trim().isEmpty()) {
            String[] userExams=userExamsText.split(",");
            String examsStr=userExams[0];
            if(userExams.length>=2){
                examsStr += ", " + userExams[1];
            }
            if(examsStr!=null && !examsStr.trim().isEmpty()) {
                examsTv.setText("Exam: " + examsStr);
            }else {
                examsTv.setText("Exam: Not Available");
            }
        }else{
            examsTv.setText("Exam: Not Available");
        }

        if(institute.getApplication_end_date()!=null && !institute.getApplication_end_date().trim().isEmpty()){
            try {
                String[] arr=institute.getApplication_end_date().split(" ");
                ((TextView)convertView.findViewById(R.id.txt_month)).setText(arr[0]);
                applicationEndDate.setText(arr[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            convertView.findViewById(R.id.clock_layout).setVisibility(View.INVISIBLE);
            convertView.findViewById(R.id.txt_last_application_text).setVisibility(View.INVISIBLE);
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

        if (institute.getImages().get("Banner") != null) {
            imageLoader.get(institute.getImages().get("Banner"), imageListener);
        } else if (institute.getImages().get("Primary") != null) {
            imageLoader.get(institute.getImages().get("Primary"), imageListener);
        } else if (institute.getImages().get("Student") != null) {
            imageLoader.get(institute.getImages().get("Student"), imageListener);
        } else if (institute.getImages().get("Infra") != null) {
            imageLoader.get(institute.getImages().get("Infra"), imageListener);
        } else if (institute.getImages().get("Other") != null) {
            imageLoader.get(institute.getImages().get("Other"), imageListener);
        }

        if (institute.getL3_number() != null && !institute.getL3_number().trim().matches("")) {
            convertView.findViewById(R.id.btn_call_now).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri number = Uri.parse("tel:" + institute.getL3_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    mContext.startActivity(callIntent);
                }
            });
        }else {
            convertView.findViewById(R.id.btn_call_now).setVisibility(View.GONE);
        }

        convertView.findViewById(R.id.btn_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(convertView.findViewById(R.id.overlay_layout).getVisibility()==View.VISIBLE){
                    convertView.findViewById(R.id.overlay_layout).setVisibility(View.GONE);
                    ((ImageView)convertView.findViewById(R.id.btn_details)).setImageResource(R.drawable.ic_wishlist_information);
                }else{
                    convertView.findViewById(R.id.overlay_layout).setVisibility(View.VISIBLE);
                    ((ImageView)convertView.findViewById(R.id.btn_details)).setImageResource(R.drawable.ic_wish_list_close_info);
                }
            }
        });
    }



    private void setVectorDrawable(View view){


        if(like_bitmap == null)
            like_bitmap   =  Utils.getBitmapDrawable(this.drawableLike);
        //if(placement_bitmap == null)
        //  placement_bitmap  =  Utils.getBitmapDrawable(this.drawablePlacement);
        if(fees_bitmap == null)
            fees_bitmap =  Utils.getBitmapDrawable(this.drawableFees);
        if(info_bitmap == null)
            info_bitmap =  Utils.getBitmapDrawable(this.drawableInfo);
        if(facilityArrow_bitmap == null)
            facilityArrow_bitmap =  Utils.getBitmapDrawable(this.drawableFacilityArrow);


        if(view != null){
            // ((ImageView) view.findViewById(R.id.placement_image)).setImageBitmap(placement_bitmap);
            ((ImageView) view.findViewById(R.id.fees_range_image)).setImageBitmap(fees_bitmap);
            ((ImageView) view.findViewById(R.id.btn_details)).setImageBitmap(info_bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                (view.findViewById(R.id.heart_layout)).setBackground(drawableHeart);
            }else{
                (view.findViewById(R.id.heart_layout)).setBackgroundResource(R.drawable.ic_wishlist_heart);
            }
            ((ImageView) view.findViewById(R.id.see_all_image)).setImageBitmap(facilityArrow_bitmap);
        }
    }

    public boolean isLoadingNext() {
        return mLoadingNext;
    }

    public void setLoadingNext(boolean mLoadingNext) {
        this.mLoadingNext = mLoadingNext;
    }

    public interface OnCDRecommendedAdapterInterface {
        void OnLoadNext();
        void OnInstituteSelected(Institute institute);
        void OnInstituteLiked(Institute institute, boolean isLastCard);
        void OnInstituteDislike(Institute institute, boolean isLastCard);
        void OnDecideLater(Institute institute, boolean isLastCard);
        void OnShowMessage(String message);
        void OnAppliedInstitute(Institute institute);
        boolean isLast();

        //void onRemoveShortlisted(Institute institute);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        try{
            return getCardModel(position);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public CardModel getCardModel(int position) {
        return mData.get(position);
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

    class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilitiesViewHolder> {
        ArrayList<Facility> facilitiesList;
        ImageLoader imageLoader;
        TextView facilityText;
        //AnimationSet mAnimationSet = new AnimationSet(false);

        FacilitiesAdapter(ArrayList<Facility> facilities, TextView tempFacilityText) {
            facilitiesList = facilities;
            facilityText = tempFacilityText;
            imageLoader = MySingleton.getInstance(getContext()).getImageLoader();

            /*Animation fadeInAnimation = AnimationUtils.loadAnimation(SimpleCardStackAdapter.this.mContext, R.anim.fade_in);
            Animation fadeOutAnimation = AnimationUtils.loadAnimation(SimpleCardStackAdapter.this.mContext, R.anim.fade_out);
            mAnimationSet.addAnimation(fadeInAnimation);
            mAnimationSet.addAnimation(fadeOutAnimation);*/
        }

        @Override
        public FacilitiesAdapter.FacilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NetworkImageView imageView = (NetworkImageView) LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.item_facility_38dp, parent, false);
            return new FacilitiesViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(final FacilitiesAdapter.FacilitiesViewHolder holder, int position) {
            holder.image.setImageUrl(facilitiesList.get(holder.getAdapterPosition()).image_new, imageLoader);
            holder.image.setTag(facilitiesList.get(holder.getAdapterPosition()).tag);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    facilityText.clearAnimation();
                    facilityText.setText((String) holder.image.getTag());
                    //facilityText.animate().translationY(facilityText.getHeight()).start();
                    //facilityText.animate().alpha(1).start();
                    //facilityText.animate().alpha(0).setStartDelay(300).start();
                    //facilityText.startAnimation(mAnimationSet);
                    animateView(facilityText);
                }
            });
        }

        @Override
        public int getItemCount() {
            return facilitiesList.size();
        }

        class FacilitiesViewHolder extends RecyclerView.ViewHolder {
            NetworkImageView image;

            public FacilitiesViewHolder(View itemView) {
                super(itemView);
                image = (NetworkImageView) itemView;
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
   /* public void setCardCategory(int category){
        this.cardCategory=category;
    }

    public int getCardCategory() {
        return cardCategory;
    }*/
}