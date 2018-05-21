package com.ckr.mediabrowser.presenter;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.photo.PhotoModel;
import com.ckr.mediabrowser.model.photo.PhotoModelImpl;
import com.ckr.mediabrowser.view.MediaView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class MediaPresenterImpl implements MediaPresenter, OnDataLoadListener<MediaItem> {

	private MediaView mPhotoView;
	private PhotoModel mPhotoModel;

	public MediaPresenterImpl(@NonNull MediaView view) {
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
			mPhotoView.updateMedia(list);
		}
	}

	@Override
	public void onFailure(int code, String msg) {

	}
}
