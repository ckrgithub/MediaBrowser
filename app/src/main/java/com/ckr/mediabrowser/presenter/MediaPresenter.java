package com.ckr.mediabrowser.presenter;

import android.database.Cursor;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public interface MediaPresenter extends LiftCyclePresenter{
	void loadMedia(Cursor cursor,String[] mediaTable);
}
