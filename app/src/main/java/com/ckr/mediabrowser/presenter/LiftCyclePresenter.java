package com.ckr.mediabrowser.presenter;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface LiftCyclePresenter {
	void onResume();

	void onPause();

	void onStop();

	void onDestroy();
}
