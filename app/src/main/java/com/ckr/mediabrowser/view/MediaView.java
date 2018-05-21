package com.ckr.mediabrowser.view;

import com.ckr.mediabrowser.presenter.MediaPresenter;
import com.ckr.mediabrowser.view.BaseView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaView<T> extends BaseView<MediaPresenter> {
	void updateMedia(List<T> list);
}
