package com.bignerdbranch.andorid.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Jun on 2017-06-30.
 */

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private Handler mRequestHandler;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;

    private LruCache<T, Bitmap> mMemoryCache;

    public interface ThumbnailDownloadListener<T>{
        // This will be called when an image has completely downloaded and ready to set UI.
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener){
        mThumbnailDownloadListener = listener;
    }


    public ThumbnailDownloader(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;

        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory/8;

        mMemoryCache = new LruCache<T, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(T target, Bitmap bitmap){
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(T target, Bitmap bitmap){
        if(getBitmapFromMemCache(target) == null){
            mMemoryCache.put(target, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(T target){
        return mMemoryCache.get(target);
    }

    @Override
    protected void onLooperPrepared(){
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == MESSAGE_DOWNLOAD){
                    T target = (T)msg.obj;

                    // Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target); // Start Download!!
                }
            }
        };
    }

    public void queueThumbnail(T target, String url){
        // Log.i(TAG, "Got a URL: " + url);

        if(url == null)
        {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                    .sendToTarget();
        }
    }

    public void clearQueue(){
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
    }

    private void handleRequest(final T target){
        try{
            final String url = mRequestMap.get(target);
            if(url == null) return;

            final Bitmap bitmap;

            if(getBitmapFromMemCache(target) != null)
            {
                bitmap = getBitmapFromMemCache(target);
            }
            else {

                byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
                bitmap = BitmapFactory
                        .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                // Log.i(TAG, "Bitmap created");

                addBitmapToMemoryCache(target, bitmap);
            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    // mRespnseHandler is connected to Looper from main thread.
                    // So this will be executed on main thread.
                    if(mRequestMap.get(target) != url){
                        return;
                    }
                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });


        } catch (IOException ioe){
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
}
