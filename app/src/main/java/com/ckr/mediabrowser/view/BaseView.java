package com.ckr.mediabrowser.view;

import com.ckr.mediabrowser.presenter.BasePresenter;
import com.ckr.mediabrowser.presenter.OnLoadingDialogListener;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface BaseView<P extends BasePresenter> extends OnLoadingDialogListener{
	void setPresenter(P p);
}
