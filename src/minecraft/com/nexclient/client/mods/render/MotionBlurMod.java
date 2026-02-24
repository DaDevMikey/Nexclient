package com.nexclient.client.mods.render;

import com.nexclient.client.mods.Mod;

public class MotionBlurMod extends Mod {

	private float blurAmount = 0.5F;

	public MotionBlurMod() {
		super("MotionBlur", "Adds a motion blur effect", Category.RENDER);
	}

	public float getBlurAmount() {
		return blurAmount;
	}

	public void setBlurAmount(float blurAmount) {
		this.blurAmount = blurAmount;
	}
}
