package com.collegedekho.app.display.swipableList.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.BitMapHolder;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.FadeInImageView;

import java.util.ArrayList;
import java.util.Vector;

public final class SimpleCardStackAdapterNew extends BaseAdapter {

    private final ImageLoader imageLoader;
    private OnCDRecommendedAdapterInterface mListener;
    private boolean mLoadingNext;
    private Context mContext;
    private Activity mActivity;
    private Vector<CardModel> mData;


    public SimpleCardStackAdapterNew(Activity activity, Context context, OnCDRecommendedAdapterInterface listener) {
        //super(context);
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
        this.mListener = listener;
        this.mContext = context;
        this.mActivity = activity;
        this.mData = new Vector<>();

    }

    public void addAll(ArrayList<CardModel> item) {
        mData.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.wishlist_card_layout, parent, false);
        }

        final CardModel model = getCardModel(position);
        this.mParseAndPopulateCards(model, convertView);

        //card_recommended_institute_container
        (convertView.findViewById(R.id.btn_apply_now)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleCardStackAdapterNew.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapterNew.this.getItem(position)).getInstitute());
            }
        });

        model.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike() {
                if (position > 0)
                    SimpleCardStackAdapterNew.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapterNew.this.isLoadingNext()) {
                    SimpleCardStackAdapterNew.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), true);
                    SimpleCardStackAdapterNew.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }

            @Override
            public void onDislike() {
                if (position > 0)
                    SimpleCardStackAdapterNew.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapterNew.this.isLoadingNext()) {
                    SimpleCardStackAdapterNew.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), true);
                    SimpleCardStackAdapterNew.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }

            @Override
            public void onUpSwipe() {
                if (position > 0)
                    SimpleCardStackAdapterNew.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapterNew.this.isLoadingNext()) {
                    SimpleCardStackAdapterNew.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapterNew.this.getItem(getCount() - 1 - position)).getInstitute(), true);
                    SimpleCardStackAdapterNew.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }
        });
        return convertView;
    }


    private void mParseAndPopulateCards(final CardModel model, final View convertView) {
        final FadeInImageView fadeInImageView;
        ImageLoader.ImageListener imageListener;
        ImageLoader.ImageListener bottomImageListener;
        Institute institute = model.getInstitute();
        final FadeInImageView bottomImage;
        // set institute name
        ((TextView) convertView.findViewById(R.id.title)).setText(institute.getName());

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

        //setting institute image_new
        fadeInImageView = ((FadeInImageView) convertView.findViewById(R.id.institute_image));
        bottomImage = ((FadeInImageView) convertView.findViewById(R.id.bottom_image));
        //fadeInImageView.setDefaultImageResId(R.drawable.default_banner);
        //fadeInImageView.setErrorImageResId(R.drawable.default_banner);
        if (BitMapHolder.DEFAULT_BANNER != null)
            fadeInImageView.setLocalImageBitmap(BitMapHolder.DEFAULT_BANNER);
        fadeInImageView.setBackgroundResource(R.drawable.default_banner);


   /*     try{
            ((ImageView) convertView.findViewById(R.id.like_textview)).setImageBitmap(BitMapHolder.SHORTLISTED_BITMAP);
            ((ImageView) convertView.findViewById(R.id.dislike_textview)).setImageBitmap(BitMapHolder.UNSHORTLISTED_BITMAP);
            ((ImageView) convertView.findViewById(R.id.decide_later_textview)).setImageBitmap(BitMapHolder.UNDECIDED_BITMAP);
        }catch (Exception e){
            e.printStackTrace();
        }*/


        if (institute.getStreams() != null && institute.getStreams().size() > 0) {
            TextView streamTV = ((TextView) convertView.findViewById(R.id.recommended_streams));
            String streamText = "";
            TextView examsTv=(TextView)convertView.findViewById(R.id.recommended_exams);
            TextView maxSalary = ((TextView) convertView.findViewById(R.id.max_sal_val));
            TextView minSalary = ((TextView) convertView.findViewById(R.id.min_sal_val));
            TextView avgSalary = ((TextView) convertView.findViewById(R.id.avg_sal_val));
            TextView placementPercent = ((TextView) convertView.findViewById(R.id.placement_percentage));
            TextView feesText=(TextView)convertView.findViewById(R.id.fees_range_text);

            try {
                int markerMargin=0;
                int markerHeight=0;
                float deviceDensity=getContext().getResources().getDimension(R.dimen.m1dp);

                double averageSal=Double.parseDouble(institute.getAvg_salary());
                double maxSal=Double.parseDouble(institute.getMax_salary());
                double minSal=Double.parseDouble(institute.getMin_salary());

                maxSalary.setText(Utils.rupeeFormatter(maxSal));
                minSalary.setText(Utils.rupeeFormatter(minSal));
                avgSalary.setText(Utils.rupeeFormatter(averageSal));

                double avgPercent=(averageSal/maxSal)*100;
                markerMargin=(int)(avgPercent*50*deviceDensity)/100;
                View averageSalMark=convertView.findViewById(R.id.avg_sal_mark);
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)averageSalMark.getLayoutParams();
                layoutParams.setMargins(0,0,0,markerMargin);
                averageSalMark.setLayoutParams(layoutParams);

                double minPercent=(minSal/maxSal)*100;
                markerHeight=(int)(minPercent*50*deviceDensity)/100;
                View minSalMark=convertView.findViewById(R.id.min_sal_bar);
                RelativeLayout.LayoutParams minLayoutParams=(RelativeLayout.LayoutParams)minSalMark.getLayoutParams();
                minLayoutParams.height=markerHeight;
                minSalMark.setLayoutParams(minLayoutParams);

            } catch (NumberFormatException e) {

            } catch (Exception e) {

            }
            if (institute.getPlacement_percentage() != null && !institute.getPlacement_percentage().isEmpty()) {
                placementPercent.setText("" + institute.getPlacement_percentage() + "%");
            }else {
                placementPercent.setText("");
            }
            TextView likes = ((TextView) convertView.findViewById(R.id.vote_count));
            likes.setText(String.valueOf(institute.getUpvotes()));
            ((TextView)convertView.findViewById(R.id.applied_count)).setText(""+(institute.getUpvotes()+institute.getShortlist_count()));
            final RecyclerView facilitiesRecycler = (RecyclerView) convertView.findViewById(R.id.facilities_recycler);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
//            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            facilitiesRecycler.setLayoutManager(layoutManager);
            final ArrayList<Facility> facilityArrayList = institute.getFacilities();
            if (facilityArrayList != null && !facilityArrayList.isEmpty()) {
                facilitiesRecycler.setAdapter(new FacilitiesAdapter(facilityArrayList));
                View seeAllButton = convertView.findViewById(R.id.see_all_layout);

                if (facilityArrayList.size() > 7) {
                    seeAllButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ViewGroup.LayoutParams layoutParams = facilitiesRecycler.getLayoutParams();
                            final int height = (int) getContext().getResources().getDimension(R.dimen.m35dp);
                            int marginLeft = (int) getContext().getResources().getDimension(R.dimen.m10dp);
                            int marginTop = (int) getContext().getResources().getDimension(R.dimen.m6dp);
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
            }
            feesText.setText(institute.getFees());
//            if (institute.getStreams().size() == 1)
//                ((TextView) convertView.findViewById(R.id.card_recommended_streams_label)).setText("Stream :");

            for (String stream : institute.getStreams()) {
                streamText += stream;
                streamText += "/";
            }
            int count=0;
            String examsText="";
            for (String exam:institute.getExams()){
                count++;
                if(count==2)
                    break;
                examsText=examsText+exam+",";
            }
            streamTV.setText("Stream: " + streamText.substring(0, streamText.length() - 1));
            examsTv.setText("Exams: "+examsText.substring(0, examsText.length() - 1));
        } else {

//            convertView.findViewById(R.id.card_recommended_streams_label).setVisibility(View.GONE);
            convertView.findViewById(R.id.recommended_streams).setVisibility(View.GONE);

        }

        imageListener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    fadeInImageView.setLocalImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        bottomImageListener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    bottomImage.setLocalImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };


                /*if (institute.getImages().get("Banner") != null)
                    fadeInImageView.setImageUrl(institute.getImages().get("Banner"), this.imageLoader);
                else if (institute.getImages().get("Primary") != null)
                    fadeInImageView.setImageUrl(institute.getImages().get("Primary"), this.imageLoader);
                else if (institute.getImages().get("Student") != null)
                    fadeInImageView.setImageUrl(institute.getImages().get("Student"), this.imageLoader);
                else if (institute.getImages().get("Infra") != null)
                    fadeInImageView.setImageUrl(institute.getImages().get("Infra"), this.imageLoader);
                else if (institute.getImages().get("Other") != null)
                    fadeInImageView.setImageUrl(institute.getImages().get("Other"), this.imageLoader);*//*
                */

        String used = null;

        if (institute.getImages().get("Banner") != null) {
            imageLoader.get(institute.getImages().get("Banner"), imageListener);
            used = institute.getImages().get("Banner");
        } else if (institute.getImages().get("Primary") != null) {
            imageLoader.get(institute.getImages().get("Primary"), imageListener);
            used = institute.getImages().get("Primary");
        } else if (institute.getImages().get("Student") != null) {
            imageLoader.get(institute.getImages().get("Student"), imageListener);
            used = institute.getImages().get("Student");
        } else if (institute.getImages().get("Infra") != null) {
            imageLoader.get(institute.getImages().get("Infra"), imageListener);
            used = institute.getImages().get("Infra");
        } else if (institute.getImages().get("Other") != null) {
            imageLoader.get(institute.getImages().get("Other"), imageListener);
            used = institute.getImages().get("Other");
        }

        if (institute.getImages().get("Banner") != null && !institute.getImages().get("Banner").equals(used))
            imageLoader.get(institute.getImages().get("Banner"), bottomImageListener);
        else if (institute.getImages().get("Primary") != null && !institute.getImages().get("Primary").equals(used))
            imageLoader.get(institute.getImages().get("Primary"), bottomImageListener);
        else if (institute.getImages().get("Student") != null && !institute.getImages().get("Student").equals(used))
            imageLoader.get(institute.getImages().get("Student"), bottomImageListener);
        else if (institute.getImages().get("Infra") != null && !institute.getImages().get("Infra").equals(used))
            imageLoader.get(institute.getImages().get("Infra"), bottomImageListener);
        else if (institute.getImages().get("Other") != null && !institute.getImages().get("Other").equals(used))
            imageLoader.get(institute.getImages().get("Other"), bottomImageListener);
        else if (BitMapHolder.DEFAULT_BANNER != null)
            bottomImage.setLocalImageBitmap(BitMapHolder.DEFAULT_BANNER);
        else
            bottomImage.setBackgroundResource(R.drawable.default_banner);

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
    }

    public void clear() {
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

    class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilitiesViewHolder> {
        ArrayList<Facility> facilitiesList;
        ImageLoader imageLoader;

        FacilitiesAdapter(ArrayList<Facility> facilities) {
            facilitiesList = facilities;
            imageLoader = MySingleton.getInstance(getContext()).getImageLoader();
        }

        @Override
        public FacilitiesAdapter.FacilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NetworkImageView imageView = (NetworkImageView) LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.item_facility_35dp, parent, false);
            return new FacilitiesViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(FacilitiesAdapter.FacilitiesViewHolder holder, int position) {
            holder.image.setImageUrl(facilitiesList.get(holder.getAdapterPosition()).image_new, imageLoader);
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
    }
}