package com.nexclient.client.mods.render;

import com.nexclient.client.mods.Mod;

public class GlintColorMod extends Mod {

	private int glintColor = 0xFF8000FF;

	public GlintColorMod() {
		super("GlintColor", "Custom enchantment glint color", Category.RENDER);
	}

	public int getGlintColor() {
		return glintColor;
	}

	public void setGlintColor(int glintColor) {
		this.glintColor = glintColor;
	}
}
