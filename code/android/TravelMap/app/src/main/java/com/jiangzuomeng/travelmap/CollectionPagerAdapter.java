package com.jiangzuomeng.travelmap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by wilbert on 2015/10/27.
 */
public class CollectionPagerAdapter  extends FragmentStatePagerAdapter {
    private int numberOfType;
    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public CollectionPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        numberOfType = num;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SingleFragement();
        Bundle args = new Bundle();
        String s = "position " + position;
        args.putString(SingleFragement.TYPE, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfType;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Position " + position;
    }
}
