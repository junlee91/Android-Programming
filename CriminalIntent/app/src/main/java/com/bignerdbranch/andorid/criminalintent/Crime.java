package com.bignerdbranch.andorid.criminalintent;

import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Jun on 2017-06-19.
 */

public class Crime {

    private UUID mId;           // Universally Unique Identifier
    private String mTitle;

    public Crime(){
        mId = UUID.randomUUID();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
