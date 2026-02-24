package com.tornhost.tornclient.mods;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Zoom extends Mod {
	
	public static boolean isZooming = false;
	public static float zoomLevel = 4.0F; // Default to 4x zoom
	
	public Zoom() {
		super("Zoom", "Hold C to zoom. Scroll to adjust.");
		this.setEnabled(true); // Always on
	}
	
	@Override
	public void onUpdate() {
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			isZooming = true;
			mc.gameSettings.smoothCamera = true;
			
			int scroll = Mouse.getDWheel();
			if (scroll < 0) {
				zoomLevel -= 1.0F; // Scroll down = zoom out
			} else if (scroll > 0) {
				zoomLevel += 1.0F; // Scroll up = zoom in
			}
			
			// Lock the zoom so they can't zoom out past noraml or zoom in too far
			if (zoomLevel < 1.0F) zoomLevel = 1.0F;
			if (zoomLevel > 15.0F) zoomLevel = 15.0F;
		} else {
			// If they let go of 'C' reset everything
			if (isZooming) {
				isZooming = false;
				mc.gameSettings.smoothCamera = false;
				zoomLevel = 4.0F; // Reset back to default 4x
			}
		}
	}
}
