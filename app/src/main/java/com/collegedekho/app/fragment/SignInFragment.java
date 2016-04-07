package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by suresh on 23/9/15.
 */
public class SignInFragment extends  BaseFragment implements View.OnClickListener
        {

    private static final String TAG ="SignInFragment";
    private static final String MSG ="chat_msg";
    private LoginFragment1.OnSignUpListener mListener;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private String mMessage;

    public static SignInFragment newInstance(String value) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(MSG, value);
        fragment.setArguments(args);
        return fragment;
    }

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString(MSG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceISState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        view.findViewById(R.id.signIn_button).setOnClickListener(this);


        LoginButton fbLoginutton = (LoginButton) view.findViewById(R.id.facebook_sign_in);
        fbLoginutton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        mFacebookCallbackListener(fbLoginutton);

        return view;
    }

    /**
     * This method is used to register this fragment with  facebook login account
     *  and callback methods call when  try to login with facebook
      */
    private  void mFacebookCallbackListener(LoginButton loginButton)
    {/*
        loginButton.registerCallback(.callbackManager, new FacebookCallback<LoginResult>() {
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
        });*/
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
                        JSONObject pictureJsonObj = json.getJSONObject("picture");
                        JSONObject data = pictureJsonObj.getJSONObject("data");
                        String image = data.getString("url");
                        if(MainActivity.user != null)
                            MainActivity.user.setImage(image);

                        HashMap hashMap = new HashMap<>();
                        hashMap.put(MainActivity.getResourceString(R.string.USER_FIRST_NAME), json.getString("first_name"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_LAST_NAME), json.getString("last_name"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_VERIFIED), json.getString("verified"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_NAME), json.getString("name"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_LOCALE), json.getString("locale"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_GENDER), json.getString("gender"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_UPDATED_TIME), json.getString("updated_time"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_LINK), json.getString("link"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_ID), json.getString("id"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_TIMEZONE), json.getString("timezone"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_EMAIL), json.getString("email"));
                        hashMap.put(MainActivity.getResourceString(R.string.USER_IMAGE), image);
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        hashMap.put(MainActivity.getResourceString(R.string.USER_TOKEN), accessToken.getToken());
                        hashMap.put(MainActivity.getResourceString(R.string.USER_EXPIRE_AT), new SimpleDateFormat("yyyy-MM-dd").format(accessToken.getExpires()) + "T" + new SimpleDateFormat("HH:mm:ss").format(accessToken.getExpires()));
                        SignInFragment.this.mFacebookLogin(hashMap, mMessage);
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


    private void mUserSignedIn(HashMap params, String msg)
    {
        if(mListener != null)
            mListener.onUserSignIn(Constants.BASE_URL + "auth/login/", params, msg);

    }
    private void mFacebookLogin(HashMap params, String msg)
    {
        if(mListener != null)
            mListener.onUserSignIn(Constants.BASE_URL + "auth/facebook/", params, msg);

    }
    private void mGooglePlusLogin(HashMap params, String msg)
    {
        if(mListener != null)
            mListener.onUserSignIn(Constants.BASE_URL + "auth/google/", params, msg);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (LoginFragment1.OnSignUpListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        if( new NetworkUtils(getActivity(),null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED)
        {
            Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_LONG).show();
            return;
        }

       if (view.getId() == R.id.signIn_button)
        {

            String email = ((EditText) getView().findViewById(R.id.signin_email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.signin_password)).getText().toString();
            if (email == null || email.isEmpty())
            {
                Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_LONG).show();
                return;
            }
           if (password == null || password.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_LONG).show();
                return;
            }
            HashMap hashMap = new HashMap<>();
            hashMap.put(MainActivity.getResourceString(R.string.USER_EMAIL), email);
            hashMap.put(MainActivity.getResourceString(R.string.USER_PASSWORD), password);
            mUserSignedIn(hashMap, mMessage);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != getActivity().RESULT_OK) {
                mShouldResolve = false;
                ((MainActivity)getActivity()).hideProgressDialog();
            }


        }
        else {
           // MainActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }


}
