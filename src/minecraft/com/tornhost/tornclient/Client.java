package com.tornhost.tornclient;

import com.tornhost.tornclient.managers.CosmeticManager;
import com.tornhost.tornclient.managers.ModManager;

public class Client {
	private static final Client INSTANCE = new Client();
	
	public static final Client getInstance() {
		return INSTANCE;
	}
	
	// Client info
	public final String name = "TornClient";
	public final String version = "1.0.0";
	public final String author = "atticl";
	
	public ModManager modManager;
	public CosmeticManager cosmeticManager;
	
	// Run when game starts
	public void startup() {
		System.out.println("=======================================");
        System.out.println("Starting " + name + " v" + version + " by " + author);
        System.out.println("=======================================");
        
        modManager = new ModManager();
        cosmeticManager = new CosmeticManager();
	}
}
