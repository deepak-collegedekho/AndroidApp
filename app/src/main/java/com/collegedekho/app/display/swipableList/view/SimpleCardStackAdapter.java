package com.collegedekho.app.display.swipableList.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.display.swipableList.model.CardModel;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.FadeInImageView;


public final class SimpleCardStackAdapter extends CardStackAdapter {

    private ImageLoader imageLoader;
    private OnCDRecommendedAdapterInterface mListener;
    private boolean mLoadingNext;

    public SimpleCardStackAdapter(Context mContext, OnCDRecommendedAdapterInterface listener) {
        super(mContext);
        this.imageLoader = MySingleton.getInstance(mContext).getImageLoader();
        this.mListener = listener;
    }

    @Override
    public View getCardView(final int position, final CardModel model, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_recommended_institute, parent, false);
            assert convertView != null;
        }

        Institute institute;
        final FadeInImageView fadeInImageView;
        final RelativeLayout viewToBlur;
        final LinearLayout streamsContainer;

        institute = model.getInstitute();

        //setting institute name
        ((TextView) convertView.findViewById(R.id.card_recommended_institute_title)).setText(institute.getName());

        //setting location
        String text = "";
        if (model.getInstitute().getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (model.getInstitute().getState_name() != null)
            text += institute.getState_name();

        if (text == "" || text.isEmpty())
            ((TextView) convertView.findViewById(R.id.card_recommended_geolocation)).setVisibility(View.GONE);
        else
            ((TextView) convertView.findViewById(R.id.card_recommended_geolocation)).setText(text);

        //setting institute image
        fadeInImageView = ((FadeInImageView) convertView.findViewById(R.id.card_recommended_institute_image));

        fadeInImageView.setDefaultImageResId(R.drawable.default_banner);
        fadeInImageView.setErrorImageResId(R.drawable.default_banner);

        //viewToBlur = (RelativeLayout) convertView.findViewById(R.id.card_recommended_institute_info_container);
        //streamsContainer = (LinearLayout) convertView.findViewById(R.id.card_recommended_streams_container);

        if (institute.getStreams() != null && institute.getStreams().size() > 0)
        {
            TextView streamTV = ((TextView) convertView.findViewById(R.id.card_recommended_streams));
            String streamText = "";

            if (institute.getStreams().size() == 1)
                ((TextView) convertView.findViewById(R.id.card_recommended_streams_label)).setText("Stream :");

            TableRow.LayoutParams lparams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(0, 0, 0, 10);

            for (String stream : institute.getStreams())
            {
                streamText += stream;
                streamText += "/";
            }

            streamTV.setText(streamText.substring(0, streamText.length() - 1));
        }
        else
            ((TextView) convertView.findViewById(R.id.card_recommended_streams_label)).setVisibility(View.GONE);

/*
        if (institute.getImages().get("Banner") != null)
        {
            this.imageLoader.get(institute.getImages().get("Banner"), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Log.v("", "response");
                    //fadeInImageView.setImageBitmap(response.getBitmap());
                    fadeInImageView.setImageDrawable(new BitmapDrawable(response.getBitmap()));
                    //SimpleCardStackAdapter.this.applyBlur(fadeInImageView, viewToBlur);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("", "error");
                }
            });
            //this.imageLoader.get(institute.getImages().get("Banner"), ImageLoader.getImageListener(fadeInImageView, R.drawable.default_banner, R.drawable.default_banner));
        }
*/

        if (institute.getImages().get("Banner") != null)
            fadeInImageView.setImageUrl(institute.getImages().get("Banner"), this.imageLoader);

        //card_recommended_institute_container
        //setting likes
        //((TextView) convertView.findViewById(R.id.card_recommended_like_count)).setText(String.valueOf(institute.getUpvotes()));
        ((TextView) convertView.findViewById(R.id.card_recommended_institute_detail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Swipeable Cards","I am pressing the card");
                SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
            }
        });

        //setting courses
        //((TextView) convertView.findViewById(R.id.card_recommended_courses_count)).setText(institute.getCourse_count() + " Courses");

        //setting placement percentage
        /*if (institute.getPlacement_percentage() != null && Float.parseFloat(institute.getPlacement_percentage()) > 0)
            ((TextView) convertView.findViewById(R.id.card_recommended_placement_value)).setText(institute.getPlacement_percentage());
        else
            ((TextView) convertView.findViewById(R.id.card_recommended_placement_value)).setText("100");*/

/*        model.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards","I am pressing the card");
                SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
            }
        });*/

        /*convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("Swipeable Cards","I am pressing the card");
                    SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
                }

                return true;
            }
        });*/

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

    public boolean isLoadingNext() {
        return mLoadingNext;
    }

    public void setLoadingNext(boolean mLoadingNext) {
        this.mLoadingNext = mLoadingNext;
    }

    private void applyBlur(final NetworkImageView niv, final View viewToBlur) {
        niv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                niv.getViewTreeObserver().removeOnPreDrawListener(this);
                niv.buildDrawingCache();

                Bitmap bmp = niv.getDrawingCache();
                blur(bmp, viewToBlur);

                return true;
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg, View view) {
        //long startMs = System.currentTimeMillis();

        float radius = 25;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(this.getContext());

        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(this.getContext().getResources(), overlay));

        rs.destroy();
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
}