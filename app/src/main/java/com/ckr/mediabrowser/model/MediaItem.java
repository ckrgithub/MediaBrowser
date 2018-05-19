package com.ckr.mediabrowser.model;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public abstract class MediaItem {


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
}
