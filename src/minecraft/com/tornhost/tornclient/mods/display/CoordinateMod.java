package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;

public class CoordinateMod extends Mod {

	public CoordinateMod() {
		super("Coordinates", "Displays your XYZ coordinates", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		String coords = String.format("X: %.1f Y: %.1f Z: %.1f",
				mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		mc.fontRendererObj.drawStringWithShadow(coords, getHudX(), getHudY(), 0xFFFFFFFF);
	}
}
