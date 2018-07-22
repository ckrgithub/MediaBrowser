package com.ckr.mediabrowser.model.file;

import android.support.annotation.NonNull;

import com.ckr.mediabrowser.presenter.file.OnFileScanListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class FileModelImpl implements FileModel {
	private static final String TAG = "VideoModelImpl";
	private OnFileScanListener mOnFileScanListener;
	private ExecutorService mExecutor;
	private Future<?> future;

	public FileModelImpl(@NonNull OnFileScanListener listener) {
		mOnFileScanListener = listener;
	}

	@Override
	public void loadFile() {
		if (mOnFileScanListener == null) {
			return;
		}
		if (FileScanner.getStatus() == FileScanner.IDLE) {
			if (mExecutor == null) {
				mExecutor = Executors.newSingleThreadExecutor();
			}
			if (future != null) {
				boolean cancel = future.cancel(true);
				Logd(TAG, "loadMedia: cancel:" + cancel);
			}
			mOnFileScanListener.showLoadingDialog();
			future = mExecutor.submit(new FileRunnable());
		}

	}

	@Override
	public void onDestroy() {
	}

	private final class FileRunnable implements Runnable {

		public FileRunnable() {
		}

		@Override
		public void run() {
			FileScanner.scanFile(mOnFileScanListener);
		}
	}
}
