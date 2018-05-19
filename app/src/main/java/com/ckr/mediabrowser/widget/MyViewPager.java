package com.ckr.mediabrowser.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by PC大佬 on 2018/5/12.
 */

public class MyViewPager extends ViewPager {
	private boolean isCanScroll = true;

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item, false);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item, false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}
	}
}
