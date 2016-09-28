package com.collegedekho.app.listener;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by sureshsaini on 2/7/16.
 */

 public interface ProfileFragmentListener {
    void requestForCropProfileImage(Intent data);
    void uploadUserProfileImage(Uri ur);
    void deleteTempImageFile();
}
