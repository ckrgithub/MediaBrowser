package com.ckr.mediabrowser.model;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public abstract class MediaItem extends MediaLabel implements IMediaStore {
	protected String path;
	protected long size;
	protected String displayName;
	protected String title;
	protected String dateAdded;
	protected String dateModified;
	protected String mimeType;
	protected Long id;

	public MediaItem(String path, long size, String displayName, String title, String dateAdded, String dateModified, String mimeType, Long id) {
		this.path = path;
		this.size = size;
		this.displayName = displayName;
		this.title = title;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.mimeType = mimeType;
		this.id = id;
	}

	public MediaItem(){

	}

	public String getPath() {
		return path;
	}

	public long getSize() {
		return size;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getTitle() {
		return title;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public String getDateModified() {
		return dateModified;
	}

	public String getMimeType() {
		return mimeType;
	}

	public Long getId() {
		return id;
	}
}
