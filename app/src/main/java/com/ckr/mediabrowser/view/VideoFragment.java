package com.ckr.mediabrowser.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {

	public static VideoFragment newInstance() {
		
		Bundle args = new Bundle();
		
		VideoFragment fragment = new VideoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_video;
	}

	@Override
	protected void init() {

	}

}
