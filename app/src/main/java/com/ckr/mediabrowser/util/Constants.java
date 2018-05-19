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
					MediaStore.Images.Media._ID,
					MediaStore.Images.Media.DATA,
					MediaStore.Images.Media.SIZE,
					MediaStore.Images.Media.DISPLAY_NAME,
					MediaStore.Images.Media.TITLE,
					MediaStore.Images.Media.DATE_ADDED,
					MediaStore.Images.Media.DATE_MODIFIED,
					MediaStore.Images.Media.MIME_TYPE,
					MediaStore.Images.Media.WIDTH,
					MediaStore.Images.Media.HEIGHT,

					MediaStore.Images.Media.DESCRIPTION,
					MediaStore.Images.Media.IS_PRIVATE,
					MediaStore.Images.Media.LATITUDE,
					MediaStore.Images.Media.LONGITUDE,
					MediaStore.Images.Media.DATE_TAKEN,
					MediaStore.Images.Media.ORIENTATION,
					MediaStore.Images.Media.MINI_THUMB_MAGIC,
					MediaStore.Images.Media.BUCKET_ID,
					MediaStore.Images.Media.BUCKET_DISPLAY_NAME
			},
			//音频数据库的列
			{
					MediaStore.Video.Media._ID,
					MediaStore.Video.Media.DATA,
					MediaStore.Video.Media.SIZE,
					MediaStore.Video.Media.DISPLAY_NAME,
					MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.DATE_ADDED,
					MediaStore.Video.Media.DATE_MODIFIED,
					MediaStore.Video.Media.MIME_TYPE,
					MediaStore.Video.Media.WIDTH,
					MediaStore.Video.Media.HEIGHT,

					MediaStore.Audio.Media.DURATION,
					MediaStore.Audio.Media.ARTIST_ID,
					MediaStore.Audio.Media.ARTIST,
					MediaStore.Audio.Media.ALBUM_ID,
					MediaStore.Audio.Media.ALBUM,
					MediaStore.Audio.Media.YEAR,
					MediaStore.Audio.Media.IS_MUSIC,
					MediaStore.Audio.Media.IS_PODCAST,
					MediaStore.Audio.Media.IS_RINGTONE,
					MediaStore.Audio.Media.IS_ALARM,
					MediaStore.Audio.Media.IS_NOTIFICATION
			},
			//视频数据库的列
			{
					MediaStore.Video.Media._ID,
					MediaStore.Video.Media.DATA,
					MediaStore.Video.Media.SIZE,
					MediaStore.Video.Media.DISPLAY_NAME,
					MediaStore.Video.Media.TITLE,
					MediaStore.Video.Media.DATE_ADDED,
					MediaStore.Video.Media.DATE_MODIFIED,
					MediaStore.Video.Media.MIME_TYPE,
					MediaStore.Video.Media.WIDTH,
					MediaStore.Video.Media.HEIGHT,

					MediaStore.Video.Media.DURATION,
					MediaStore.Video.Media.ARTIST,
					MediaStore.Video.Media.ALBUM,
					MediaStore.Video.Media.RESOLUTION,
					MediaStore.Video.Media.DESCRIPTION,
					MediaStore.Video.Media.IS_PRIVATE,
					MediaStore.Video.Media.LANGUAGE,
					MediaStore.Video.Media.LATITUDE,
					MediaStore.Video.Media.LONGITUDE,
					MediaStore.Video.Media.DATE_TAKEN,
					MediaStore.Video.Media.BUCKET_ID,
					MediaStore.Video.Media.BUCKET_DISPLAY_NAME
			}

	};
}
