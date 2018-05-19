package com.ckr.mediabrowser.view.photo;

import com.ckr.mediabrowser.model.photo.Photo;
import com.ckr.mediabrowser.presenter.photo.PhotoPresenter;
import com.ckr.mediabrowser.view.MediaView;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface PhotoView extends MediaView<PhotoPresenter> {
	void updatePhoto(List<Photo> list);
}
