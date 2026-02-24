package com.nexclient.client.mods;

import net.minecraft.client.Minecraft;

public class Mod {
	
	public enum Category {
		DISPLAY("Display"),
		PLAYER("Player"),
		RENDER("Render"),
		CHAT("Chat"),
		HYPIXEL("Hypixel"),
		OPTIMIZATION("Optimization");
		
		private final String displayName;
		Category(String displayName) { this.displayName = displayName; }
		public String getDisplayName() { return displayName; }
	}
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	private String name;
	private String description;
	private boolean enabled = false;
	private Category category;
	
	// HUD positioning for draggable mods
	private int hudX = 2;
	private int hudY = 2;
	private boolean draggable = false;
	
	public Mod(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.enabled = false;
	}
	
	// Toggles the mod on and off
	public void toggle() {
		this.enabled = !this.enabled;
		if (this.enabled) {
			onEnable();
		} else {
			onDisable();
		}
	}
	
	// What happens when turned on
	public void onEnable() {
	}
	
	// What happens when turned off
	public void onDisable() {
	}
	
	// What happens every single tick (20 times a second)
	public void onUpdate() {
	}
	
	// Called to render HUD elements
	public void onRender() {
	}
	
	// Getters and Setters
	public String getName() { return name; }
	public String getDescription() { return description; }
	public Category getCategory() { return category; }
	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	public int getHudX() { return hudX; }
	public int getHudY() { return hudY; }
	public void setHudX(int x) { this.hudX = x; }
	public void setHudY(int y) { this.hudY = y; }
	public boolean isDraggable() { return draggable; }
	public void setDraggable(boolean draggable) { this.draggable = draggable; }
}
