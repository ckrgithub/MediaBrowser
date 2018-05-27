package com.ckr.mediabrowser.model;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public interface IMediaStore {
	int MEDIA_TYPE_NONE=-1;
	int MEDIA_TYPE_PHOTO=0;
	int MEDIA_TYPE_AUDIO=1;
	int MEDIA_TYPE_VIDEO=2;
	int MEDIA_TYPE_FILE=3;
	String[][] MEDIA_TABLE = {
			//图片数据库的列
			{
					android.provider.MediaStore.Images.Media._ID,
					android.provider.MediaStore.Images.Media.DATA,
					android.provider.MediaStore.Images.Media.SIZE,
					android.provider.MediaStore.Images.Media.DISPLAY_NAME,
					android.provider.MediaStore.Images.Media.TITLE,
					android.provider.MediaStore.Images.Media.DATE_ADDED,
					android.provider.MediaStore.Images.Media.DATE_MODIFIED,
					android.provider.MediaStore.Images.Media.MIME_TYPE,
					android.provider.MediaStore.Images.Media.WIDTH,
					android.provider.MediaStore.Images.Media.HEIGHT,

					android.provider.MediaStore.Images.Media.DESCRIPTION,
					android.provider.MediaStore.Images.Media.IS_PRIVATE,
					android.provider.MediaStore.Images.Media.LATITUDE,
					android.provider.MediaStore.Images.Media.LONGITUDE,
					android.provider.MediaStore.Images.Media.DATE_TAKEN,
					android.provider.MediaStore.Images.Media.ORIENTATION,
					android.provider.MediaStore.Images.Media.MINI_THUMB_MAGIC,
					android.provider.MediaStore.Images.Media.BUCKET_ID,
					android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME
			},
			//音频数据库的列
			{
					android.provider.MediaStore.Video.Media._ID,
					android.provider.MediaStore.Video.Media.DATA,
					android.provider.MediaStore.Video.Media.SIZE,
					android.provider.MediaStore.Video.Media.DISPLAY_NAME,
					android.provider.MediaStore.Video.Media.TITLE,
					android.provider.MediaStore.Video.Media.DATE_ADDED,
					android.provider.MediaStore.Video.Media.DATE_MODIFIED,
					android.provider.MediaStore.Video.Media.MIME_TYPE,

					android.provider.MediaStore.Audio.Media.DURATION,
					android.provider.MediaStore.Audio.Media.ARTIST_ID,
					android.provider.MediaStore.Audio.Media.ARTIST,
					android.provider.MediaStore.Audio.Media.ALBUM_ID,
					android.provider.MediaStore.Audio.Media.ALBUM,
					android.provider.MediaStore.Audio.Media.YEAR,
					android.provider.MediaStore.Audio.Media.IS_MUSIC,
					android.provider.MediaStore.Audio.Media.IS_PODCAST,
					android.provider.MediaStore.Audio.Media.IS_RINGTONE,
					android.provider.MediaStore.Audio.Media.IS_ALARM,
					android.provider.MediaStore.Audio.Media.IS_NOTIFICATION
			},
			//视频数据库的列
			{
					android.provider.MediaStore.Video.Media._ID,
					android.provider.MediaStore.Video.Media.DATA,
					android.provider.MediaStore.Video.Media.SIZE,
					android.provider.MediaStore.Video.Media.DISPLAY_NAME,
					android.provider.MediaStore.Video.Media.TITLE,
					android.provider.MediaStore.Video.Media.DATE_ADDED,
					android.provider.MediaStore.Video.Media.DATE_MODIFIED,
					android.provider.MediaStore.Video.Media.MIME_TYPE,
					android.provider.MediaStore.Video.Media.WIDTH,
					android.provider.MediaStore.Video.Media.HEIGHT,

					android.provider.MediaStore.Video.Media.DURATION,
					android.provider.MediaStore.Video.Media.ARTIST,
					android.provider.MediaStore.Video.Media.ALBUM,
					android.provider.MediaStore.Video.Media.RESOLUTION,
					android.provider.MediaStore.Video.Media.DESCRIPTION,
					android.provider.MediaStore.Video.Media.IS_PRIVATE,
					android.provider.MediaStore.Video.Media.LANGUAGE,
					android.provider.MediaStore.Video.Media.LATITUDE,
					android.provider.MediaStore.Video.Media.LONGITUDE,
					android.provider.MediaStore.Video.Media.DATE_TAKEN,
					android.provider.MediaStore.Video.Media.BUCKET_ID,
					android.provider.MediaStore.Video.Media.BUCKET_DISPLAY_NAME
			}
	};

	int getMediaType();
}
