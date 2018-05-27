package com.ckr.mediabrowser.view.video;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.ckr.decoration.DividerLinearItemDecoration;
import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.adapter.GridAdapter;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.video.Video;
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
public class VideoFragment extends BaseFragment implements OnMediaListener<Video> {
	private static final String TAG = "VideoFragment";
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	private boolean isVisible = false;
	private MediaObserver mMediaObserver;
	private List<MediaItem> targetList;
	private List<Video> srcList;
	private static final int COLUMN = 5;
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
		targetList = new ArrayList<>();
		srcList = new ArrayList<>();
		initView();
		mMediaObserver = MediaObserver.getInstance();
		mMediaObserver.registerListener(this);
	}


	private void initView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		DividerLinearItemDecoration.Builder builder = new DividerLinearItemDecoration.Builder(getContext());
		builder.setDivider(R.drawable.bg_divider_linear)
				.removeFooterDivider(true)
				.removeHeaderDivider(true);
		recyclerView.addItemDecoration(builder.build());
		adapter = new GridAdapter(this, COLUMN);
		recyclerView.setAdapter(adapter);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				switch (newState) {
					case SCROLL_STATE_SETTLING:
						Glide.with(VideoFragment.this).pauseRequests();
						break;
					case SCROLL_STATE_IDLE:
						Glide.with(VideoFragment.this).resumeRequests();
						break;
					case SCROLL_STATE_DRAGGING:
						Glide.with(VideoFragment.this).resumeRequests();
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
	public void subscribeOn(List<Video> list, int mediaType) {
		Logd(TAG, "subscribeOn: mediaType:" + mediaType);
		if (mediaType != IMediaStore.MEDIA_TYPE_VIDEO) {
			return;
		}
		synchronized (this) {
			handleData(list);
		}
	}

	private final Map<String, Integer> hashMap = new HashMap<String, Integer>();

	private void handleData(List<Video> list) {
		if (list.size() > 0) {
			hashMap.clear();
			targetList.clear();
			srcList.clear();
			srcList.addAll(list);
			int size = srcList.size();
			for (int i = 0; i < size; i++) {
				Video photo = srcList.get(i);
				String dateTaken = photo.getSubMineType();
				if (hashMap.containsKey(dateTaken)) {
					Logd(TAG, "handleData: 已添加日期:" + dateTaken);
				} else {
					Loge(TAG, "handleData: 未添加日期:" + dateTaken);
					hashMap.put(dateTaken, i);
					MediaItem label = new Video();
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
	public void subscribeOnFile(Map<String, List<Video>> map, int mediaType) {

	}
}
