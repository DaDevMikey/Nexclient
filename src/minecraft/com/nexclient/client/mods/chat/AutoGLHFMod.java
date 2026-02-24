package com.nexclient.client.mods.chat;

import com.nexclient.client.mods.Mod;

public class AutoGLHFMod extends Mod {

	private String message = "glhf";

	public AutoGLHFMod() {
		super("AutoGLHF", "Automatically sends glhf at the start of a game", Category.CHAT);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
