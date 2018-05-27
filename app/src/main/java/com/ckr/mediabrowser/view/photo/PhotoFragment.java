package com.ckr.mediabrowser.view.photo;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.ckr.decoration.DividerGridItemDecoration;
import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.adapter.GridAdapter;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.observer.OnMediaListener;
import com.ckr.mediabrowser.view.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static com.ckr.mediabrowser.util.MediaLog.Logd;
import static com.ckr.mediabrowser.util.MediaLog.Loge;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseFragment implements OnMediaListener<Photo> {
	private static final String TAG = "PhotoFragment";
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	private boolean isVisible = false;
	private MediaObserver mMediaObserver;
	private List<MediaItem> targetList;
	private List<Photo> srcList;
	private static final int COLUMN = 4;
	private GridAdapter adapter;
	private Activity activity;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = (Activity) context;
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
		initView();
		mMediaObserver = MediaObserver.getInstance();
		mMediaObserver.registerListener(this);
	}

	private void initView() {
		GridLayoutManager layoutManager = new GridLayoutManager(getContext(), COLUMN);
		layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				MediaItem photo = targetList.get(position);
				if (photo.isLabel()) {
					return COLUMN;
				}
				return 1;
			}
		});
		recyclerView.setLayoutManager(layoutManager);
		DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), COLUMN);
		builder.setDivider(R.drawable.bg_divider_grid)
				.setShowOtherStyle(true);
		recyclerView.addItemDecoration(builder.build());
		adapter = new GridAdapter(this, COLUMN);
		recyclerView.setAdapter(adapter);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				switch (newState) {
					case SCROLL_STATE_SETTLING:
						Glide.with(PhotoFragment.this).pauseRequests();
						break;
					case SCROLL_STATE_IDLE:
						Glide.with(PhotoFragment.this).resumeRequests();
						break;
					case SCROLL_STATE_DRAGGING:
						Glide.with(PhotoFragment.this).resumeRequests();
						break;
				}
			}
		});
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
	public void refreshFragment() {

	}

	@Override
	public void subscribeOn(List<Photo> list, int mediaType) {
		Logd(TAG, "subscribeOn: mediaType:" + mediaType);
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
					MediaItem label = new Photo();
					label.setLabel(true);
					label.setLabelText(dateTaken);
					targetList.add(targetList.size(), label);
				}
				targetList.add(photo);
			}
			Logd(TAG, "handleData: size:" + size + ",:" + activity);
			if (activity != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Logd(TAG, "run: size:" + targetList.size());
						adapter.updateAll(targetList);
					}
				});
			}
		}
	}

	@Override
	public void subscribeOnFile(Map<String, List<Photo>> map, int mediaType) {

	}
}
