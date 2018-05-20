package com.ckr.mediabrowser.view.photo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.observer.OnMediaListener;
import com.ckr.mediabrowser.util.Constants;
import com.ckr.mediabrowser.view.BaseFragment;

import java.util.List;
import java.util.Map;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseFragment implements OnMediaListener<Photo> {
	private static final String TAG = "PhotoFragment";
	private boolean isVisible = false;
	private MediaObserver mMediaObserver;

	public static PhotoFragment newInstance() {

		Bundle args = new Bundle();

		PhotoFragment fragment = new PhotoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.recycler_view;
	}

	@Override
	protected void init() {
		Logd(TAG, "init: ");
		mMediaObserver = MediaObserver.getInstance();
		mMediaObserver.registerListener(this);
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
	public void subscribeOn(List<Photo> list, int mediaType) {
		if (mediaType != Constants.MEDIA_TYPE_PHOTO) {
			return;
		}
		synchronized (this) {
			handleData(list);
		}
	}

	private void handleData(List<Photo> list) {
		if (list.size()>0) {

		}
	}

	@Override
	public void subscribeOnFile(Map<String, List<Photo>> map, int mediaType) {

	}
}
