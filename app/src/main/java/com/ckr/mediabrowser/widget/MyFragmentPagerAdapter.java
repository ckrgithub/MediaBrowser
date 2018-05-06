package com.ckr.mediabrowser.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ckr.mediabrowser.view.BaseFragment;

/**
 * Created by PC大佬 on 2018/5/6.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    BaseFragment[] fragments;
    String[] titles;

    public MyFragmentPagerAdapter(FragmentManager fm, BaseFragment[] fragmentList, String[] titles) {
        super(fm);
        this.fragments = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
