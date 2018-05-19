package com.ckr.mediabrowser.view;

import com.ckr.mediabrowser.presenter.MediaPresenter;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaView<T extends MediaPresenter> {
	void setPresenter(T t);

	void showLoadingDialog();

	void hideLoadingDialog();
}
