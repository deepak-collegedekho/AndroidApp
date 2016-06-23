package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.display.peekandpop.BlurBuilder;
import com.collegedekho.app.display.peekandpop.PeekAndPop;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.WishlistFragment;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.FadeInImageView;

import java.util.ArrayList;

import static com.collegedekho.app.resource.Constants.HOLD_ENTER_VIBRATION_DURATION;
import static com.collegedekho.app.resource.Constants.HOLD_REMOVE_VIBRATION_DURATION;

public class WishlistInstituteListAdapter extends RecyclerView.Adapter<WishlistInstituteListAdapter.InstituteHolder> {

    private final ImageLoader mImageLoader;
    private ArrayList<Institute> mInstitutes;
    private Activity mContext;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    private int mViewType;
    private int mPeekPopViewType;
    private PeekAndPop mPeekAndPop;
    private View mPeekView;
    private WishlistCardAdapter mPeekViewAdapter;
    private WishlistFragment.WishlistInstituteInteractionListener mListener;

    public WishlistInstituteListAdapter(Activity context, ArrayList<Institute> institutes, int type, PeekAndPop peekAndPop) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mListener = (WishlistFragment.WishlistInstituteInteractionListener) context;
        this.mPeekPopViewType = type;
        this.mPeekAndPop = peekAndPop;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
        this.mPeekViewAdapter = new WishlistCardAdapter(context);
        this.mPeekView = this.mPeekAndPop.getPeekView();


        this.mSetupPeekAndPopStandard();
    }

    private void mSetupPeekAndPopStandard() {
        final Vibrator vibratorService = (Vibrator) WishlistInstituteListAdapter.this.mContext.getApplicationContext().getSystemService(Activity.VIBRATOR_SERVICE);
        this.mPeekAndPop.setOnGeneralActionListener(new PeekAndPop.OnGeneralActionListener() {
            @Override
            public void onPeek(View view, int position) {
                WishlistInstituteListAdapter.this.mShowInstituteCardOnLongPress(position);
            }

            @Override
            public void onPop(View view, int position) {
            }

            @Override
            public void onClickPeek(View view, int position) {

            }

            @Override
            public void onClickPop(View view, int position) {

            }
        });

        this.mPeekAndPop.addLongHoldView(R.id.wishlist_institute_see_all_layout, true);
        this.mPeekAndPop.addLongHoldView(R.id.wishlist_btn_details, true);
        this.mPeekAndPop.addLongHoldView(R.id.wishlist_header_view, true);
        //this.mPeekAndPop.addLongHoldView(R.id.facility_image, true);

        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_call_now);
        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_apply_now);
        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_remove_shortlist);

        this.mPeekAndPop.setOnLongHoldListener(new PeekAndPop.OnLongHoldListener(){

            @Override
            public void onEnter(View view, int position) {
                Log.d("WlInsAdap", "onEnter:");
                vibratorService.vibrate(HOLD_ENTER_VIBRATION_DURATION);
            }

            @Override
            public void onLongHold(View view, int position) {
                vibratorService.vibrate(HOLD_REMOVE_VIBRATION_DURATION);
                if (view.getId() == R.id.wishlist_institute_see_all_layout) {
                    WishlistInstituteListAdapter.this.mPeekViewAdapter.ToggleFacilitiesLayout();
                }
                else if (view.getId() == R.id.wishlist_btn_details)
                {
                    WishlistInstituteListAdapter.this.mPeekViewAdapter.ToggleWishlistInstituteDetails();
                }
                else if (view.getId() == R.id.wishlist_header_view)
                {
                    //show institute
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteSelected(WishlistInstituteListAdapter.this.mInstitutes.get(position));
                }
            }
        });

        this.mPeekAndPop.setOnHoldAndReleaseListener(new PeekAndPop.OnHoldAndReleaseListener() {
            @Override
            public void onHold(View view, int position) {
                vibratorService.vibrate(HOLD_ENTER_VIBRATION_DURATION);
            }

            @Override
            public void onLeave(View view, int position) {}

            @Override
            public void onRelease(View view, int position) {
                vibratorService.vibrate(HOLD_REMOVE_VIBRATION_DURATION);

                String message = "";

                Institute institute = WishlistInstituteListAdapter.this.mInstitutes.get(position);
                if (view.getId() == R.id.wishlist_institute_btn_call_now) {
                    message = "Calling " + WishlistInstituteListAdapter.this.mInstitutes.get(position).getShort_name();
                    //Call intent
                    Uri number = Uri.parse("tel:" + institute.getL3_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    WishlistInstituteListAdapter.this.mContext.startActivity(callIntent);
                }
                else if (view.getId() == R.id.wishlist_institute_btn_apply_now) {
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteApplied(institute, position);
                }
                else if (view.getId() == R.id.wishlist_institute_btn_remove_shortlist) {
                    message = "Removing " + institute.getShort_name() +  " from Wishlist";
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteRemoved(institute, position);
                }

                if (message != "")
                    Snackbar.make(WishlistInstituteListAdapter.this.mContext.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public InstituteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewID =  R.layout.card_wishlist_institute_list_grid_view;
        View rootView = LayoutInflater.from(mContext).inflate(viewID, parent, false);
        try {
            return new InstituteHolder(rootView);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(final InstituteHolder instituteHolder, int position) {
        Institute institute = this.mInstitutes.get(position);

        WishlistInstituteListAdapter.this.mPeekAndPop.addLongClickView(instituteHolder.instituteCard, position);

        String text = "";

        if (institute.getAcronym() != null && institute.getAcronym().equalsIgnoreCase("None") && !institute.getAcronym().isEmpty())
            text = institute.getAcronym();
        else if (institute.getShort_name() != null && !institute.getShort_name().equalsIgnoreCase("None") && !institute.getShort_name().isEmpty())
            text += institute.getShort_name();

        if (institute.getCity_name() != null && !institute.getCity_name().isEmpty())
            if (! text.toLowerCase().contains(institute.getCity_name().toLowerCase()))
                text += " " + institute.getCity_name();

        institute.setShort_name(text);

        instituteHolder.instituteLogo.setDefaultImageResId(R.drawable.ic_cd);
        instituteHolder.instituteLogo.setErrorImageResId(R.drawable.ic_cd);

/*
        ImageLoader.ImageListener imageListener = new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    instituteHolder.instituteLogo.setLocalImageBitmap(response.getBitmap(), true);
                    //applyBlur(instituteHolder);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
*/

        if (institute.getLogo() != null && !institute.getLogo().isEmpty()) {
            //this.mImageLoader.get(institute.getLogo(), imageListener);
            instituteHolder.instituteLogo.setImageUrl(institute.getLogo(), this.mImageLoader);
        }

        instituteHolder.instituteShortName.setText(text);

        //Setting event listener on textview, so that when layout phase is over we get a callback and do our thing \m/ !!
        instituteHolder.instituteShortName.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                instituteHolder.instituteShortName.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                instituteHolder.instituteShortName.setBackgroundDrawable(new BitmapDrawable(WishlistInstituteListAdapter.this.mContext.getResources(), BlurBuilder.blur(instituteHolder.instituteShortName)));
            }
        });
    }

    /*private void applyBlur(final InstituteHolder instituteHolder) {
        instituteHolder.instituteLogo.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                instituteHolder.instituteLogo.getViewTreeObserver().removeOnPreDrawListener(this);
                instituteHolder.instituteLogo.buildDrawingCache();

                Bitmap bmp = instituteHolder.instituteLogo.getDrawingCache();
                blur(bmp, instituteHolder.instituteShortName);

                return true;
            }
        });
    }

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float radius = 20;

        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);
        overlay = Utils.FastBlur(overlay, 2.0f, (int)radius);
        view.setBackground(new BitmapDrawable(this.mContext.getResources(), overlay));
    }*/

    @Override
    public void onViewDetachedFromWindow(InstituteHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    public static class InstituteHolder extends RecyclerView.ViewHolder{
        View instituteCard;

        TextView instituteShortName;
        FadeInImageView instituteLogo;

        public InstituteHolder(View itemView) {
            super(itemView);
            this.instituteCard = itemView;

            this.instituteShortName = (TextView) itemView.findViewById(R.id.wishlist_institute_shortname);
            this.instituteLogo = (FadeInImageView) itemView.findViewById(R.id.wishlist_institute_logo);
            this.instituteLogo.setDefaultImageResId(R.drawable.ic_default_image);
            this.instituteLogo.setErrorImageResId(R.drawable.ic_default_image);
        }
    }

    public void mShowInstituteCardOnLongPress(int position)
    {
        this.mPeekViewAdapter.ShowInstituteCard(this.mInstitutes.get(position), this.mPeekView);
    }

    public void mShowInstituteCardOnClick(int position)
    {
        this.mPeekViewAdapter.ShowInstituteCard(this.mInstitutes.get(position), this.mPeekView);
    }
}