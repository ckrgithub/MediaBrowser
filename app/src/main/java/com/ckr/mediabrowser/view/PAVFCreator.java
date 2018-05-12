package com.ckr.mediabrowser.view;

import com.ckr.mediabrowser.view.audio.AudioMainFragment;
import com.ckr.mediabrowser.view.file.FileMainFragment;
import com.ckr.mediabrowser.view.photo.PhotoMainFragment;
import com.ckr.mediabrowser.view.video.VideoMainFragment;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public enum PAVFCreator implements BaseCreator {
	Photo(PhotoMainFragment.class),
	Audio(AudioMainFragment.class),
	Video(VideoMainFragment.class),
	FileFragment(FileMainFragment.class);


	private Class mClazz;

	private PAVFCreator(Class clazz) {
		mClazz = clazz;
	}

	@Override
	public BaseFragment getInstance() {
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
