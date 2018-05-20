package com.ckr.mediabrowser.model;

import java.util.List;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public class Album {
	private String name;
	private List<? extends MediaItem> list;

	public Album(String name, List<? extends MediaItem> list) {
		this.name = name;
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public List<? extends MediaItem> getList() {
		return list;
	}
}
