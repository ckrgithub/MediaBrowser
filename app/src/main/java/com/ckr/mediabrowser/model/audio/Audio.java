package com.ckr.mediabrowser.model.audio;

import com.ckr.mediabrowser.model.MediaItem;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class Audio extends MediaItem {
	public Audio(String path, long size, String displayName, String title, String dateAdded, String dateModified, String mimeType, Long id) {
		super(path, size, displayName, title, dateAdded, dateModified, mimeType, id);
	}
}
