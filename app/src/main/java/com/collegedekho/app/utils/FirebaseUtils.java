package com.collegedekho.app.utils;

import com.collegedekho.app.resource.Constants;

/**
 * Created by root on 3/8/16.
 */
public class FirebaseUtils {
    public static String appIndexingLink(String urlString){
        urlString = urlString.replace("https://","");
        String appNdx= new StringBuilder("android-app://").append("com.collegedekho.app/").append("https/").append("www.collegedekho.com").append(urlString).toString();
        return appNdx;
    }
}
