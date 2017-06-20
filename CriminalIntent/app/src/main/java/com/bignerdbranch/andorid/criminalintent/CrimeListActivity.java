package com.bignerdbranch.andorid.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Jun on 2017-06-20.
 */

public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
