package com.ckr.mediabrowser.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.util.PermissionRequest;
import com.ckr.mediabrowser.util.ToastUtils;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/6.
 */

public abstract class BaseFragment extends Fragment implements PermissionRequest.PermissionListener, DialogInterface.OnClickListener ,MediaContext{
	private static final String TAG = "BaseFragment";
	private static final int REQUEST_SETTING_CODE = 2018;

	private View view;
	private Unbinder unbinder;
	private AlertDialog dialog;
	@BindString(R.string.tips)
	String tips;
	@BindString(R.string.message)
	String message;

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
		Logd(TAG, "onViewCreated: " + this);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Logd(TAG, "onActivityCreated: " + this);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Nullable
	@Override
	public Context getContext() {
		return super.getContext();
	}

	protected abstract int getLayoutId();

	protected abstract void init();

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logd(TAG, "onActivityResult: requestCode:" + requestCode + ",resultCode:" + resultCode);
		if (requestCode == REQUEST_SETTING_CODE) {
			if (PermissionRequest.requestPermission(this, PermissionRequest.PERMISSION_STORAGE, PermissionRequest.REQUEST_STORAGE)) {
				onPermissionGranted();
			} else {
				ToastUtils.toast(message);
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case PermissionRequest.REQUEST_STORAGE:
				if (PermissionRequest.isPermissionGranted(this, permissions, grantResults)) {
					onPermissionGranted();
				}
				break;
		}
	}

	@Override
	public void onPermissionGranted() {
		Logd(TAG, "onPermissionGranted: ");
	}

	@Override
	public void onPermissionPermanentlyDenied() {
		Logd(TAG, "onPermissionPermanentlyDenied: ");
		if (dialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			dialog = builder.setCancelable(true)
					.setTitle(tips)
					.setMessage(message)
					.setPositiveButton(R.string.confirm, this)
					.setNegativeButton(R.string.cancel, this).create();
		}
		dialog.show();
	}

	@Override
	public void onClick(DialogInterface dialogInterface, int which) {
		if (dialog != null) {
			dialog.dismiss();
		}
		switch (which) {
			case Dialog.BUTTON_POSITIVE:
				startActivityForResult(
						new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
								.setData(Uri.fromParts("package", getContext().getPackageName(), null)),
						REQUEST_SETTING_CODE);
				break;
			case Dialog.BUTTON_NEGATIVE:
				break;
		}
	}

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

	public Dialog createLoadingDialog() {
		if (getActivity() == null) {
			return null;
		} else {
			Dialog loadingDialog = new Dialog(getActivity(), R.style.Theme_Transparent_Dialog);
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dp2px(100), dp2px(100));
			View inflate = View.inflate(getActivity(), R.layout.dialog_loading, null);
			loadingDialog.addContentView(inflate, params);
			return loadingDialog;
		}
	}


	protected void showDialog(Dialog dialog) {
		if (dialog != null) {
			if (!dialog.isShowing()) {
				dialog.show();
			}
		}
	}

	protected void dismissDialog(Dialog dialog) {
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	protected int dp2px(float dp) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
