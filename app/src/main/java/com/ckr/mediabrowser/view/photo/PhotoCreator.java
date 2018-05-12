package com.ckr.mediabrowser.view.photo;

import com.ckr.mediabrowser.view.BaseFragment;
import com.ckr.mediabrowser.view.BaseCreator;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public enum PhotoCreator implements BaseCreator {
	Photo(PhotoFragment.class),
	Album(AlbumFragment.class);

	private Class mClazz;

	private PhotoCreator(Class clazz) {
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
