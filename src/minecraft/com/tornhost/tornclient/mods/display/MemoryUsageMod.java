package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;

public class MemoryUsageMod extends Mod {

	public MemoryUsageMod() {
		super("MemoryUsage", "Displays current RAM usage", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1048576L;
		long maxMB = rt.maxMemory() / 1048576L;
		String text = "RAM: " + usedMB + " / " + maxMB + " MB";
		mc.fontRendererObj.drawStringWithShadow(text, getHudX(), getHudY(), 0xFFFFAA00);
	}
}
