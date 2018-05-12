package com.ckr.mediabrowser.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.view.BaseCreator;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/6.
 */

public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
	private static final String TAG = "MyFragmentPagerAdapter";
	BaseFragment[] fragments;
	String[] titles;
	BaseCreator[] factories;

	public MyFragmentPagerAdapter(FragmentManager fm,BaseFragment[] fragments, String[] titles, BaseCreator[] factories) {
		super(fm);
		this.fragments = fragments;
		this.titles = titles;
		this.factories = factories;
	}

	@Override
	public Fragment getItem(int position) {
		Logd(TAG, "getItem: position:" + position);
		BaseFragment fragment = fragments[position];
		if (fragment == null) {
			fragment = factories[position].getInstance();
			fragments[position] = fragment;
		}
		Logd(TAG, "getItem: fragment:" + fragment);
		return fragment;
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
