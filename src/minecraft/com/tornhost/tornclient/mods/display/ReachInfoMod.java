package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;

public class ReachInfoMod extends Mod {

	public ReachInfoMod() {
		super("ReachInfo", "Shows your current reach distance", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		float reach = mc.playerController.getBlockReachDistance();
		String text = String.format("Reach: %.1f", reach);
		mc.fontRendererObj.drawStringWithShadow(text, getHudX(), getHudY(), 0xFF00FF00);
	}
}
