package com.tornhost.tornclient.mods;

import net.minecraft.client.Minecraft;

public class FPS extends HudMod {

    public FPS() {
        // Name, Description, Default X (5), Default Y (5)
        super("FPS Display", "Shows your frames per second.", 5, 5);
        this.setEnabled(true); // Let's leave it on by default so we can see it
    }

    @Override
    public void draw() {
        // Only draw if the mod is turned ON
        if (this.isEnabled()) {
            String text = "FPS: " + Minecraft.getDebugFPS();
            
            // Draw our custom background (width based on text length, height 12)
            drawBackground(mc.fontRendererObj.getStringWidth(text) + 4, 12);
            
            // Draw the text itself at our dynamic X and Y coordinates
            mc.fontRendererObj.drawStringWithShadow(text, x + 2, y + 2, 0xFFFFFF);
        }
    }
}