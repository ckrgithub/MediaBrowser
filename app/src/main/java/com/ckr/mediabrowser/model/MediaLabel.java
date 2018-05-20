package com.ckr.mediabrowser.model;

/**
 * Created by PC大佬 on 2018/5/20.
 */

public abstract class MediaLabel {
	private boolean isLabel;
	private String labelText;

	public boolean isLabel() {
		return isLabel;
	}

	public void setLabel(boolean label) {
		isLabel = label;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
}
