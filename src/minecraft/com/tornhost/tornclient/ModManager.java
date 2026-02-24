package com.tornhost.tornclient;

import java.util.ArrayList;
import java.util.List;
import com.tornhost.tornclient.mods.Mod;
import com.tornhost.tornclient.mods.Mod.Category;

public class ModManager {
	
	private List<Mod> mods = new ArrayList<>();
	
	public ModManager() {
		// Display mods
		mods.add(new com.tornhost.tornclient.mods.display.BwStatsOverlay());
		mods.add(new com.tornhost.tornclient.mods.display.KeystrokeMod());
		mods.add(new com.tornhost.tornclient.mods.display.ArmorHud());
		mods.add(new com.tornhost.tornclient.mods.display.CoordinateMod());
		mods.add(new com.tornhost.tornclient.mods.display.MemoryUsageMod());
		mods.add(new com.tornhost.tornclient.mods.display.PotionHud());
		mods.add(new com.tornhost.tornclient.mods.display.ReachInfoMod());
		mods.add(new com.tornhost.tornclient.mods.display.SpeedometerMod());
		mods.add(new com.tornhost.tornclient.mods.display.ScoreboardMod());
		mods.add(new com.tornhost.tornclient.mods.display.TabListMod());
		
		// Player mods
		mods.add(new com.tornhost.tornclient.mods.ToggleSprint());
		mods.add(new com.tornhost.tornclient.mods.player.SprintMod());
		mods.add(new com.tornhost.tornclient.mods.player.HitMod());
		mods.add(new com.tornhost.tornclient.mods.player.ItemPhysicsMod());
		mods.add(new com.tornhost.tornclient.mods.player.MinimViewbobMod());
		
		// Render mods
		mods.add(new com.tornhost.tornclient.mods.FullBright());
		mods.add(new com.tornhost.tornclient.mods.render.BlockOverlayMod());
		mods.add(new com.tornhost.tornclient.mods.render.CrosshairMod());
		mods.add(new com.tornhost.tornclient.mods.render.Animation17Mod());
		mods.add(new com.tornhost.tornclient.mods.render.FreeLookMod());
		mods.add(new com.tornhost.tornclient.mods.render.TimeChangerMod());
		mods.add(new com.tornhost.tornclient.mods.render.MotionBlurMod());
		mods.add(new com.tornhost.tornclient.mods.render.GlintColorMod());
		mods.add(new com.tornhost.tornclient.mods.render.ThreeDSkinMod());
		
		// Chat mods
		mods.add(new com.tornhost.tornclient.mods.chat.NickHiderMod());
		mods.add(new com.tornhost.tornclient.mods.chat.AutoGGMod());
		mods.add(new com.tornhost.tornclient.mods.chat.AutoGLHFMod());
		mods.add(new com.tornhost.tornclient.mods.chat.AutoTextMod());
		mods.add(new com.tornhost.tornclient.mods.chat.ChatMod());
		
		// Hypixel mods
		mods.add(new com.tornhost.tornclient.mods.hypixel.HypixelHeightLimitMod());
		mods.add(new com.tornhost.tornclient.mods.hypixel.QuickPlayMod());
		mods.add(new com.tornhost.tornclient.mods.hypixel.AutoAddMod());
		mods.add(new com.tornhost.tornclient.mods.hypixel.AutoFriendMod());
		
		// Optimization mods
		mods.add(new com.tornhost.tornclient.mods.optimization.MultiCoreChunkLoadingMod());
		mods.add(new com.tornhost.tornclient.mods.optimization.VulkanRenderingMod());
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
