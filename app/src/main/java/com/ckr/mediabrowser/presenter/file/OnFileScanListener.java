package com.ckr.mediabrowser.presenter.file;

import com.ckr.mediabrowser.presenter.OnLoadingDialogListener;

import java.util.Map;

/**
 * Created on 2018/7/22.
 *
 * @author ckr
 * Github: https://github.com/ckrgithub/MediaBrowser
 */

public interface OnFileScanListener<T> extends OnLoadingDialogListener {
	void onSuccess(Map<String, T> map);

	void onFailure(int code, String msg);
}
