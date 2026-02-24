package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;

public class BwStatsOverlay extends Mod {

	public BwStatsOverlay() {
		super("BW Stats Overlay", "Shows Bedwars stats overlay", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		mc.fontRendererObj.drawStringWithShadow("BW Stats: Waiting for game...", getHudX(), getHudY(), 0xFFFFAA00);
	}
}
