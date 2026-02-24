package com.nexclient.client.gui;

import java.io.IOException;

import com.nexclient.client.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class NexMainMenu extends GuiScreen {

	private static final ResourceLocation[] PANORAMA_PATHS = new ResourceLocation[] {
		new ResourceLocation("textures/gui/title/background/panorama_0.png"),
		new ResourceLocation("textures/gui/title/background/panorama_1.png"),
		new ResourceLocation("textures/gui/title/background/panorama_2.png"),
		new ResourceLocation("textures/gui/title/background/panorama_3.png"),
		new ResourceLocation("textures/gui/title/background/panorama_4.png"),
		new ResourceLocation("textures/gui/title/background/panorama_5.png")
	};

	private DynamicTexture viewportTexture;
	private ResourceLocation backgroundTexture;
	private int panoramaTimer;
	private float animationProgress;

	// Button dimensions
	private static final int BUTTON_WIDTH = 180;
	private static final int BUTTON_HEIGHT = 24;
	private static final int BUTTON_GAP = 6;

	@Override
	public void initGui() {
		this.viewportTexture = new DynamicTexture(256, 256);
		this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("nex_bg", this.viewportTexture);
		this.animationProgress = 0.0F;

		int centerX = this.width / 2;
		int startY = this.height / 2 + 10;

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, centerX - BUTTON_WIDTH / 2, startY,
				BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("menu.singleplayer")));
		this.buttonList.add(new GuiButton(2, centerX - BUTTON_WIDTH / 2, startY + BUTTON_HEIGHT + BUTTON_GAP,
				BUTTON_WIDTH, BUTTON_HEIGHT, I18n.format("menu.multiplayer")));
		this.buttonList.add(new GuiButton(3, centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_GAP) * 2,
				BUTTON_WIDTH, BUTTON_HEIGHT, "Mods"));
		this.buttonList.add(new GuiButton(4, centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_GAP) * 3,
				BUTTON_WIDTH, BUTTON_HEIGHT, "HUD Editor"));
		this.buttonList.add(new GuiButton(0, centerX - BUTTON_WIDTH / 2, startY + (BUTTON_HEIGHT + BUTTON_GAP) * 4,
				(BUTTON_WIDTH - BUTTON_GAP) / 2, BUTTON_HEIGHT, I18n.format("menu.options")));
		this.buttonList.add(new GuiButton(5, centerX - BUTTON_WIDTH / 2 + (BUTTON_WIDTH + BUTTON_GAP) / 2,
				startY + (BUTTON_HEIGHT + BUTTON_GAP) * 4,
				(BUTTON_WIDTH - BUTTON_GAP) / 2, BUTTON_HEIGHT, I18n.format("menu.quit")));
	}

	@Override
	public void updateScreen() {
		++this.panoramaTimer;
		if (this.animationProgress < 1.0F) {
			this.animationProgress += 0.04F;
			if (this.animationProgress > 1.0F) this.animationProgress = 1.0F;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// Render panorama background
		GlStateManager.disableAlpha();
		renderSkybox(mouseX, mouseY, partialTicks);
		GlStateManager.enableAlpha();

		// Dark overlay
		Gui.drawRect(0, 0, this.width, this.height, 0xA0000000);

		// Gradient at top
		this.drawGradientRect(0, 0, this.width, 80, 0xC0000000, 0x00000000);
		// Gradient at bottom
		this.drawGradientRect(0, this.height - 40, this.width, this.height, 0x00000000, 0xC0000000);

		// Draw NexClient logo text with animation
		float logoAlpha = Math.min(1.0F, animationProgress * 2.0F);
		int logoAlphaInt = (int)(logoAlpha * 255);
		if (logoAlphaInt > 4) {
			GlStateManager.pushMatrix();
			float logoY = this.height / 2 - 50;
			float floatOffset = MathHelper.sin((this.panoramaTimer + partialTicks) / 20.0F) * 3.0F;
			GlStateManager.translate(this.width / 2, logoY + floatOffset, 0);

			// Large title
			float scale = 3.0F;
			GlStateManager.scale(scale, scale, 1.0F);
			this.drawCenteredString(this.fontRendererObj,
					"\u00A7b\u00A7lNex\u00A7f\u00A7lClient", 0, -10, 0xFFFFFFFF);
			GlStateManager.popMatrix();

			// Subtitle
			String subtitle = "v" + Client.getInstance().version;
			this.drawCenteredString(this.fontRendererObj, "\u00A77" + subtitle,
					this.width / 2, (int)(logoY + floatOffset + 22), 0xFFAAAAAA);
		}

		// Draw custom styled buttons with hover effects
		for (int i = 0; i < this.buttonList.size(); i++) {
			GuiButton button = (GuiButton) this.buttonList.get(i);
			drawNexButton(button, mouseX, mouseY);
		}

		// Version info
		this.drawString(this.fontRendererObj,
				"\u00A78NexClient " + Client.getInstance().version,
				4, this.height - 12, 0xFF555555);
		this.drawString(this.fontRendererObj,
				"\u00A78Minecraft 1.8.8",
				this.width - this.fontRendererObj.getStringWidth("Minecraft 1.8.8") - 4,
				this.height - 12, 0xFF555555);
	}

	private void drawNexButton(GuiButton button, int mouseX, int mouseY) {
		if (!button.visible) return;

		boolean hovered = mouseX >= button.xPosition && mouseY >= button.yPosition
				&& mouseX < button.xPosition + button.width && mouseY < button.yPosition + button.height;

		// Button background
		int bgColor = hovered ? 0xC01E90FF : 0xA0181822;
		Gui.drawRect(button.xPosition, button.yPosition,
				button.xPosition + button.width, button.yPosition + button.height, bgColor);

		// Bottom border accent
		int borderColor = hovered ? 0xFF1E90FF : 0xFF2A2A3A;
		Gui.drawRect(button.xPosition, button.yPosition + button.height - 2,
				button.xPosition + button.width, button.yPosition + button.height, borderColor);

		// Button text
		int textColor = hovered ? 0xFFFFFFFF : 0xFFCCCCCC;
		this.drawCenteredString(this.fontRendererObj, button.displayString,
				button.xPosition + button.width / 2,
				button.yPosition + (button.height - 8) / 2, textColor);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 0:
				this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
				break;
			case 1:
				this.mc.displayGuiScreen(new GuiSelectWorld(this));
				break;
			case 2:
				this.mc.displayGuiScreen(new GuiMultiplayer(this));
				break;
			case 3:
				this.mc.displayGuiScreen(new GuiModSelector(this));
				break;
			case 4:
				this.mc.displayGuiScreen(new GuiHudEditor(this));
				break;
			case 5:
				this.mc.shutdown();
				break;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	// Panorama rendering (adapted from vanilla GuiMainMenu)
	private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
		this.mc.getFramebuffer().unbindFramebuffer();
		GlStateManager.viewport(0, 0, 256, 256);
		drawPanorama(partialTicks);
		rotateAndBlurSkybox(partialTicks);
		rotateAndBlurSkybox(partialTicks);
		rotateAndBlurSkybox(partialTicks);
		rotateAndBlurSkybox(partialTicks);
		this.mc.getFramebuffer().bindFramebuffer(true);
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);

		float f = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
		float f1 = (float)this.height * f / 256.0F;
		float f2 = (float)this.width * f / 256.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(0.0D, this.height, this.zLevel).tex(0.5F - f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos(this.width, this.height, this.zLevel).tex(0.5F - f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos(this.width, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F - f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.5F + f1, 0.5F + f2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		tessellator.draw();
	}

	private void drawPanorama(float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

		for (int j = 0; j < 64; ++j) {
			GlStateManager.pushMatrix();
			float f = ((float)(j % 8) / 8.0F - 0.5F) / 64.0F;
			float f1 = ((float)(j / 8) / 8.0F - 0.5F) / 64.0F;
			GlStateManager.translate(f, f1, 0.0F);
			GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + partialTicks) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-(this.panoramaTimer + partialTicks) * 0.1F, 0.0F, 1.0F, 0.0F);

			for (int k = 0; k < 6; ++k) {
				GlStateManager.pushMatrix();
				if (k == 1) GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				if (k == 2) GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				if (k == 3) GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				if (k == 4) GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				if (k == 5) GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);

				this.mc.getTextureManager().bindTexture(PANORAMA_PATHS[k]);
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int l = 255 / (j + 1);
				worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
				worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
				worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
				worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
				tessellator.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}

		worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(5889);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	private void rotateAndBlurSkybox(float partialTicks) {
		this.mc.getTextureManager().bindTexture(this.backgroundTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.disableAlpha();

		for (int j = 0; j < 3; ++j) {
			float f = 1.0F / (float)(j + 1);
			float f1 = (float)(j - 1) / 256.0F;
			worldrenderer.pos(this.width, this.height, this.zLevel).tex(0.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos(this.width, 0.0D, this.zLevel).tex(1.0F + f1, 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(1.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
			worldrenderer.pos(0.0D, this.height, this.zLevel).tex(0.0F + f1, 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
		}

		tessellator.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}
}
