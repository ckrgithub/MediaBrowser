package com.ckr.mediabrowser.widget;

import com.ckr.mediabrowser.view.AudioFragment;
import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.view.FileFragment;
import com.ckr.mediabrowser.view.PhotoFragment;
import com.ckr.mediabrowser.view.VideoFragment;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public enum  PAVF {
	Photo(PhotoFragment.class),
	Audio(AudioFragment.class),
	Video(VideoFragment.class),
	FileFragment(FileFragment.class);


	private Class mClazz;

	private PAVF(Class clazz){
		mClazz = clazz;
	}

	public BaseFragment getInstance(){
		try {
			if (mClazz == null) {
				return null;
			} else {
				return (BaseFragment) mClazz.newInstance();
			}
		} catch (Exception e) {
			throw new Error("Can not init mClazz instance");
		}
	}
}
