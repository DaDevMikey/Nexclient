package com.tornhost.tornclient.ui;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import com.tornhost.tornclient.Client;
import com.tornhost.tornclient.mods.Mod;

public class ClickGUI extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Darken the game background (the blur effect)
        this.drawDefaultBackground();
        
        int windowWidth = 400;
        int windowHeight = 250;
        // This math perfectly centers the window on any screen size
        int startX = (this.width - windowWidth) / 2;
        int startY = (this.height - windowHeight) / 2;
        
        // Main Dashboard Background (Dark Gray)
        drawRect(startX, startY, startX + windowWidth, startY + windowHeight, 0xFF1E1E1E);
        
        // Sidebar Background (Slightly Darker Gray)
        drawRect(startX, startY, startX + 100, startY + windowHeight, 0xFF121212);
        
        this.drawString(this.fontRendererObj, "MODS", startX + 15, startY + 20, 0xFFFFFF); // White = Active
        this.drawString(this.fontRendererObj, "COSMETICS", startX + 15, startY + 40, 0x777777); // Gray = Inactive placeholder
        this.drawString(this.fontRendererObj, "SETTINGS", startX + 15, startY + 60, 0x777777);
        
        // Client Name at the bottom of the sidebar
        this.drawString(this.fontRendererObj, Client.getInstance().name, startX + 15, startY + windowHeight - 20, 0x555555);

        int modX = startX + 120; // Start drawing to the right of the sidebar
        int modY = startY + 20;  // Start near the top
        
        for (Mod mod : Client.getInstance().modManager.getMods()) {
            
            // Draw a sleek "Card" background for the mod
            drawRect(modX, modY, modX + 260, modY + 30, 0xFF2A2A2A); // Lighter Gray
            
            // Draw the Mod Name on the left side of the card
            this.drawString(this.fontRendererObj, mod.getName(), modX + 10, modY + 11, 0xFFFFFF);
            
            // Draw a modern Toggle Switch on the right side of the card
            // Cyan/Blue if ON, dark gray if OFF
            int toggleColor = mod.isEnabled() ? 0xFF00AAFF : 0xFF444444; 
            drawRect(modX + 220, modY + 8, modX + 250, modY + 22, toggleColor);
            
            modY += 35; // Space out the cards vertically
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // Only trigger on Left-Click
        if (mouseButton == 0) {
            
            // We need the exact same math to find where the buttons are
            int windowWidth = 400;
            int windowHeight = 250;
            int startX = (this.width - windowWidth) / 2;
            int startY = (this.height - windowHeight) / 2;
            
            int modX = startX + 120;
            int modY = startY + 20;
            
            for (Mod mod : Client.getInstance().modManager.getMods()) {
                
                // Hitbox Math: Did they click directly on the Toggle Switch?
                if (mouseX >= modX + 220 && mouseX <= modX + 250 && mouseY >= modY + 8 && mouseY <= modY + 22) {
                    mod.toggle();
                    // Play a vanilla click sound!
                    this.mc.thePlayer.playSound("random.click", 1.0F, 1.0F); 
                }
                modY += 35;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    // This allows us to press Right Shift again (or Escape) to easily close the menu
    @Override
    public boolean doesGuiPauseGame() {
        return false; 
    }
}