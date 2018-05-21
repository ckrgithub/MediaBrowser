package com.ckr.mediabrowser.view.photo;

import com.ckr.mediabrowser.presenter.photo.PAVPresenter;
import com.ckr.mediabrowser.view.BaseView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaView<T> extends BaseView<PAVPresenter> {
	void updateMedia(List<T> list);
}
