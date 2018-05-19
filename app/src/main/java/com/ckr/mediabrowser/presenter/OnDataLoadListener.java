package com.ckr.mediabrowser.presenter;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface OnDataLoadListener<T> extends OnLoadingDialogListener {
	void onSuccess(List<? extends T> list);

	void onFailure(int code, String msg);
}
