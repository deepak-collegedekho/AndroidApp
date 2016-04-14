package com.collegedekho.app.display.swipableList.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.BitMapHolder;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.FadeInImageView;

import java.util.ArrayList;
import java.util.Vector;

public final class SimpleCardStackAdapter extends BaseAdapter {

    private final  ImageLoader imageLoader;
    private OnCDRecommendedAdapterInterface mListener;
    private boolean mLoadingNext;
    private Context mContext;
    private Activity mActivity;
    private Vector<CardModel> mData;


    public SimpleCardStackAdapter(Activity activity, Context context, OnCDRecommendedAdapterInterface listener) {
        //super(context);
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
        this.mListener = listener;
        this.mContext = context;
        this.mActivity = activity;
        this. mData = new Vector<>();

    }

    public void addAll(ArrayList<CardModel> item) {
        mData.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position,  View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_recommended_institute, parent, false);
        }

        final CardModel model = getCardModel(position);
        this.mParseAndPopulateCards(model, convertView);

        //card_recommended_institute_container
        (convertView.findViewById(R.id.card_recommended_institute_detail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
            }
        });

        model.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike() {
                if (position > 0)
                    SimpleCardStackAdapter.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext())
                {
                    SimpleCardStackAdapter.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }

            @Override
            public void onDislike() {
                if (position > 0)
                    SimpleCardStackAdapter.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext())
                {
                    SimpleCardStackAdapter.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }

            @Override
            public void onUpSwipe() {
                if (position > 0)
                    SimpleCardStackAdapter.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), false);
                if (position == 0 && !SimpleCardStackAdapter.this.isLoadingNext())
                {
                    SimpleCardStackAdapter.this.mListener.OnDecideLater(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute(), true);
                    SimpleCardStackAdapter.this.mListener.OnShowMessage("Looking for more institutes..");
                }
            }
        });
        return convertView;
    }


    private void mParseAndPopulateCards(final CardModel model,final  View convertView)
    {
        final FadeInImageView fadeInImageView;
        ImageLoader.ImageListener imageListener;
        Institute institute =  model.getInstitute();

        // set institute name
        ((TextView) convertView.findViewById(R.id.card_recommended_institute_title)).setText(institute.getName());

        //setting location
        String text = "";
        if (model.getInstitute().getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (model.getInstitute().getState_name() != null)
            text += institute.getState_name();

        if (text == "" || text.isEmpty())
            convertView.findViewById(R.id.card_recommended_geolocation).setVisibility(View.GONE);
        else
            ((TextView) convertView.findViewById(R.id.card_recommended_geolocation)).setText(text);

        //setting institute image_new
        fadeInImageView = ((FadeInImageView) convertView.findViewById(R.id.card_recommended_institute_image));

        //fadeInImageView.setDefaultImageResId(R.drawable.default_banner);
        //fadeInImageView.setErrorImageResId(R.drawable.default_banner);
        if(BitMapHolder.DEFAULT_BANNER != null)
            fadeInImageView.setLocalImageBitmap(BitMapHolder.DEFAULT_BANNER);
         else
            fadeInImageView.setBackgroundResource(R.drawable.default_banner);

        try{
            ((ImageView) convertView.findViewById(R.id.like_textview)).setImageBitmap(BitMapHolder.SHORTLISTED_BITMAP);
            ((ImageView) convertView.findViewById(R.id.dislike_textview)).setImageBitmap(BitMapHolder.UNSHORTLISTED_BITMAP);
            ((ImageView) convertView.findViewById(R.id.decide_later_textview)).setImageBitmap(BitMapHolder.UNDECIDED_BITMAP);
        }catch (Exception e){
            e.printStackTrace();
        }


        if (institute.getStreams() != null && institute.getStreams().size() > 0)
        {
            TextView streamTV = ((TextView) convertView.findViewById(R.id.card_recommended_streams));
            String streamText = "";

            if (institute.getStreams().size() == 1)
                ((TextView) convertView.findViewById(R.id.card_recommended_streams_label)).setText("Stream :");

            for (String stream : institute.getStreams())
            {
                streamText += stream;
                streamText += "/";
            }
            streamTV.setText(streamText.substring(0, streamText.length() - 1));
        }
        else {

            convertView.findViewById(R.id.card_recommended_streams_label).setVisibility(View.GONE);
            convertView.findViewById(R.id.card_recommended_streams).setVisibility(View.GONE);

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

        if (institute.getImages().get("Banner") != null)
            imageLoader.get(institute.getImages().get("Banner"), imageListener);
        else if (institute.getImages().get("Primary") != null)
            imageLoader.get(institute.getImages().get("Primary"), imageListener);
        else if (institute.getImages().get("Student") != null)
            imageLoader.get(institute.getImages().get("Student"), imageListener);
        else if (institute.getImages().get("Infra") != null)
            imageLoader.get(institute.getImages().get("Infra"), imageListener);
        else if (institute.getImages().get("Other") != null)
            imageLoader.get(institute.getImages().get("Other"), imageListener);


    }


    public boolean isLoadingNext() {
        return mLoadingNext;
    }

    public void setLoadingNext(boolean mLoadingNext) {
        this.mLoadingNext = mLoadingNext;
    }

    public interface OnCDRecommendedAdapterInterface
    {
        void OnLoadNext();
        void OnInstituteSelected(Institute institute);
        void OnInstituteLiked(Institute institute, boolean isLastCard);
        void OnInstituteDislike(Institute institute, boolean isLastCard);
        void OnDecideLater(Institute institute, boolean isLastCard);
        void OnShowMessage(String message);
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