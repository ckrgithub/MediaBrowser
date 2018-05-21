package com.ckr.mediabrowser.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.util.MediaLog;
import com.ckr.mediabrowser.widget.MyFragmentPagerAdapter;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,MediaContext {
	private static final String TAG = "MainActivity";

	@BindView(R.id.viewPager)
	ViewPager viewPager;
	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindArray(R.array.tab_main)
	String[] tabTitles;
	private Unbinder unbinder;
	BaseFragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		initTabLayout();
		MediaLog.debug();
		Logd(TAG, "onCreate: ");
	}

	private void initTabLayout() {
		tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]), true);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments = new BaseFragment[tabTitles.length], tabTitles, PAVFCreator.values()));
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Logd(TAG, "onResume: ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		Logd(TAG, "onPageScrolled: position:"+position);
	}

	@Override
	public void onPageSelected(int position) {
		Logd(TAG, "onPageSelected: position:"+position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public Context getContext() {
		return this;
	}
}
