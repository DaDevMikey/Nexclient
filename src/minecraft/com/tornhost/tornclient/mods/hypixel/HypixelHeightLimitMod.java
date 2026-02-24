package com.tornhost.tornclient.mods.hypixel;

import com.tornhost.tornclient.mods.Mod;

public class HypixelHeightLimitMod extends Mod {

	public HypixelHeightLimitMod() {
		super("HeightLimit", "Warns when approaching Hypixel height limit", Category.HYPIXEL);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		if (mc.thePlayer.posY > 200) {
			String warning = "\u00a7cWARNING: Height limit! Y: " + String.format("%.0f", mc.thePlayer.posY);
			mc.fontRendererObj.drawStringWithShadow(warning, getHudX(), getHudY(), 0xFFFF0000);
		}
	}
}
