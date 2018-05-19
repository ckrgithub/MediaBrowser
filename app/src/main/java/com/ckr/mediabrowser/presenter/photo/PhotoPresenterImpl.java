package com.ckr.mediabrowser.presenter.photo;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.photo.PhotoModelImpl;
import com.ckr.mediabrowser.presenter.OnDataLoadListener;
import com.ckr.mediabrowser.view.photo.PhotoView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class PhotoPresenterImpl implements PhotoPresenter, OnDataLoadListener {

	private PhotoView mPhotoView;

	public PhotoPresenterImpl(@NonNull PhotoView view) {
		mPhotoView = view;
		mPhotoView.setPresenter(this);
		new PhotoModelImpl(this);
	}

	@Override
	public void loadMedia(Cursor cursor, String[] mediaTable) {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void showLoadingDialog() {

	}

	@Override
	public void dismissLoadingDialog() {

	}

	@Override
	public void onSuccess(List list) {

	}

	@Override
	public void onFailure(int code, String msg) {

	}
}
