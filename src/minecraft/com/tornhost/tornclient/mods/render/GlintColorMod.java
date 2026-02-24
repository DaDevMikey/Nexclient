package com.tornhost.tornclient.mods.render;

import com.tornhost.tornclient.mods.Mod;

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
