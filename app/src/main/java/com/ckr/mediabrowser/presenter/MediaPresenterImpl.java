package com.ckr.mediabrowser.presenter;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.MediaModel;
import com.ckr.mediabrowser.model.audio.AudioModelImpl;
import com.ckr.mediabrowser.model.photo.PhotoModelImpl;
import com.ckr.mediabrowser.model.video.VideoModelImpl;
import com.ckr.mediabrowser.view.MediaView;

import java.util.List;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class MediaPresenterImpl implements MediaPresenter, OnDataLoadListener<MediaItem> {
	private static final String TAG = "MediaPresenterImpl";

	private MediaView mMediaView;
	private MediaModel mMediaModel;
	private Cursor mCursor;

	public MediaPresenterImpl(@NonNull MediaView view, int mediaType) {
		mMediaView = view;
		mMediaView.setPresenter(this);
		if (mediaType == IMediaStore.MEDIA_TYPE_PHOTO) {
			mMediaModel = new PhotoModelImpl(this);
		} else if (mediaType == IMediaStore.MEDIA_TYPE_AUDIO) {
			mMediaModel = new AudioModelImpl(this);
		}else if (mediaType == IMediaStore.MEDIA_TYPE_VIDEO) {
			mMediaModel = new VideoModelImpl(this);
		}else if (mediaType == IMediaStore.MEDIA_TYPE_FILE) {
//			mMediaModel = new AudioModelImpl(this);
		}
	}

	@Override
	public void loadMedia(Cursor cursor, String[] mediaTable) {
		if (null == cursor|| mCursor == cursor) {
			return;
		}
		mCursor = cursor;
		Logd(TAG, "loadMedia:  cursor:"+cursor);
		mMediaModel.loadMedia(cursor, mediaTable);
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
		if (mMediaView != null) {
			mMediaView.showLoadingDialog();
		}
	}

	@Override
	public void dismissLoadingDialog() {
		if (mMediaView != null) {
			mMediaView.dismissLoadingDialog();
		}
	}

	@Override
	public void onSuccess(List<? extends MediaItem> list) {
		if (mMediaView != null) {
			mMediaView.updateMedia(list);
		}
	}

	@Override
	public void onFailure(int code, String msg) {

	}
}
