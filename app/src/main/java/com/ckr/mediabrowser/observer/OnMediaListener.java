package com.ckr.mediabrowser.observer;

import com.ckr.mediabrowser.model.MediaItem;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/5.
 */

public interface OnMediaListener<T extends MediaItem>{
    void subscribeOn(List<T> list, int mediaType);
    void subscribeOnFile(Map<String, List<T>> map, int mediaType);
}
