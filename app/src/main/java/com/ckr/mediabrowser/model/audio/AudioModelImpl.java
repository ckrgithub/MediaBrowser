package com.ckr.mediabrowser.model.audio;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.ckr.mediabrowser.model.MediaModel;
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

public class AudioModelImpl implements MediaModel {
	private static final String TAG = "AudioModelImpl";
	private static final int MINIMUM_SIZE = 5 * 1024;
	private OnDataLoadListener mOnDataLoadListener;
	private ExecutorService mExecutor;
	private Future<?> future;
	private final Handler mHandler = new Handler(Looper.myLooper());

	public AudioModelImpl(@NonNull OnDataLoadListener listener) {
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
		future = mExecutor.submit(new AudioRunnable(cursor, mediaTable));
	}

	@Override
	public void onDestroy() {
	}

	private final class AudioRunnable implements Runnable {

		private Cursor cursor;
		private String[] mediaTable;

		public AudioRunnable(Cursor cursor, String[] mediaTable) {
			this.cursor = cursor;
			this.mediaTable = mediaTable;
		}

		@Override
		public void run() {
			queryData(cursor, mediaTable);
		}
	}

	private List<Audio> data;
	SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy年MM月dd日");

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

				final long duration = cursor.getLong(cursor.getColumnIndex(mediaTable[10]));
				final long artistId = cursor.getLong(cursor.getColumnIndex(mediaTable[11]));
				final String artist = cursor.getString(cursor.getColumnIndex(mediaTable[12]));
				final long albumId = cursor.getLong(cursor.getColumnIndex(mediaTable[13]));
				final String album = cursor.getString(cursor.getColumnIndex(mediaTable[14]));
				final int year = cursor.getInt(cursor.getColumnIndex(mediaTable[15]));
				final int isMusic = cursor.getInt(cursor.getColumnIndex(mediaTable[16]));
				final int isPodcast = cursor.getInt(cursor.getColumnIndex(mediaTable[17]));
				final int isRingtone = cursor.getInt(cursor.getColumnIndex(mediaTable[18]));
				final int isAlarm = cursor.getInt(cursor.getColumnIndex(mediaTable[19]));
				final int isNotification = cursor.getInt(cursor.getColumnIndex(mediaTable[20]));
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
				
				Logd(TAG, "queryData: duration:" + duration);
				Logd(TAG, "queryData: artistId:" + artistId);
				Logd(TAG, "queryData: artist:" + artist);
				Logd(TAG, "queryData: albumId:" + albumId);
				Logd(TAG, "queryData: album:" + album);
				Logd(TAG, "queryData: year:" + year);
				Logd(TAG, "queryData: isMusic:" + isMusic);
				Logd(TAG, "queryData: isPodcast:" + isPodcast);
				Logd(TAG, "queryData: isRingtone:" + isRingtone);
				Logd(TAG, "queryData: isAlarm:" + isAlarm);
				Logd(TAG, "queryData: isNotification:" + isNotification);
				String addedDate = getDate(dateAdded*1000);
				String modifiedDate = getDate(dateModified*1000);
				Logd(TAG, "queryData: addedDate:" + addedDate);
				Logd(TAG, "queryData: modifiedDate:" + modifiedDate);
				Audio audio = new Audio(path, fileSize, displayName, title, addedDate, modifiedDate, mimeType, id);
				audio.setDuration(duration);
				audio.setArtistId(artistId);
				audio.setArtist(artist);
				audio.setAlbumId(albumId);
				audio.setAlbum(album);
				audio.setYear(year);
				audio.setIsMusic(isMusic);
				audio.setIsPodcast(isPodcast);
				audio.setIsRingtone(isRingtone);
				audio.setIsAlarm(isAlarm);
				audio.setIsNotification(isNotification);
				data.add(audio);
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
