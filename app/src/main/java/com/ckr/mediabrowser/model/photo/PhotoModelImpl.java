package com.ckr.mediabrowser.model.photo;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.presenter.OnDataLoadListener;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class PhotoModelImpl implements PhotoModel{

	private OnDataLoadListener mOnDataLoadListener;

	public PhotoModelImpl(@NonNull OnDataLoadListener listener) {
		mOnDataLoadListener = listener;
	}

	@Override
	public void loadMedia(Cursor cursor, String[] mediaTable) {

	}

	@Override
	public void onDestroy() {

	}
}
