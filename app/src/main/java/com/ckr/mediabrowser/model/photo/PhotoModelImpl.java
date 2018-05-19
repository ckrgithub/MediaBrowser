package com.ckr.mediabrowser.model.photo;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.presenter.OnDataLoadListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

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
		if (future == null) {
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
			splitData(cursor, mediaTable);
		}
	}

	private List<Photo> data;
	SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy 年 MM 月 dd 日");

	private void splitData(Cursor cursor, String[] mediaTable) {
		Log.d(TAG, "splitData: mediaTable:" + Arrays.toString(mediaTable) + ",newCursor:" + cursor);
		if (data == null) {
			data = new ArrayList<>();
		}
		int count = cursor.getCount();
		Log.d(TAG, "splitData: count:" + count);
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
				final long date = cursor.getLong(cursor.getColumnIndex(mediaTable[14]));
				final int orientation = cursor.getInt(cursor.getColumnIndex(mediaTable[15]));
				final int thumbMagic = cursor.getInt(cursor.getColumnIndex(mediaTable[16]));
				final String bucketId = cursor.getString(cursor.getColumnIndex(mediaTable[17]));
				final String bucketDisplayName = cursor.getString(cursor.getColumnIndex(mediaTable[18]));
				Log.i(TAG, "splitData: id:" + id);
				Log.e(TAG, "splitData: path:" + path);
				Log.d(TAG, "splitData: fileSize:" + fileSize);
				Log.d(TAG, "splitData: displayName:" + displayName);
				Log.d(TAG, "splitData: title:" + title);
				Log.d(TAG, "splitData: dateAdded:" + dateAdded);
				Log.d(TAG, "splitData: dateModified:" + dateModified);
				Log.d(TAG, "splitData: mimeType:" + mimeType);
				Log.d(TAG, "splitData: width:" + width);
				Log.d(TAG, "splitData: height:" + height);
				Log.d(TAG, "splitData: description:" + description);
				Log.d(TAG, "splitData: isPrivate:" + isPrivate);
				Log.d(TAG, "splitData: latitude:" + latitude);
				Log.d(TAG, "splitData: longitude:" + longitude);
				Log.d(TAG, "splitData: date:" + date);
				Log.d(TAG, "splitData: orientation:" + orientation);
				Log.d(TAG, "splitData: thumbMagic:" + thumbMagic);
				Log.d(TAG, "splitData: bucketId:" + bucketId);
				Log.d(TAG, "splitData: bucketDisplayName:" + bucketDisplayName);
//				String format = "1970 年 01月 01日";
//				try {
//					format = simpleFormatter.format(new Date(date));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				Photo image = new Photo(path, fileSize, displayName, title, dateAdded + "", dateModified + "", mimeType, id);
				image.setDescription(description);
				image.setPrivate(isPrivate);
				image.setLatitude(latitude);
				image.setLongitude(longitude);
				image.setDateTaken(date + "");
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
}
