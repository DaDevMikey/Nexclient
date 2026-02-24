package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionHud extends Mod {

	public PotionHud() {
		super("PotionHud", "Shows active potion effects on the HUD", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		int x = getHudX();
		int y = getHudY();
		for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
			Potion potion = Potion.potionTypes[effect.getPotionID()];
			if (potion == null) continue;
			int amplifier = effect.getAmplifier() + 1;
			int seconds = effect.getDuration() / 20;
			String text = potion.getName() + " " + amplifier + " - " + formatTime(seconds);
			mc.fontRendererObj.drawStringWithShadow(text, x, y, potion.getLiquidColor());
			y += 12;
		}
	}

	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format("%d:%02d", minutes, seconds);
	}
}
