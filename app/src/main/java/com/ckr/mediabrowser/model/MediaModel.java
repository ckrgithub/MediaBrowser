package com.ckr.mediabrowser.model;

import android.database.Cursor;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaModel {
	void loadMedia(Cursor cursor, String[] mediaTable);

	void onDestroy();
}
