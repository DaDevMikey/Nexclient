package com.nexclient.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nexclient.client.Client;
import com.nexclient.client.mods.Mod;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GuiHudEditor extends GuiScreen {

	private final GuiScreen parentScreen;
	private Mod draggingMod = null;
	private int dragOffsetX, dragOffsetY;

	public GuiHudEditor(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height - 28, 100, 20, "Done"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// Semi-transparent background
		Gui.drawRect(0, 0, this.width, this.height, 0x80000000);

		// Title
		this.drawCenteredString(this.fontRendererObj,
				"\u00A7b\u00A7lHUD Editor \u00A77- Drag mods to reposition them",
				this.width / 2, 6, 0xFFFFFFFF);

		// Handle dragging
		if (draggingMod != null) {
			draggingMod.setHudX(mouseX - dragOffsetX);
			draggingMod.setHudY(mouseY - dragOffsetY);
		}

		// Draw all draggable mod placeholders
		List<Mod> draggableMods = getDraggableMods();
		for (Mod mod : draggableMods) {
			boolean isDragging = mod == draggingMod;
			boolean isHovered = !isDragging && isMouseOverMod(mod, mouseX, mouseY);

			int x = mod.getHudX();
			int y = mod.getHudY();
			int w = getModWidth(mod);
			int h = getModHeight(mod);

			// Background
			int bgColor = isDragging ? 0x801E90FF : (isHovered ? 0x60FFFFFF : 0x40FFFFFF);
			Gui.drawRect(x - 2, y - 2, x + w + 2, y + h + 2, bgColor);

			// Border
			int borderColor = isDragging ? 0xFF1E90FF : (isHovered ? 0xFFFFFFFF : 0xFF666666);
			Gui.drawRect(x - 2, y - 2, x + w + 2, y - 1, borderColor);
			Gui.drawRect(x - 2, y + h + 1, x + w + 2, y + h + 2, borderColor);
			Gui.drawRect(x - 2, y - 2, x - 1, y + h + 2, borderColor);
			Gui.drawRect(x + w + 1, y - 2, x + w + 2, y + h + 2, borderColor);

			// Render the mod's HUD content
			if (mod.isEnabled()) {
				mod.onRender();
			} else {
				// Show placeholder for disabled mods
				this.fontRendererObj.drawStringWithShadow(
						"\u00A78" + mod.getName(), x, y, 0xFF666666);
			}

			// Label
			this.fontRendererObj.drawStringWithShadow(
					"\u00A77" + mod.getName(),
					x, y - 12, 0xFFAAAAAA);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private List<Mod> getDraggableMods() {
		List<Mod> result = new ArrayList<>();
		for (Mod mod : Client.getInstance().modManager.getMods()) {
			if (mod.isDraggable()) {
				result.add(mod);
			}
		}
		return result;
	}

	private boolean isMouseOverMod(Mod mod, int mouseX, int mouseY) {
		int x = mod.getHudX();
		int y = mod.getHudY();
		int w = getModWidth(mod);
		int h = getModHeight(mod);
		return mouseX >= x - 2 && mouseX <= x + w + 2
				&& mouseY >= y - 2 && mouseY <= y + h + 2;
	}

	private int getModWidth(Mod mod) {
		return Math.max(80, this.fontRendererObj.getStringWidth(mod.getName()) + 20);
	}

	private int getModHeight(Mod mod) {
		return 16;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			List<Mod> draggableMods = getDraggableMods();
			for (Mod mod : draggableMods) {
				if (isMouseOverMod(mod, mouseX, mouseY)) {
					draggingMod = mod;
					dragOffsetX = mouseX - mod.getHudX();
					dragOffsetY = mouseY - mod.getHudY();
					return;
				}
			}
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		if (state == 0) {
			draggingMod = null;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}
}
