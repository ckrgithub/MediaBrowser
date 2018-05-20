package com.ckr.mediabrowser.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ckr.mediabrowser.R;
import com.ckr.mediabrowser.view.MediaContext;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class GlideUtil {
	private static final String TAG = "GlideUtil";

	public static void loadImageByPath(MediaContext context, ImageView imageView, String path) {
		if (context == null || imageView == null) {
			Logd(TAG, "loadCornerImage: context or imageView is null");
			return;
		}
		if (TextUtils.isEmpty(path)) {
			Logd(TAG, "loadCornerImage: path is empty");
			return;
		}
		RequestOptions options = new RequestOptions()
				.centerCrop()
				.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
				.dontAnimate()
				.placeholder(R.mipmap.ic_default_photo)
				.error(R.mipmap.ic_default_photo);
		if (context instanceof Activity) {
			Glide.with((Activity) context)
					.load(path)
					.transition(DrawableTransitionOptions.withCrossFade())//渐变动画
					.apply(options)
					.into(imageView);
		} else if (context instanceof Fragment) {
			Glide.with((Fragment) context)
					.load(path)
					.transition(DrawableTransitionOptions.withCrossFade())//渐变动画
					.apply(options)
					.into(imageView);
		}
	}

}