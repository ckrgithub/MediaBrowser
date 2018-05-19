package com.ckr.mediabrowser.model.photo.bean;

import com.ckr.mediabrowser.model.MediaItem;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class Photo extends MediaItem {
	private String description;
	private int isPrivate;
	private double latitude;
	private double longitude;
	private String dateTaken;
	private int orientation;
	private int miniThumbMagic;
	private String bucketId;
	private String bucketDisplayName;

	public Photo(String path, long size, String displayName, String title, String dateAdded, String dateModified, String mimeType, Long id) {
		super(path, size, displayName, title, dateAdded, dateModified, mimeType, id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int isPrivate() {
		return isPrivate;
	}

	public void setPrivate(int aPrivate) {
		isPrivate = aPrivate;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getMiniThumbMagic() {
		return miniThumbMagic;
	}

	public void setMiniThumbMagic(int miniThumbMagic) {
		this.miniThumbMagic = miniThumbMagic;
	}

	public String getBucketId() {
		return bucketId;
	}

	public void setBucketId(String bucketId) {
		this.bucketId = bucketId;
	}

	public String getBucketDisplayName() {
		return bucketDisplayName;
	}

	public void setBucketDisplayName(String bucketDisplayName) {
		this.bucketDisplayName = bucketDisplayName;
	}
}
