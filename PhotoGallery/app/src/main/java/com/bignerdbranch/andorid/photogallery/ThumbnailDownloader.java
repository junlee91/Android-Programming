package com.bignerdbranch.andorid.photogallery;

import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by Jun on 2017-06-30.
 */

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";

    public ThumbnailDownloader(){
        super(TAG);
    }

    public void queueThumbnail(T target, String url){
        Log.i(TAG, "Got a URL: " + url);
    }
}
