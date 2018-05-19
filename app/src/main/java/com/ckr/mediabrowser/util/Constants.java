package com.ckr.mediabrowser.util;

import android.provider.MediaStore;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class Constants {
	public static final int MEDIA_TYPE_PHOTO=0;
	public static final int MEDIA_TYPE_AUDIO=1;
	public static final int MEDIA_TYPE_VIDEO=2;
	public static final int MEDIA_TYPE_FILE=3;

	public static final String[][] MEDIA_CONFIG = {
			//图片数据库的列
			{
					MediaStore.Images.Media.DATA,
					MediaStore.Images.Media.DATE_TAKEN,
					MediaStore.Images.Media.MIME_TYPE,
					MediaStore.Images.Media.DISPLAY_NAME,
					MediaStore.Images.Media.TITLE,
					MediaStore.Images.Media.SIZE,
					MediaStore.Images.Media.ORIENTATION,
					MediaStore.Images.Media.WIDTH,
					MediaStore.Images.Media.HEIGHT,
					MediaStore.Images.Media.DESCRIPTION,
					MediaStore.Images.Media.IS_PRIVATE,
					MediaStore.Images.Media.BUCKET_ID,
					MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
					MediaStore.Images.Media._ID
			},
			//视频数据库的列
			{
					MediaStore.Video.Media.DATA,
					MediaStore.Video.Media.DATE_TAKEN,
					MediaStore.Video.Media.MIME_TYPE,
					MediaStore.Video.Media.DISPLAY_NAME,
					MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.SIZE,
					MediaStore.Video.Media.DURATION,
					MediaStore.Video.Media.ARTIST,
					MediaStore.Video.Media.ALBUM,
					MediaStore.Video.Media.DESCRIPTION,
					MediaStore.Video.Media.IS_PRIVATE,
					MediaStore.Video.Media.CATEGORY,
					MediaStore.Video.Media.LANGUAGE,
					MediaStore.Video.Media.MINI_THUMB_MAGIC,
					MediaStore.Video.Media.BUCKET_ID,
					MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
					MediaStore.Video.Media._ID
			},
			//音频数据库的列
			{
					MediaStore.Audio.Media.DATA,
					MediaStore.MediaColumns.DATE_MODIFIED,
					MediaStore.Audio.Media.MIME_TYPE,
					MediaStore.Audio.Media.DISPLAY_NAME,
					MediaStore.Audio.Media.TITLE,
					MediaStore.Audio.Media.SIZE,
					MediaStore.Audio.Media.DURATION,
					MediaStore.Audio.Media.ARTIST,
					MediaStore.Audio.Media.ALBUM,
					MediaStore.Audio.Media.YEAR,

					MediaStore.Audio.Media.IS_MUSIC,
					MediaStore.Audio.Media._ID
			}
	};
}
