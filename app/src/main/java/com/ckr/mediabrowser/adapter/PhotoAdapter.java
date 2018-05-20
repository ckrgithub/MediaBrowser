package com.ckr.mediabrowser.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.photo.bean.Photo;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class PhotoAdapter extends BaseAdapter<Photo, PhotoAdapter.PhotoHolder> {


	public PhotoAdapter(Context context) {
		super(context);
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
		if (getItemViewType(position)==0) {
			holder.labelView.setText(photo.getLabelText());
		}else {
//			Glide.with(holder.imageView).
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
			}
		}
	}
}
