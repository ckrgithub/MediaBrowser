package com.ckr.mediabrowser.view.photo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.observer.OnMediaListener;
import com.ckr.mediabrowser.view.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ckr.mediabrowser.util.MediaLog.Logd;
import static com.ckr.mediabrowser.util.MediaLog.Loge;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseFragment implements OnMediaListener<Photo> {
	private static final String TAG = "PhotoFragment";
	private boolean isVisible = false;
	private MediaObserver mMediaObserver;
	private List<Photo> targetList;
	private List<Photo> srcList;

	public static PhotoFragment newInstance() {

		Bundle args = new Bundle();

		PhotoFragment fragment = new PhotoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.recycler_view;
	}

	@Override
	protected void init() {
		Logd(TAG, "init: ");
		targetList = new ArrayList<>();
		srcList = new ArrayList<>();
		mMediaObserver = MediaObserver.getInstance();
		mMediaObserver.registerListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Logd(TAG, "onResume: ");
	}

	@Override
	protected void onVisible() {
		Logd(TAG, "onVisible: " + isVisible);
		isVisible = true;
	}

	@Override
	protected void onInvisible() {
		Log.d(TAG, "onInvisible: " + isVisible);
		isVisible = false;
	}

	@Override
	public void subscribeOn(List<Photo> list, int mediaType) {
		if (mediaType != IMediaStore.MEDIA_TYPE_PHOTO) {
			return;
		}
		synchronized (this) {
			handleData(list);
		}
	}

	private final Map<String, Integer> hashMap = new HashMap<String, Integer>();

	private void handleData(List<Photo> list) {
		if (list.size() > 0) {
			hashMap.clear();
			targetList.clear();
			srcList.clear();
			srcList.addAll(list);
			int size = srcList.size();
			for (int i = 0; i < size; i++) {
				Photo photo = srcList.get(i);
				String dateTaken = photo.getDateTaken();
				if (hashMap.containsKey(dateTaken)) {
					Logd(TAG, "handleData: 已添加日期:" + dateTaken);
				} else {
					Loge(TAG, "handleData: 未添加日期:" + dateTaken);
					hashMap.put(dateTaken, i);
					Photo label = new Photo();
					label.setLabel(true);
					label.setLabelText(dateTaken);
					targetList.add(targetList.size(), label);
				}
				targetList.add(photo);
			}
		}
	}

	@Override
	public void subscribeOnFile(Map<String, List<Photo>> map, int mediaType) {

	}
}
