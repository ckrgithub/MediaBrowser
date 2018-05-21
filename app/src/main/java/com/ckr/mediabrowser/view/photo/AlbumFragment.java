package com.ckr.mediabrowser.view.photo;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.ckr.decoration.DividerLinearItemDecoration;
import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.adapter.LinearAdapter;
import com.ckr.mediabrowser.model.Album;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.observer.MediaObserver;
import com.ckr.mediabrowser.observer.OnMediaListener;
import com.ckr.mediabrowser.view.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends BaseFragment implements OnMediaListener<Photo> {
	private static final String TAG = "AlbumFragment";
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	private boolean isVisible = false;
	private MediaObserver mMediaObserver;
	private List<Album> targetList;
	private List<String> pathList;
	private LinearAdapter adapter;
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
		pathList = new ArrayList<>();
		initView();
		mMediaObserver = MediaObserver.getInstance();
		mMediaObserver.registerListener(this);
	}

	private void initView() {
		int dimension = (int) getContext().getResources().getDimension(R.dimen.padding);
		recyclerView.setPadding(dimension,0,dimension,0);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		DividerLinearItemDecoration.Builder builder = new DividerLinearItemDecoration.Builder(getContext());
		builder.setDivider(R.drawable.bg_divider_linear);
		recyclerView.addItemDecoration(builder.build());
		adapter = new LinearAdapter(this);
		recyclerView.setAdapter(adapter);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				switch (newState) {
					case SCROLL_STATE_SETTLING:
						Glide.with(AlbumFragment.this).pauseRequests();
						break;
					case SCROLL_STATE_IDLE:
						Glide.with(AlbumFragment.this).resumeRequests();
						break;
					case SCROLL_STATE_DRAGGING:
						Glide.with(AlbumFragment.this).resumeRequests();
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

	private final Map<String, List<MediaItem>> hashMap = new HashMap<>();

	private void handleData(List<Photo> list) {
		if (list.size() > 0) {
			hashMap.clear();
			targetList.clear();
			pathList.clear();
			int size = list.size();
			List<MediaItem> data = null;
			for (int i = 0; i < size; i++) {
				Photo photo = list.get(i);
				String parentPath = photo.getParentPath();
				String name = parentPath.substring(parentPath.lastIndexOf("/") + 1, parentPath.length());
				if (pathList.contains(parentPath)) {
					List<MediaItem> mediaItems = hashMap.get(name);
					mediaItems.add(photo);
				} else {
					data = new ArrayList<>();
					pathList.add(parentPath);
					data.add(photo);
					hashMap.put(name, data);
				}
			}
			for (Map.Entry<String, List<MediaItem>> entry : hashMap.entrySet()) {
				Log.d(TAG, "handleData: key:" + entry.getKey() + ",value:" + entry.getValue());
				Album album = new Album(entry.getKey(), entry.getValue());
				targetList.add(album);
			}
			Collections.sort(targetList, new Comparator<Album>() {
				@Override
				public int compare(Album s, Album s1) {
					String name = s.getName();
					String name1 = s1.getName();
					return name.compareToIgnoreCase(name1);
				}
			});
			Logd(TAG, "handleData: size:" + targetList.size() + ",:" + activity);
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
	public void subscribeOnFile(Map map, int mediaType) {

	}
}
