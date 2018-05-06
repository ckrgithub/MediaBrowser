package com.ckr.mediabrowser;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ckr.mediabrowser.view.AudioFragment;
import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.view.FileFragment;
import com.ckr.mediabrowser.view.PhotoFragment;
import com.ckr.mediabrowser.view.VideoFragment;
import com.ckr.mediabrowser.widget.MyFragmentPagerAdapter;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

	@BindView(R.id.viewPager)
	ViewPager viewPager;
	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindArray(R.array.tab_title)
	String[] tabTitles;
	private Unbinder unbinder;
	BaseFragment[] fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		initFragment();
		initTabLayout();
	}

	private void initFragment() {
		fragments = new BaseFragment[tabTitles.length];
		fragments[0] = PhotoFragment.newInstance();
		fragments[1] = AudioFragment.newInstance();
		fragments[2] = VideoFragment.newInstance();
		fragments[3] = FileFragment.newInstance();
	}

	private void initTabLayout() {
		tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]), true);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, tabTitles));
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbinder.unbind();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
