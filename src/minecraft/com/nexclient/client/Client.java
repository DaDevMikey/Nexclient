package com.nexclient.client;

public class Client {
	private static final Client INSTANCE = new Client();
	
	public static final Client getInstance() {
		return INSTANCE;
	}
	
	public final String name = "NexClient";
	public final String version = "1.0.0";
	public final String author = "NexTeam";
	
	public ModManager modManager;
	
	public void startup() {
		System.out.println("=======================================");
        System.out.println("Starting " + name + " v" + version + " by " + author);
        System.out.println("=======================================");
        
        modManager = new ModManager();
	}
}
