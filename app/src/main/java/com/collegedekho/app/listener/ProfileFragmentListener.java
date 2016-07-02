package com.collegedekho.app.listener;

import android.content.Intent;

/**
 * Created by sureshsaini on 2/7/16.
 */

 public interface ProfileFragmentListener {
    void requestForCropProfileImage(Intent data);
    void uploadUserProfileImage();
}
