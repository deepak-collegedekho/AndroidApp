package com.collegedekho.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.display.peekandpop.PeekAndPop;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.fragment.WishlistFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.FadeInImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.collegedekho.app.activity.MainActivity.getResourceString;

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
    private TextView mCalloutButton;
    private String mEventCategory = "";
    private String mEventAction = "";
    private Calendar calendar;
    private static HashMap<String, Object> mEventValue;


    public WishlistInstituteListAdapter(Activity context, ArrayList<Institute> institutes, int type, PeekAndPop peekAndPop) {
        this.mInstitutes = institutes;
        this.mContext = context;
        this.mListener = (WishlistFragment.WishlistInstituteInteractionListener) context;
        this.mPeekPopViewType = type;
        this.mPeekAndPop = peekAndPop;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
        this.mPeekViewAdapter = new WishlistCardAdapter(context);
        this.mPeekView = this.mPeekAndPop.getPeekView();
        this.mCalloutButton = (TextView) this.mPeekView.findViewById(R.id.wishlist_institute_btn_callout);

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_INSTITUTES);
        this.mEventValue = new HashMap<String, Object>();

        this.mSetupPeekAndPopStandard();
    }

    private void mSetupPeekAndPopStandard() {
        final Vibrator vibratorService = (Vibrator) WishlistInstituteListAdapter.this.mContext.getApplicationContext().getSystemService(Activity.VIBRATOR_SERVICE);
        this.mPeekAndPop.setOnGeneralActionListener(new PeekAndPop.OnGeneralActionListener() {
            @Override
            public void onPeek(View view, int position) {
                WishlistInstituteListAdapter.this.calendar = Calendar.getInstance();

                WishlistInstituteListAdapter.this.mShowInstituteCardOnLongPress(position);

                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_PEEK);
                WishlistInstituteListAdapter.mEventValue.put("action_what", "institute_peek");
                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
            }

            @Override
            public void onPop(View view, int position) {

                int timeUsedInMS;

                timeUsedInMS = Utils.getTimeInMilliSec(WishlistInstituteListAdapter.this.calendar);

                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_POP);

                WishlistInstituteListAdapter.mEventValue.put("action_what", "institute_pop");
                WishlistInstituteListAdapter.mEventValue.put("action_duration", String.valueOf(timeUsedInMS));
                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
            }

            @Override
            public void onClickPeek(View view, int position) {
                WishlistInstituteListAdapter.this.mShowInstituteCardOnClick(position);

                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_CLICK);

                WishlistInstituteListAdapter.mEventValue.put("action_what", "institute_peek_click");
                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
            }

            @Override
            public void onClickPop(View view, int position) {
            }
        });

        this.mPeekAndPop.addLongHoldView(R.id.wishlist_institute_see_all_layout, true);
        this.mPeekAndPop.addLongHoldView(R.id.wishlist_btn_details, true);
        this.mPeekAndPop.addLongHoldView(R.id.wishlist_header_view, true);

        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_call_now);
        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_apply_now);
        this.mPeekAndPop.addHoldAndReleaseView(R.id.wishlist_institute_btn_remove_shortlist);

        this.mPeekAndPop.setOnLongHoldListener(new PeekAndPop.OnLongHoldListener(){

            @Override
            public void onEnter(View view, int position) {
                Log.d("WlInsAdap", "onEnter:");
                vibratorService.vibrate(Constants.HOLD_ENTER_VIBRATION_DURATION);
            }

            @Override
            public void onLongHold(View view, int position) {
                vibratorService.vibrate(Constants.HOLD_REMOVE_VIBRATION_DURATION);
                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_LONG_HOLD);

                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());
                WishlistInstituteListAdapter.mEventValue.put("action_what", "long_hold");

                if (view.getId() == R.id.wishlist_institute_see_all_layout) {
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_facilities");
                    WishlistInstituteListAdapter.this.mPeekViewAdapter.ToggleFacilitiesLayout();
                }
                else if (view.getId() == R.id.wishlist_btn_details)
                {
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_info");
                    WishlistInstituteListAdapter.this.mPeekViewAdapter.ToggleWishlistInstituteDetails();
                }
                else if (view.getId() == R.id.wishlist_header_view)
                {
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_institute_details");
                    //show institute
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteSelected(WishlistInstituteListAdapter.this.mInstitutes.get(position), true);
                }

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
            }
        });

        this.mPeekAndPop.setOnHoldAndReleaseListener(new PeekAndPop.OnHoldAndReleaseListener() {
            @Override
            public void onHold(View view, int position) {
                vibratorService.vibrate(Constants.HOLD_ENTER_VIBRATION_DURATION);

                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_HOLD_AND_RELEASE);

                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());
                WishlistInstituteListAdapter.mEventValue.put("action_what", "hold_release_held");

                if (view.getId() == R.id.wishlist_institute_btn_call_now)
                {
                    WishlistInstituteListAdapter.this.mCalloutButton.setText(getResourceString(R.string.ACTION_CALL_COLLEGE));
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_call");
                }
                else if (view.getId() == R.id.wishlist_institute_btn_apply_now)
                {
                    WishlistInstituteListAdapter.this.mCalloutButton.setText(getResourceString(R.string.ACTION_APPLY_COLLEGE));
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_apply");
                }
                else if (view.getId() == R.id.wishlist_institute_btn_remove_shortlist)
                {
                    WishlistInstituteListAdapter.this.mCalloutButton.setText(getResourceString(R.string.ACTION_REMOVE_COLLEGE));
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_shortlist");
                }

                WishlistInstituteListAdapter.this.mCalloutButton.animate()
                        .setDuration(100)
                        .alpha(1f)
                        .setInterpolator(new LinearInterpolator())
                        .y(WishlistInstituteListAdapter.this.mPeekView.getHeight() >> 1);

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
            }

            @Override
            public void onLeave(View view, int position) {
                WishlistInstituteListAdapter.this.mCalloutButton.animate()
                        .setDuration(50)
                        .alpha(0f)
                        .setInterpolator(new LinearInterpolator())
                        .y(WishlistInstituteListAdapter.this.mPeekView.getHeight() + WishlistInstituteListAdapter.this.mPeekView.getHeight() >> 1);
            }

            @Override
            public void onRelease(View view, int position) {
                WishlistInstituteListAdapter.this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SHORTLIST_HOLD_AND_RELEASE);

                WishlistInstituteListAdapter.mEventValue.put("institute_name", WishlistInstituteListAdapter.this.mInstitutes.get(position).getName());
                WishlistInstituteListAdapter.mEventValue.put("institute_id", WishlistInstituteListAdapter.this.mInstitutes.get(position).getId());
                WishlistInstituteListAdapter.mEventValue.put("action_what", "hold_release_released");

                WishlistInstituteListAdapter.this.mCalloutButton.animate()
                        .setDuration(100)
                        .alpha(0f)
                        .setInterpolator(new LinearInterpolator());

                vibratorService.vibrate(Constants.HOLD_REMOVE_VIBRATION_DURATION);

                String message = "";

                Institute institute = WishlistInstituteListAdapter.this.mInstitutes.get(position);
                if (view.getId() == R.id.wishlist_institute_btn_call_now) {
                    message = "Calling " + WishlistInstituteListAdapter.this.mInstitutes.get(position).getShort_name();
                    //Call intent
                    Uri number = Uri.parse("tel:" + institute.getL3_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    WishlistInstituteListAdapter.this.mContext.startActivity(callIntent);
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_call");
                }
                else if (view.getId() == R.id.wishlist_institute_btn_apply_now) {
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteApplied(institute, position);
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_apply");
                }
                else if (view.getId() == R.id.wishlist_institute_btn_remove_shortlist) {
                    message = "Removing " + institute.getShort_name() +  " from Wishlist";
                    WishlistInstituteListAdapter.this.mListener.OnWishlistInstituteRemoved(institute, position);
                    WishlistInstituteListAdapter.mEventValue.put("action_on", "institute_peek_shortlist");
                }

                if (message != "")
                    Snackbar.make(WishlistInstituteListAdapter.this.mContext.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();

                //Events
                AnalyticsUtils.SendAppEvent(WishlistInstituteListAdapter.this.mEventCategory, WishlistInstituteListAdapter.this.mEventAction, WishlistInstituteListAdapter.this.mEventValue, WishlistInstituteListAdapter.this.mContext);

                WishlistInstituteListAdapter.this.mResetEventVariables();
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
        /*if (instituteHolder.instituteCard.getTag() == null)
            instituteHolder.instituteCard.setTag("");*/

        Institute institute = this.mInstitutes.get(position);
        institute.setPosition(position);

        WishlistInstituteListAdapter.this.mPeekAndPop.addLongClickView(instituteHolder.instituteCard, position);

        String text = "";

        if (institute.getAcronym() != null && !institute.getAcronym().equalsIgnoreCase("None") && !institute.getAcronym().isEmpty())
            text = institute.getAcronym();
        else if (institute.getShort_name() != null && !institute.getShort_name().equalsIgnoreCase("None") && !institute.getShort_name().isEmpty())
            text += institute.getShort_name();
        else
            text = institute.getName();

        if (institute.getCity_name() != null && !institute.getCity_name().isEmpty())
            if (! text.toLowerCase().contains(institute.getCity_name().toLowerCase()))
                text += " " + institute.getCity_name();

        institute.setShort_name(text);

        instituteHolder.instituteLogo.setDefaultImageResId(R.drawable.ic_cd);
        instituteHolder.instituteLogo.setErrorImageResId(R.drawable.ic_cd);

        if (institute.getLogo() != null && !institute.getLogo().isEmpty())
            instituteHolder.instituteLogo.setImageUrl(institute.getLogo(), this.mImageLoader);

        //setting handmade shortname as shortname for institute
        instituteHolder.instituteShortName.setText(text);

        //Setting event listener on textview, so that when layout phase is over we get a callback and do our thing \m/ !!
        /*if (!instituteHolder.instituteCard.getTag().equals("set"))
        {
            instituteHolder.instituteShortName.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    instituteHolder.instituteShortName.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    instituteHolder.instituteShortName.setBackgroundDrawable(new BitmapDrawable(WishlistInstituteListAdapter.this.mContext.getResources(), BlurBuilder.blur(instituteHolder.instituteShortName)));
                }
            });
        }*/

        instituteHolder.instituteCard.setTag("set");
    }

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
        this.mListener.OnWishlistInstituteSelected(WishlistInstituteListAdapter.this.mInstitutes.get(position), false);
    }

    private void mResetEventVariables()
    {
        this.mEventAction = "";
        WishlistInstituteListAdapter.mEventValue.clear();
    }
}