package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment1.OnSignUpListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener
{

    private static final String TAG ="SignUpFragment";
    private static final String MSG ="chat_msg";
    private LoginFragment1.OnSignUpListener mListener;
    private String mMessage;

    public static SignUpFragment newInstance(String value) {
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
        view.findViewById(R.id.signUp_button).setOnClickListener(this);
        return view;
    }


    private void mUserRegisteration(HashMap params, String msg)
    {
        if(mListener != null)
            mListener.onUserSignUp(Constants.BASE_URL + "auth/register/", params, msg);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (LoginFragment1.OnSignUpListener) context;
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
        if( new NetworkUtils(getActivity(),null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED)
        {
            Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_LONG).show();
            return;
        }
        if(view.getId() == R.id.signUp_button)
        {
            String email    = ((EditText) getView().findViewById(R.id.signup_email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.signup_password)).getText().toString();
            String name     = ((EditText) getView().findViewById(R.id.signup_name)).getText().toString();
            String phone    = ((EditText) getView().findViewById(R.id.signup_phone)).getText().toString();
            if (name == null || name.isEmpty())
            {
                mListener.displayMessage(R.string.NAME_EMPTY);
                return;
            }
            else if(!isValidName(name)){
                mListener.displayMessage(R.string.NAME_INVALID);
                return;
            }
            else if(phone == null || phone.isEmpty()) {
                mListener.displayMessage(R.string.PHONE_EMPTY);
                return;
            }
            else if(phone.length() <= 9 ||!isValidPhone(phone)){
                mListener.displayMessage(R.string.PHONE_INVALID);
                return;
            }
            else if (email == null || email.isEmpty()){
                mListener.displayMessage(R.string.EMAIL_EMPTY);
                return;
            }
            else if(!isValidEmail(email)){
                mListener.displayMessage(R.string.EMAIL_INVALID);
                return;
            }
            else if (password == null || password.isEmpty()){
                mListener.displayMessage(R.string.PASSWORD_EMPTY);
                return;
            }
            else if (password.length() < 6 ){
                mListener.displayMessage(R.string.PASSWORD_INVALID);
                return;
            }

            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(MainActivity.getResourceString(R.string.USER_EMAIL), email);
            hashMap.put(MainActivity.getResourceString(R.string.USER_PASSWORD), password);
            hashMap.put(MainActivity.getResourceString(R.string.USER_NAME), name);
            hashMap.put(MainActivity.getResourceString(R.string.USER_PHONE), phone);
            mUserRegisteration(hashMap, mMessage);
        }
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
}
