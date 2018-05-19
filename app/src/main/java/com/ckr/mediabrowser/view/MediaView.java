package com.ckr.mediabrowser.view;

import com.ckr.mediabrowser.presenter.MediaPresenter;
import com.ckr.mediabrowser.presenter.OnLoadingDialogListener;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaView<P extends MediaPresenter> extends OnLoadingDialogListener{
	void setPresenter(P p);

}
