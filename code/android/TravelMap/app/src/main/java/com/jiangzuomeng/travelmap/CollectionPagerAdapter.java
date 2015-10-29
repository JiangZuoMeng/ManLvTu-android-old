package com.jiangzuomeng.travelmap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wilbert on 2015/10/27.
 */
public class CollectionPagerAdapter  extends FragmentPagerAdapter {
    private int numberOfType;
    private List<SingleFragement> fragements;

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public CollectionPagerAdapter(FragmentManager fm, int num, List<SingleFragement> f) {
        super(fm);
        numberOfType = num;
        fragements = f;
    }

    @Override
    public Fragment getItem(int position) {
        return fragements.get(position);
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
