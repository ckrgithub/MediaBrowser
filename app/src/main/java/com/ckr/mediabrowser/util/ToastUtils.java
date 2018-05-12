package com.ckr.mediabrowser.util;

import android.widget.Toast;

import com.ckr.mediabrowser.MediaBrowserApplication;

public class ToastUtils {
	private static Toast makeText;

	public static void toast(String content) {
		if (makeText == null) {
			makeText = Toast.makeText(MediaBrowserApplication.getContext(), "", Toast.LENGTH_SHORT);
		}
		makeText.setText(content);
		makeText.show();
	}
}
