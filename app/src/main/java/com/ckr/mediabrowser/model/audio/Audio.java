package com.ckr.mediabrowser.model.audio;

import com.ckr.mediabrowser.model.MediaItem;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class Audio extends MediaItem {
	private long duration;
	private long artistId;
	private String artist;
	private long albumId;
	private String album;
	private int year;
	private int isMusic;
	private int isPodcast;
	private int isRingtone;
	private int isAlarm;
	private int  isNotification;

	public Audio(String path, long size, String displayName, String title, String dateAdded, String dateModified, String mimeType, Long id) {
		super(path, size, displayName, title, dateAdded, dateModified, mimeType, id);
	}

	public Audio() {
		super();
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getArtistId() {
		return artistId;
	}

	public void setArtistId(long artistId) {
		this.artistId = artistId;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getIsMusic() {
		return isMusic;
	}

	public void setIsMusic(int isMusic) {
		this.isMusic = isMusic;
	}

	public int getIsPodcast() {
		return isPodcast;
	}

	public void setIsPodcast(int isPodcast) {
		this.isPodcast = isPodcast;
	}

	public int getIsRingtone() {
		return isRingtone;
	}

	public void setIsRingtone(int isRingtone) {
		this.isRingtone = isRingtone;
	}

	public int getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(int isAlarm) {
		this.isAlarm = isAlarm;
	}

	public int getIsNotification() {
		return isNotification;
	}

	public void setIsNotification(int isNotification) {
		this.isNotification = isNotification;
	}

	@Override
	public int getMediaType() {
		return MEDIA_TYPE_AUDIO;
	}
}
