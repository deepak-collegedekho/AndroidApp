package com.collegedekho.app.fragment.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.resource.Constants;

/**
 * Created by sureshsaini on 22/2/17.
 */

public abstract class BaseLoginFragment extends BaseFragment {


    protected abstract void onRequestForOTP();

    protected void checkForSmsPermission() {
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Use the Builder class for convenient dialog construction
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage(R.string.sms_permission)
//                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECEIVE_SMS},
//                                    Constants.RC_HANDLE_SMS_PERM);
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//            // Create the AlertDialog object and return it
//            builder.create();
//            builder.show();
//
//        } else {
            onRequestForOTP();
     //   }
    }

    @Override
    public String getEntity() {
        return null;
    }
}
