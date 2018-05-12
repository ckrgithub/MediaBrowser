package com.ckr.mediabrowser.view.audio;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.view.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioCategoryFragment extends BaseFragment {

	public static AudioCategoryFragment newInstance() {

		Bundle args = new Bundle();

		AudioCategoryFragment fragment = new AudioCategoryFragment();
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
