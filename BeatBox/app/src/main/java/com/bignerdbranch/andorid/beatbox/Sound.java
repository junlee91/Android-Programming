package com.bignerdbranch.andorid.beatbox;

/**
 * Created by Jun on 2017-06-27.
 */

public class Sound {

    private String mAssetPath;
    private String mName;

    public Sound(String assetPath){
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length-1];
        mName = filename.replace(".wav", "");
    }

    public String getmAssetPath(){
        return mAssetPath;
    }

    public String getName(){
        return mName;
    }
}
