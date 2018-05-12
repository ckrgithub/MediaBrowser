package com.ckr.mediabrowser.view.video;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.view.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoCategoryFragment extends BaseFragment {

	public static VideoCategoryFragment newInstance() {

		Bundle args = new Bundle();

		VideoCategoryFragment fragment = new VideoCategoryFragment();
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

}
