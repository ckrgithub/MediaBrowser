package com.ckr.mediabrowser.view.file;

import com.ckr.mediabrowser.view.BaseCreator;
import com.ckr.mediabrowser.view.BaseFragment;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public enum FileCreator implements BaseCreator {
	File(FileFragment.class),
	Category(FileCategoryFragment.class);

	private Class mClazz;

	private FileCreator(Class clazz) {
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
