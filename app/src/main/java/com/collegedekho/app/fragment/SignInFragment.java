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
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by suresh on 23/9/15.
 */
public class SignInFragment extends  BaseFragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG ="SignInFragment";
    private static final String MSG ="chat_msg";
    private LoginFragment1.OnSignUpListener mListener;

    public GoogleApiClient mGoogleApiClient;

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

        SignInButton googleSignIn = (SignInButton) view.findViewById(R.id.google_sign_in);
        googleSignIn.setOnClickListener(this);
        googleSignIn.setSize(SignInButton.SIZE_WIDE);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();



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

        if(view.getId() == R.id.google_sign_in)
        {
            ((MainActivity)getActivity()).showProgressDialog("Synchronizing with Google Account");
            mShouldResolve = true;
            mGoogleApiClient.connect();
        }
        else  if (view.getId() == R.id.signIn_button)
        {

            String email = ((EditText) getView().findViewById(R.id.signin_email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.signin_password)).getText().toString();
            if (email == null || email.isEmpty())
            {
                Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_LONG).show();
                return;
            }
            else if(!isValidEmail(email))
            {
                Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_LONG).show();
                return;
            }
            else if (password == null || password.isEmpty()) {
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

            mIsResolving = false;
            if(!mGoogleApiClient.isConnecting())
                mGoogleApiClient.connect();
        }
        else {
            MainActivity.callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

// [END on_connection_failed]

    private void showErrorDialog(ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            // Show the default Google Play services error dialog which may still start an intent
            // on our behalf if the user can resolve the issue.
            GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(), RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;
                            //updateUI(false);
                        }
                    }).show();
        } else {

            ((MainActivity)getActivity()).hideProgressDialog();
            // No default Google Play Services error, display a message to the user.
            //  String errorString = GoogleApiAvailability.getInstance().getErrorString(errorCode);
            Toast.makeText(getActivity(), "Google SignIn is failed", Toast.LENGTH_LONG).show();

            mShouldResolve = false;
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

// Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d("", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            Log.e("","");
            //updateUI(false);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e(TAG, "Google sign in connected");

        //Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(getActivity());

        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        final HashMap params =  new HashMap<>();
        if(currentPerson != null)
        {
            // get google user account info
            params.put(MainActivity.getResourceString(R.string.USER_NAME), currentPerson.getDisplayName());
            params.put("picture", currentPerson.getImage().getUrl());
            params.put(MainActivity.getResourceString(R.string.USER_IMAGE),currentPerson.getImage().getUrl());// for local save
            params.put(MainActivity.getResourceString(R.string.USER_LOCALE), currentPerson.getLanguage());
            params.put(MainActivity.getResourceString(R.string.USER_ID), currentPerson.getId());
            params.put(MainActivity.getResourceString(R.string.USER_LINK), currentPerson.getUrl());
            params.put(MainActivity.getResourceString(R.string.USER_VERIFIED_EMAIL), currentPerson.isVerified());

            Person.Name personName =  currentPerson.getName();
            if(personName != null) {
                params.put(MainActivity.getResourceString(R.string.USER_GIVEN_NAME), personName.getGivenName());
                params.put(MainActivity.getResourceString(R.string.USER_FAMILY_NAME), personName.getFamilyName());
            }
             // get gender info of google user
            int personGender =  currentPerson.getGender();
            if(personGender == 0){
                params.put(MainActivity.getResourceString(R.string.USER_GENDER), "Male");
            }
            else if(personGender == 1){
                params.put(MainActivity.getResourceString(R.string.USER_GENDER), "Female");
            }
            else if(personGender == 2){
                params.put(MainActivity.getResourceString(R.string.USER_GENDER), "Other");
            }

            //params.put(MainActivity.getResourceString(R.string.USER_EXPIRE_AT), currentPerson.getLanguage());
        }
        // get user email id
        final String email =  Plus.AccountApi.getAccountName(mGoogleApiClient);
        params.put(MainActivity.getResourceString(R.string.USER_EMAIL), email);

        // request to get token of google plus user
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... param1) {
               try {
                    // We can retrieve the token to check via
                    // tokeninfo or to pass to a service-side
                    // application.
                    String token = GoogleAuthUtil.getToken(getActivity(),
                            email, "oauth2:" + Scopes.PLUS_LOGIN);
                   params.put(MainActivity.getResourceString(R.string.USER_TOKEN),token);
                } catch (UserRecoverableAuthException e) {
                    // This error is recoverable, so we could fix this
                    // by displaying the intent to the user.
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GoogleAuthException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
               // super.onPostExecute(o);
                Log.e(TAG,"");
               // mGooglePlusLogin(params, mMessage);
            }
        };
        task.execute((Void) null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Connection suspended");

    }
    private static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private static boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }

}
