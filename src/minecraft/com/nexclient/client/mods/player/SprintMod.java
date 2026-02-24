package com.nexclient.client.mods.player;

import com.nexclient.client.mods.Mod;

public class SprintMod extends Mod {

	public SprintMod() {
		super("Sprint", "Automatically sprints when moving forward", Category.PLAYER);
	}

	@Override
	public void onUpdate() {
		if (mc.thePlayer == null) return;
		if (mc.thePlayer.movementInput.moveForward >= 0.8F
				&& !mc.thePlayer.isSneaking()
				&& !mc.thePlayer.isUsingItem()
				&& !mc.thePlayer.isCollidedHorizontally
				&& mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F) {
			mc.thePlayer.setSprinting(true);
		}
	}
}
