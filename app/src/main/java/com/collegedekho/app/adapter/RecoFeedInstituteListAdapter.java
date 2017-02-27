package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.jr.ob.JSON;
import com.collegedekho.app.network.MySingleton;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class RecoFeedInstituteListAdapter extends RecyclerView.Adapter {

    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    public int feedPosition = -1;
    private int mViewType;
    private int mCardWidth;

    public RecoFeedInstituteListAdapter(Context context, ArrayList<Institute> institutes) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
        WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        int width;
        display.getSize(size);
        width = size.x;
        // 1/3 of the screen's width, so another card can be seen too.
        this.mCardWidth = (width >> 1) + (width >> 2);
    }

    public void setFeedPosition(int feedPosition)
    {
        this.feedPosition = feedPosition;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(this.mContext).inflate(R.layout.card_feed_reco, parent, false);
        try {
            return new InstituteHolder(rootView, (FeedFragment.onFeedInteractionListener) this.mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String imageURL = "";

        Institute institute = this.mInstitutes.get(position);

        InstituteHolder instituteHolder = (InstituteHolder) holder;
        instituteHolder.instituteCard.setLayoutParams(new CardView.LayoutParams(
               this.mCardWidth, CardView.LayoutParams.MATCH_PARENT));

        //setting image
        if (institute.getImages().get("Primary") != null)
            imageURL = institute.getImages().get("Primary");
        else if (institute.getImages().get("Banner") != null)
            imageURL = institute.getImages().get("Banner");
        else if (institute.getImages().get("Infra") != null)
            imageURL = institute.getImages().get("Infra");
        else if (institute.getImages().get("Student") != null)
            imageURL = institute.getImages().get("Student");
        else if (institute.getImages().get("Other") != null)
            imageURL = institute.getImages().get("Other");

        instituteHolder.updateInstituteImage(imageURL);

        //setting name
        try {
            String name= new String(institute.getName().getBytes("ISO-8859-1"),"UTF-8");
            instituteHolder.instituteName.setText(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            instituteHolder.instituteName.setText(institute.getName());
        }

        //setting location
        String text = "";
        if (institute.getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (institute.getState_name() != null)
            text += institute.getState_name();
        else if (institute.getCity_name() == null || institute.getCity_name().isEmpty())
            text = text.replace(",", "");

        if (text.isEmpty())
            instituteHolder.instituteLocation.setVisibility(View.GONE);
        else
        {
            instituteHolder.instituteLocation.setVisibility(View.VISIBLE);
            instituteHolder.instituteLocation.setText(text);
        }

        //setting fee
        String fee = "Fee - ";
        if (institute.getFees() != null && !institute.getFees().isEmpty())
        {
            instituteHolder.instituteFee.setVisibility(View.VISIBLE);

            fee += institute.getFees();
            instituteHolder.instituteFee.setText(fee);
        }
        else
            instituteHolder.instituteFee.setVisibility(View.GONE);

        //setting placement
        String placement = "Average Placement: ";
        if (institute.getPlacement_percentage() != null && !institute.getPlacement_percentage().isEmpty())
        {
            instituteHolder.institutePlacement.setVisibility(View.VISIBLE);

            placement += institute.getPlacement_percentage() + "%";
            instituteHolder.institutePlacement.setText(placement);
        }
        else
            instituteHolder.institutePlacement.setVisibility(View.GONE);

        //this.mSetAnimation(instituteHolder.instituteCard, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void mSetAnimation(View viewToAnimate, int position)
    {
        Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.enter_from_right);
        viewToAnimate.startAnimation(animation);
        lastPosition = position;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView instituteCard;
        public NetworkImageView instituteImage;
        TextView instituteName;
        TextView instituteLocation;
        TextView instituteFee;
        TextView institutePlacement;
        FeedFragment.onFeedInteractionListener mListener;

        public InstituteHolder(View itemView, FeedFragment.onFeedInteractionListener listener) {
            super(itemView);
            this.instituteCard = (CardView) itemView.findViewById(R.id.card_feed_reco_card);
            this.instituteImage = (NetworkImageView) itemView.findViewById(R.id.card_feed_reco_institute_image);
            this.instituteName = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_name);
            this.instituteLocation = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_location);
            this.instituteFee = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_fee);
            this.institutePlacement = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_placement);
            this.mListener = listener;

            (itemView.findViewById(R.id.card_feed_reco_close)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_feed_reco_undecided)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_feed_reco_like)).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void updateInstituteImage(String image){
            this.instituteImage.setErrorImageResId(R.drawable.default_banner);

            if (image != null && !image.isEmpty())
                this.instituteImage.setImageUrl(image, mImageLoader);
            else
                this.instituteImage.setDefaultImageResId(R.drawable.default_banner);

                /*RecoFeedInstituteListAdapter.this.mImageLoader.get(image, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InstituteHolder.this.instituteImage.setDefaultImageResId(R.drawable.default_banner);
                        InstituteHolder.this.instituteImage.setErrorImageResId(R.drawable.default_banner);
                    }

                    @Override
                    public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {

                        if (response.getBitmap() != null && !isImmediate)
                        {
                            Bitmap drawableBitmap = response.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                            Canvas canvas = new Canvas(drawableBitmap);
                            Paint paint = new Paint(Color.BLACK);
                            ColorFilter filter = new LightingColorFilter(0xf09f09, 0x000000);
                            paint.setColorFilter(filter);
                            canvas.drawBitmap(drawableBitmap, new Matrix(), paint);

                            InstituteHolder.this.instituteImage.setImageBitmap(response.getBitmap());
                        }
                        else if (response.getBitmap() != null && isImmediate)
                        {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                        }
                    }
                });
            else
                this.instituteImage.setDefaultImageResId(R.drawable.default_banner);*/
        }

        @Override
        public void onClick(View v) {
            HashMap<String, String> map = new HashMap<>();
            switch (v.getId()) {
                case R.id.card_feed_reco_close:
                    map.put("position", String.valueOf(this.getAdapterPosition()));
                    map.put("feedPosition", String.valueOf(RecoFeedInstituteListAdapter.this.feedPosition));
                    map.put("feedActionType", Constants.FEED_RECO_ACTION);
                    map.put("action", Constants.CDRecommendedInstituteType.NOT_INTERESTED.toString());
                    map.put("id", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getId()));
                    map.put("url", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getResource_uri() + "shortlist/"));
                    this.mListener.onFeedAction(Constants.WIDGET_RECOMMENDED_INSTITUTES, map);
                    break;
                case R.id.card_feed_reco_undecided:
                    map.put("position", String.valueOf(this.getAdapterPosition()));
                    map.put("feedPosition", String.valueOf(RecoFeedInstituteListAdapter.this.feedPosition));
                    map.put("feedActionType", Constants.FEED_RECO_ACTION);
                    map.put("action", Constants.CDRecommendedInstituteType.UNDECIDED.toString());
                    map.put("id", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getId()));
                    map.put("url", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getResource_uri() + "shortlist/"));
                    this.mListener.onFeedAction(Constants.WIDGET_RECOMMENDED_INSTITUTES, map);
                    break;
                case R.id.card_feed_reco_like:
                case R.id.card_feed_reco_shortlist:
                    map.put("position", String.valueOf(this.getAdapterPosition()));
                    map.put("feedPosition", String.valueOf(RecoFeedInstituteListAdapter.this.feedPosition));
                    map.put("feedActionType", Constants.FEED_RECO_ACTION);
                    map.put("action", Constants.CDRecommendedInstituteType.SHORTLIST.toString());
                    map.put("id", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getId()));
                    map.put("url", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getResource_uri() + "shortlist/"));
                    this.mListener.onFeedAction(Constants.WIDGET_RECOMMENDED_INSTITUTES, map);
                    break;
                default:
                    try {
                        map.put("position", String.valueOf(this.getAdapterPosition()));
                        map.put("feedPosition", String.valueOf(RecoFeedInstituteListAdapter.this.feedPosition));
                        map.put("feedActionType", Constants.FEED_RECO_INSTITUTE_DETAILS_ACTION);
                        map.put("url", String.valueOf(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition()).getResource_uri()));
                        map.put("institute", JSON.std.asString(RecoFeedInstituteListAdapter.this.mInstitutes.get(this.getAdapterPosition())));

                        this.mListener.onFeedAction(Constants.WIDGET_RECOMMENDED_INSTITUTES, map);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}