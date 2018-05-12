package com.ckr.mediabrowser.util;

import android.util.Log;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public class MediaLog {
	private static final String TAG="MediaLog";
	private static boolean isDebug = false;

	public static void debug() {
		MediaLog.isDebug = true;
	}

	public static void Logd(String msg) {
		Logd("", msg);
	}

	public static void Logd(String tag, String msg) {
		if (isDebug) {
			Log.d(TAG, tag + "--->" + msg);
		}
	}

	public static void Logi(String msg) {
		Logi("", msg);
	}

	public static void Logi(String tag, String msg) {
		if (isDebug) {
			Log.i(TAG, tag + "--->" + msg);
		}
	}

	public static void Logw(String msg) {
		Logw("", msg);
	}

	public static void Logw(String tag, String msg) {
		if (isDebug) {
			Log.w(TAG, tag + "--->" + msg);
		}
	}

	public static void Loge(String msg) {
		Loge("", msg);
	}

	public static void Loge(String tag, String msg) {
		if (isDebug) {
			Log.e(TAG, tag + "--->" + msg);
		}
	}
}
