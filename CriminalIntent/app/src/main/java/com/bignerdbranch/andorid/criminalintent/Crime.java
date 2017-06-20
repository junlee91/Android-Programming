package com.bignerdbranch.andorid.criminalintent;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by Jun on 2017-06-19.
 */

public class Crime {

    private UUID mId;           // Universally Unique Identifier
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }
}
