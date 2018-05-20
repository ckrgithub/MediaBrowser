package com.ckr.mediabrowser.view;

import android.app.Application;
import android.content.Context;

/**
 * Created by PC大佬 on 2018/4/9.
 */

public class MediaBrowserApplication extends Application {

	private static MediaBrowserApplication application;

	public static Context getContext() {
		return application.getApplicationContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}
}
