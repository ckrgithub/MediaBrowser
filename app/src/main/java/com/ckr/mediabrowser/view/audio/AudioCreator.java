package com.ckr.mediabrowser.view.audio;

import com.ckr.mediabrowser.view.BaseCreator;
import com.ckr.mediabrowser.view.BaseFragment;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public enum AudioCreator implements BaseCreator {
	Audio(AudioFragment.class),
	Category(AudioCategoryFragment.class);

	private Class mClazz;

	private AudioCreator(Class clazz) {
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
