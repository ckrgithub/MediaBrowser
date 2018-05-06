package com.ckr.mediabrowser.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends BaseFragment {

	public static AudioFragment newInstance() {
		
		Bundle args = new Bundle();
		
		AudioFragment fragment = new AudioFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_audio;
	}

	@Override
	protected void init() {

	}

}
