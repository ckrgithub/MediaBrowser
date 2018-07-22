package com.ckr.mediabrowser.presenter.file;

import com.ckr.mediabrowser.model.file.Document;
import com.ckr.mediabrowser.model.file.FileModel;
import com.ckr.mediabrowser.model.file.FileModelImpl;
import com.ckr.mediabrowser.view.file.FileView;

import java.util.List;
import java.util.Map;

/**
 * Created on 2018/7/22
 *
 * @author ckr
 *         Github: https://github.com/ckrgithub/MediaBrowser
 */

public class FilePresenterImpl implements FilePresenter, OnFileScanListener<List<Document>> {

	private FileView mFileView;
	private FileModel mFileModel;

	public FilePresenterImpl(FileView fileView) {
		mFileView = fileView;
		mFileModel = new FileModelImpl(this);
		mFileView.setPresenter(this);
	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void showLoadingDialog() {
		mFileView.showLoadingDialog();
	}

	@Override
	public void dismissLoadingDialog() {
		mFileView.dismissLoadingDialog();
	}

	@Override
	public void onSuccess(Map<String, List<Document>> map) {
		mFileView.updateFile(map);
	}

	@Override
	public void onFailure(int code, String msg) {

	}

	@Override
	public void loadFile() {
		mFileModel.loadFile();
	}
}
