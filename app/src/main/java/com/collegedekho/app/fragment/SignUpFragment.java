package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnSignUpListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG ="SignUpFragment";
    private static final String MSG ="chat_msg";
    private OnSignUpListener mListener;

    public GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;

    private String mMessage;
    private LoginButton fbLoginutton;

    public static SignUpFragment newInstance(String value, boolean flag) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(MSG, value);
        fragment.setArguments(args);
        return fragment;
    }

    public SignUpFragment() {
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        view.findViewById(R.id.signIn_button).setOnClickListener(this);
        SignInButton googleSignIn = (SignInButton) view.findViewById(R.id.google_sign_in);
        googleSignIn.setOnClickListener(this);
        googleSignIn.setSize(SignInButton.SIZE_WIDE);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();


         fbLoginutton = (LoginButton) view.findViewById(R.id.facebook_sign_in);
        fbLoginutton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_likes"));
        //fbLoginutton.setOnClickListener(this);
        mFacebookCallbackListener(fbLoginutton);

        return view;
    }
    private  void mFacebookCallbackListener(LoginButton loginButton)
    {
        loginButton.registerCallback(MainActivity.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("", "on connected");
                ((MainActivity) getActivity()).showProgressDialog("Signing facebook account");
                loginResult.getAccessToken();
                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                }
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("", "on cancel");
                Toast.makeText(getActivity(), "Facebook SignIn is failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {

                Log.e("", "on error");
                Toast.makeText(getActivity(), "Facebook SignIn has some error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        // for profile image
                        JSONObject pictureJsonObj = json.getJSONObject("picture");
                        JSONObject data = pictureJsonObj.getJSONObject("data");
                        String image = data.getString("url");

                        HashMap hashMap = new HashMap<String, String>();
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
                        SignUpFragment.this.mFacebookLogin(hashMap, mMessage);
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

    private void mFacebookLogin(HashMap hashMap, String msg)
    {
        if(mListener != null)
            mListener.onFacebookLogin(hashMap, msg);

    }

    private void mUserSignedIn(HashMap hashMap, String msg)
    {
        if(mListener != null)
            mListener.onUserSignedIn(hashMap, msg);


    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSignUpListener {

        void onUserSignedIn(HashMap hashMap, String msg);
        void onFacebookLogin(HashMap hashMap, String msg);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnSignUpListener) activity;
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
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

    @Override
    public void onClick(View view) {
        if( new NetworkUtils(getActivity(),null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED)
        {
            Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(view.getId() == R.id.google_sign_in)
        {
            ((MainActivity)getActivity()).showProgressDialog("Synchronizing Google Account");
            mShouldResolve = true;
            mGoogleApiClient.connect();
        }
        else  if (view.getId() == R.id.signIn_button)
        {
            String name = ((EditText) getView().findViewById(R.id.name)).getText().toString();
            if (name == null || name.isEmpty())
            {
                Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constants.USER_NAME, name);
            mUserSignedIn(hashMap, mMessage);

            /*String email = ((EditText) getView().findViewById(R.id.email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.password)).getText().toString();
            if (email == null || email.isEmpty())
             {
                    Toast.makeText(getActivity(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
             }
            else if(!isValidEmail(email))
            {
                Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (password == null || password.isEmpty()) {
                  Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_SHORT).show();
                  return;
              }
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constants.USER_EMAIL, email);
            hashMap.put(Constants.USER_PASSWORD, password);
            mUserSignedIn(hashMap, mMessage);*/

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
            Toast.makeText(getActivity(), "Google SignIn is failed", Toast.LENGTH_SHORT).show();

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
                    Log.e("", "Could not resolve ConnectionResult.", e);
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
        Log.e("","connected");
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        HashMap hashMap =  new HashMap<String, String>();
        if(currentPerson != null)
        {
            String name = currentPerson.getDisplayName();
            String image = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            hashMap.put(Constants.USER_NAME, name);
            hashMap.put(Constants.USER_IMAGE,image);
        }
        else
        {
            //TODO:: delete when gooogle service.json is done
            hashMap.put(Constants.USER_NAME, "default");
        }
        String email =  Plus.AccountApi.getAccountName(mGoogleApiClient);
        Log.e("", "email is " + email);

        // hashMap.put(Constants.USER_EMAIL, email);
        mUserSignedIn(hashMap, mMessage);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("", "Connection suspended");

    }
    private static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
