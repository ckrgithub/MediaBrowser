package com.ckr.mediabrowser.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/6.
 */

public abstract class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";
	private View view;
	private Unbinder unbinder;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Logd(TAG, "onAttach: " + this);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Logd(TAG, "onCreate: " + this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), container, false);
		Logd(TAG, "onCreateView: " + this);
		unbinder = ButterKnife.bind(this, view);
		init();
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.d(TAG, "onViewCreated: " + this);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated: " + this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	protected abstract int getLayoutId();

	protected abstract void init();

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		Logd(TAG, "setUserVisibleHint: isVisibleToUser:" + isVisibleToUser + ",this:" + this);
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			onVisible();
		} else {
			onInvisible();
		}
	}

	protected void onVisible() {
	}

	protected void onInvisible() {
	}

}
