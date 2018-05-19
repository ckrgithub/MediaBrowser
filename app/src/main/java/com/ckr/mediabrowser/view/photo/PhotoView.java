package com.ckr.mediabrowser.view.photo;

import com.ckr.mediabrowser.presenter.photo.PhotoPresenter;
import com.ckr.mediabrowser.view.MediaView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface PhotoView<T> extends MediaView<PhotoPresenter> {
	void updatePhoto(List<T> list);
}
