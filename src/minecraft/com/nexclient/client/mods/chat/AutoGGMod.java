package com.nexclient.client.mods.chat;

import com.nexclient.client.mods.Mod;

public class AutoGGMod extends Mod {

	private String message = "gg";

	public AutoGGMod() {
		super("AutoGG", "Automatically sends gg at the end of a game", Category.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
