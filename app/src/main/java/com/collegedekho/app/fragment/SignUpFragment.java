package com.collegedekho.app.fragment;

import android.app.Activity;
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
import com.collegedekho.app.utils.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnSignUpListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends BaseFragment implements View.OnClickListener
{

    private static final String TAG ="SignUpFragment";
    private static final String MSG ="chat_msg";
    private LoginFragment.OnSignUpListener mListener;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (LoginFragment.OnSignUpListener) activity;
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

        if(view.getId() == R.id.signUp_button)
        {
            String email    = ((EditText) getView().findViewById(R.id.email)).getText().toString();
            String password = ((EditText) getView().findViewById(R.id.password)).getText().toString();
            String name     = ((EditText) getView().findViewById(R.id.name)).getText().toString();
            String phone    = ((EditText) getView().findViewById(R.id.phone)).getText().toString();
            if (name == null || name.isEmpty())
            {
                Utils.displayToast(getActivity(), Constants.NAME_EMPTY);
                return;
            }
            else if(!isValidName(name)){
                Utils.displayToast(getActivity(), Constants.NAME_INVALID);
                return;
            }
            else if(phone == null || phone.isEmpty()) {
                Utils.displayToast(getActivity(), Constants.PHONE_EMPTY);
                return;
            }
            else if(phone.length() <= 9 ||!isValidPhone(phone)){
                Utils.displayToast(getActivity(),Constants.PHONE_INVALID);
                return;
            }
            else if (email == null || email.isEmpty()){
                Utils.displayToast(getActivity(), Constants.EMAIL_EMPTY);
                return;
            }
            else if(!isValidEmail(email)){
                Utils.displayToast(getActivity(), Constants.EMAIL_INVALID);
                return;
            }
            else if (password == null || password.isEmpty()){
                Utils.displayToast(getActivity(), Constants.PASSWORD_EMPTY);
                return;
            }

            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constants.USER_EMAIL, email);
            hashMap.put(Constants.USER_PASSWORD, password);
            hashMap.put(Constants.USER_NAME, name);
            hashMap.put(Constants.USER_PHONE, phone);
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
