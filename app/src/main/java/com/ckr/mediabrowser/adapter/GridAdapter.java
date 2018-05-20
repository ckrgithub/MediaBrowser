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
import com.ckr.mediabrowser.model.photo.bean.Photo;
import com.ckr.mediabrowser.util.GlideUtil;
import com.ckr.mediabrowser.view.MediaContext;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class GridAdapter extends BaseAdapter<Photo, GridAdapter.PhotoHolder> {
	private static final String TAG = "GridAdapter";

	private MediaContext context;
	private int imageHeight;

	public GridAdapter(@NonNull MediaContext context, int column) {
		super(context.getContext());
		this.context = context;
		float dimension = mContext.getResources().getDimension(R.dimen.divider_size);
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
		Photo photo = data.get(position);
		boolean isLabel = photo.isLabel();
		if (isLabel) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	protected int getLayoutId(int viewType) {
		if (viewType == 0) {
			return R.layout.item_label;
		} else {
			return R.layout.item_photo_grid;
		}
	}

	@Override
	protected PhotoHolder getViewHolder(View itemView, int viewType) {
		return new PhotoHolder(itemView, viewType);
	}

	@Override
	protected void convert(PhotoHolder holder, int position, Photo photo) {
		if (getItemViewType(position) == 0) {
			holder.labelView.setText(photo.getLabelText());
		} else {
			String path = photo.getPath();
			Logd(TAG, "convert: path:"+path);
			GlideUtil.loadImageByPath(context, holder.imageView, path);
		}
	}

	class PhotoHolder extends BaseViewHolder {

		private TextView labelView;
		private ImageView imageView;

		public PhotoHolder(View itemView, int viewType) {
			super(itemView);
			if (viewType == 0) {
				labelView = itemView.findViewById(R.id.labelView);
			} else {
				imageView = itemView.findViewById(R.id.imageView);
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				layoutParams.height = imageHeight;
				imageView.setLayoutParams(layoutParams);
			}
		}
	}
}
