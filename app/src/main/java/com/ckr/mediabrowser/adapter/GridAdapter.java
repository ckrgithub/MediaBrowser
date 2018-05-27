package com.ckr.mediabrowser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
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
		if (viewType == MEDIA_TYPE_NONE) {
			return R.layout.item_label;
		}else if (viewType == MEDIA_TYPE_PHOTO) {
			return R.layout.item_grid;
		}
		return R.layout.item_grid_2;
	}

	@Override
	protected MediaItemHolder getViewHolder(View itemView, int viewType) {
		return new MediaItemHolder(itemView, viewType);
	}

	@Override
	protected void convert(MediaItemHolder holder, int position, MediaItem mediaItem) {
		int itemViewType = getItemViewType(position);
		if (itemViewType == MEDIA_TYPE_NONE) {
			int mediaType = mediaItem.getMediaType();
			if (mediaType!=MEDIA_TYPE_PHOTO) {
				holder.labelView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.bg_label));
			}
			holder.labelView.setText(mediaItem.getLabelText());
		} else if (itemViewType == MEDIA_TYPE_PHOTO) {
			String path = mediaItem.getPath();
			Logd(TAG, "convert: path:" + path);
			GlideUtil.loadImageByPath(context, holder.imageView, path);
		}else {
			String path = mediaItem.getPath();
			Logd(TAG, "convert: path:" + path);
			GlideUtil.loadImageByPath(context, holder.imageView, path);
			holder.tvName.setText(mediaItem.getDisplayName());
			long size = mediaItem.getSize();
			String sizeString = convertSize(size);
			holder.tvSize.setText(sizeString);
			holder.tvDate.setText(mediaItem.getDateModified());
		}
	}

	@NonNull
	private String convertSize(long size) {
		int kb = (int) (size / 1024);
		Logd(TAG, "convertSize: kb:" + kb);
		String sizeString = "";
		if (kb < 1) {
			sizeString = size + "B";
		} else {
			int mb = (kb / 1024);
			Log.d(TAG, "convertSize: mb:" + mb);
			if (mb > 0) {
				int k = (int) (kb % 1024 / 10.24f);
				Logd(TAG, "convertSize: 小数点:" + k);
				sizeString = mb + "." + k + "MB";
			} else {
				sizeString = kb + "KB";
			}
		}
		return sizeString;
	}

	class MediaItemHolder extends BaseViewHolder {

		private TextView labelView;
		private ImageView imageView;
		private TextView tvName;
		private TextView tvSize;
		private TextView tvDate;

		public MediaItemHolder(View itemView, int viewType) {
			super(itemView);
			if (viewType == MEDIA_TYPE_NONE) {
				labelView = itemView.findViewById(R.id.labelView);
			} else if (viewType == MEDIA_TYPE_PHOTO) {
				imageView = itemView.findViewById(R.id.imageView);
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				layoutParams.height = imageHeight;
				imageView.setLayoutParams(layoutParams);
			}else {
				imageView = itemView.findViewById(R.id.imageView);
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				layoutParams.height = imageHeight;
				layoutParams.width = imageHeight;
				imageView.setLayoutParams(layoutParams);
				tvName = itemView.findViewById(R.id.tv_name);
				tvSize = itemView.findViewById(R.id.tv_size);
				tvDate = itemView.findViewById(R.id.tv_date);
			}
		}
	}
}
