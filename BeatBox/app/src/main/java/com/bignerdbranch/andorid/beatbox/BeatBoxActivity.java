package com.bignerdbranch.andorid.beatbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return BeatBoxFragment.newInstance();
    }
}
