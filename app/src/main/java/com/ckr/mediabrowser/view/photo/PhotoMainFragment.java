package com.ckr.mediabrowser.view.photo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.widget.MyFragmentPagerAdapter;
import com.ckr.mediabrowser.widget.MyViewPager;

import butterknife.BindArray;
import butterknife.BindView;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoMainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
	private static final String TAG = "PhotoMainFragment";

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.myViewPager)
	MyViewPager viewPager;
	@BindArray(R.array.tab_photo)
	String[] tabTitles;
	private BaseFragment[] fragments;
	private boolean isVisible = false;

	public static PhotoMainFragment newInstance() {

		Bundle args = new Bundle();

		PhotoMainFragment fragment = new PhotoMainFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_base;
	}

	@Override
	protected void init() {
		Logd(TAG, "init: ");
		initTabLayout();
	}

	private void initTabLayout() {
		tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]), true);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments = new BaseFragment[tabTitles.length], tabTitles, PhotoCreator.values()));
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Logd(TAG, "onResume: ");
	}

	@Override
	protected void onVisible() {
		Logd(TAG, "onVisible: " + isVisible);
		isVisible = true;
	}

	@Override
	protected void onInvisible() {
		Log.d(TAG, "onInvisible: " + isVisible);
		isVisible = false;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		Logd(TAG, "onPageScrolled: position:" + position);
	}

	@Override
	public void onPageSelected(int position) {
		Logd(TAG, "onPageSelected: position:" + position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
