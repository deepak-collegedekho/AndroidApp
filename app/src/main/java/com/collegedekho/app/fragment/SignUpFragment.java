package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.NetworkUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        fbLoginutton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
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
                ((MainActivity)getActivity()).showProgressDialog("Signing facebook account");
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
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put(Constants.USER_NAME, json.getString("name"));
                        JSONObject pictureJsonObj = json.getJSONObject("picture");
                        JSONObject data = pictureJsonObj.getJSONObject("data");
                        String image = data.getString("url");

                        hashMap.put(Constants.USER_IMAGE, image);
                        // hashMap.put(Constants.USER_EMAIL, json.getString("email"));
                        mUserSignedIn(hashMap, mMessage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

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
        // TODO: Update argument type and name
        public void onUserSignedIn(HashMap hashMap, String msg);
    }
    private void mUserSignedIn(HashMap hashMap, String msg)
    {
        if(mListener != null)
        mListener.onUserSignedIn(hashMap, msg);


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
       /* else if(view.getId() == R.id.facebook_sign_in)
        {
            // Callback registration
            mFacebookCallbackListener(fbLoginutton);
        }*/
        else  if (view.getId() == R.id.signIn_button)
        {
            String name = ((EditText) getView().findViewById(R.id.name)).getText().toString();
              if (name == null || name.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap hashMap = new HashMap<String, String>();
                hashMap.put(Constants.USER_NAME, name);
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

}
