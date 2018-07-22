package com.ckr.mediabrowser.view.file;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.file.Document;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.presenter.file.FilePresenter;
import com.ckr.mediabrowser.presenter.file.FilePresenterImpl;
import com.ckr.mediabrowser.util.PermissionRequest;
import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.widget.MyFragmentPagerAdapter;
import com.ckr.mediabrowser.widget.MyViewPager;

import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * A simple {@link Fragment} subclass.
 */

public class FileMainFragment extends BaseFragment implements ViewPager.OnPageChangeListener, FileView<Document> {
	private static final String TAG = "FileMainFragment";

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.myViewPager)
	MyViewPager viewPager;
	@BindArray(R.array.tab_file)
	String[] tabTitles;
	BaseFragment[] fragments;
	private MediaObserver mMediaObserver;
	private FilePresenter mFilePresenter;
	private boolean isResume = false;
	private boolean isVisible = false;
	private boolean isNeedRefresh = false;
	private Dialog mLoadingDialog;
	private final Handler mHandler = new Handler(Looper.myLooper());


	public static FileMainFragment newInstance() {

		Bundle args = new Bundle();
		FileMainFragment fragment = new FileMainFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_base;
	}

	@Override
	protected void init() {
		initTabLayout();
		mMediaObserver = MediaObserver.getInstance();
		new FilePresenterImpl(this);
		if (isVisible) {
			if (PermissionRequest.requestPermission(this, PermissionRequest.PERMISSION_STORAGE, PermissionRequest.REQUEST_STORAGE)) {
				onPermissionGranted(PermissionRequest.REQUEST_STORAGE);
			}
		} else {
			if (PermissionRequest.hasPermissionGranted(getContext(), PermissionRequest.PERMISSION_STORAGE)) {
				onPermissionGranted(PermissionRequest.REQUEST_STORAGE);
			}
		}
	}

	@Override
	public void onPermissionGranted(int requestCode) {
		Logd(TAG, "onPermissionGranted: ");
		loadFile();
	}

	private void loadFile() {
		if (mFilePresenter != null) {
//			mFilePresenter.loadFile();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Logd(TAG, "onResume: isVisible:" + isVisible);
		isResume = true;
		if (isVisible) {
			loadFile();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		isResume = false;
	}

	@Override
	protected void onVisible() {
		Logd(TAG, "onVisible: " + isVisible);
		isVisible = true;
		if (isNeedRefresh) {
			isNeedRefresh = false;
			loadFile();
		}
	}

	@Override
	protected void onInvisible() {
		Logd(TAG, "onInvisible: " + isVisible);
		isVisible = false;
	}

	@Override
	public void refreshFragment() {
		if (isVisible) {
			loadFile();
		} else {
			isNeedRefresh = true;
		}
	}

	private void initTabLayout() {
		tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]), true);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments = new BaseFragment[tabTitles.length], tabTitles, FileCreator.values()));
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(this);
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

	@Override
	public void showLoadingDialog() {
		if (mLoadingDialog == null) {
			mLoadingDialog = createLoadingDialog();
		}
		showDialog(mLoadingDialog);
	}

	@Override
	public void dismissLoadingDialog() {
		dismissDialog(mLoadingDialog);
	}

	@Override
	public void updateFile(Map<String, List<Document>> map) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				dismissLoadingDialog();
			}
		});
		if (mMediaObserver != null) {
			mMediaObserver.subscribeOnFile(map, IMediaStore.MEDIA_TYPE_FILE);
		}
	}

	@Override
	public void setPresenter(FilePresenter p) {
		mFilePresenter = p;
	}
}
