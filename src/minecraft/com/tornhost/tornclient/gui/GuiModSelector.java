package com.tornhost.tornclient.gui;

import java.io.IOException;
import java.util.List;

import com.tornhost.tornclient.Client;
import com.tornhost.tornclient.mods.Mod;
import com.tornhost.tornclient.mods.Mod.Category;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class GuiModSelector extends GuiScreen {

	private final GuiScreen parentScreen;
	private Category selectedCategory = Category.DISPLAY;
	private int scrollOffset = 0;
	private int maxScroll = 0;

	// Layout constants
	private static final int SIDEBAR_WIDTH = 120;
	private static final int CARD_HEIGHT = 48;
	private static final int CARD_PADDING = 6;
	private static final int HEADER_HEIGHT = 40;
	
	// Category icon characters
	private static final String[] CATEGORY_ICONS = {
		"\u2726", // Display - star
		"\u2694", // Player - swords
		"\u2728", // Render - sparkles
		"\u270E", // Chat - pencil
		"\u2302", // Hypixel - house
		"\u26A1"  // Optimization - lightning
	};

	public GuiModSelector(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		// Back button
		this.buttonList.add(new GuiButton(0, 8, this.height - 28, 100, 20, "Back"));
		this.scrollOffset = 0;
		updateMaxScroll();
	}

	private void updateMaxScroll() {
		List<Mod> mods = Client.getInstance().modManager.getModsByCategory(selectedCategory);
		int contentAreaHeight = this.height - HEADER_HEIGHT - 10;
		int totalHeight = mods.size() * (CARD_HEIGHT + CARD_PADDING);
		this.maxScroll = Math.max(0, totalHeight - contentAreaHeight);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		// Draw sidebar background
		Gui.drawRect(0, 0, SIDEBAR_WIDTH, this.height, 0xE0101018);

		// Draw sidebar border
		Gui.drawRect(SIDEBAR_WIDTH, 0, SIDEBAR_WIDTH + 1, this.height, 0xFF2A2A3A);

		// Draw NexClient logo in sidebar header
		GlStateManager.pushMatrix();
		float logoScale = 1.5F;
		GlStateManager.scale(logoScale, logoScale, 1.0F);
		this.drawCenteredString(this.fontRendererObj, "\u00A7b\u00A7lNex\u00A7f\u00A7lClient",
				(int)(SIDEBAR_WIDTH / 2 / logoScale), (int)(12 / logoScale), 0xFFFFFFFF);
		GlStateManager.popMatrix();

		// Draw category buttons in sidebar
		Category[] categories = Category.values();
		for (int i = 0; i < categories.length; i++) {
			int catY = 36 + i * 28;
			boolean selected = categories[i] == selectedCategory;
			boolean hovered = mouseX >= 4 && mouseX <= SIDEBAR_WIDTH - 4
					&& mouseY >= catY && mouseY < catY + 24;

			// Category background
			if (selected) {
				Gui.drawRect(6, catY, SIDEBAR_WIDTH - 6, catY + 24, 0xFF1E90FF);
			} else if (hovered) {
				Gui.drawRect(6, catY, SIDEBAR_WIDTH - 6, catY + 24, 0x40FFFFFF);
			}

			// Rounded corners simulation
			if (selected || hovered) {
				int cornerColor = selected ? 0xFF1E90FF : 0x40FFFFFF;
				Gui.drawRect(8, catY, SIDEBAR_WIDTH - 8, catY + 24, cornerColor);
			}

			// Category icon + name
			String icon = CATEGORY_ICONS[i];
			String label = icon + " " + categories[i].getDisplayName();
			int textColor = selected ? 0xFFFFFFFF : 0xFFAAAAAA;
			this.fontRendererObj.drawStringWithShadow(label,
					14, catY + 8, textColor);
		}

		// Content area header
		int contentX = SIDEBAR_WIDTH + 16;
		int contentWidth = this.width - SIDEBAR_WIDTH - 32;

		// Title
		GlStateManager.pushMatrix();
		float titleScale = 1.3F;
		GlStateManager.scale(titleScale, titleScale, 1.0F);
		this.fontRendererObj.drawStringWithShadow(
				selectedCategory.getDisplayName() + " Mods",
				(int)(contentX / titleScale), (int)(10 / titleScale), 0xFFFFFFFF);
		GlStateManager.popMatrix();

		// Mod count
		List<Mod> mods = Client.getInstance().modManager.getModsByCategory(selectedCategory);
		this.fontRendererObj.drawStringWithShadow(
				mods.size() + " mods",
				contentX, 28, 0xFF888888);

		// Separator line
		Gui.drawRect(contentX, HEADER_HEIGHT - 2, contentX + contentWidth, HEADER_HEIGHT - 1, 0xFF2A2A3A);

		// Enable scissor to clip mod cards
		int scaleFactor = new net.minecraft.client.gui.ScaledResolution(this.mc).getScaleFactor();
		int clipTop = HEADER_HEIGHT;
		int clipBottom = this.height - 36;
		org.lwjgl.opengl.GL11.glEnable(org.lwjgl.opengl.GL11.GL_SCISSOR_TEST);
		org.lwjgl.opengl.GL11.glScissor(
				(SIDEBAR_WIDTH + 1) * scaleFactor,
				(this.height - clipBottom) * scaleFactor,
				(this.width - SIDEBAR_WIDTH - 1) * scaleFactor,
				(clipBottom - clipTop) * scaleFactor);

		// Draw mod cards
		for (int i = 0; i < mods.size(); i++) {
			Mod mod = mods.get(i);
			int cardY = HEADER_HEIGHT + 4 + i * (CARD_HEIGHT + CARD_PADDING) - scrollOffset;
			drawModCard(mod, contentX, cardY, contentWidth, mouseX, mouseY);
		}

		org.lwjgl.opengl.GL11.glDisable(org.lwjgl.opengl.GL11.GL_SCISSOR_TEST);

		// Draw scrollbar if needed
		if (maxScroll > 0) {
			int scrollBarX = this.width - 6;
			int trackHeight = clipBottom - clipTop;
			int barHeight = Math.max(20, trackHeight * trackHeight / (trackHeight + maxScroll));
			int barY = clipTop + (int)((float) scrollOffset / maxScroll * (trackHeight - barHeight));
			Gui.drawRect(scrollBarX, clipTop, scrollBarX + 4, clipBottom, 0x40FFFFFF);
			Gui.drawRect(scrollBarX, barY, scrollBarX + 4, barY + barHeight, 0xAA1E90FF);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawModCard(Mod mod, int x, int y, int maxWidth, int mouseX, int mouseY) {
		boolean hovered = mouseX >= x && mouseX <= x + maxWidth && mouseY >= y && mouseY < y + CARD_HEIGHT;

		// Card background
		int bgColor = hovered ? 0xD0202030 : 0xC0181822;
		Gui.drawRect(x, y, x + maxWidth, y + CARD_HEIGHT, bgColor);

		// Left accent bar (color-coded by category)
		int accentColor = getCategoryColor(mod.getCategory());
		Gui.drawRect(x, y, x + 3, y + CARD_HEIGHT, accentColor);

		// Mod icon area
		int iconX = x + 10;
		int iconY = y + (CARD_HEIGHT - 16) / 2;
		Gui.drawRect(iconX, iconY, iconX + 16, iconY + 16, 0x60FFFFFF);
		String initial = mod.getName().substring(0, 1).toUpperCase();
		this.fontRendererObj.drawStringWithShadow(initial,
				iconX + (16 - this.fontRendererObj.getStringWidth(initial)) / 2,
				iconY + 4, accentColor);

		// Mod name
		this.fontRendererObj.drawStringWithShadow(mod.getName(),
				iconX + 22, y + 10, 0xFFFFFFFF);

		// Mod description
		String desc = mod.getDescription();
		if (this.fontRendererObj.getStringWidth(desc) > maxWidth - 120) {
			desc = this.fontRendererObj.trimStringToWidth(desc, maxWidth - 126) + "...";
		}
		this.fontRendererObj.drawStringWithShadow(desc,
				iconX + 22, y + 22, 0xFF888888);

		// Draggable indicator
		if (mod.isDraggable()) {
			this.fontRendererObj.drawStringWithShadow("\u2725",
					iconX + 22 + this.fontRendererObj.getStringWidth(mod.getName()) + 4,
					y + 10, 0xFF555555);
		}

		// Toggle switch
		int toggleX = x + maxWidth - 38;
		int toggleY = y + (CARD_HEIGHT - 14) / 2;
		drawToggleSwitch(toggleX, toggleY, mod.isEnabled());
	}

	private void drawToggleSwitch(int x, int y, boolean on) {
		// Track
		int trackColor = on ? 0xFF1E90FF : 0xFF444444;
		Gui.drawRect(x, y, x + 32, y + 14, trackColor);

		// Track inner (darker for depth)
		Gui.drawRect(x + 1, y + 1, x + 31, y + 13, on ? 0xFF1678D0 : 0xFF333333);

		// Knob
		int knobX = on ? x + 18 : x + 2;
		Gui.drawRect(knobX, y + 2, knobX + 12, y + 12, 0xFFFFFFFF);

		// Knob inner highlight
		Gui.drawRect(knobX + 1, y + 3, knobX + 11, y + 11, on ? 0xFFE8E8E8 : 0xFFCCCCCC);
	}

	private int getCategoryColor(Category category) {
		switch (category) {
			case DISPLAY: return 0xFF1E90FF;
			case PLAYER: return 0xFF00CC66;
			case RENDER: return 0xFFFF6B35;
			case CHAT: return 0xFFFFD700;
			case HYPIXEL: return 0xFFFF4444;
			case OPTIMIZATION: return 0xFF9B59B6;
			default: return 0xFFFFFFFF;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton != 0) return;

		// Category clicks in sidebar
		Category[] categories = Category.values();
		for (int i = 0; i < categories.length; i++) {
			int catY = 36 + i * 28;
			if (mouseX >= 4 && mouseX <= SIDEBAR_WIDTH - 4
					&& mouseY >= catY && mouseY < catY + 24) {
				selectedCategory = categories[i];
				scrollOffset = 0;
				updateMaxScroll();
				return;
			}
		}

		// Mod card toggle clicks
		int contentX = SIDEBAR_WIDTH + 16;
		int contentWidth = this.width - SIDEBAR_WIDTH - 32;
		List<Mod> mods = Client.getInstance().modManager.getModsByCategory(selectedCategory);

		for (int i = 0; i < mods.size(); i++) {
			int cardY = HEADER_HEIGHT + 4 + i * (CARD_HEIGHT + CARD_PADDING) - scrollOffset;
			if (mouseX >= contentX && mouseX <= contentX + contentWidth
					&& mouseY >= cardY && mouseY < cardY + CARD_HEIGHT
					&& mouseY >= HEADER_HEIGHT && mouseY < this.height - 36) {
				mods.get(i).toggle();
				return;
			}
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int scroll = org.lwjgl.input.Mouse.getEventDWheel();
		if (scroll != 0) {
			scrollOffset -= scroll > 0 ? 20 : -20;
			scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));
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
		if (keyCode == 1) { // Escape
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}
}
