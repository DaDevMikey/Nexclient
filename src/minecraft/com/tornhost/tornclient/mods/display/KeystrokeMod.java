package com.tornhost.tornclient.mods.display;

import com.tornhost.tornclient.mods.Mod;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class KeystrokeMod extends Mod {

	private static final int KEY_SIZE = 22;
	private static final int GAP = 2;

	public KeystrokeMod() {
		super("Keystrokes", "Shows WASD key presses on screen", Category.DISPLAY);
		this.setDraggable(true);
	}

	@Override
	public void onRender() {
		if (mc.thePlayer == null) return;
		int x = getHudX();
		int y = getHudY();

		boolean w = mc.gameSettings.keyBindForward.isKeyDown();
		boolean a = mc.gameSettings.keyBindLeft.isKeyDown();
		boolean s = mc.gameSettings.keyBindBack.isKeyDown();
		boolean d = mc.gameSettings.keyBindRight.isKeyDown();

		drawKey("W", x + KEY_SIZE + GAP, y, w);
		drawKey("A", x, y + KEY_SIZE + GAP, a);
		drawKey("S", x + KEY_SIZE + GAP, y + KEY_SIZE + GAP, s);
		drawKey("D", x + (KEY_SIZE + GAP) * 2, y + KEY_SIZE + GAP, d);
	}

	private void drawKey(String label, int x, int y, boolean pressed) {
		int bg = pressed ? 0xAAFFFFFF : 0xAA000000;
		int textColor = pressed ? 0xFF000000 : 0xFFFFFFFF;
		Gui.drawRect(x, y, x + KEY_SIZE, y + KEY_SIZE, bg);
		int textX = x + (KEY_SIZE - mc.fontRendererObj.getStringWidth(label)) / 2;
		int textY = y + (KEY_SIZE - 8) / 2;
		mc.fontRendererObj.drawStringWithShadow(label, textX, textY, textColor);
	}
}
