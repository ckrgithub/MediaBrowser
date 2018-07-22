package com.ckr.mediabrowser.view.file;

import com.ckr.mediabrowser.presenter.OnLoadingDialogListener;
import com.ckr.mediabrowser.presenter.file.FilePresenter;

import java.util.List;
import java.util.Map;

/**
 * Created on 2018/7/22
 *
 * @author ckr
 *         Github: https://github.com/ckrgithub/MediaBrowser
 */

public interface FileView<T> extends OnLoadingDialogListener {
	void updateFile(Map<String, List<T>> map);

	void setPresenter(FilePresenter p);
}
