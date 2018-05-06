package com.ckr.mediabrowser.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ckr.mediabrowser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseFragment {

	public static PhotoFragment newInstance() {
		
		Bundle args = new Bundle();
		
		PhotoFragment fragment = new PhotoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_photo;
	}

	@Override
	protected void init() {

	}

}
