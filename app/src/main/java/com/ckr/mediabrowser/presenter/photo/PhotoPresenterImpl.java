package com.ckr.mediabrowser.presenter.photo;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.photo.PhotoModel;
import com.ckr.mediabrowser.model.photo.PhotoModelImpl;
import com.ckr.mediabrowser.presenter.OnDataLoadListener;
import com.ckr.mediabrowser.view.photo.PhotoView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class PhotoPresenterImpl implements PhotoPresenter, OnDataLoadListener<MediaItem> {

	private PhotoView mPhotoView;
	private PhotoModel mPhotoModel;

	public PhotoPresenterImpl(@NonNull PhotoView view) {
		mPhotoView = view;
		mPhotoView.setPresenter(this);
		mPhotoModel = new PhotoModelImpl(this);
	}

	@Override
	public void loadMedia(Cursor cursor, String[] mediaTable) {
		if (cursor == null) {
			return;
		}
		mPhotoModel.loadMedia(cursor, mediaTable);
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
		if (mPhotoView != null) {
			mPhotoView.showLoadingDialog();
		}
	}

	@Override
	public void dismissLoadingDialog() {
		if (mPhotoView != null) {
			mPhotoView.dismissLoadingDialog();
		}
	}

	@Override
	public void onSuccess(List<? extends MediaItem> list) {
		if (mPhotoView != null) {
			mPhotoView.updatePhoto(list);
		}
	}

	@Override
	public void onFailure(int code, String msg) {

	}
}
