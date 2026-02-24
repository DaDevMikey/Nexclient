package com.tornhost.tornclient.managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class CosmeticManager {
	
	// Stores the UUIDs and their assigned cape names
	private HashMap<String, String> playerCapes = new HashMap<>();
	private HashMap<String, net.minecraft.util.ResourceLocation> downloadedTextures = new HashMap<>();
	
	// API URL
	private final String API_URL = "http://127.0.0.1:8000/cosmetics/";
	
	public CosmeticManager() {
		// Constructor
	}
	
	// Call this when a player renders on screen to check if they have a cape
	public void fetchCosmetics(String uuid) {
		
		// If we already checked this player, don't spam the API again!
		if (playerCapes.containsKey(uuid)) return;
		
		new Thread(() -> {
			try {
				URL url = new URL(API_URL + uuid);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(5000); // 5 second timeout
				
				int responseCode = connection.getResponseCode();
				
				if (responseCode == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String response = in.readLine(); // Let's assume the API just returns "dev_cape" or "none"
					in.close();
					
					if (response != null && !response.equals("none")) {
						// Save it to our map!
						playerCapes.put(uuid, response);
						System.out.println("Fetched cape for " + uuid + ": " + response);
					} else {
						playerCapes.put(uuid, "none"); // Mark as checked but empty
					}
				}
			} catch (Exception e) {
				System.out.println("Failed to fetch cosmetics for " + uuid);
				playerCapes.put(uuid, "none"); // Prevent retrying repeatedly on failure
			}
		}).start();
	}
	
	public String getCape(String uuid) {
		return playerCapes.getOrDefault(uuid, "none");
	}
	
	public net.minecraft.util.ResourceLocation getCapeTexture(String capeName) {
		if (capeName == null || capeName.equals("none")) {
			return null;
		}
		
		if (downloadedTextures.containsKey(capeName)) {
			return downloadedTextures.get(capeName);
		}
		
		System.out.println("Downloading new cape texture: " + capeName);
		
		net.minecraft.util.ResourceLocation location = new net.minecraft.util.ResourceLocation("tornclient_capes", capeName);
		
		String url = "http://127.0.0.1:8000/capes/" + capeName + ".png";
		
		net.minecraft.client.renderer.ThreadDownloadImageData downloader = 
	            new net.minecraft.client.renderer.ThreadDownloadImageData(null, url, null, null);
		
		net.minecraft.client.Minecraft.getMinecraft().getTextureManager().loadTexture(location, downloader);
		
		downloadedTextures.put(capeName, location);
		
		return location;
	}
}
