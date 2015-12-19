package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sureshsaini on 20/11/15.
 */
public class LoginFragment extends  BaseFragment{

    private OnUserSignUpListener mListener;

    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return  fragment;
    }
    public LoginFragment(){
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_splash_login,container, false);
        //rootView.findViewById(R.id.sign_up_button).setOnClickListener(this);
        rootView.findViewById(R.id.sign_up_skip).setOnClickListener(this);

        LoginButton fbLoginutton = (LoginButton) rootView.findViewById(R.id.facebook_sign_in);
        fbLoginutton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_likes", "user_education_history"));
        mFacebookCallbackListener(fbLoginutton);

        return rootView;
    }

    /**
     * This method is used to register this fragment with  facebook login account
     *  and callback methods call when  try to login with facebook
     */
    private  void mFacebookCallbackListener(LoginButton loginButton)
    {
        loginButton.registerCallback(MainActivity.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "facebook login successfully connected");
                ((MainActivity) getActivity()).showProgressDialog("Signing with facebook account");
                loginResult.getAccessToken();
                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                }
            }

            @Override
            public void onCancel() {
                // App code
                Log.e(TAG, "facebook login canceled");
                Toast.makeText(getActivity(), "Facebook SignIn is failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {

                Log.e(TAG, "facebook login on error");
                Toast.makeText(getActivity(), "Facebook SignIn has some error", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     *  This method request user profile info after successfully
     *  login with facebook account
     */
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        // get user profile info
                       /* JSONObject pictureJsonObj = json.getJSONObject("picture");
                        JSONObject data = pictureJsonObj.getJSONObject("data");*/

                        //String image = data.getString("url");
                        String image ="https://graph.facebook.com/" + json.optString("id") + "/picture?type=large";

                        if (MainActivity.user == null)
                            MainActivity.user = new User();

                        MainActivity.user.setImage(image);

                        HashMap hashMap = new HashMap<>();
                        hashMap.put(Constants.USER_FIRST_NAME, json.getString("first_name"));
                        hashMap.put(Constants.USER_LAST_NAME, json.getString("last_name"));
                        hashMap.put(Constants.USER_VERIFIED, json.getString("verified"));
                        hashMap.put(Constants.USER_NAME, json.getString("name"));
                        hashMap.put(Constants.USER_LOCALE, json.getString("locale"));
                        hashMap.put(Constants.USER_GENDER, json.getString("gender"));
                        hashMap.put(Constants.USER_UPDATED_TIME, json.getString("updated_time"));
                        hashMap.put(Constants.USER_LINK, json.getString("link"));
                        hashMap.put(Constants.USER_ID, json.getString("id"));
                        hashMap.put(Constants.USER_TIMEZONE, json.getString("timezone"));
                        hashMap.put(Constants.USER_EMAIL, json.getString("email"));
                        hashMap.put(Constants.USER_IMAGE, image);
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        hashMap.put(Constants.USER_TOKEN, accessToken.getToken());
                        hashMap.put(Constants.USER_EXPIRE_AT, new SimpleDateFormat("yyyy-MM-dd").format(accessToken.getExpires()) + "T" + new SimpleDateFormat("HH:mm:ss").format(accessToken.getExpires()));
                        mFacebookLogin(hashMap);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,verified,name,locale,gender,updated_time,link,id,timezone,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            MainActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnUserSignUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onClick(View view) {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_LONG).show();
            return;
        }
        if(view.getId() == R.id.sign_up_skip)
        {
          mSkipUserLogin();
        }
       /* else if (view.getId() == R.id.sign_up_button) {
            String email = ((EditText) getView().findViewById(R.id.splash_login_email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.splash_login_password)).getText().toString();
            String name = ((EditText) getView().findViewById(R.id.splash_login_name)).getText().toString();
            String phone = ((EditText) getView().findViewById(R.id.splash_login_phone)).getText().toString();
            if (name == null || name.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.NAME_EMPTY);
                return;
            } else if (!isValidName(name)) {
                Utils.DisplayToast(getActivity(), Constants.NAME_INVALID);
                return;
            } else if (phone == null || phone.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.PHONE_EMPTY);
                return;
            } else if (phone.length() <= 9 || !isValidPhone(phone)) {
                Utils.DisplayToast(getActivity(), Constants.PHONE_INVALID);
                return;
            } else if (email == null || email.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.EMAIL_EMPTY);
                return;
            } else if (!isValidEmail(email)) {
                Utils.DisplayToast(getActivity(), Constants.EMAIL_INVALID);
                return;
            } else if (password == null || password.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.PASSWORD_EMPTY);
                return;
            } else if (password.length() < 6) {
                Utils.DisplayToast(getActivity(), Constants.PASSWORD_INVALID);
                return;
            }

            HashMap hashMap = new HashMap<>();
            hashMap.put(Constants.USER_EMAIL, email);
            hashMap.put(Constants.USER_PASSWORD, password);
            hashMap.put(Constants.USER_NAME, name);
            hashMap.put(Constants.USER_PHONE, phone);
            mUserSignUp(hashMap);
        }*/
    }
    private void mUserSignUp(HashMap params)
    {
        if(mListener != null)
            mListener.onSplashUserSignUp(params);
    }

    private void mSkipUserLogin()
    {
        if(mListener != null)
            mListener.onSkipUserLogin();
    }

    private void mFacebookLogin(HashMap params)
    {
        if(mListener != null)
            mListener.onFacebookLogin(params);
    }

    private static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private static boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

    private static boolean isValidName(CharSequence target) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }

    public interface OnUserSignUpListener {

      void onSplashUserSignUp(HashMap<String,String> params);

        void onSkipUserLogin();

        void onFacebookLogin(HashMap<String,String> params);

    }

}
