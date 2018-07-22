package com.ckr.mediabrowser.observer;

import com.ckr.mediabrowser.model.MediaItem;
import com.ckr.mediabrowser.model.file.Document;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.ckr.mediabrowser.util.MediaLog.Logd;

/**
 * Created by PC大佬 on 2018/5/19.
 */

public class MediaObserver {
	private static final String TAG = "MediaObserver";
	private static LinkedList<OnMediaListener> mListeners = new LinkedList<>();

	private static final class MediaHolder{
		private static final MediaObserver observer=new MediaObserver();
	}

	public static MediaObserver getInstance(){
		return MediaHolder.observer;
	}

	public void registerListener(OnMediaListener listener) {
		if (listener == null) {
			return;
		}
		mListeners.add(listener);
	}

	public void unregisterListener(OnMediaListener listener) {
		if (listener == null) {
			return;
		}
		if (mListeners.contains(listener)) {
			mListeners.remove(listener);
		}
	}

	public void release(){
		mListeners.clear();
	}

	public void subscribeOn(List<? extends MediaItem> media, int mediaType) {
		Logd(TAG, "subscribeOnAudio: " + mListeners.size());
		for (int i = 0; i < mListeners.size(); i++) {
			OnMediaListener listener = mListeners.get(i);
			if (listener == null) {
				mListeners.remove(listener);
				i--;
			} else {
				listener.subscribeOn(media, mediaType);
			}
		}
	}

	public void subscribeOnFile(Map<String, List<Document>> map, int mediaType) {
		Iterator<OnMediaListener> iterator = mListeners.iterator();
		while (iterator.hasNext()) {
			OnMediaListener listener = iterator.next();
			if (listener == null) {
				iterator.remove();
			} else {
				listener.subscribeOnFile(map, mediaType);
			}
		}
	}
}
