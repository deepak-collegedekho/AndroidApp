package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import java.util.HashMap;
/**
 * Created by sureshsaini on 6/1/16.
 */
public class ProfileEditFragment extends BaseFragment {

    private onProfileUpdateListener mListener ;

    public ProfileEditFragment(){
        // required empty constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ProfileEditFragment newInstance(){
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        rootView.findViewById(R.id.profile_save_button).setOnClickListener(this);

        EditText mProfileName   = (EditText) rootView.findViewById(R.id.profile_edit_name);
        EditText mProfilePhone  = (EditText) rootView.findViewById(R.id.profile_edit_phone);
        //EditText mProfileEmail  = (EditText) rootView.findViewById(R.id.profile_edit_email);

        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        mProfilePhone.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER)
                    mUpdateProfile();
                return false;
            }

        });

        if(MainActivity.user != null)
        {
            String name = MainActivity.user.getName();
            String email = MainActivity.user.getEmail();
            String phone = MainActivity.user.getPhone_no();

            if(phone != null)
                phone = phone.replace(" ","");

            if(name.contains("Anonymous User"))
            {
                if(MainActivity.user.profileData[0] != null)
                    mProfileName.setText(MainActivity.user.profileData[0]);
            }else
                mProfileName.setText(name);


          /*  if(email.contains("anonymouscollegedekho"))
                mProfileEmail.setText(MainActivity.user.getPrimaryEmail());
            else
                mProfileEmail.setText(email);*/

            if(phone== null || phone.isEmpty()) {
                String mPhone = MainActivity.user.getPrimaryPhone();
                if(mPhone != null)
                    mPhone = mPhone.replace(" ","");
                 mProfilePhone.setText(mPhone);
            }
            else
                mProfilePhone.setText(phone);

            String image = MainActivity.user.getImage();
            if (image != null && ! image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
       }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            MainActivity activity = (MainActivity)getActivity();
            activity.showOverflowMenu(true);

        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            MainActivity activity = (MainActivity)getActivity();
            activity.showOverflowMenu(false);

        }catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if( new NetworkUtils(getActivity(),null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED)
        {
            Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_LONG).show();
            return;
        }
        if(view.getId() == R.id.profile_save_button)
        {
           mUpdateProfile();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (onProfileUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onProfileUpdateListener");
        }
    }

    private void mUpdateProfile(){
        View v = getView();
        if(v == null)return;

        // String email    = ((EditText) v.findViewById(R.id.profile_edit_email)).getText().toString();
        String name     = ((EditText) v.findViewById(R.id.profile_edit_name)).getText().toString();
        String phone    = ((EditText) v.findViewById(R.id.profile_edit_phone)).getText().toString();
        if (name == null || name.isEmpty())
        {
            Utils.DisplayToast(getActivity(), Constants.NAME_EMPTY);
            return;
        }
        else if(!Utils.isValidName(name)){
            Utils.DisplayToast(getActivity(), Constants.NAME_INVALID);
            return;
        }
        else if(phone == null || phone.isEmpty()) {
            Utils.DisplayToast(getActivity(), Constants.PHONE_EMPTY);
            return;
        }
        else if(phone.length() <= 9 ||!Utils.isValidPhone(phone)){
            Utils.DisplayToast(getActivity(), Constants.PHONE_INVALID);
            return;
        }
           /* else if (email == null || email.isEmpty()){
                Utils.DisplayToast(getActivity(), Constants.EMAIL_EMPTY);
                return;
            }
            else if(!Utils.isValidEmail(email)){
                Utils.DisplayToast(getActivity(), Constants.EMAIL_INVALID);
                return;
            }*/

        HashMap<String, String> hashMap = new HashMap<>();
        // hashMap.put(Constants.USER_EMAIL, email);
        hashMap.put(Constants.USER_NAME, name);
        hashMap.put(Constants.USER_PHONE, phone);
        if(mListener!=null) {
            this.mListener.onProfileUpdated(hashMap);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface  onProfileUpdateListener
    {
        void  onProfileUpdated(HashMap<String, String> hashMap);
    }

}
