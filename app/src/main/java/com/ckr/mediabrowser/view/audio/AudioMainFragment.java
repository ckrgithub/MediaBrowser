package com.ckr.mediabrowser.view.audio;


import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.audio.Audio;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.presenter.MediaPresenter;
import com.ckr.mediabrowser.presenter.MediaPresenterImpl;
import com.ckr.mediabrowser.util.PermissionRequest;
import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.view.MediaView;
import com.ckr.mediabrowser.widget.MyFragmentPagerAdapter;
import com.ckr.mediabrowser.widget.MyViewPager;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

import static com.ckr.mediabrowser.model.IMediaStore.MEDIA_TABLE;
import static com.ckr.mediabrowser.model.IMediaStore.MEDIA_TYPE_AUDIO;
import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioMainFragment extends BaseFragment implements ViewPager.OnPageChangeListener, LoaderManager.LoaderCallbacks<Cursor>, MediaView<Audio> {
	private static final String TAG = "AudioMainFragment";

	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.myViewPager)
	MyViewPager viewPager;
	@BindArray(R.array.tab_audio)
	String[] tabTitles;
	private BaseFragment[] fragments;
	private boolean isVisible = false;
	private MediaPresenter mMediaPresenter;
	private Cursor mCursor;
	private boolean isResume;
	private Dialog mLoadingDialog;
	private MediaObserver mMediaObserver;
	private boolean isDelayLoad = false;
	private boolean isNeedRefresh=false;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_base;
	}

	@Override
	protected void init() {
		Logd(TAG, "init: ");
		initTabLayout();
		mMediaObserver = MediaObserver.getInstance();
		new MediaPresenterImpl(this, IMediaStore.MEDIA_TYPE_AUDIO);
		if (isVisible) {
			if (PermissionRequest.requestPermission(this, PermissionRequest.PERMISSION_STORAGE, PermissionRequest.REQUEST_STORAGE)) {
				onPermissionGranted(PermissionRequest.REQUEST_STORAGE);
			}
		}else {
			if (PermissionRequest.hasPermissionGranted(getContext(),PermissionRequest.PERMISSION_STORAGE)) {
				onPermissionGranted(PermissionRequest.REQUEST_STORAGE);
			}
		}
	}

	private void initTabLayout() {
		tabLayout.addTab(tabLayout.newTab().setText(tabTitles[0]), true);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments = new BaseFragment[tabTitles.length], tabTitles, AudioCreator.values()));
		tabLayout.setupWithViewPager(viewPager);
		viewPager.addOnPageChangeListener(this);
	}

	@Override
	public void onPermissionGranted(int requestCode) {
		Logd(TAG, "onPermissionGranted: ");
		getActivity().getSupportLoaderManager().initLoader(IMediaStore.MEDIA_TYPE_AUDIO, null, this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Logd(TAG, "onResume: ");
		isResume = true;
		if (isVisible) {
			if (isDelayLoad) {
				isDelayLoad = false;
				if (mMediaPresenter != null) {
					mMediaPresenter.loadMedia(mCursor, IMediaStore.MEDIA_TABLE[IMediaStore.MEDIA_TYPE_AUDIO]);
				}
			}
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
			isNeedRefresh=false;
			mMediaPresenter.loadMedia(mCursor, MEDIA_TABLE[MEDIA_TYPE_AUDIO]);
		}
	}

	@Override
	protected void onInvisible() {
		Log.d(TAG, "onInvisible: " + isVisible);
		isVisible = false;
	}

	@Override
	public void refreshFragment() {
		Logd(TAG, "refreshFragment: isVisible:" + isVisible+",mMediaPresenter:"+mMediaPresenter);
		if (isVisible) {
			mMediaPresenter.loadMedia(mCursor, MEDIA_TABLE[MEDIA_TYPE_AUDIO]);
		}else {
			isNeedRefresh=true;
		}
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

	@NonNull
	@Override
	public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String[] storage = IMediaStore.MEDIA_TABLE[IMediaStore.MEDIA_TYPE_AUDIO];
		String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " desc";
		CursorLoader cursorLoader = new CursorLoader(getContext(), uri, storage, null, null, orderBy);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
		Logd(TAG, "onLoadFinished: cursor:" + cursor + ",mCursor:" + mCursor);
		if (mCursor == cursor) {//cursor没变，无需更新数据源
			return;
		}
		mCursor = cursor;
		if (isVisible && isResume) {//fragment可见才更新数据源
			if (mMediaPresenter != null) {
				mMediaPresenter.loadMedia(cursor, IMediaStore.MEDIA_TABLE[IMediaStore.MEDIA_TYPE_AUDIO]);
			}
		} else {
			isDelayLoad = true;
		}
	}

	@Override
	public void onLoaderReset(@NonNull Loader<Cursor> loader) {
		mCursor = null;
	}

	@Override
	public void setPresenter(MediaPresenter mediaPresenter) {
		mMediaPresenter = mediaPresenter;
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
	public void updateMedia(List<Audio> list) {
		Logd(TAG, "updateMedia: " + list.size());
		if (list.size() == 0) {
			return;
		}
		if (mMediaObserver != null) {
			mMediaObserver.subscribeOn(list, IMediaStore.MEDIA_TYPE_AUDIO);
		}
	}
}
