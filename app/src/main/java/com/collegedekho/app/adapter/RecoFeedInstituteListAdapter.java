package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.display.peekandpop.BlurBuilder;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RecoFeedInstituteListAdapter extends RecyclerView.Adapter {

    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Context mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
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
        try {
            String name= new String(institute.getName().getBytes("ISO-8859-1"),"UTF-8");
            instituteHolder.instituteName.setText(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            instituteHolder.instituteName.setText(institute.getName());
        }

        String text = "";
        if (institute.getCity_name() != null)
            text += institute.getCity_name() + ", ";
        if (institute.getState_name() != null)
            text += institute.getState_name();
        else if (institute.getCity_name() == null || institute.getCity_name().isEmpty())
            text = text.replace(",", "");

        instituteHolder.instituteLocation.setText(text);

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

        this.mSetAnimation(instituteHolder.instituteCard, position);
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

    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    class InstituteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView instituteCard;
        public NetworkImageView instituteImage;
        TextView instituteName;
        TextView instituteLocation;
        TextView instituteFee;
        TextView institutePlacement;
        RelativeLayout instituteImageOver;
        FeedFragment.onFeedInteractionListener mListener;

        public InstituteHolder(View itemView, FeedFragment.onFeedInteractionListener listener) {
            super(itemView);
            this.instituteCard = (CardView) itemView.findViewById(R.id.card_feed_reco_card);
            this.instituteImage = (NetworkImageView) itemView.findViewById(R.id.card_feed_reco_institute_image);
            this.instituteName = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_name);
            this.instituteLocation = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_location);
            this.instituteFee = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_fee);
            this.institutePlacement = (TextView) itemView.findViewById(R.id.card_feed_reco_institute_placement);
            this.instituteImageOver = (RelativeLayout) itemView.findViewById(R.id.card_feed_reco_institute_details);
            this.mListener = listener;

            (itemView.findViewById(R.id.card_feed_reco_close)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_feed_reco_undecided)).setOnClickListener(this);
            (itemView.findViewById(R.id.card_feed_reco_like)).setOnClickListener(this);
        }

        public void updateInstituteImage(String image){
            if (image != null && !image.isEmpty())
                this.instituteImage.setImageUrl(image, mImageLoader);
            else
                this.instituteImage.setDefaultImageResId(R.drawable.default_banner);

            //this.instituteImage.setErrorImageResId(R.drawable.default_banner);

                /*RecoFeedInstituteListAdapter.this.mImageLoader.get(image, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InstituteHolder.this.instituteImage.setDefaultImageResId(R.drawable.default_banner);
                        InstituteHolder.this.instituteImage.setErrorImageResId(R.drawable.default_banner);
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        if (response.getBitmap() != null)
                        {
                            InstituteHolder.this.instituteImage.setImageBitmap(response.getBitmap());
                            //int height = InstituteHolder.this.instituteImage.getHeight();

                            //InstituteHolder.this.instituteImageOver.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
                        }
                    }
                });
            else
                this.instituteImage.setDefaultImageResId(R.drawable.default_banner);*/
        }

        @Override
        public void onClick(View v) {
            int connectivityStatus = new NetworkUtils(v.getContext(), null).getConnectivityStatus();
            switch (v.getId()) {
                case R.id.card_feed_reco_close:
                case R.id.card_feed_reco_undecided:
                case R.id.card_feed_reco_like:
                    break;
                default:
                    //mListener.onFeedSelected(getAdapterPosition());
                    break;
            }
        }
    }
}