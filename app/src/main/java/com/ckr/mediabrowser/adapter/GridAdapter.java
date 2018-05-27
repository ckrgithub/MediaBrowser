package com.ckr.mediabrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.util.GlideUtil;
import com.ckr.mediabrowser.view.MediaContext;

import static com.ckr.mediabrowser.model.IMediaStore.MEDIA_TYPE_NONE;
import static com.ckr.mediabrowser.model.IMediaStore.MEDIA_TYPE_PHOTO;
import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class GridAdapter extends BaseAdapter<MediaItem, GridAdapter.MediaItemHolder> {
	private static final String TAG = "GridAdapter";

	private MediaContext context;
	private int imageHeight;

	public GridAdapter(@NonNull MediaContext context, int column) {
		super(context.getContext());
		this.context = context;
		float dimension = mContext.getResources().getDimension(R.dimen.divider_grid);
		imageHeight = (int) ((getScreenWidth() - dimension * (column + 1)) / column);
		Logd(TAG, "GridAdapter: imageHeight:" + imageHeight);
	}

	private int getScreenWidth() {
		WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		int widthPixels = outMetrics.widthPixels;
		return widthPixels;
	}

	@Override
	public int getItemViewType(int position) {
		MediaItem mediaItem = data.get(position);
		boolean isLabel = mediaItem.isLabel();
		if (isLabel) {
			return MEDIA_TYPE_NONE;
		} else {
			return mediaItem.getMediaType();
		}
	}

	@Override
	protected int getLayoutId(int viewType) {
		if (viewType == MEDIA_TYPE_PHOTO) {
			return R.layout.item_grid;
		}
		return R.layout.item_label;
	}

	@Override
	protected MediaItemHolder getViewHolder(View itemView, int viewType) {
		return new MediaItemHolder(itemView, viewType);
	}

	@Override
	protected void convert(MediaItemHolder holder, int position, MediaItem mediaItem) {
		int itemViewType = getItemViewType(position);
		if (itemViewType == MEDIA_TYPE_NONE) {
			holder.labelView.setText(mediaItem.getLabelText());
		} else if (itemViewType == MEDIA_TYPE_PHOTO) {
			String path = mediaItem.getPath();
			Logd(TAG, "convert: path:" + path);
			GlideUtil.loadImageByPath(context, holder.imageView, path);
		}
	}

	class MediaItemHolder extends BaseViewHolder {

		private TextView labelView;
		private ImageView imageView;

		public MediaItemHolder(View itemView, int viewType) {
			super(itemView);
			if (viewType == MEDIA_TYPE_NONE) {
				labelView = itemView.findViewById(R.id.labelView);
			} else if (viewType == MEDIA_TYPE_PHOTO) {
				imageView = itemView.findViewById(R.id.imageView);
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				layoutParams.height = imageHeight;
				imageView.setLayoutParams(layoutParams);
			}
		}
	}
}
