package com.collegedekho.app.display.swipableList.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
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

        institute = model.getInstitute();

        //setting institute name
        ((TextView) convertView.findViewById(R.id.card_recommended_institute_title)).setText(institute.getName());

        //setting institute image
        ((FadeInImageView) convertView.findViewById(R.id.card_recommended_institute_image)).setDefaultImageResId(R.drawable.default_banner);
        ((FadeInImageView) convertView.findViewById(R.id.card_recommended_institute_image)).setErrorImageResId(R.drawable.default_banner);

        if (institute.getImages().get("Banner") != null)
            ((FadeInImageView) convertView.findViewById(R.id.card_recommended_institute_image)).setImageUrl(institute.getImages().get("Banner"), this.imageLoader);

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

        //setting likes
        ((TextView) convertView.findViewById(R.id.card_recommended_like_count)).setText(String.valueOf(institute.getUpvotes()));

        //setting courses
        ((TextView) convertView.findViewById(R.id.card_recommended_courses_count)).setText(institute.getCourse_count() + " Courses");

        //setting placement percentage
        if (institute.getPlacement_percentage() != null && Float.parseFloat(institute.getPlacement_percentage()) > 0)
            ((TextView) convertView.findViewById(R.id.card_recommended_placement_value)).setText(institute.getPlacement_percentage());
        else
            ((TextView) convertView.findViewById(R.id.card_recommended_placement_value)).setText("100");

/*        model.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards","I am pressing the card");
                SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
            }
        });*/

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("Swipeable Cards","I am pressing the card");
                    SimpleCardStackAdapter.this.mListener.OnInstituteSelected(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
                }

                return true;
            }
        });

        model.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards","I like the card");
                SimpleCardStackAdapter.this.mListener.OnInstituteLiked(((CardModel) SimpleCardStackAdapter.this.getItem(position)).getInstitute());
                //load next if on last position
                if (position < 4 && !SimpleCardStackAdapter.this.isLoadingNext())
                    SimpleCardStackAdapter.this.mListener.OnLoadNext();
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards","I dislike the card");
                //Institute i =  model.getInstitute();
                //Institute i2 = ((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute();
                SimpleCardStackAdapter.this.mListener.OnInstituteDislike(((CardModel) SimpleCardStackAdapter.this.getItem(getCount()-1-position)).getInstitute());
                //load next if on last position
                if (position < 4 && !SimpleCardStackAdapter.this.isLoadingNext())
                    SimpleCardStackAdapter.this.mListener.OnLoadNext();
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

    public interface OnCDRecommendedAdapterInterface
    {
        void OnLoadNext();
        void OnInstituteSelected(Institute institute);
        void OnInstituteLiked(Institute institute);
        void OnInstituteDislike(Institute institute);
    }
}