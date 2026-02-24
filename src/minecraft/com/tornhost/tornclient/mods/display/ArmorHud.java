package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

public class ArmorHud extends Mod {

	public ArmorHud() {
		super("ArmorHud", "Shows your equipped armor on the HUD", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		ScaledResolution sr = new ScaledResolution(mc);
		int x = getHudX();
		int y = getHudY();
		for (int i = 3; i >= 0; i--) {
			ItemStack stack = mc.thePlayer.inventory.armorInventory[i];
			if (stack != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, x, y, null);
				y += 18;
			}
		}
	}
}
