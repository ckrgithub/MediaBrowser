package com.ckr.mediabrowser.model.photo;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.presenter.OnDataLoadListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.ckr.mediabrowser.util.MediaLog.Logd;
import static com.ckr.mediabrowser.util.MediaLog.Loge;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class PhotoModelImpl implements PhotoModel {
	private static final String TAG = "PhotoModelImpl";
	private static final int MINIMUM_SIZE = 5 * 1024;
	private OnDataLoadListener mOnDataLoadListener;
	private ExecutorService mExecutor;
	private Future<?> future;
	private final Handler mHandler = new Handler(Looper.myLooper());

	public PhotoModelImpl(@NonNull OnDataLoadListener listener) {
		mOnDataLoadListener = listener;
	}

	@Override
	public void loadMedia(Cursor cursor, String[] mediaTable) {
		if (mOnDataLoadListener == null) {
			return;
		}
		if (mExecutor == null) {
			mExecutor = Executors.newSingleThreadExecutor();
		}
		if (future != null) {
			boolean cancel = future.cancel(true);
			Logd(TAG, "loadMedia: cancel:" + cancel);
		}
		mOnDataLoadListener.showLoadingDialog();
		future = mExecutor.submit(new PhotoRunnable(cursor, mediaTable));
	}

	@Override
	public void onDestroy() {

	}

	class PhotoRunnable implements Runnable {

		private Cursor cursor;
		private String[] mediaTable;

		public PhotoRunnable(Cursor cursor, String[] mediaTable) {
			this.cursor = cursor;
			this.mediaTable = mediaTable;
		}

		@Override
		public void run() {
			queryData(cursor, mediaTable);
		}
	}

	private List<Photo> data;
	SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

	private void queryData(Cursor cursor, String[] mediaTable) {
		Logd(TAG, "queryData: mediaTable:" + Arrays.toString(mediaTable) + ",newCursor:" + cursor);
		if (data == null) {
			data = new ArrayList<>();
		}
		int count = cursor.getCount();
		Logd(TAG, "queryData: count:" + count);
		data.clear();
		for (int i = 0; i < count; i++) {
			if (cursor.moveToPosition(i)) {
				final long id = cursor.getLong(cursor.getColumnIndex(mediaTable[0]));
				final String path = cursor.getString(cursor.getColumnIndex(mediaTable[1]));
				File file = new File(path);
				if (!file.exists() || file.length() < MINIMUM_SIZE) {
					continue;
				}
				final long fileSize = cursor.getLong(cursor.getColumnIndex(mediaTable[2]));
				final String displayName = cursor.getString(cursor.getColumnIndex(mediaTable[3]));
				final String title = cursor.getString(cursor.getColumnIndex(mediaTable[4]));
				final long dateAdded = cursor.getLong(cursor.getColumnIndex(mediaTable[5]));
				final long dateModified = cursor.getLong(cursor.getColumnIndex(mediaTable[6]));
				final String mimeType = cursor.getString(cursor.getColumnIndex(mediaTable[7]));
				final String width = cursor.getString(cursor.getColumnIndex(mediaTable[8]));
				final String height = cursor.getString(cursor.getColumnIndex(mediaTable[9]));

				final String description = cursor.getString(cursor.getColumnIndex(mediaTable[10]));
				final int isPrivate = cursor.getInt(cursor.getColumnIndex(mediaTable[11]));
				final double latitude = cursor.getInt(cursor.getColumnIndex(mediaTable[12]));
				final double longitude = cursor.getInt(cursor.getColumnIndex(mediaTable[13]));
				final long dateTaken = cursor.getLong(cursor.getColumnIndex(mediaTable[14]));
				final int orientation = cursor.getInt(cursor.getColumnIndex(mediaTable[15]));
				final int thumbMagic = cursor.getInt(cursor.getColumnIndex(mediaTable[16]));
				final String bucketId = cursor.getString(cursor.getColumnIndex(mediaTable[17]));
				final String bucketDisplayName = cursor.getString(cursor.getColumnIndex(mediaTable[18]));
				Logd(TAG, "queryData: id:" + id);
				Loge(TAG, "queryData: path:" + path);
				Logd(TAG, "queryData: fileSize:" + fileSize);
				Logd(TAG, "queryData: displayName:" + displayName);
				Logd(TAG, "queryData: title:" + title);
				Logd(TAG, "queryData: dateAdded:" + dateAdded);
				Logd(TAG, "queryData: dateModified:" + dateModified);
				Logd(TAG, "queryData: mimeType:" + mimeType);
				Logd(TAG, "queryData: width:" + width);
				Logd(TAG, "queryData: height:" + height);
				Logd(TAG, "queryData: description:" + description);
				Logd(TAG, "queryData: isPrivate:" + isPrivate);
				Logd(TAG, "queryData: latitude:" + latitude);
				Logd(TAG, "queryData: longitude:" + longitude);
				Logd(TAG, "queryData: dateTaken:" + dateTaken);
				Logd(TAG, "queryData: orientation:" + orientation);
				Logd(TAG, "queryData: thumbMagic:" + thumbMagic);
				Logd(TAG, "queryData: bucketId:" + bucketId);
				Logd(TAG, "queryData: bucketDisplayName:" + bucketDisplayName);
				String addedDate = getDate(dateAdded*1000);
				String modifiedDate = getDate(dateModified*1000);
				String takenDate = getDate(dateTaken);
				Logd(TAG, "queryData: addedDate:" + addedDate);
				Logd(TAG, "queryData: modifiedDate:" + modifiedDate);
				Logd(TAG, "queryData: takenDate:" + takenDate);
				Photo image = new Photo(path, fileSize, displayName, title, addedDate, modifiedDate, mimeType, id);
				image.setDescription(description);
				image.setPrivate(isPrivate);
				image.setLatitude(latitude);
				image.setLongitude(longitude);
				image.setDateTaken(takenDate);
				image.setOrientation(orientation);
				image.setMiniThumbMagic(thumbMagic);
				image.setBucketId(bucketId);
				image.setBucketDisplayName(bucketDisplayName);
				data.add(image);
			}
		}
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (mOnDataLoadListener != null) {
					mOnDataLoadListener.dismissLoadingDialog();
				}
			}
		});
		if (mOnDataLoadListener != null) {
			mOnDataLoadListener.onSuccess(data);
		}
	}

	private String getDate(long date) {
		String format = null;
		try {
			format = simpleFormatter.format(new Date(date));
		} catch (Exception e) {
			e.printStackTrace();
			format = "1970年01月01日 00:00:00";
		}
		return format;
	}
}
