package com.ckr.mediabrowser.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.model.Album;
import com.ckr.mediabrowser.model.IMediaStore;
import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.util.GlideUtil;
import com.ckr.mediabrowser.view.MediaContext;

import java.util.List;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class LinearAdapter extends BaseAdapter<Album, LinearAdapter.AlbumHolder> {
	private static final String TAG = "GridAdapter";

	private MediaContext context;
	private String formatString;

	public LinearAdapter(@NonNull MediaContext context, int mediaType) {
		super(context.getContext());
		this.context = context;
		if (mediaType == IMediaStore.MEDIA_TYPE_PHOTO) {
			formatString = mContext.getString(R.string.album_sum);
		}else if (mediaType == IMediaStore.MEDIA_TYPE_AUDIO) {
			formatString = mContext.getString(R.string.audio_sum);
		}else if (mediaType == IMediaStore.MEDIA_TYPE_VIDEO) {
			formatString = mContext.getString(R.string.video_sum);
		}
	}

	@Override
	protected int getLayoutId(int viewType) {
		return R.layout.item_linear;
	}

	@Override
	protected AlbumHolder getViewHolder(View itemView, int viewType) {
		return new AlbumHolder(itemView, viewType);
	}

	@Override
	protected void convert(AlbumHolder holder, int position, Album album) {

		List<? extends MediaItem> list = album.getList();
		holder.tvName.setText(album.getName());
		holder.tvSum.setText(String.format(formatString, list.size()));
		MediaItem mediaItem = list.get(0);
		String path = mediaItem.getPath();
		Logd(TAG, "convert: path:" + path);
		GlideUtil.loadImageByPath(context, holder.imageView, path);
	}

	class AlbumHolder extends BaseViewHolder {

		private TextView tvName;
		private TextView tvSum;
		private ImageView imageView;

		public AlbumHolder(View itemView, int viewType) {
			super(itemView);
			tvName = itemView.findViewById(R.id.tv_name);
			tvSum = itemView.findViewById(R.id.tv_size);
			imageView = itemView.findViewById(R.id.imageView);
		}
	}
}
