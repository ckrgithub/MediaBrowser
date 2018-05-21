package com.ckr.mediabrowser.view.file;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.view.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends BaseFragment {

	public static FileFragment newInstance() {

		Bundle args = new Bundle();

		FileFragment fragment = new FileFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	protected int getLayoutId() {
		return R.layout.recycler_view;
	}

	@Override
	protected void init() {

	}

	@Override
	public void refreshFragment() {

	}

}
