package com.nexclient.client.mods.display;

import com.nexclient.client.mods.Mod;

public class SpeedometerMod extends Mod {

	public SpeedometerMod() {
		super("Speedometer", "Shows your movement speed", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		double dx = mc.thePlayer.motionX;
		double dz = mc.thePlayer.motionZ;
		double speed = Math.sqrt(dx * dx + dz * dz) * 20.0;
		String text = String.format("Speed: %.2f b/s", speed);
		mc.fontRendererObj.drawStringWithShadow(text, getHudX(), getHudY(), 0xFFFFFFFF);
	}
}
