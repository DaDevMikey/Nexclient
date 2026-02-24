package com.nexclient.client;

import java.util.ArrayList;
import java.util.List;
import com.nexclient.client.mods.Mod;
import com.nexclient.client.mods.Mod.Category;

public class ModManager {
	
	private List<Mod> mods = new ArrayList<>();
	
	public ModManager() {
		// Display mods
		mods.add(new com.nexclient.client.mods.display.BwStatsOverlay());
		mods.add(new com.nexclient.client.mods.display.KeystrokeMod());
		mods.add(new com.nexclient.client.mods.display.ArmorHud());
		mods.add(new com.nexclient.client.mods.display.CoordinateMod());
		mods.add(new com.nexclient.client.mods.display.MemoryUsageMod());
		mods.add(new com.nexclient.client.mods.display.PotionHud());
		mods.add(new com.nexclient.client.mods.display.ReachInfoMod());
		mods.add(new com.nexclient.client.mods.display.SpeedometerMod());
		mods.add(new com.nexclient.client.mods.display.ScoreboardMod());
		mods.add(new com.nexclient.client.mods.display.TabListMod());
		
		// Player mods
		mods.add(new com.nexclient.client.mods.ToggleSprint());
		mods.add(new com.nexclient.client.mods.player.SprintMod());
		mods.add(new com.nexclient.client.mods.player.HitMod());
		mods.add(new com.nexclient.client.mods.player.ItemPhysicsMod());
		mods.add(new com.nexclient.client.mods.player.MinimViewbobMod());
		
		// Render mods
		mods.add(new com.nexclient.client.mods.FullBright());
		mods.add(new com.nexclient.client.mods.render.BlockOverlayMod());
		mods.add(new com.nexclient.client.mods.render.CrosshairMod());
		mods.add(new com.nexclient.client.mods.render.Animation17Mod());
		mods.add(new com.nexclient.client.mods.render.FreeLookMod());
		mods.add(new com.nexclient.client.mods.render.TimeChangerMod());
		mods.add(new com.nexclient.client.mods.render.MotionBlurMod());
		mods.add(new com.nexclient.client.mods.render.GlintColorMod());
		mods.add(new com.nexclient.client.mods.render.ThreeDSkinMod());
		
		// Chat mods
		mods.add(new com.nexclient.client.mods.chat.NickHiderMod());
		mods.add(new com.nexclient.client.mods.chat.AutoGGMod());
		mods.add(new com.nexclient.client.mods.chat.AutoGLHFMod());
		mods.add(new com.nexclient.client.mods.chat.AutoTextMod());
		mods.add(new com.nexclient.client.mods.chat.ChatMod());
		
		// Hypixel mods
		mods.add(new com.nexclient.client.mods.hypixel.HypixelHeightLimitMod());
		mods.add(new com.nexclient.client.mods.hypixel.QuickPlayMod());
		mods.add(new com.nexclient.client.mods.hypixel.AutoAddMod());
		mods.add(new com.nexclient.client.mods.hypixel.AutoFriendMod());
		
		// Optimization mods
		mods.add(new com.nexclient.client.mods.optimization.MultiCoreChunkLoadingMod());
		mods.add(new com.nexclient.client.mods.optimization.VulkanRenderingMod());
	}
	
	public List<Mod> getMods() {
		return mods;
	}
	
	public List<Mod> getModsByCategory(Category category) {
		List<Mod> result = new ArrayList<>();
		for (Mod mod : mods) {
			if (mod.getCategory() == category) {
				result.add(mod);
			}
		}
		return result;
	}
	
	public Mod getModByName(String name) {
		for (Mod mod : mods) {
			if (mod.getName().equalsIgnoreCase(name)) {
				return mod;
			}
		}
		return null;
	}
	
	public void update() {
		for (Mod mod : mods) {
			if (mod.isEnabled()) {
				mod.onUpdate();
			}
		}
	}
	
	public void renderHud() {
		for (Mod mod : mods) {
			if (mod.isEnabled()) {
				mod.onRender();
			}
		}
	}
}
